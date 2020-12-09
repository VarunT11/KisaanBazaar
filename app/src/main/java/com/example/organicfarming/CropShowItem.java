package com.example.organicfarming;

public class CropShowItem {

    public String cropName,cropUrl;
    public int cropSellers,cropDistance;

    public CropShowItem(String cropName, String cropUrl, int cropSellers, int cropDistance){
        this.cropName=cropName;
        this.cropUrl=cropUrl;
        this.cropSellers=cropSellers;
        this.cropDistance=cropDistance;
    }

    public int getCropDistance() {
        return cropDistance;
    }

    public int getCropSellers() {
        return cropSellers;
    }

    public String getCropName() {
        return cropName;
    }

    public String getCropUrl() {
        return cropUrl;
    }
}
