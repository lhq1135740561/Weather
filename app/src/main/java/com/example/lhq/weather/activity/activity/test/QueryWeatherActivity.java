package com.example.lhq.weather.activity.activity.test;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.network.model.Weather;
import com.example.lhq.weather.activity.network.viewmodel.WeatherViewModel;
import com.example.lhq.weather.activity.network.viewmodel.base.LViewModelProviders;
import com.google.gson.Gson;

public class QueryWeatherActivity extends BaseActivity {

    private WeatherViewModel weatherViewModel;

    private EditText et_cityName;

    private TextView tv_weather; //显示json后的数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_weather);
        et_cityName = findViewById(R.id.et_cityName);
        tv_weather = findViewById(R.id.tv_weather);
    }

    @Override
    protected ViewModel initViewModel() {
        weatherViewModel = LViewModelProviders.of(this,WeatherViewModel.class);
        weatherViewModel.getWeatherLiveData().observe(this,this::handlerWeather);
        return weatherViewModel;
    }

    private void handlerWeather(Weather weather) {
        StringBuilder result = new StringBuilder();
        for (Weather.InnerWeather.NearestWeather nearestWeather : weather.getData().getWeather()) {
            result.append("\n\n").append(new Gson().toJson(nearestWeather));
        }
        tv_weather.setText(result.toString());
    }


    public void queryWeather(View view){
        tv_weather.setText(null);
        weatherViewModel.queryWeather(et_cityName.getText().toString());
    }
}
