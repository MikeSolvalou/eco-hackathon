package com.example.godinemich.green_yelp;

/**
 * Created by gelboljohn on 23/02/2017.
 */

public class Restaurant {

    private String name;
    private String address;
    private String description;
    private double latitude;
    private double longitude;
    private double distance;

    public Restaurant(String name, String address, String description, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getDistance() {return distance;}
    public void setDistance(double d) {distance = d;}
}
