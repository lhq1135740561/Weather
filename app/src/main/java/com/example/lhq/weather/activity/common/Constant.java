package com.example.lhq.weather.activity.common;

import android.support.v4.app.Fragment;

import com.example.lhq.weather.activity.db.FragmentList;
import com.example.lhq.weather.activity.fragment.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

public class Constant {

    //获取豆瓣 Top250 榜单地址
    public static final String BASE_URL= "https://api.douban.com/v2/movie/";
    /**
     * Sha
     */
    //储存背景图片标识符
    public static final String BING_PIC= "bingPic";
    public static final String BING_DATE="bingPicDate";
    //必应图片接口地址
    public static String requestBingPic = "http://guolin.tech/api/bing_pic";
    //和风天气接口
    public static String getWeatherUrl(){
        String weatherUrl = "https://free-api.heweather.com/v5/weather?key=38776487133c447b955055d162399c42&city=";
        return  weatherUrl;
    }

    //数据智汇天气接口
    public static String getWeatherUrl2(){
        String weatherUrl = "https://api.shujuzhihui.cn/api/weather/area?appKey=0f433b0b63b643a8ad9717260901e51c&area=";
        return  weatherUrl;
    }

    //全国城市接口
    public static String citiesUrl = "https://guolin.tech/api/china";

    //收藏的Fragment列表
    public static List<WeatherFragment> fragmentList = new ArrayList<>();


    //华氏度
    public static String getTmp(int info){

        double nowInfo = info*1.8+32;
        String info1 = String.valueOf(nowInfo);
        String info2 ;
        if(info1.contains(".")){
            info2 = info1.substring(0,info1.indexOf("."));
        }else {
            info2 = info1.substring(0,3);
        }

        return info2;
    }

}
