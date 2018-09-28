package com.codingblocks.suraksha.Models;

public class TripResponse {
    String travelTime, travelDelay;

    public TripResponse(String travelTime, String travelDelay) {
        this.travelTime = travelTime;
        this.travelDelay = travelDelay;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public String getTravelDelay() {
        return travelDelay;
    }
}
