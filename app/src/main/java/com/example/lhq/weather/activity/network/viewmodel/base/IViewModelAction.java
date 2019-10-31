package com.example.lhq.weather.activity.network.viewmodel.base;

import android.arch.lifecycle.MutableLiveData;

import com.example.lhq.weather.activity.network.event.BaseActionEvent;

public interface IViewModelAction {

    void startLoading();

    void startLoading(String message);

    void dismissLoading();

    void showToast(String message);

    void finish();

    void finishResultOk();

    MutableLiveData<BaseActionEvent> getActionLiveData();
}
