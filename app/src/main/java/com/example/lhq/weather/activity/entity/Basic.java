package com.example.lhq.weather.activity.entity;

import com.google.gson.annotations.SerializedName;

//天气基本信息类
public class Basic {
    @SerializedName("city")  //九江
    public String cityName;

    @SerializedName("id")  //CN101240201
    public String weatherId;

    public Update update;

    public class Update{
        @SerializedName("loc")    //更新时间
        public String updateTime;
    }
}
