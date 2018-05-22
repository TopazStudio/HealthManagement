package com.flycode.healthbloom.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

public class GoogleMap {

    @Data
    public class Duration{
        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("value")
        @Expose
        private Integer value;
    }

    @Data
    public class Distance{
        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("value")
        @Expose
        private Integer value;
    }

    @Data
    public class Leg{
        @SerializedName("distance")
        @Expose
        private Distance distance;
        @SerializedName("duration")
        @Expose
        private Duration duration;
    }

    @Data
    public class OverviewPolyline{
        @SerializedName("points")
        @Expose
        private String points;
    }

    @Data
    public class Route{
        @SerializedName("legs")
        @Expose
        private List<Leg> legs = new ArrayList<Leg>();
        @SerializedName("overview_polyline")
        @Expose
        private OverviewPolyline overviewPolyline;
    }

    @Data
    public class Routes{
        @SerializedName("routes")
        @Expose
        private List<Route> routes = new ArrayList<>();
    }
}
