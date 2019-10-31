package com.example.lhq.weather.activity.network.http.datasource.base;

import com.example.lhq.weather.activity.network.model.Weather;
import com.example.lhq.weather.activity.network.http.basis.callback.RequestCallback;


/**
 * 作者：leavesC
 * 时间：2018/10/27 21:10
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public interface IWeatherDataSource {

    void queryWeather(String cityName, RequestCallback<Weather> callback);

}
