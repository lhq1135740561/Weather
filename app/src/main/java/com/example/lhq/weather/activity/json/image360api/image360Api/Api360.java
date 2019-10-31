package com.example.lhq.weather.activity.json.image360api.image360Api;

import com.google.gson.annotations.SerializedName;

public class Api360 {

    //地址
    public String url;

    @SerializedName("utag")
    public String tag; //标题

    /**
     * 图片分辨率
     */
    public String img_1280_800;
    public String img_1280_1024;
    public String img_1024_768;

}
