package com.example.lhq.weather.activity.json.image360api.musicApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MusicApi {

    //云音乐热歌榜
    public String songListName;
    //图片地址
    @SerializedName("songListPic")
    public String songImage;

    /**
     * 获取歌曲列表信息
     */
    @SerializedName("songs")
    public List<Songs> songsList;
}
