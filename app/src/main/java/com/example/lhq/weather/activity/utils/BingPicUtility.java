package com.example.lhq.weather.activity.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.activity.FirstActivity;
import com.example.lhq.weather.activity.activity.WeatherActivity;
import com.example.lhq.weather.activity.common.Constant;
import com.example.lhq.weather.activity.entity.Weather;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BingPicUtility {

    private static Timer timer;


    public static void getGsonNowdate(final Context context) {
        HttpUtility.AsynchttpRequest(Constant.getWeatherUrl() + "北京", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Weather weather = GsonUtility.handleWeatherResponse(responseString);
                String nowUpdate = weather.basic.update.updateTime.split(" ")[0];
                BingPicUtility.putGsonDate(context, nowUpdate); //存储年月日
            }
        });
    }


    public static String getNowDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 显示每日必应网站的背景图片
     *
     * @param context
     * @param imageView
     */
    public static void showBingPic(final Context context, final Activity activity, final ImageView imageView) {
        HttpUtility.AsynchttpRequest(Constant.requestBingPic, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, final String responseString) {
                SharedPreferences.Editor editor = context.getSharedPreferences(Constant.BING_PIC
                        , Context.MODE_PRIVATE).edit();
                editor.putString("bing_pic", responseString);
                editor.apply();

                if (!activity.isFinishing()) {
                    Glide.with(context).load(responseString).into(imageView);
                }

                if(responseString != null){
                    timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(context, WeatherActivity.class);
                            context.startActivity(intent);
                            activity.finish();
                        }
                    };
                    timer.schedule(task,2000);
                }

            }
        });
    }

    /**
     * 后台更新每日必应网站的背景图片
     *
     * @param context
     */
    public static void updateBingPic(final Context context) {
        HttpUtility.AsynchttpRequest(Constant.requestBingPic, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, final String responseString) {
                SharedPreferences.Editor editor = context.getSharedPreferences(Constant.BING_PIC
                        , Context.MODE_PRIVATE).edit();
                editor.putString("bing_pic", responseString);
                editor.apply();

            }
        });
    }

    //获取解析图片字符
    public static String getBingpic(Context context) {
        SharedPreferences spf = context.getSharedPreferences(Constant.BING_PIC, Context.MODE_PRIVATE);
        return spf.getString("bing_pic", null);
    }


    public static void weatherSetBing(String pic,Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.BING_PIC
                , Context.MODE_PRIVATE).edit();
        editor.putString("weather_bing_pic", pic);
        editor.apply();
    }

    //获取解析图片字符
    public static String weatherGetBingpic(Context context) {
        SharedPreferences spf = context.getSharedPreferences(Constant.BING_PIC, Context.MODE_PRIVATE);
        return spf.getString("weather_bing_pic", null);
    }

    //存储解析的年月日
    public static void putGsonDate(Context context, String date) {
        SharedPreferences.Editor spf = context.getSharedPreferences(Constant.BING_DATE, Context.MODE_PRIVATE).edit();
        spf.putString("bingdate", date);
        spf.apply();
    }

    //获取解析后的年月日
    public static String getGsonDate(Context context) {
        SharedPreferences spf = context.getSharedPreferences(Constant.BING_DATE, Context.MODE_PRIVATE);

        return spf.getString("bingdate", null);
    }

}
