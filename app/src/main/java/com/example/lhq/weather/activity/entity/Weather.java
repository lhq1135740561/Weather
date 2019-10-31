package com.example.lhq.weather.activity.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    public String status; //是否获取信息成功

    public Basic basic;

    public AQI aqi;

    public Now now;

    public Suggestion suggestion;
    //未来三天的天气状况
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;

    @SerializedName("hourly_forecast")
    public List<Hourly> hourlyList;
}
