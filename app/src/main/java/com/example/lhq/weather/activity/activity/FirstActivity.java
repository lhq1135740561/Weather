package com.example.lhq.weather.activity.activity;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.common.ActivityCollector;
import com.example.lhq.weather.activity.fragment.FirstFragment;
import com.example.lhq.weather.activity.utils.BingPicUtility;
import com.example.lhq.weather.activity.utils.DateString;
import com.example.lhq.weather.activity.utils.GsonUtility;
import com.example.lhq.weather.activity.utils.Utility;
import com.example.lhq.weather.activity.utils.interf.SendMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class FirstActivity extends BaseActivity implements SendMessage.Propose{

    private static final String TAG = "FirstActivity";

    public static final String T = "你好";
    public static final String T2 = "在干嘛了";

    public static Timer timer;

    @BindView(R.id.first_image)
    ImageView imageView;

    //百度地图定位
    public LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetwork = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetwork = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());


        //获取权限
        List<String> permissList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ContextCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permissList.isEmpty()) {
            Log.d(TAG,"不同意权限了");
            String[] perssions = permissList.toArray(new String[permissList.size()]);
            ActivityCompat.requestPermissions(FirstActivity.this, perssions, 1);
        } else {
            Log.d(TAG,"同意权限了");
            //判断是否有网络数据
            if (mobNetwork.isConnected() || wifiNetwork.isConnected()) {
                requestLocation();
            }
            //显示背景
            showBackground(imageView);
            //把布局替代成Fragment布局

            //容易出问题的地方
            BingPicUtility.getGsonNowdate(this);


            //实例化该类的对象
            SendMessage message = new SendMessage();
            message.response(FirstActivity.this);

        }


    }

    /**
     * 加载必应背景逻辑
     */
    private void showBackground(ImageView imageView_bingPic) {

        //判断是否是第一次进入
        if(!Utility.getisFirst(this)){
            Utility.isChangBg(this,true);
            Utility.isFirst(this,true);
        }

        //没有获取数据时
        if(BingPicUtility.getBingpic(this) == null){
            //防止
            BingPicUtility.putGsonDate(this, "2018-8-22"); //存储年月日
            Glide.with(this).load(R.mipmap.ten).into(imageView);
            setTimer();
        }

        //更换壁纸
        if(!Utility.getChangBg(this)){ //不想更换壁纸
            Glide.with(this).load(BingPicUtility.getBingpic(this)).into(imageView_bingPic);
            //定时跳转
            setTimer();
//            Log.d(TAG,BingPicUtility.getGsonDate(this)+"不想更换壁纸");
        }else {
            if (!BingPicUtility.getGsonDate(this).equals(BingPicUtility.getNowDate())) {
//                Log.d(TAG,BingPicUtility.getNowDate()+"1111");
//                Log.d(TAG,BingPicUtility.getGsonDate(this)+"2222");
                BingPicUtility.showBingPic(this, this, imageView_bingPic);
            } else {
//                Log.d(TAG,"不想更换壁纸--2222");
                Glide.with(this).load(BingPicUtility.getBingpic(this)).into(imageView_bingPic);

                BingPicUtility.weatherSetBing(BingPicUtility.getBingpic(this),this);

                setTimer();
            }
        }

    }

    private void setTimer(){
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(FirstActivity.this, WeatherActivity.class);
                startActivity(intent);
                FirstActivity.this.finish();
            }
        };
        timer.schedule(task, 3000);
    }

    @OnClick(R.id.first_image)
    public void onClickIntent(View view){
        Intent intent = new Intent(FirstActivity.this, WeatherActivity.class);
        startActivity(intent);
        FirstActivity.this.finish();
    }

    /**
     * 把布局替代成Fragment布局
     * @param firstFragment
     */
    private void replaceFragment(FirstFragment firstFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();//开启事务
//        transaction.replace(R.id.ac_first_fragment,firstFragment);  //向容器里添加或者替换碎片
        transaction.commit();  //提交
    }

    /**
     * 底部栏隐藏
     */
    private void initview() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();

    }

    //实时定位
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(60 * 60 * 1000 * 3); //刷新一次
        option.setIsNeedAddress(true); //获取当前位置的详细地址信息
        mLocationClient.setLocOption(option);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        mLocationClient.stop();
    }




    @Override
    public int getLayoutId() {
        return R.layout.activity_first;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有的权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    requestLocation();
                    //显示背景
                    showBackground(imageView);
                    //容易出问题的地方
                    BingPicUtility.getGsonNowdate(this);
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    public void answer(String info) {
        switch (info){
            case T:
                Log.d(TAG,"你也好啊！");
                break;
            case T2:
                Log.d(TAG,"你是？");
                break;
                default:
        }
    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //城区
            String districtName = bdLocation.getDistrict();
            //街道
            String streetName = bdLocation.getStreet();

            if (districtName.equals("")) {
                String name = Utility.getLocationDistrictName(FirstActivity.this);
                if (name == null) {
                    districtName = "深圳";
                    Utility.setLocationDistrictName(FirstActivity.this, districtName);
                } else {
                    districtName = name;
                    Utility.setLocationDistrictName(FirstActivity.this, districtName);
                }
            }else {
                Utility.setLocationDistrictName(FirstActivity.this, districtName);
            }


            if(streetName.equals("")){
                String name = Utility.getLocationStreetName(FirstActivity.this);
                if (name == null) {
                    streetName = "";
                    Utility.setLocationStreetName(FirstActivity.this, streetName);
                } else {
                    Utility.setLocationStreetName(FirstActivity.this, streetName);
                }
            }else {
                Utility.setLocationStreetName(FirstActivity.this, streetName);
            }

        }
    }
}
