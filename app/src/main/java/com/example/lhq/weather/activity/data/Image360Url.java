package com.example.lhq.weather.activity.data;

import org.litepal.crud.LitePalSupport;

public class Image360Url extends LitePalSupport {

    private String imageUrl;

    public Image360Url() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
