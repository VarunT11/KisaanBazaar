package com.example.organicfarming;

public class FarmerItem {
    public String name;
    public float latitude;
    public float longitude;
    public String uid;
    public float rating;
    public int time;
    public int rate;
    public String address;
    public String photoUrl;

    public FarmerItem(String name,float latitude,float longitude, String uid, float rating , int time, int rate, String address,String photoUrl){
        this.name=name;
        this.latitude=latitude;
        this.longitude=longitude;
        this.uid=uid;
        this.rating=rating;
        this.time=time;
        this.rate=rate;
        this.address=address;
        this.photoUrl=photoUrl;
    }


    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getRating() {
        return rating;
    }

    public int getRate() {
        return rate;
    }

    public int getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

}

