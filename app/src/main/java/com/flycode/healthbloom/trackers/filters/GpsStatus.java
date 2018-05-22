package com.flycode.healthbloom.trackers.filters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

/**
 *
 * This is a helper class that is used to determine when the GPS status is good
 * enough (isFixed())
 *
 */
public class GpsStatus implements LocationListener,
        android.location.GpsStatus.Listener {

    private static final int HIST_LEN = 3;

    private boolean mIsFixed = false;
    private Context context = null;
    private Location mHistory[] = null;
    private LocationManager locationManager = null;
    private TickListener listener = null;

    /**
     * If we get a location with accurancy <= mFixAccurancy mFixed => true
     */
    private final float mFixAccurancy = 10;

    /**
     * If we get fixed satellites >= mFixSatellites mFixed => true
     */
    private final int mFixSatellites = 2;

    /**
     * If we get location updates with time difference <= mFixTime mFixed =>
     * true
     */
    private final int mFixTime = 3;

    private int mKnownSatellites = 0;
    private int mUsedInLastFixSatellites = 0;

    public GpsStatus(Context ctx) {
        this.context = ctx;
        mHistory = new Location[HIST_LEN];
    }

    public void start(TickListener listener) {
        clear(true);
        this.listener = listener;
        if (ContextCompat.checkSelfPermission(this.context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            try {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            } catch (Exception ex) {
                lm = null;
            }
            if (lm != null) {
                locationManager = lm;
                locationManager.addGpsStatusListener(this);
            }
        }
    }

    /**
     * Release the resources and listener.
    **/
    public void stop(TickListener listener) {
        this.listener = null;
        if (locationManager != null) {
            locationManager.removeGpsStatusListener(this);

            try {
                locationManager.removeUpdates(this);
            } catch (SecurityException ex) {
                //Ignore if user turn off GPS
            }
        }
        locationManager = null;
    }

    @Override
    public void onLocationChanged(Location location) {
        //add new location to the front
        System.arraycopy(mHistory, 0, mHistory, 1, HIST_LEN - 1);
        mHistory[0] = location;

        //The lesser the accuracy the better
        if (location.hasAccuracy() && location.getAccuracy() < mFixAccurancy) {
            mIsFixed = true;

            //The lesser the update time the better
        } else if (mHistory[1] != null
                && (location.getTime() - mHistory[1].getTime()) <= (1000 * mFixTime)) {
            mIsFixed = true;

            //The more the satellites the better
        } else if (mKnownSatellites >= mFixSatellites) {
            mIsFixed = true;
        }
        if (listener != null)
            listener.onTick();
    }

    /**
     * When the provider is disabled clear
     * */
    @Override
    public void onProviderDisabled(String provider) {
        if (provider.equalsIgnoreCase("gps")) {
            clear(true);
            if (listener != null)
                listener.onTick();
        }
    }

    /**
    * When the provider is operational don't clear
     * */
    @Override
    public void onProviderEnabled(String provider) {
        if (provider.equalsIgnoreCase("gps")) {
            clear(false);
            if (listener != null)
                listener.onTick();
        }
    }

    /**
     * When the provider is out of service or unavailable clear the check
     *
     * */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        if (provider.equalsIgnoreCase("gps")) {
            if (status == LocationProvider.OUT_OF_SERVICE
                    || status == LocationProvider.TEMPORARILY_UNAVAILABLE) {
                clear(true);
            }
            if (listener != null)
                listener.onTick();
        }
    }

    /**
     * Count the number of satellites available and the number of satellites that
     * where used in the last GPS fix.
     *
     * */
    @Override
    public void onGpsStatusChanged(int event) {
        if (locationManager == null)
            return;

        //noinspection MissingPermission
        @SuppressLint("MissingPermission") android.location.GpsStatus gpsStatus = locationManager.getGpsStatus(null);

        if (gpsStatus == null)
            return;

        int cnt0 = 0, cnt1 = 0;
        Iterable<GpsSatellite> list = gpsStatus.getSatellites();
        for (GpsSatellite satellite : list) {
            cnt0++;
            if (satellite.usedInFix()) {
                cnt1++;
            }
        }
        mKnownSatellites = cnt0;
        mUsedInLastFixSatellites = cnt1;
        if (listener != null)
            listener.onTick();
    }

    /**
     * Clear all satellites counted and the the history
     * */
    private void clear(boolean resetIsFixed) {
        if (resetIsFixed) {
            mIsFixed = false;
        }
        mKnownSatellites = 0;
        mUsedInLastFixSatellites = 0;
        for (int i = 0; i < HIST_LEN; i++)
            mHistory[i] = null;
    }

    public boolean isLogging() {
        return locationManager != null;
    }

    public boolean isFixed() {
        return mIsFixed;
    }

    public int getSatellitesAvailable() {
        return mKnownSatellites;
    }

    public int getSatellitesFixed() {
        return mUsedInLastFixSatellites;
    }

    public boolean isEnabled() {
        LocationManager lm = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public interface TickListener {

        public void onTick();
    }
}


