package com.example.lhq.weather.activity.entity;

import com.google.gson.annotations.SerializedName;

//未来三天的天气状况
public class Forecast {

    public String date; //今天日期2018-07-12

    @SerializedName("tmp")
    public Temperatrue temperatrue;

    @SerializedName("cond")
    public More more;

    public Astro astro;

    public class Temperatrue{
        public String max;  //最高温度32
        public String min;  //最低温度29
    }

    public class More{
        @SerializedName("txt_d")
        public String info;    //小雨
        @SerializedName("code_d")
        public String code;    //306
    }

    public class Astro{
        @SerializedName("sr")
        public String sunrise; //日出时间
        @SerializedName("ss")
        public String sunset;  //日落时间
    }
}
