package com.example.lhq.weather.activity.json.image360api.musicApi;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class SreachGsonMusic {

    /**
     * 获取搜索音乐的地址
     * @param song
     * @param count
     * @return
     */
    public static String getSreachMusic(String song,int count){
        String urlKey = "https://api.bzqll.com/music/tencent/search?key=579621905&s=";
        String urlCount ="&limit="+count+"&offset=0&type=song";

        return urlKey+song+urlCount;
    }

    /**
     * json解析网易云音乐的api
     * @return
     */
    public static SreachMusicApi handleMusicResponse(String response){

        try {
            JSONObject object = new JSONObject(response);

            String data = object.toString();
            Gson gson = new Gson();

            return gson.fromJson(data,SreachMusicApi.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
