package com.example.lhq.weather.activity.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.activity.WeatherActivity;
import com.example.lhq.weather.activity.common.Constant;
import com.example.lhq.weather.activity.common.WeatherApplication;
import com.example.lhq.weather.activity.entity.SearchCity;
import com.example.lhq.weather.activity.entity.Weather;
import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.loopj.android.http.TextHttpResponseHandler;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.skyfishjy.library.RippleBackground;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import xyz.matteobattilana.library.Common.Constants;
import xyz.matteobattilana.library.WeatherView;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.lhq.weather.R.color.wea_rain;

public class GsonUtility {

    public static final int COUNT = 1;

    public static Context mContext = WeatherApplication.getContext();

    /**
     * 解析所有的天气信息
     *
     * @param response
     * @return
     */
    public static Weather handleWeatherResponse(String response) {
        try {
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
//            String weatherContent = jsonArray.getJSONObject(0).toString();
//            //Gson解析数据并储存到实体类中
//            Gson gson = new Gson();
//            Weather weather = gson.fromJson(weatherContent,Weather.class);
//
//            return  weather;

            JSONObject object = new JSONObject(response);
            JSONArray array = object.getJSONArray("HeWeather5");
            String weatherContext = array.getJSONObject(0).toString();

            Gson gson = new Gson();

            return gson.fromJson(weatherContext, Weather.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析所有城市名字的信息
     *
     * @param response
     * @return
     */
    public static SearchCity handleSearchWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            //Gson解析数据并储存到实体类中
            Gson gson = new Gson();
            SearchCity city = gson.fromJson(weatherContent, SearchCity.class);

            return city;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析定位城市的信息进行通知推送
     *
     * @param context
     */
    public static void showWeather(final Context context, final NotificationManager notificationManager) {
        String url = Constant.getWeatherUrl() + Utility.getLocationDistrictName(context);
        HttpUtility.AsynchttpRequest(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Weather weather = handleWeatherResponse(responseString);
                if (weather != null && weather.status.equals("ok")) {
                    showNotification(weather, context, notificationManager);
                }
            }
        });
    }


    public static void showNotification(Weather weather, Context context, NotificationManager notificationManager) {
        String cityName = weather.basic.cityName;
        String nowInfoTexts = weather.now.more.info; //多云
        String nowDirTexts = weather.suggestion.air.brf;   //东风

        String id = "my_channel_01";
        String name = "我是渠道名字";
        Notification notification = null;

        Intent intent = new Intent(context, WeatherActivity.class);
        intent.putExtra("page", -2);
        intent.putExtra("id", COUNT);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(context)
                    .setChannelId(id)
                    .setContentTitle(cityName)
                    .setContentText(nowInfoTexts + "\t" + nowDirTexts)
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(pendingIntent).build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle(cityName)
                    .setContentText(nowInfoTexts + "\t" + nowDirTexts)
                    .setSmallIcon(R.mipmap.logo)
                    .setOngoing(true)
                    .setContentIntent(pendingIntent)
                    .setChannelId(id);//无效
            notification = notificationBuilder.build();
            notificationManager.cancel(111123);
        }
        notificationManager.notify(111123, notification);


    }


    /**
     * 显示天气的背景动画图
     *
     * @param name
     * @param mWeatherView
     */
    public static void showWeatherType(final Context context, String name, final WeatherView mWeatherView, final ImageView imageView) {
        String url = Constant.getWeatherUrl() + name;
        HttpUtility.AsynchttpRequest(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Weather weather = handleWeatherResponse(responseString);
                if (weather != null && weather.status.equals("ok")) {
                    String nowInfoTexts = weather.now.more.info; //多云
                    /**
                     * 判断显示的背景颜色和动画效果
                     */
                    if (nowInfoTexts.contains("雨")) {
                        if (nowInfoTexts.equals("小雨")) {
                            imageView.setBackgroundResource(wea_rain);
                            mWeatherView.setWeather(Constants.WeatherStatus.RAIN)
                                    .setRainParticles(10)
                                    .setRainAngle(-5)
                                    .startAnimation();
                        } else if (nowInfoTexts.equals("雨夹雪")) {
                            imageView.setBackgroundResource(R.color.wea_snow);
                            mWeatherView.setWeather(Constants.WeatherStatus.SNOW)
                                    .setRainParticles(10)
                                    .setRainAngle(-5)
                                    .startAnimation();
                        } else {
                            imageView.setBackgroundResource(wea_rain);
                            mWeatherView.setWeather(Constants.WeatherStatus.RAIN).startAnimation();
                        }

                    } else if (nowInfoTexts.contains("雪")) {
                        if (nowInfoTexts.equals("小雪")) {
                            imageView.setBackgroundResource(R.color.wea_snow);
                            mWeatherView.setWeather(Constants.WeatherStatus.SNOW)
                                    .setRainParticles(10)
                                    .setRainAngle(-5)
                                    .startAnimation();
                        } else {
                            imageView.setBackgroundResource(R.color.wea_snow);
                            mWeatherView.setWeather(Constants.WeatherStatus.SNOW).startAnimation();
                        }

                    } else if (nowInfoTexts.equals("阴")) {
                        imageView.setBackgroundResource(R.color.citylayoutBg5);
                        mWeatherView.setWeather(Constants.WeatherStatus.SUN)
                                .startAnimation();
                    } else if (nowInfoTexts.equals("多云")) {
                        imageView.setBackgroundResource(R.color.citylayoutBg5);
                        mWeatherView.setWeather(Constants.WeatherStatus.SUN)
                                .startAnimation();
                    } else {
                        imageView.setBackgroundResource(R.color.wea_sun);
                        mWeatherView.setWeather(Constants.WeatherStatus.SUN)
                                .startAnimation();
                    }
                    setWeaType(context, nowInfoTexts);

                }
            }
        });
    }

    /**
     * 判断刷新控件的颜色
     *
     * @param cityName
     * @param refreshLayout
     */
    public static void showRefreshLayout(String cityName, final RefreshLayout refreshLayout) {
        String url = Constant.getWeatherUrl() + cityName;
        HttpUtility.AsynchttpRequest(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Weather weather = GsonUtility.handleWeatherResponse(responseString);
                if (weather != null && weather.status.equals("ok")) {
                    String nowInfoTexts = weather.now.more.info; //多云
                    if (nowInfoTexts.contains("雨")) {
//                        refreshLayout.setColorSchemeResources(wea_rain);
                    } else if (nowInfoTexts.contains("雪")) {
//                        refreshLayout.setColorSchemeResources(R.color.wea_snow);
                    } else if (nowInfoTexts.equals("阴")) {
//                        refreshLayout.setColorSchemeResources(R.color.citylayoutBg5);
                    } else if (nowInfoTexts.equals("多云")) {
//                        refreshLayout.setColorSchemeResources(R.color.citylayoutBg5);
                    } else {
//                        refreshLayout.setColorSchemeResources(R.color.wea_sun);
                    }

                }
            }
        });

    }

    //存储天气状况WeatherAcitiviity
    public static void setWeaType(Context context, String type) {
        SharedPreferences.Editor editor = context.getSharedPreferences("set_wea_type", Context.MODE_PRIVATE).edit();
        editor.putString("Weatype", type);
        editor.apply();

    }

    public static String getWeaType(Context context) {
        SharedPreferences editor = context.getSharedPreferences("set_wea_type", Context.MODE_PRIVATE);

        return editor.getString("Weatype", null);
    }


    /**
     * 判断设置页面标题栏的颜色
     *
     * @param activity
     * @param type
     * @param relativeLayout
     */
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void showSetTitleLayout(final Activity activity, String type, final RelativeLayout relativeLayout) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        if (type.contains("雨")) {
            window.setStatusBarColor(activity.getResources().getColor(wea_rain));
            relativeLayout.setBackgroundResource(wea_rain);
        } else if (type.contains("雪")) {
            window.setStatusBarColor(activity.getResources().getColor(R.color.wea_snow));
            relativeLayout.setBackgroundResource(R.color.wea_snow);
        } else if (type.equals("阴")) {
            window.setStatusBarColor(activity.getResources().getColor(R.color.citylayoutBg5));
            relativeLayout.setBackgroundResource(R.color.citylayoutBg5);
        } else if (type.equals("多云")) {
            window.setStatusBarColor(activity.getResources().getColor(R.color.citylayoutBg5));
            relativeLayout.setBackgroundResource(R.color.citylayoutBg5);
        } else {
            relativeLayout.setBackgroundResource(R.color.wea_sun);
            window.setStatusBarColor(activity.getResources().getColor(R.color.wea_sun));
        }

        if(SetUtility.getFloatBg(mContext)){
            int count = SetUtility.getFloatIntBg(mContext);
            Log.d(WeatherActivity.TAG, "firflyInt---2:" + count);
            if(count == 1){
                setColor(relativeLayout,R.color.wea_float,window,activity);
            }else if(count == 2){
                setColor(relativeLayout,R.color.wea_float2,window,activity);
            }else if(count == 3){
                setColor(relativeLayout,R.color.wea_float3,window,activity);
            }else if(count == 4){
                setColor(relativeLayout,R.color.wea_float4,window,activity);
            }

        }
    }

    //设置颜色
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void setColor(RelativeLayout relativeLayout, int color, Window window, Activity activity) {
        relativeLayout.setBackgroundResource(color);
        window.setStatusBarColor(activity.getResources().getColor(color));
    }


    /**
     * 判断设置页面标题栏的颜色
     *
     * @param activity
     * @param type
     * @param relativeLayout
     * @param
     */
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void showCityTitleLayout(final Activity activity, String type, RelativeLayout relativeLayout, FloatingActionButton fab, LinearLayout SearchRelativeLayout) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        if (type.contains("雨")) {
            window.setStatusBarColor(activity.getResources().getColor(wea_rain));
            relativeLayout.setBackgroundResource(wea_rain);
            fab.setBackgroundResource(R.color.wea_rain);
            fab.setRippleColor(activity.getResources().getColorStateList(R.color.wea_rain));
            fab.setBackgroundTintList(activity.getResources().getColorStateList(R.color.wea_rain));
            SearchRelativeLayout.setBackgroundResource(R.color.wea_rain);
        } else if (type.contains("雪")) {
            window.setStatusBarColor(activity.getResources().getColor(R.color.wea_snow));
            relativeLayout.setBackgroundResource(R.color.wea_snow);
            fab.setBackgroundResource(R.color.wea_snow);
            fab.setRippleColor(activity.getResources().getColorStateList(R.color.wea_snow));
            fab.setBackgroundTintList(activity.getResources().getColorStateList(R.color.wea_snow));
            SearchRelativeLayout.setBackgroundResource(R.color.wea_snow);

        } else if (type.equals("阴") || type.equals("多云")) {
            window.setStatusBarColor(activity.getResources().getColor(R.color.citylayoutBg5));
            relativeLayout.setBackgroundResource(R.color.citylayoutBg5);
            fab.setBackgroundResource(R.color.citylayoutBg5);
            fab.setRippleColor(activity.getResources().getColorStateList(R.color.citylayoutBg5));
            fab.setBackgroundTintList(activity.getResources().getColorStateList(R.color.citylayoutBg5));
            SearchRelativeLayout.setBackgroundResource(R.color.citylayoutBg5);
        } else {
            relativeLayout.setBackgroundResource(R.color.wea_sun);
            window.setStatusBarColor(activity.getResources().getColor(R.color.wea_sun));
            fab.setBackgroundResource(R.color.wea_sun);
            fab.setRippleColor(activity.getResources().getColorStateList(R.color.wea_sun));
            fab.setBackgroundTintList(activity.getResources().getColorStateList(R.color.wea_sun));
            SearchRelativeLayout.setBackgroundResource(R.color.wea_sun);
        }


        if(SetUtility.getFloatBg(WeatherApplication.getContext())){
            int count = SetUtility.getFloatIntBg(mContext);
            if(count == 1){
                relativeLayout.setBackgroundResource(R.color.wea_float);
                window.setStatusBarColor(activity.getResources().getColor(R.color.wea_float));
                fab.setBackgroundResource(R.color.wea_float);
                fab.setRippleColor(activity.getResources().getColorStateList(R.color.wea_float));
                fab.setBackgroundTintList(activity.getResources().getColorStateList(R.color.wea_float));
                SearchRelativeLayout.setBackgroundResource(R.color.wea_float);
            }else if(count == 2){
                relativeLayout.setBackgroundResource(R.color.wea_float2);
                window.setStatusBarColor(activity.getResources().getColor(R.color.wea_float2));
                fab.setBackgroundResource(R.color.wea_float2);
                fab.setRippleColor(activity.getResources().getColorStateList(R.color.wea_float2));
                fab.setBackgroundTintList(activity.getResources().getColorStateList(R.color.wea_float2));
                SearchRelativeLayout.setBackgroundResource(R.color.wea_float2);
            }else if(count == 3){
                relativeLayout.setBackgroundResource(R.color.wea_float3);
                window.setStatusBarColor(activity.getResources().getColor(R.color.wea_float3));
                fab.setBackgroundResource(R.color.wea_float3);
                fab.setRippleColor(activity.getResources().getColorStateList(R.color.wea_float3));
                fab.setBackgroundTintList(activity.getResources().getColorStateList(R.color.wea_float3));
                SearchRelativeLayout.setBackgroundResource(R.color.wea_float3);
            }else if(count == 4){
                relativeLayout.setBackgroundResource(R.color.wea_float4);
                window.setStatusBarColor(activity.getResources().getColor(R.color.wea_float4));
                fab.setBackgroundResource(R.color.wea_float4);
                fab.setRippleColor(activity.getResources().getColorStateList(R.color.wea_float4));
                fab.setBackgroundTintList(activity.getResources().getColorStateList(R.color.wea_float4));
                SearchRelativeLayout.setBackgroundResource(R.color.wea_float4);
            }

        }

    }
}
