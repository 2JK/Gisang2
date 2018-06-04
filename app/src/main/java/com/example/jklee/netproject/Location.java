package com.example.jklee.netproject;

public class Location {

    private double latitude;
    private double longitude;
    private String address;
    private GpsInfo gps;

    public Location() {
        latitude = 0;
        longitude = 0;
        address = "";
    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = "";
    }

    public Location(double latitude, double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public void setLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getAddress(){
        return this.address;
    }

}