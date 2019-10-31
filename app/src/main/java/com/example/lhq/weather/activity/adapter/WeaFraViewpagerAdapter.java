package com.example.lhq.weather.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lhq.weather.activity.db.FragmentList;
import com.example.lhq.weather.activity.fragment.WeatherFragment;

import java.util.List;

public class WeaFraViewpagerAdapter extends FragmentStatePagerAdapter{

    private List<WeatherFragment> weatherFragmentList;

    public WeaFraViewpagerAdapter(FragmentManager fm,List<WeatherFragment> weatherFragmentList) {
        super(fm);
        this.weatherFragmentList = weatherFragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        return (weatherFragmentList == null || weatherFragmentList.size() == 0) ? null : weatherFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return weatherFragmentList == null ? null : weatherFragmentList.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        //删除感兴趣的Fragmnet时，对应删除此Fragment,适配器强制刷新
        return POSITION_NONE;
    }
}
