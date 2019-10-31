package com.example.lhq.weather.activity.data;

import org.litepal.crud.LitePalSupport;

public class CityLitepal extends LitePalSupport{

    private String cityName;

    public CityLitepal() {
    }

    public CityLitepal(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
