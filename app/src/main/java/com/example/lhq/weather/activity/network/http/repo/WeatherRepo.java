package com.example.lhq.weather.activity.network.http.repo;

import android.arch.lifecycle.MutableLiveData;

import com.example.lhq.weather.activity.network.model.Weather;
import com.example.lhq.weather.activity.network.http.basis.BaseRepo;
import com.example.lhq.weather.activity.network.http.basis.callback.RequestCallback;
import com.example.lhq.weather.activity.network.http.datasource.base.IWeatherDataSource;

public class WeatherRepo extends BaseRepo<IWeatherDataSource> {

    public WeatherRepo(IWeatherDataSource remoteDataSource) {
        super(remoteDataSource);
    }

    public MutableLiveData<Weather> queryWeather(String cityName){
        MutableLiveData<Weather> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.queryWeather(cityName, new RequestCallback<Weather>() {
            @Override
            public void onSuccess(Weather weather) {
                mutableLiveData.setValue(weather);
            }
        });
        return mutableLiveData;
    }
}
