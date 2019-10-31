package com.example.lhq.weather.activity.network.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.example.lhq.weather.activity.network.http.datasource.WeatherDataSource;
import com.example.lhq.weather.activity.network.http.repo.WeatherRepo;
import com.example.lhq.weather.activity.network.model.Weather;
import com.example.lhq.weather.activity.network.viewmodel.base.BaseViewModel;

public class WeatherViewModel extends BaseViewModel {

    private MutableLiveData<Weather> weatherLiveData;

    private WeatherRepo weatherRepo;

    public WeatherViewModel(){
        weatherLiveData = new MutableLiveData<>();
        weatherRepo = new WeatherRepo(new WeatherDataSource(this));
    }

    public void queryWeather(String cityName){
        weatherRepo.queryWeather(cityName).observe(lifecycleOwner, new Observer<Weather>() {
            @Override
            public void onChanged(@Nullable Weather weather) {
                weatherLiveData.setValue(weather);
            }
        });
    }

    public MutableLiveData<Weather> getWeatherLiveData() {
        
        return weatherLiveData;
    }
}
