package com.example.lhq.weather.activity.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.common.ActivityCollector;
import com.example.lhq.weather.activity.service.AutoUpdateService;
import com.example.lhq.weather.activity.adapter.WeaFraViewpagerAdapter;
import com.example.lhq.weather.activity.common.Constant;
import com.example.lhq.weather.activity.db.City;
import com.example.lhq.weather.activity.db.CityLab;
import com.example.lhq.weather.activity.fragment.WeatherFragment;
import com.example.lhq.weather.activity.utils.BingPicUtility;
import com.example.lhq.weather.activity.utils.GsonUtility;
import com.example.lhq.weather.activity.utils.SetUtility;
import com.example.lhq.weather.activity.utils.Utility;
import com.meis.widget.particle.FireflyView;
import com.skyfishjy.library.RippleBackground;


import java.util.List;
import java.util.Random;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.matteobattilana.library.WeatherView;

public class WeatherActivity extends BaseActivity {

    public static final String TAG = "WeatherActivity";

    @BindView(R.id.ac_main_viewpager)
    ViewPager viewPager;

    private WeaFraViewpagerAdapter adapter;

    @BindView(R.id.ac_main_bingPic)
    ImageView imageView;

    @BindView(R.id.weatherView)
    WeatherView mWeatherView;

    @BindView(R.id.firefly)
    FireflyView fireflyView;


    private int current;
    NotificationManager notificationManager;


    Random randomFirefly = new Random();

    private int[] fireflys = {
            R.mipmap.ic_firefly_bg
            ,R.mipmap.ic_firefly_bg2
            ,R.mipmap.ic_firefly_bg3
            ,R.mipmap.ic_firefly_bg4};


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WeatherActivity.class);
        intent.putExtra("page", Utility.getCurrrent(context));
        intent.putExtra("change", SetUtility.getChangeBg(context));
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        Log.d(TAG, "onCreate");

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        GsonUtility.showWeather(this, notificationManager);

        onPagerView();

        showfireflyViewBackground();

        //初始化Fragment列表数据
        initFragmentList();
        //绑定适配器ViewPager
        bindViewpagerAdapter();

        //开启后台服务
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);


    }

    private void showfireflyViewBackground() {
        int firflyInt = randomFirefly.nextInt(fireflys.length);
        fireflyView.setBackgroundResource(fireflys[firflyInt]);
        Log.d(TAG, "firflyInt:" + firflyInt);
        //存储数据
        SetUtility.setFloatIntBg(this,firflyInt+1);
    }

    private void onIntent() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        if (id == GsonUtility.COUNT) {
            notificationManager.cancel(111123);
        }

    }

    /**
     * 设置刚开始第一个城市的晴，下雨，下雪背景
     */
    private void showBackground() {

        Random randomMoveRate = new Random();
        int moveRate = randomMoveRate.nextInt(6)+1;

        List<City> cities = CityLab.getCityList(WeatherActivity.this);
        String cityName = cities.get(0).getCityName();
        //解析天气状况显示背景效果
        if (SetUtility.getChangeBg(this)) {
            //浮点背景
            if(SetUtility.getFloatBg(this)){
                Log.d(TAG, "浮点背景---1；移动速度："+moveRate);
                fireflyView.setParticleMoveRate(moveRate);
                fireflyView.setVisibility(View.VISIBLE);
            }else {
                Log.d(TAG, "浮点背景---2");
                fireflyView.setVisibility(View.GONE);
                Glide.with(this).load(BingPicUtility.getBingpic(this)).into(imageView);
            }
        } else {
            //浮点背景
            if(SetUtility.getFloatBg(this)){
                Log.d(TAG, "浮点背景---3;移动速度:" +moveRate);
                fireflyView.setParticleMoveRate(moveRate);
                fireflyView.setVisibility(View.VISIBLE);
            }else {
                Log.d(TAG, "浮点背景---4");
                fireflyView.setVisibility(View.GONE);
                GsonUtility.showWeatherType(this, cityName, mWeatherView, imageView);
            }
        }

    }

    /**
     * 设置刚开始第一个城市的晴，下雨，下雪背景
     */
    private void showPageBackground(int page) {

        List<City> cities = CityLab.getCityList(WeatherActivity.this);
        String cityName = cities.get(page).getCityName();

        GsonUtility.showWeatherType(this, cityName, mWeatherView, imageView);

    }


    /**
     * 绑定适配器ViewPager
     */
    private void bindViewpagerAdapter() {
        FragmentManager manager = getSupportFragmentManager();
        adapter = new WeaFraViewpagerAdapter(manager, Constant.fragmentList);
        viewPager.setAdapter(adapter);
    }

    /**
     * 初始化Fragment列表数据
     */
    private void initFragmentList() {
        //先将列表清空，防止刷新时多次添加
        Constant.fragmentList.clear();
        //获取感兴趣城市列表
        List<City> cities = CityLab.getCityList(this);

        //城市列表中有多少个城市，就创建多少个Fragment
        if (cities != null && cities.size() > 0) {
            for (int i = 0; i < cities.size(); i++) {
                WeatherFragment weaFragment = WeatherFragment.createInstance(cities.get(i).getCityName());
                //将新创建Fragment添加到Fragment列表中，用于ViewPager的绑定滑动。
                Constant.fragmentList.add(weaFragment);
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

        //设置天气情况变换
        onIntent();

        //接收Intent传过来的值。
        // 如果是城市列表跳转过来的索引值，跳转到Viewpager指定页面
        //如果是搜索页面跳转过来的话，跳转到最后一页，Intent传过来的值1000。
        Intent intent = getIntent();
        int page = intent.getIntExtra("page", 0);
        boolean ischange = intent.getBooleanExtra("change", false);

        if (page > 0) {
            if (page == 1000) {
                //跳转到最后一页，最新的一页
                adapter.notifyDataSetChanged();
                viewPager.setCurrentItem(Constant.fragmentList.size() - 1);
                onChange(ischange,Constant.fragmentList.size() - 1);
                Log.d(TAG, "page到1000指定页面" + page);
            } else {
                //跳转到指定页面
                adapter.notifyDataSetChanged();
                viewPager.setCurrentItem(page);
                Log.d(TAG, "page到指定页面" + page);
//                onChange(ischange,page);  数组越界错误
                onChange(ischange,Utility.getCurrrent(this));
                showBackground();
            }
        } else if (page < -1) {
            adapter.notifyDataSetChanged();
            viewPager.setCurrentItem(0);
            showBackground();
            Log.d(TAG, "page到主页面" + page);
            onChange(ischange,0);
        } else if (page == -1) {
            adapter.notifyDataSetChanged();
            Log.d(TAG, "page到主页面我的页面中");
            viewPager.setCurrentItem(Utility.getCurrrent(this));
            onChange(ischange,Utility.getCurrrent(this));
        } else if (page == 0) {
            Log.d(TAG, "page为0");
            showBackground();
            onChange(ischange,0);
        }else {
            adapter.notifyDataSetChanged();
            onChange(ischange,Utility.getCurrrent(this));
        }
        adapter.notifyDataSetChanged();


    }

    private void onChange(boolean isChange,int page) {
        if (isChange) {
            Glide.with(this).load(BingPicUtility.getBingpic(this)).into(imageView);
        } else {
            showPageBackground(page);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");

    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        Intent intent = getIntent();
        intent.putExtra("page", -1);

        Utility.setCurrrent(WeatherActivity.this, current);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
        viewPager.setCurrentItem(Utility.getCurrrent(this));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //针对singleTask启动模式，重新设置Intent
        setIntent(intent);
        Log.d(TAG, "onNewIntent");
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    /**
     * viewpager滑动事件
     */
    private void onPagerView() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                current = i;
                Utility.setCurrrent(WeatherActivity.this, current);
                List<City> cities = CityLab.getCityList(WeatherActivity.this);
                String cityName = cities.get(i).getCityName();
                //解析天气状况显示背景效果
//                GsonUtility.showWeatherType(WeatherActivity.this,cityName,mWeatherView,imageView);
                if (SetUtility.getChangeBg(WeatherActivity.this)) {
                    Glide.with(WeatherActivity.this).load(BingPicUtility.getBingpic(WeatherActivity.this)).into(imageView);
                }
                GsonUtility.showWeatherType(WeatherActivity.this, cityName, mWeatherView, imageView);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notificationManager.cancel(111123);
        ActivityCollector.finishAll();
    }
}

