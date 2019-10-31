package com.example.lhq.weather.activity.network.http.basis;

import com.example.lhq.weather.BuildConfig;
import com.example.lhq.weather.activity.network.holder.ContextHolder;
import com.example.lhq.weather.activity.network.http.basis.config.HttpCode;
import com.example.lhq.weather.activity.network.http.basis.config.HttpConfig;
import com.example.lhq.weather.activity.network.http.basis.exception.AccountInvalidException;
import com.example.lhq.weather.activity.network.http.basis.exception.ServerResultException;
import com.example.lhq.weather.activity.network.http.basis.exception.TokenInvalidException;
import com.example.lhq.weather.activity.network.http.basis.interceptor.FilterInterceptor;
import com.example.lhq.weather.activity.network.http.basis.interceptor.HeaderInterceptor;
import com.example.lhq.weather.activity.network.http.basis.interceptor.HttpInterceptor;
import com.example.lhq.weather.activity.network.http.basis.model.BaseResponseBody;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 为了提升性能，Retrofit 一般是设计成单例模式。
 * 为了应对应用中 BaseUrl 可能有多个的情况（本文提供的Demo就是如此），
 * 此处使用 Map 来存储多个 Retrofit 实例。
 */
public class RetrofitManagement {

    private static final long READ_TIMEOUT = 6000;

    private static final long WRITE_TIMEOUT = 6000;

    private static final long CONNECT_TIMEOUT = 6000;

    private Map<String,Object> serviceMap = new ConcurrentHashMap<>();

    private RetrofitManagement(){

    }

    public static RetrofitManagement getInstance(){

        return RetrofitHolder.retrofitManageMent;
    }

    private static class RetrofitHolder{
        private static final RetrofitManagement retrofitManageMent = new RetrofitManagement();
    }

    private Retrofit createRetrofit(String url){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.MICROSECONDS)
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.MICROSECONDS)
                .connectTimeout(CONNECT_TIMEOUT,TimeUnit.MICROSECONDS)
                .addInterceptor(new HttpInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new FilterInterceptor())
                .retryOnConnectionFailure(true);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
            builder.addInterceptor(new ChuckInterceptor(ContextHolder.getContext()));
        }
        OkHttpClient client = builder.build();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    <T> ObservableTransformer<BaseResponseBody<T>, T> applySchedulers() {
        return new ObservableTransformer<BaseResponseBody<T>, T>() {
            private ObservableSource<? extends T> apply(BaseResponseBody<T> result) {
                switch (result.getCode()) {
                    case HttpCode.CODE_SUCCESS: {
                        return RetrofitManagement.this.createData(result.getData());
                    }
                    case HttpCode.CODE_TOKEN_INVALID: {
                        throw new TokenInvalidException();
                    }
                    case HttpCode.CODE_ACCOUNT_INVALID: {
                        throw new AccountInvalidException();
                    }
                    default: {
                        throw new ServerResultException(result.getCode(), result.getMsg());
                    }
                }
            }

            @Override
            public ObservableSource<T> apply(Observable<BaseResponseBody<T>> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(this::apply);
            }
        };
    }



    <T> T getService(Class<T> clz){
        return getService(clz, HttpConfig.BASE_URL_WEATHER);
    }


    <T> T getService(Class<T> clz,String host){
        T value;

        if(serviceMap.containsKey(host)){
            Object obj = serviceMap.get(host);
            if(obj == null){
                value = createRetrofit(host).create(clz);
                serviceMap.put(host,clz);
            }else {
                value = (T) obj;
            }
        }else {
            value = createRetrofit(host).create(clz);
            serviceMap.put(host,clz);
        }
        return  value;
    }

    private <T> Observable<T> createData(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }
}
