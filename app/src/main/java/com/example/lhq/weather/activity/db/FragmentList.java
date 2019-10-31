package com.example.lhq.weather.activity.db;

import com.example.lhq.weather.activity.fragment.WeatherFragment;

import org.litepal.crud.LitePalSupport;

public class FragmentList extends LitePalSupport{

    private WeatherFragment fragment;


    public WeatherFragment getFragment() {
        return fragment;
    }

    public void setFragment(WeatherFragment fragment) {
        this.fragment = fragment;
    }
}
