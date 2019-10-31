package com.example.lhq.weather.activity.network.holder;

import android.content.Context;

import com.example.lhq.weather.activity.common.WeatherApplication;

public class ContextHolder {

    public static Context getContext(){

        return WeatherApplication.getContext();
    }
}
