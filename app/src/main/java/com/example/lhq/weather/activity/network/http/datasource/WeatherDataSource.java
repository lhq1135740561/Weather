package com.example.lhq.weather.activity.network.http.datasource;

import com.example.lhq.weather.activity.network.model.Weather;
import com.example.lhq.weather.activity.network.http.basis.BaseRemoteDataSource;
import com.example.lhq.weather.activity.network.http.basis.callback.RequestCallback;
import com.example.lhq.weather.activity.network.http.datasource.base.IWeatherDataSource;
import com.example.lhq.weather.activity.network.http.service.ApiService;
import com.example.lhq.weather.activity.network.viewmodel.base.BaseViewModel;


/**
 * 作者：leavesC
 * 时间：2018/10/27 20:48
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class WeatherDataSource extends BaseRemoteDataSource implements IWeatherDataSource {

    public WeatherDataSource(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void queryWeather(String cityName, RequestCallback<Weather> responseCallback) {
        execute(getService(ApiService.class).queryWeather(cityName), responseCallback);
    }

}
