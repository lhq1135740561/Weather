package com.example.lhq.weather.activity.network.http.basis.interceptor;

import java.io.IOException;
import java.net.ConnectException;

import android.support.annotation.NonNull;

import com.example.lhq.weather.activity.network.http.basis.exception.ResultInvalidException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;

public class HttpInterceptor implements Interceptor {

    public HttpInterceptor(){

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        try {
            response = chain.proceed(request);
        }catch (Exception e){
            throw new ConnectException();
        }

        if(response.code() != 200){
            throw new ResultInvalidException();
        }
        //获取资源
        BufferedSource source = response.body().source();
        source.request(Integer.MAX_VALUE);
        String byteString = source.buffer().snapshot().utf8();
        ResponseBody responseBody = ResponseBody.create(null,byteString);

        return response.newBuilder().body(responseBody).build();
    }
}
