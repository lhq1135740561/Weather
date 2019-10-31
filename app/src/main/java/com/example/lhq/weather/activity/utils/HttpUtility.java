package com.example.lhq.weather.activity.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class HttpUtility {

    /**
     * 异步网络请求
     * @param address
     * @param responseHandler
     */
    public static void AsynchttpRequest(String address, TextHttpResponseHandler responseHandler){

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(address,responseHandler);

    }

    /**
     * OkHttp网络请求
     * @param address
     * @param callback
     */
    public static void OkhttpRequest(String address, Callback callback){
       OkHttpClient client = new OkHttpClient();
       Request request = new Request.Builder().url(address).build();
       client.newCall(request).enqueue(callback);
    }

}
