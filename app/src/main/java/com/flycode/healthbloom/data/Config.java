package com.flycode.healthbloom.data;

import android.content.Context;

public class Config {
    private Context context;

    public Config(Context context) {
        this.context = context;
    }

//    public final String GoogleAPIKey = context.getResources().getString(R.string.google_maps_key);
    public final String GOOGLE_MAPS_URL = "https://maps.googleapis.com/maps/";
}
