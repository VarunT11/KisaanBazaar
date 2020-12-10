package com.example.organicfarming;

public class FarmerCrop {
    private String name;
    private String cropID;
    private String image;
    private int views;

    public FarmerCrop(String name, String image, int views){
        this.name=name;
        this.views=views;
        this.image=image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getCropID() {
        return cropID;
    }

    public void setCropID(String cropID) {
        this.cropID = cropID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
