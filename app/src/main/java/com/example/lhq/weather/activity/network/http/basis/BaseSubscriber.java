package com.example.lhq.weather.activity.network.http.basis;

import android.widget.Toast;

import com.example.lhq.weather.activity.network.holder.ContextHolder;
import com.example.lhq.weather.activity.network.http.basis.callback.RequestCallback;
import com.example.lhq.weather.activity.network.http.basis.callback.RequestMultiplyCallback;
import com.example.lhq.weather.activity.network.http.basis.config.HttpCode;
import com.example.lhq.weather.activity.network.http.basis.exception.BaseException;
import com.example.lhq.weather.activity.network.viewmodel.base.BaseViewModel;

import io.reactivex.observers.DisposableObserver;


/**
 * 作者：leavesC
 * 时间：2018/10/27 20:52
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class BaseSubscriber<T> extends DisposableObserver<T> {

    private BaseViewModel baseViewModel;

    private RequestCallback<T> requestCallback;

    public BaseSubscriber(BaseViewModel baseViewModel) {
        this.baseViewModel = baseViewModel;
    }

    BaseSubscriber(BaseViewModel baseViewModel, RequestCallback<T> requestCallback) {
        this.baseViewModel = baseViewModel;
        this.requestCallback = requestCallback;
    }

    @Override
    public void onNext(T t) {
        if (requestCallback != null) {
            requestCallback.onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (requestCallback instanceof RequestMultiplyCallback) {
            RequestMultiplyCallback callback = (RequestMultiplyCallback) requestCallback;
            if (e instanceof BaseException) {
                callback.onFail((BaseException) e);
            } else {
                callback.onFail(new BaseException(HttpCode.CODE_UNKNOWN, e.getMessage()));
            }
        } else {
            if (baseViewModel == null) {
                Toast.makeText(ContextHolder.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                baseViewModel.showToast(e.getMessage());
            }
        }
    }

    @Override
    public void onComplete() {

    }

}