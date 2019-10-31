package com.example.lhq.weather.activity.activity.test;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.view.View;

import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.network.viewmodel.FailExampleViewModel;
import com.example.lhq.weather.activity.network.viewmodel.base.LViewModelProviders;


/**
 * 作者：leavesC
 * 时间：2019/1/30 12:58
 * 描述：
 */
public class FailExampleActivity extends BaseActivity {

    private FailExampleViewModel failExampleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_example);
    }

    @Override
    protected ViewModel initViewModel() {
        failExampleViewModel = LViewModelProviders.of(this, FailExampleViewModel.class);
        return failExampleViewModel;
    }

    public void test1(View view) {
        failExampleViewModel.test1();
    }

    public void test2(View view) {
        failExampleViewModel.test2();
    }

}
