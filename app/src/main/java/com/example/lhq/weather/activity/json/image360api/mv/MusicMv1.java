package com.example.lhq.weather.activity.json.image360api.mv;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MusicMv1 {

    //网易云音乐MV
    public String result;

    public String code;

    @SerializedName("data")
    public List<Mv> mvList;
}
