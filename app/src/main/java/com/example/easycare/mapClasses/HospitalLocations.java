package com.example.easycare.mapClasses;

import com.google.android.gms.maps.model.LatLng;

public class HospitalLocations {


    LatLng hospital;
    String name;
    float distance;

    public LatLng getHospital() {
        return hospital;
    }

    public HospitalLocations(LatLng hospital, String name) {
        this.hospital = hospital;
        this.name = name;
    }

    public void setHospital(LatLng hospital) {
        this.hospital = hospital;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public HospitalLocations(LatLng hospital, String name, float distance) {
        this.hospital = hospital;
        this.name = name;
        this.distance = distance;
    }
}
