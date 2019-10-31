package com.example.lhq.weather.activity.entity;

import com.google.gson.annotations.SerializedName;

public class Suggestion {

    @SerializedName("comf")
    public Comfort comfort;  //舒适度

    @SerializedName("cw")
    public CarWash carWash;  //洗车指数

    public Sport sport;     //运动

    @SerializedName("uv")
    public Ulight ulight;   //紫外线建议

    public Air air;

    public class Comfort{
        @SerializedName("txt")
        public String info;  //白天虽然有雨，但仍无法削弱较高气温带来的暑意，同时降雨造成湿度加大会您感到有些闷热，不很舒
    }

    public class CarWash{
        @SerializedName("txt")
        public String info;  //洗车建议
    }

    public class Sport{
        @SerializedName("txt")
        public String info;  //运动建议
    }

    public class Ulight{    //紫外线建议
        @SerializedName("txt")
        public String info;
    }

    public class Air{
        public String brf;
    }
}
