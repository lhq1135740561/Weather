package com.example.lhq.weather.activity.entity;

import com.google.gson.annotations.SerializedName;


public class AQI {

    @SerializedName("city")
    public AQICity city;

    public class AQICity{
        public String aqi; //28

        public String qlty; //优

        public String pm25; //15
    }
}
