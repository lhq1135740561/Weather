package com.example.lhq.weather.activity.network.http.basis.callback;

import com.example.lhq.weather.activity.network.http.basis.exception.BaseException;

/**
 * 请求失败的接口
 * @param <T>
 */
public interface RequestMultiplyCallback<T> extends RequestCallback<T> {

    void onFail(BaseException e);  //自定义一个BaseException类异常的抛出
}
