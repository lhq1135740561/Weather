package com.example.lhq.weather.activity.common;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import org.litepal.LitePal;

public class WeatherApplication extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(this);
        MultiDex.install(this);
    }

    public static Context getContext(){
        return context;
    }
}
