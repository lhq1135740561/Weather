package com.example.lhq.weather.activity.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MvUtility {

    public static void setIsRefresh(Context context, boolean isFoo) {
        SharedPreferences.Editor ed = context.getSharedPreferences("wea_mv", Context.MODE_PRIVATE).edit();
        ed.putBoolean("refresh", isFoo);
        ed.apply();
    }

    public static boolean getIsRefresh(Context context) {
        SharedPreferences ed1 = context.getSharedPreferences("wea_mv", Context.MODE_PRIVATE);

        return ed1.getBoolean("refresh", false);
    }
}
