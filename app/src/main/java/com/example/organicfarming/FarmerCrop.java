package com.example.organicfarming;

public class FarmerCrop {
    private String name;
    private int views;

    public FarmerCrop(String name, int views){
        this.name=name;
        this.views=views;
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
}
