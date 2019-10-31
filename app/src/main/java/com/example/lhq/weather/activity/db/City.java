package com.example.lhq.weather.activity.db;

import org.litepal.crud.LitePalSupport;

public class City extends LitePalSupport{
    private int id;

    private String cityName;

    private String cityCode;

    private String weatherInfo;

    public City(){

    }

    public City(String cityName){
        this.cityName = cityName;
    }

    public City(String cityName, String cityCode) {
        this.cityName = cityName;
        this.cityCode = cityCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(String weatherInfo) {
        this.weatherInfo = weatherInfo;
    }
}
