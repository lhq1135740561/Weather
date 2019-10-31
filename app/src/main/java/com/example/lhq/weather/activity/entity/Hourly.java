package com.example.lhq.weather.activity.entity;

import com.google.gson.annotations.SerializedName;

public class Hourly {

    public String date; //2018-07-21 19:00

    public String hum;  //相对湿度84

    public String tmp; //温度30

    public Cond cond;


    public class Cond{
        @SerializedName("txt")
        public String info;

        public String code;
    }

}
