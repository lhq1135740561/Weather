package com.example.lhq.weather.activity.entity;

import com.google.gson.annotations.SerializedName;

//当前天气信息类
public class Now {

    @SerializedName("tmp")
    public String temper;   //30℃

    public String hum; //湿度

    @SerializedName("cond")
    public More more;

    public Wind wind; //

    public class More{
        @SerializedName("txt")
        public String info;  //多云
    }

    public class Wind{
        public String dir;  //西南风

        public String sc; //几级风力

        public String spd; //速度
    }
}
