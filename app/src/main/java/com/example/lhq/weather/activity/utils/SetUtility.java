package com.example.lhq.weather.activity.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SetUtility {

    //存储设置城市是否推送预警
    public static void setCityName(Context context, boolean f) {
        SharedPreferences.Editor editor = context.getSharedPreferences("set_wea_city", MODE_PRIVATE).edit();
        editor.putBoolean("SetCityName", f);
        editor.apply();

    }

    public static boolean getCityName(Context context) {
        SharedPreferences editor = context.getSharedPreferences("set_wea_city", MODE_PRIVATE);

        return editor.getBoolean("SetCityName", false);
    }


    //设置风力单位值
    public static void setWind(Context context, int tmp) {
        SharedPreferences.Editor editor = context.getSharedPreferences("set_tmp", MODE_PRIVATE).edit();
        editor.putInt("tmp", tmp);
        editor.apply();
    }

    public static int getWind(Context context) {
        SharedPreferences editor = context.getSharedPreferences("set_tmp", MODE_PRIVATE);

        return editor.getInt("tmp", 0);
    }

    //设置温度单位值
    public static void setTmp(Context context, int tmp) {
        SharedPreferences.Editor editor = context.getSharedPreferences("setTmp", MODE_PRIVATE).edit();

        editor.putInt("wind", tmp);

        editor.apply();
    }

    public static int getTmp(Context context) {
        SharedPreferences editor = context.getSharedPreferences("setTmp", MODE_PRIVATE);

        return editor.getInt("wind", 0);
    }

    //切换背景
    public static void setChangeBg(Context context, boolean change) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ChangeBg", MODE_PRIVATE).edit();

        editor.putBoolean("change", change);

        editor.apply();
    }

    public static boolean getChangeBg(Context context) {
        SharedPreferences editor = context.getSharedPreferences("ChangeBg", MODE_PRIVATE);

        return editor.getBoolean("change", false);
    }


    //浮点背景
    public static void setFloatBg(Context context, boolean change) {
        SharedPreferences.Editor editor = context.getSharedPreferences("floatBg", MODE_PRIVATE).edit();

        editor.putBoolean("float", change);

        editor.apply();
    }

    public static boolean getFloatBg(Context context) {
        SharedPreferences editor = context.getSharedPreferences("floatBg", MODE_PRIVATE);

        return editor.getBoolean("float", false);
    }

    //浮点背景
    public static void setFloatIntBg(Context context, int change) {
        SharedPreferences.Editor editor = context.getSharedPreferences("floatBg", MODE_PRIVATE).edit();

        editor.putInt("floatInt", change);

        editor.apply();
    }

    public static int getFloatIntBg(Context context) {
        SharedPreferences editor = context.getSharedPreferences("floatBg", MODE_PRIVATE);

        return editor.getInt("floatInt", 0);
    }

}
