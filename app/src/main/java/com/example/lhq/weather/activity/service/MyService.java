package com.example.lhq.weather.activity.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


public class MyService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetwork = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetwork = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetwork.isConnected() && !wifiNetwork.isConnected()) {
            context.unregisterReceiver(this); // 这句话必须要写要不会报错，不写虽然能关闭，会报一堆错
            Toast.makeText(context, "无移动数据网络，请开启网络刷新！", Toast.LENGTH_SHORT).show();
        }
    }

}
