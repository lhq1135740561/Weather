package com.example.lhq.weather.activity.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.WindowManager;

/**
 * 获取常量的方法类
 */
public class Utility {

    //屏幕的宽度
    public static int getWindowWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        int width = manager.getDefaultDisplay().getWidth();
        return width;
    }

    //屏幕的宽度
    public static int getWindowHeight(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        int height = manager.getDefaultDisplay().getHeight();
        return height;
    }

    public static void setCurrrent(Context context, int pager) {
        SharedPreferences.Editor editor = context.getSharedPreferences("viewpage", Context.MODE_PRIVATE).edit();

        editor.putInt("pager", pager);
        editor.apply();
    }

    public static int getCurrrent(Context context) {
        SharedPreferences editor = context.getSharedPreferences("viewpage", Context.MODE_PRIVATE);

        return editor.getInt("pager", 0);

    }

    //获取地图定位的城市（区）
    public static void setLocationDistrictName(Context context, String name) {
        SharedPreferences.Editor editor = context.getSharedPreferences("baidu", Context.MODE_PRIVATE).edit();

        editor.putString("districtName", name);
        editor.apply();
    }

    public static String getLocationDistrictName(Context context) {
        SharedPreferences editor = context.getSharedPreferences("baidu", Context.MODE_PRIVATE);

        return editor.getString("districtName", null);
    }


    //获取地图定位的城市（街区）
    public static void setLocationStreetName(Context context, String name) {
        SharedPreferences.Editor editor = context.getSharedPreferences("baidu", Context.MODE_PRIVATE).edit();

        editor.putString("StreetName", name);
        editor.apply();
    }

    public static String getLocationStreetName(Context context) {
        SharedPreferences editor = context.getSharedPreferences("baidu", Context.MODE_PRIVATE);

        return editor.getString("StreetName", null);
    }


    public static void setWeatherInfo(Context context, String cityName, String info, String temper) {
        SharedPreferences.Editor editor = context.getSharedPreferences("wea_info", Context.MODE_PRIVATE).edit();
        editor.putString("cityName", cityName);
        editor.putString("info", info);
        editor.putString("temper", temper);

        editor.apply();

    }

    public static String getWeatherInfo(Context context, String info) {
        SharedPreferences editor = context.getSharedPreferences("wea_info", Context.MODE_PRIVATE);

        return editor.getString(info, null);
    }


    public static void isChangBg(Context context, boolean isBg) {
        SharedPreferences.Editor editor = context.getSharedPreferences("wea_bg", Context.MODE_PRIVATE).edit();
        //判断是否是第一次进入
        editor.putBoolean("isBg", isBg);
        editor.apply();
    }

    public static boolean getChangBg(Context context) {
        SharedPreferences editor = context.getSharedPreferences("wea_bg", Context.MODE_PRIVATE);

        return editor.getBoolean("isBg", false);
    }


    //第一次进入
    public static void isFirst(Context context, boolean isBg) {
        SharedPreferences.Editor editor = context.getSharedPreferences("wea_bg", Context.MODE_PRIVATE).edit();
        editor.putBoolean("isFirst", isBg);
        editor.apply();
    }

    public static boolean getisFirst(Context context) {
        SharedPreferences editor = context.getSharedPreferences("wea_bg", Context.MODE_PRIVATE);

        return editor.getBoolean("isFirst", false);
    }

    //
    public static void setTmp(Context context, boolean isBg) {
        SharedPreferences.Editor ed = context.getSharedPreferences("wea_bgg", Context.MODE_PRIVATE).edit();
        ed.putBoolean("setTmp", isBg);
        ed.apply();
    }

    public static boolean getTmp(Context context) {
        SharedPreferences ed1 = context.getSharedPreferences("wea_bgg", Context.MODE_PRIVATE);

        return ed1.getBoolean("setTmp", false);
    }
    //
    public static void setTmpFirst(Context context, boolean isBg) {
        SharedPreferences.Editor ed = context.getSharedPreferences("wea_bgg", Context.MODE_PRIVATE).edit();
        ed.putBoolean("setTmpFirst", isBg);
        ed.apply();
    }

    public static boolean getTmpFirst(Context context) {
        SharedPreferences ed1 = context.getSharedPreferences("wea_bgg", Context.MODE_PRIVATE);

        return ed1.getBoolean("setTmpFirst", false);
    }

}
