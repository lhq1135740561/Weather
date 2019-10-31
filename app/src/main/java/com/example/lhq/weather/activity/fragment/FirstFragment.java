package com.example.lhq.weather.activity.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.activity.FirstActivity;
import com.example.lhq.weather.activity.activity.WeatherActivity;
import com.example.lhq.weather.activity.adapter.music.MusicAdapter.MusicOnListClickListener;
import com.example.lhq.weather.activity.utils.BingPicUtility;
import com.example.lhq.weather.activity.utils.Utility;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {

    private View mView;

    public static Timer timer;

    @BindView(R.id.first_image)
    ImageView imageView;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fra_first,container,false);
        ButterKnife.bind(this,mView);

        showBackground(imageView,mView);
        return mView;
    }



    /**
     * 加载必应背景逻辑
     */
    private void showBackground(ImageView imageView_bingPic, View view) {

        //判断是否是第一次进入
        Context thiss = view.getContext();
        if(!Utility.getisFirst(thiss)){
            Utility.isChangBg(thiss,true);
            Utility.isFirst(thiss,true);
        }

        //没有获取数据时
        if(BingPicUtility.getBingpic(thiss) == null){
            //防止
            BingPicUtility.putGsonDate(thiss, "2018-8-22"); //存储年月日
            Glide.with(this).load(R.mipmap.ten).into(imageView);
            setTimer();
        }

        //更换壁纸
        if(!Utility.getChangBg(thiss)){ //不想更换壁纸
            Glide.with(this).load(BingPicUtility.getBingpic(thiss)).into(imageView_bingPic);
            //定时跳转
            setTimer();
//            Log.d(TAG,BingPicUtility.getGsonDate(this)+"不想更换壁纸");
        }else {
            if (!BingPicUtility.getGsonDate(thiss).equals(BingPicUtility.getNowDate())) {
//                Log.d(TAG,BingPicUtility.getNowDate()+"1111");
//                Log.d(TAG,BingPicUtility.getGsonDate(this)+"2222");
                BingPicUtility.showBingPic(thiss, getActivity(), imageView_bingPic);
            } else {
//                Log.d(TAG,"不想更换壁纸--2222");
                Glide.with(this).load(BingPicUtility.getBingpic(thiss)).into(imageView_bingPic);

                BingPicUtility.weatherSetBing(BingPicUtility.getBingpic(thiss),thiss);

                setTimer();
            }
        }

    }


    private void setTimer(){
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getActivity(), WeatherActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        };
        timer.schedule(task, 3000);
    }

    @OnClick(R.id.first_image)
    public void onClickIntent(View view){
        Intent intent = new Intent(getActivity(), WeatherActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
