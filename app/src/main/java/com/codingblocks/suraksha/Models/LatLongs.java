package com.codingblocks.suraksha.Models;

public class LatLongs {
    String location, latitude, longitude;

    public LatLongs(String location, String latitude, String longitude) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
