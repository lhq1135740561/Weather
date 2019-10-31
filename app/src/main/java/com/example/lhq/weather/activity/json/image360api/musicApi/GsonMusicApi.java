package com.example.lhq.weather.activity.json.image360api.musicApi;

import com.example.lhq.weather.activity.utils.HttpUtility;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class GsonMusicApi {

    public static final String WY_MUSIC_URL ="https://api.itooi.cn/music/netease/songList?key=579621905&id=3778678&limit=10&offset=0";

    public static final String QQ_MUSIC_URL ="https://api.bzqll.com/music/tencent/songList?key=579621905&id=1147906982";

    public static final String WYy_MUSIC_URL ="https://api.bzqll.com/music/tencent/search?key=579621905&s=123&num=200&page=1";


    /**
     * json解析网易云音乐的api
     * @return
     */
    public static MusicApi handleMusicResponse(String response){

        try {
            JSONObject object = new JSONObject(response);
            String data = object.getJSONObject("data").toString();

            Gson gson = new Gson();

            return gson.fromJson(data,MusicApi.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解析网易云音乐数据
     */

}
