package com.example.lhq.weather.activity.network.http.basis.callback;

import com.example.lhq.weather.activity.network.http.basis.exception.BaseException; /**
 * 需要两个回调接口，
 * 一个只包含请求成功时的回调接口，另一个多包含了一个请求失败时的回调接口。
 * @param <T>
 */

public interface RequestCallback<T> {

    void onSuccess(T t);

}
