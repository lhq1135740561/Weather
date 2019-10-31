package com.example.lhq.weather.activity.json.image360api.musicApi;

import com.example.lhq.weather.activity.json.image360api.mv.MusicMv1;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GsonMusicMv {

    public static final String MV_WYy_MUSIC_URL ="https://api.bzqll.com/music/netease/topMvList?key=579621905&limit=100&offset=0";

    public static final String MV_QQ_MUSIC_URL="https://api.bzqll.com/music/tencent/hotMvList?key=579621905&year=0&tag=0&area=0&limit=100&offset=0";

    /**
     * json解析网易云音乐的api
     * @return
     */
    public static MusicMv1 handleMusicResponse(String response){

        try {
            JSONObject object = new JSONObject(response);

            String data = object.toString();

            Gson gson = new Gson();

            return gson.fromJson(data,MusicMv1.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }



    /**
     * 解析网易云音乐数据
     */
}
