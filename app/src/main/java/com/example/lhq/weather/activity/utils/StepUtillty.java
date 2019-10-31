package com.example.lhq.weather.activity.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lhq.weather.activity.db.StepBean;

import java.util.ArrayList;
import java.util.List;

public class StepUtillty {


    //记录打卡次数
    public static void setNumber(Context context, int number) {
        SharedPreferences.Editor ed = context.getSharedPreferences("wea_step", Context.MODE_PRIVATE).edit();
        ed.putInt("number", number);
        ed.apply();
    }

    public static int getNumber(Context context) {
        SharedPreferences ed1 = context.getSharedPreferences("wea_step", Context.MODE_PRIVATE);

        return ed1.getInt("number", 0);
    }

    //记录打卡日期
    public static void setDate(Context context, String date) {
        SharedPreferences.Editor ed = context.getSharedPreferences("wea_step", Context.MODE_PRIVATE).edit();
        ed.putString("date", date);
        ed.apply();
    }

    public static String getDate(Context context) {
        SharedPreferences ed1 = context.getSharedPreferences("wea_step", Context.MODE_PRIVATE);

        return ed1.getString("date", null);
    }

    //记录晚安打卡日期
    public static void setDateNight(Context context, String date) {
        SharedPreferences.Editor ed = context.getSharedPreferences("wea_stepp", Context.MODE_PRIVATE).edit();
        ed.putString("daten", date);
        ed.apply();
    }

    public static String getDateNight(Context context) {
        SharedPreferences ed1 = context.getSharedPreferences("wea_stepp", Context.MODE_PRIVATE);

        return ed1.getString("daten", null);
    }


    //判断是否能整除7
    public static void setisDate(Context context, boolean isFoo) {
        SharedPreferences.Editor ed = context.getSharedPreferences("wea_step", Context.MODE_PRIVATE).edit();
        ed.putBoolean("isFoo", isFoo);
        ed.apply();
    }

    public static boolean getisDate(Context context) {
        SharedPreferences ed1 = context.getSharedPreferences("wea_step", Context.MODE_PRIVATE);

        return ed1.getBoolean("isFoo", false);
    }

    //判断音乐播放方式  /-- 单曲 顺序
    public static void setisPlayWay(Context context, boolean isFoo) {
        SharedPreferences.Editor ed = context.getSharedPreferences("wea_step", Context.MODE_PRIVATE).edit();
        ed.putBoolean("way", isFoo);
        ed.apply();
    }

    public static boolean getisPlayWay(Context context) {
        SharedPreferences ed1 = context.getSharedPreferences("wea_step", Context.MODE_PRIVATE);

        return ed1.getBoolean("way", false);
    }

    //记录歌曲的地址
    public static void setMusicUrl(Context context, String date) {
        SharedPreferences.Editor ed = context.getSharedPreferences("wea_stepp", Context.MODE_PRIVATE).edit();
        ed.putString("url", date);
        ed.apply();
    }

    public static String getMusicUrl(Context context) {
        SharedPreferences ed1 = context.getSharedPreferences("wea_stepp", Context.MODE_PRIVATE);

        return ed1.getString("url", null);
    }


    //判断刚开始打卡的天数
    public static int setStartNumber(int number) {
        /**
         * 8 9 10 11 12 13 14
         */
        int count = number % 7;
        int startNumber = 0;
        switch (count) {
            case 0:
                startNumber = number;
                break;
            case 1:
                startNumber = number - 1;
                break;
            case 2:
                startNumber = number - 2;
                break;
            case 3:
                startNumber = number - 3;
                break;
            case 4:
                startNumber = number - 4;
                break;
            case 5:
                startNumber = number - 5;
                break;
            case 6:
                startNumber = number - 6;
                break;
            default:
                break;
        }
        return startNumber;
    }

}
