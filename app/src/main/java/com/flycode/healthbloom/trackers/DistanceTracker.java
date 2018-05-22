package com.flycode.healthbloom.trackers;

import android.content.Context;

import com.flycode.healthbloom.data.models.GoogleMap;
import com.flycode.healthbloom.data.network.googleApi.GoogleMapsService;
import com.flycode.healthbloom.ui.base.BaseService;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lombok.Setter;

public class DistanceTracker implements TrackerComponent{
    @Setter
    private LatLng origin;
    private LatLng dest;
    private BaseService context;
    private GoogleMapsService googleMapsService;
    private DistanceChangedListener distanceChangedListener;


    public DistanceTracker(Context context,GoogleMapsService googleMapsService) {
        this.googleMapsService = googleMapsService;
        this.context = (BaseService) context;
        this.distanceChangedListener = (DistanceChangedListener) context;
    }

    public void getDistance(LatLng dest){
        this.dest = dest;
        getDistance();
    }

    private void getDistance(){
        context.getCompositeDisposable().add(googleMapsService.getDistanceAndDuration(
                "metric",
                origin.latitude + "," + origin.longitude,
                dest.latitude + "," + dest.longitude,
                "walking"
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<GoogleMap.Routes>() {
                @Override
                public void accept(GoogleMap.Routes routes) throws Exception {
                    for (int i = 0; i < routes.getRoutes().size(); i++) {
                        int distance = routes.getRoutes().get(i).getLegs().get(i).getDistance().getValue();
                        String encodedString = routes.getRoutes().get(0).getOverviewPolyline().getPoints();
                        List<LatLng> polyline = decodePoly(encodedString);
                        if (distanceChangedListener != null)
                            distanceChangedListener.onDistanceChanged(distance,polyline);
                    }
                }
            },new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    context.sendError(throwable.getLocalizedMessage());
                }
            })
        );
    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }

    public void onLocationChange(LatLng newLocation){
        this.dest = newLocation;
        getDistance();
    }

    @Override
    public String getName() {
        return "DistanceTracker";
    }

    @Override
    public ResultCode onInit() {
        return ResultCode.RESULT_OK;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onPlay() {

    }

    @Override
    public ResultCode onFinish() {
        return ResultCode.RESULT_OK;

    }

    public interface DistanceChangedListener{
        void onDistanceChanged(int Distance,List<LatLng> polyline);
    }
}
