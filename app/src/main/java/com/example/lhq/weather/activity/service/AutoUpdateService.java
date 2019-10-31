package com.example.lhq.weather.activity.service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.example.lhq.weather.activity.common.Constant;
import com.example.lhq.weather.activity.common.WeatherApplication;
import com.example.lhq.weather.activity.entity.Weather;
import com.example.lhq.weather.activity.utils.BingPicUtility;
import com.example.lhq.weather.activity.utils.GsonUtility;
import com.example.lhq.weather.activity.utils.HttpUtility;
import com.example.lhq.weather.activity.utils.SetUtility;
import com.example.lhq.weather.activity.utils.Utility;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class AutoUpdateService extends Service {

    private NotificationManager manager;

    private boolean isTime;

    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(isTime){
            //打开预警提醒
            if(SetUtility.getCityName(this)) {
                manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                showServiceWeather(this, manager);
                AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                int anHour = 3 * 60 * 1000;
                long AtTime = SystemClock.elapsedRealtime() + anHour;
                Intent i = new Intent(this, AutoUpdateService.class);
                PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
                manager.cancel(pi);
                manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, AtTime, pi);
            }
        }else {
            BingPicUtility.updateBingPic(AutoUpdateService.this);
            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
            int anHour = 6 * 60 * 60 * 1000;
            long AtTime = SystemClock.elapsedRealtime() + anHour;
            Intent i = new Intent(this,AutoUpdateService.class);
            PendingIntent pi = PendingIntent.getService(this,0,i,0);
            manager.cancel(pi);
            manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,AtTime,pi);

        }

        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 解析定位城市的信息进行通知推送
     *
     * @param context
     */
    public void showServiceWeather(final Context context, final NotificationManager notificationManager) {
        String url = Constant.getWeatherUrl() + Utility.getLocationDistrictName(context);
        HttpUtility.AsynchttpRequest(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Weather weather = GsonUtility.handleWeatherResponse(responseString);
                if (weather != null && weather.status.equals("ok")) {
                    String weatherInfo = weather.now.more.info;
                    if(weatherInfo.contains("雨") || weatherInfo.contains("雪")){
                        isTime = true;
                        GsonUtility.showNotification(weather, context,notificationManager);
                    }
                }
            }
        });
    }
}
