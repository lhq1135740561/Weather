package com.example.lhq.weather.activity.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.lhq.weather.activity.common.ActivityCollector;
import com.example.lhq.weather.activity.data.MyMusic;
import com.example.lhq.weather.activity.service.MyService;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    private MyService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();

        ButterKnife.bind(this); //框架绑定这个活动
        ActivityCollector.addActivity(this);

        IntentFilter filter = new IntentFilter();
        service = new MyService();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(service,filter);

    }

    private void initView() {
        if(Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
//        unregisterReceiver(service);
    }

    public int getLayoutId(){

        return 0;
    }

    protected void startActivity(Class c1){
        startActivity(new Intent(this,c1));
    }


}
