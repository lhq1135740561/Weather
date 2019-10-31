package com.example.lhq.weather.activity.common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

public class Notifications {

    private static final String TAG = "Notifications";

    private static String channelID = "1";

    private static String channelName = "channel_name";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void showNotification(Context context,String title,String content){
        NotificationChannel channel = new NotificationChannel(channelID,channelName, NotificationManager.IMPORTANCE_LOW);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(content);
        //创建通知时指定channelID
        builder.setChannelId(channelID);

        Notification notification = builder.build();
        manager.notify(11222,notification);


        Log.d(TAG,"推送成功");
    }
}
