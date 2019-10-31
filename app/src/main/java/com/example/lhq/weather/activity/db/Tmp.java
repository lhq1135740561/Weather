package com.example.lhq.weather.activity.db;

public class Tmp {
    private String tmp;
    private int imageId;

    public Tmp(String tmp, int imageId) {
        this.tmp = tmp;
        this.imageId = imageId;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
