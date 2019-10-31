package com.example.lhq.weather.activity.entity;

import com.google.gson.annotations.SerializedName;

public class SearchCity {

    public String status;

    public Basic basic;

    public Now now;


    public class Basic{
        public String city;
        @SerializedName("id")
        public String code;
    }

    public class Now{

        @SerializedName("cond")
        public More more;

        public class More{
            @SerializedName("txt")
            public String info;  //多云
        }
    }


}
