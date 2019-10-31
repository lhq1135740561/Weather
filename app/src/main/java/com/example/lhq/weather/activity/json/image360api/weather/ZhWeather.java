package com.example.lhq.weather.activity.json.image360api.weather;

import com.google.gson.annotations.SerializedName;

public class ZhWeather {

    //省
    @SerializedName("areaInfo")
    public Provice provice;

    public WeatherInfo weatherInfo;

    public String code;
}
