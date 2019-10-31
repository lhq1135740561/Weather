package com.example.lhq.weather.activity.network.http.basis.interceptor;

import android.text.TextUtils;

import com.example.lhq.weather.activity.network.http.basis.config.HttpConfig;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class FilterInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl.Builder httpBuilder = request.url().newBuilder();
        Headers headers = request.headers();

        if (headers != null && headers.size() > 0) {
            String requestType = headers.get(HttpConfig.HTTP_REQUEST_TYPE_KEY);
            if (!TextUtils.isEmpty(requestType)) {
                switch (requestType) {
                    case HttpConfig.HTTP_REQUEST_WEATHER: {
                        httpBuilder.addQueryParameter(HttpConfig.KEY, HttpConfig.KEY_WEATHER);
                        break;
                    }
                    case HttpConfig.HTTP_REQUEST_QR_CODE: {
                        httpBuilder.addQueryParameter(HttpConfig.KEY, HttpConfig.KEY_QR_CODE);
                        break;
                    }
                    case HttpConfig.HTTP_REQUEST_NEWS: {
                        httpBuilder.addQueryParameter(HttpConfig.KEY, HttpConfig.KEY_NEWS);
                        break;
                    }
                }
            }
        }

        Request.Builder builder = request.newBuilder()
                .removeHeader(HttpConfig.HTTP_REQUEST_TYPE_KEY)
                .url(httpBuilder.build());

        return chain.proceed(builder.build());
    }
}
