package com.example.lhq.weather.activity.network.http.datasource;

import com.example.lhq.weather.activity.network.http.basis.BaseRemoteDataSource;
import com.example.lhq.weather.activity.network.http.basis.callback.RequestCallback;
import com.example.lhq.weather.activity.network.http.datasource.base.IFailExampleDataSource;
import com.example.lhq.weather.activity.network.http.service.ApiService;
import com.example.lhq.weather.activity.network.viewmodel.base.BaseViewModel;

/**
 * 作者：leavesC
 * 时间：2019/1/30 13:02
 * 描述：
 */
public class FailExampleDataSource extends BaseRemoteDataSource implements IFailExampleDataSource {

    public FailExampleDataSource(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void test1(RequestCallback<String> callback) {
        execute(getService(ApiService.class).test1(), callback);
    }

    @Override
    public void test2(RequestCallback<String> callback) {
        execute(getService(ApiService.class).test2(), callback);
    }

}