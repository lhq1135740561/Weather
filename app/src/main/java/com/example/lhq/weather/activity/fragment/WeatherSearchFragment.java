package com.example.lhq.weather.activity.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.activity.WeatherActivity;
import com.example.lhq.weather.activity.activity.WeatherCityActivity;
import com.example.lhq.weather.activity.common.Constant;
import com.example.lhq.weather.activity.db.City;
import com.example.lhq.weather.activity.db.CityLab;
import com.example.lhq.weather.activity.db.FragmentList;
import com.example.lhq.weather.activity.db.WeatherCity;
import com.example.lhq.weather.activity.entity.SearchCity;
import com.example.lhq.weather.activity.entity.Weather;
import com.example.lhq.weather.activity.utils.GsonUtility;
import com.example.lhq.weather.activity.utils.HttpUtility;
import com.example.lhq.weather.activity.utils.Utility;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 搜索喜欢城市的Fragment
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class WeatherSearchFragment extends Fragment {

    private View view;
    //判断是否监听到正确的城市名字
    private boolean isFlag = false;
    //城市名,id
    private String cityname;
    private String code;
    private String info;

    @BindView(R.id.search_cityName)
    AutoCompleteTextView cityNameTextview;
    @BindView(R.id.search_city_text)
    TextView textView;


    @SuppressLint("ValidFragment")
    public WeatherSearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fra_search_weather_city, container, false);
        ButterKnife.bind(this, view);


        addListener(); //添加城市监听

        return view;
    }

    /**
     * 添加城市监听
     */
    private void addListener() {
        cityNameTextview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textView.setText("正在查询城市。。。");
                cityNameCheck(s.toString());
            }
        });
    }

    /**
     * 请求天气接口，访问城市数据，判断输入的城市名是否正确
     *
     * @param cityName
     */
    private void cityNameCheck(String cityName) {
        HttpUtility.AsynchttpRequest(Constant.getWeatherUrl() + cityName, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(),"获取数据失败！",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                final SearchCity city = GsonUtility.handleSearchWeatherResponse(responseString);
                if (city != null && "ok".equals(city.status)) {
                    textView.setText(city.basic.city);
                    isFlag = true;
                    cityname = city.basic.city;
                    code = city.basic.code;
                    info = city.now.more.info;
                } else {
                    textView.setText("查不到该城市，请重新输入");
                    isFlag = false;
                }
            }
        });

    }

    /**
     * 获取城市名字点击监听事件
     *
     * @param view
     */
    @OnClick(R.id.search_city_text)
    public void onClickText(View view) {
        addSearchCityName();

    }

    @OnClick(R.id.search_back)
    public void onClickBack(View view){
        Intent intent = new Intent(getActivity(), WeatherActivity.class);
        intent.putExtra("page", Utility.getCurrrent(getActivity()));
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * 添加搜索城市名字并存储在数据库中
     */
    private void addSearchCityName() {
        if (isFlag) {
            if (CityLab.CompreteCityName(cityname, getActivity())) {
                //数据库增添城市
                City city = new City();
                city.setId(CityLab.getCityList(getActivity()).size());
                city.setCityName(cityname);
                city.setCityCode(code);
                city.setWeatherInfo(info);
                city.save();

                //添加到城市列表中
                CityLab.getCityList(getActivity()).add(CityLab.getCityList(getActivity()).size() - 1, city);

                //创建Weather的Fragment对象
                WeatherFragment fragment = WeatherFragment.createInstance(cityname);
                Constant.fragmentList.add(fragment);

                //跳转到天气主页面去
                Intent intent = new Intent(getActivity(), WeatherActivity.class);
                intent.putExtra("page", 1000);
                startActivity(intent);

                getActivity().finish();
            } else {
                cityNameTextview.setText("");
                Toast.makeText(getActivity(), "城市名重复添加，请重新输入！", Toast.LENGTH_SHORT).show();
            }

        }
    }


}
