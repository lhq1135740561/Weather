package com.example.lhq.weather.activity.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.data.Image360Url;
import com.example.lhq.weather.activity.db.CityLab;
import com.example.lhq.weather.activity.json.image360api.image360Api.Api360;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Gson360Api {

    public static final String url360 = "http://wallpaper.apc.360.cn/index.php?c=WallPaper&a=getAppsByOrder&order=create_time&start=0&count=30&from=360chrome";

    /**
     * 解析360壁纸图片
     *
     * @param response
     * @param index
     * @return
     */
    public static Api360 handle360ApiResponse(String response, int index) {
        try {
            JSONObject object = new JSONObject(response);
            JSONArray array = object.getJSONArray("data");
            String Api360Content = array.getJSONObject(index).toString();

            Gson gson = new Gson();
            return gson.fromJson(Api360Content, Api360.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 图片网络请求和解析
     */
    public static void httpGson360api(final Context mContext, final ImageView imageView, final int index) {
        HttpUtility.AsynchttpRequest(url360, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                imageView.setImageResource(R.mipmap.ten);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Api360 api360 = handle360ApiResponse(responseString, index);
                SharedPreferences.Editor editor = mContext.getSharedPreferences("imageUrl", Context.MODE_PRIVATE).edit();
                if (api360 != null) {
                    String imageUrl = api360.img_1024_768;
                    String imageTag = api360.tag;
                    Glide.with(mContext).load(imageUrl).into(imageView);
                    editor.putString(index + "", imageUrl);
                    editor.putString("date", BingPicUtility.getNowDate());
                    editor.apply();
                }
            }
        });
    }

    public interface Nullable{

        boolean isNull();
    }

    public interface DependencyBase extends Nullable{

        void Operation();
    }


    //自定义一个空对象继承这两个接口
    public class MyLisenterNull implements DependencyBase,Nullable{

        @Override
        public boolean isNull() {
            
            return true;
        }

        @Override
        public void Operation() {

        }
    }
}
