package com.example.lhq.weather.activity.network.http.service;

import com.example.lhq.weather.activity.network.model.Weather;
import com.example.lhq.weather.activity.network.http.basis.config.HttpConfig;
import com.example.lhq.weather.activity.network.http.basis.model.BaseResponseBody;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {

    //天气的接口
    @Headers({HttpConfig.HTTP_REQUEST_TYPE_KEY + ":" + HttpConfig.HTTP_REQUEST_WEATHER})
    @GET("onebox/weather/query")
    Observable<BaseResponseBody<Weather>> queryWeather(@Query("cityname") String cityName);

    @GET("leavesC/test1")
    Observable<BaseResponseBody<String>> test1();

    @GET("leavesC/test2")
    Observable<BaseResponseBody<String>> test2();


}
