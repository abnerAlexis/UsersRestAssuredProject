package com.test;

public class Geo {
    String lat;
    String lng;

    public Geo() {}

    public Geo(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}

/*
    "geo": {
            "lat": "-37.3159",
            "lng": "81.1496"
        }
 */
