package com.example.lhq.weather.activity.network.viewmodel.base;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.lhq.weather.activity.network.event.BaseActionEvent;

public class BaseViewModel extends ViewModel implements IViewModelAction {

    /**
     * LiveData是一个可被观察的数据持有类，遵这意味着它能遵守Activity、Fragment、Service等组件的生命周期。
     * 这种意识确保LiveData只更新处于活跃状态的应用程序组件Observer。
     * MutableLiveData类暴露公用的setValue(T)和postValue(T)方法
     */
    private MutableLiveData<BaseActionEvent> actionLiveData;

    //生命周期所有者
    protected LifecycleOwner lifecycleOwner;

    public BaseViewModel() {
        //通常在ViewModel中使用MutableLiveData，然后ViewModel仅向Observer公开不可变的LiveData对象
        actionLiveData = new MutableLiveData<>();
    }

    @Override
    public void startLoading() {
        startLoading(null);
    }

    @Override
    public void startLoading(String message) {
        BaseActionEvent baseActionEvent = new BaseActionEvent(BaseActionEvent.SHOW_LOADING_DIALOG);
        baseActionEvent.setMessage(message);
        //LiveData更新存储数据对象
        actionLiveData.setValue(baseActionEvent);
    }

    @Override
    public void dismissLoading() {
        actionLiveData.setValue(new BaseActionEvent(BaseActionEvent.DISMISS_LOADING_DIALOG));
    }

    @Override
    public void showToast(String message) {
        BaseActionEvent baseActionEvent = new BaseActionEvent(BaseActionEvent.SHOW_TOAST);
        baseActionEvent.setMessage(message);
        //LiveData更新存储数据对象
        actionLiveData.setValue(baseActionEvent);
    }

    @Override
    public void finish() {
        actionLiveData.setValue(new BaseActionEvent(BaseActionEvent.FINISH));
    }

    @Override
    public void finishResultOk() {
        actionLiveData.setValue(new BaseActionEvent(BaseActionEvent.FINISH_WITH_RESULT_OK));
    }

    @Override
    public MutableLiveData<BaseActionEvent> getActionLiveData() {
        return actionLiveData;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }
}
