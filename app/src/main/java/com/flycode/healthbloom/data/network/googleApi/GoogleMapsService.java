package com.flycode.healthbloom.data.network.googleApi;

import com.flycode.healthbloom.data.models.GoogleMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapsService {

    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
     */
    @GET("api/directions/json?key=AIzaSyDeDrdQbKgU6JE-_x0i5QecVWBNp6FYUWc")
    Observable<GoogleMap.Routes> getDistanceAndDuration(
            @Query("units") String units,
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("mode") String mode
    );

}
