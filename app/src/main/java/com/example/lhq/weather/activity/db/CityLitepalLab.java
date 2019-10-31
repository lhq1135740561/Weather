package com.example.lhq.weather.activity.db;

import android.content.Context;

import com.example.lhq.weather.activity.data.CityLitepal;
import com.example.lhq.weather.activity.utils.Utility;

import org.litepal.LitePal;

import java.util.List;

public class CityLitepalLab {

    public static List<CityLitepal> getCityLitepal(Context context){
        List<CityLitepal> cityLitepals = LitePal.findAll(CityLitepal.class);

        return  cityLitepals;
    }
}
