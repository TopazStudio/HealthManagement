package com.flycode.healthbloom.trackers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.flycode.healthbloom.trackers.filters.GpsStatus;

import java.util.List;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

public class GpsTracker implements TrackerComponent {

    private static final String TAG = GpsTracker.class.getSimpleName();
    private Context context;
    private LocationListener listener;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;

    private GpsStatus mGpsStatus;
    private Callback mConnectCallback;

    private LocationManager locationManager;

    public GpsTracker(Context context) {
        this.context = context;
        this.listener = (LocationListener) context;
    }

    public Location getLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return this.getLastKnownLocation();
        }
        return null;
    }

    @SuppressLint("MissingPermission")
    private Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            Log.d(TAG,String.format("last known location, provider: %s, location: %s", provider,
                    l));

            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {
                Log.d(TAG,String.format("found best last known location: %s", l));
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }

    /*############################### TRACKER_COMPONENT ################################*/


    @Override
    public String getName() {
        return "GpsTracker";
    }

    @Override
    public ResultCode onInit() {
        try {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager == null) {
                return ResultCode.RESULT_NOT_SUPPORTED;
            }

            isGPSEnabled = locationManager.isProviderEnabled(GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                return ResultCode.RESULT_NOT_SUPPORTED;
            }
        } catch (Exception ex) {
            return ResultCode.RESULT_ERROR;
        }

        return ResultCode.RESULT_OK;
    }

    @Override
    public void onPause() {
        locationManager.removeUpdates(listener);
    }

    @Override
    public void onPlay() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //ON GPS
            if (isGPSEnabled) {
                locationManager.requestLocationUpdates(
                        GPS_PROVIDER,
                        10000,
                        10,
                        listener
                );
            }else if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(
                        NETWORK_PROVIDER,
                        10000,
                        10,
                        listener
                );
            }
        }
    }

    @Override
    public ResultCode onFinish() {
        return ResultCode.RESULT_OK;
    }
}
