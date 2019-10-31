package com.example.lhq.weather.activity.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.common.Constant;
import com.example.lhq.weather.activity.db.WeatherCity;
import com.example.lhq.weather.activity.fragment.WeatherSearchFragment;
import com.example.lhq.weather.activity.utils.BingPicUtility;
import com.example.lhq.weather.activity.utils.GsonUtility;
import com.example.lhq.weather.activity.utils.Utility;
import com.skyfishjy.library.RippleBackground;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherCityActivity extends AppCompatActivity{

    @BindView(R.id.city_fab)
    FloatingActionButton fab;

    @BindView(R.id.weather_searchCity_fragment_layout)
    LinearLayout search_layout;

    @BindView(R.id.weather_city_fragment_layout)
    LinearLayout city_layout;

    //新加的
    @BindView(R.id.set_city_layout)
    RelativeLayout TitleLayout;

    //添加城市fragment
    @BindView(R.id.search_RelativeLayout)
    LinearLayout fragment_searchCityLayout;


    //fab点击的次数
    private int count;

    public static void actionStart(Context context,String type){
        Intent intent = new Intent(context, WeatherCityActivity.class);
        intent.putExtra("cityType",type);
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_city);
        ButterKnife.bind(this);

        //进入时，先设置标题颜色和fab颜色
        showTitleBackground();



    }

    //初始化标题栏和fab颜色背景
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showTitleBackground() {
        String type = getIntent().getStringExtra("cityType");
        GsonUtility.showCityTitleLayout(this,type,TitleLayout,fab,fragment_searchCityLayout);
    }


    //fab按钮
    @SuppressLint("RestrictedApi")
    @OnClick(R.id.city_fab)
    public void onClick(View view) {
        count++;
        //点击添加按钮，重置Padding,从而弹出搜索页面的Fragment
        if(count % 2==0) {
            search_layout.setVisibility(View.INVISIBLE);
            city_layout.setVisibility(View.VISIBLE);
            TitleLayout.setVisibility(View.VISIBLE);
            fragment_searchCityLayout.setVisibility(View.GONE);
        }else {
            search_layout.setVisibility(View.VISIBLE);
            city_layout.setVisibility(View.INVISIBLE);
            TitleLayout.setVisibility(View.GONE);
            fragment_searchCityLayout.setVisibility(View.VISIBLE);
        }


    }


    /**
     * 返回按钮
     * @param keyCode
     * @param event
     * @return
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        count = 0;
        Intent intent = new Intent(WeatherCityActivity.this,WeatherActivity.class);
        intent.putExtra("page",Utility.getCurrrent(this));
        startActivity(intent);
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        count = 0;
    }
}
