package com.example.lhq.weather.activity.json.image360api.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherInfo {

    //未来每小时的天气状况
    @SerializedName("h3")
    public List<H3> h3List;

}
