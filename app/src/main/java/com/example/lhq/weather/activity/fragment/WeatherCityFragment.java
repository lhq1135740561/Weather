package com.example.lhq.weather.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.activity.WeatherActivity;
import com.example.lhq.weather.activity.adapter.WeaFraCityAdapter;
import com.example.lhq.weather.activity.adapter.WeafraCity2Adapter;
import com.example.lhq.weather.activity.common.Constant;
import com.example.lhq.weather.activity.db.City;
import com.example.lhq.weather.activity.db.CityLab;
import com.example.lhq.weather.activity.db.WeatherCity;
import com.example.lhq.weather.activity.entity.Forecast;
import com.example.lhq.weather.activity.entity.Weather;
import com.example.lhq.weather.activity.utils.GsonUtility;
import com.example.lhq.weather.activity.utils.HttpUtility;
import com.example.lhq.weather.activity.utils.Utility;
import com.loopj.android.http.TextHttpResponseHandler;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * 收藏喜欢城市的Fragment
 * A simple {@link Fragment} subclass.
 */
public class WeatherCityFragment extends Fragment implements WeaFraCityAdapter.MyOnSlidingViewClickListener {

    private static final String TAG = "WeatherCityFragment";

    private View view;
    //城市adpter
    private WeaFraCityAdapter adapter;
    //收藏城市信息集合
    private List<WeatherCity> weatherCitiesList = new ArrayList<>();

    //（9/13新加属性）记录删除城市名集合,城市序号
    private List<WeatherCity> weatherCitiesPositionList = new ArrayList<>(); //用于记录删除前的集合
    private List<String> cityNameList = new ArrayList<>();
    private List<Integer> cityPositionList = new ArrayList<>();

    //收藏城市集合
    private List<City> cityList;
    @BindView(R.id.fra_city_rv)
    RecyclerView recyclerView;


    //第一个Adapter
    private WeafraCity2Adapter adapter2;
    @BindView(R.id.fra_city_rv2)
    RecyclerView recyclerView2;

    //编辑
    @BindView(R.id.city_edit)
    ImageView editImageView;

    //初始化的空间
    @BindView(R.id.city_back)
    ImageView backImage;

    @BindView(R.id.city_cancle)
    Button cancleButton;

    @BindView(R.id.city_sure)
    Button sureButton;

    @BindView(R.id.city_manage)
    TextView manageText;

    @BindView(R.id.city_manage2)
    TextView manage2Text;

    private boolean isEdit;


    public WeatherCityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather_city, container, false);
        ButterKnife.bind(this, view);

        //构建recyclerView
        BindRecyclerView(view);

        //构建recyclerView2
        BindRecyclerView2(view);


        return view;
    }

    private void BindRecyclerView2(View view) {
        //配置适配器2
        LinearLayoutManager manager2 = new LinearLayoutManager(getActivity());
        recyclerView2.setLayoutManager(manager2);
        adapter2 = new WeafraCity2Adapter(weatherCitiesList, WeatherCityFragment.this, getActivity());
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());//设置RecyclerView动画


        getAllWeather2(view); //获取所有城市的天气信息
    }

    private void BindRecyclerView(View view) {
        //配置适配器
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        adapter = new WeaFraCityAdapter(weatherCitiesList, WeatherCityFragment.this, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());//设置RecyclerView动画

        getAllWeather(view); //获取所有城市的天气信息

    }

    private void getAllWeather2(View view) {
        //刷新时，防止重复添加
        weatherCitiesList.clear();
        //获取喜欢城市列表
        cityList = CityLab.getCityList(getActivity());
        if (cityList.size() > 0 && cityList != null) {
            for (int i = 0; i < cityList.size(); i++) {
                String cityName = cityList.get(i).getCityName();
                WeatherCity weatherCity = new WeatherCity(cityName);
                weatherCity.setId(i);
                weatherCity.setCityInfo("多云");
                weatherCity.setCityTmp("30");
                weatherCity.setCityGrade("良");
                weatherCity.setCityHum("87");
                weatherCity.setCityWind("东北风9.8");
                weatherCity.setCityMax("30");
                weatherCity.setCityMin("25");

                //添加到集合中
                weatherCitiesList.add(weatherCity);
                weatherCitiesPositionList.add(weatherCity);
                requestWeather2(Constant.getWeatherUrl() + cityName, i, view);
            }
        }
        adapter2.notifyDataSetChanged();

    }

    private void requestWeather2(String url, final int index, View view) {
        HttpUtility.AsynchttpRequest(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Weather weather = GsonUtility.handleWeatherResponse(responseString);
                if (weather != null && weather.status.equals("ok")) {
                    if (index == 0) {
                        if (Utility.getLocationStreetName(getActivity()) == null) {
                            weatherCitiesList.get(index).setCityName(weather.basic.cityName);
                        } else {
                            weatherCitiesList.get(index).setCityName(Utility.getLocationStreetName(getActivity()));
                        }
                    } else {
                        weatherCitiesList.get(index).setCityName(weather.basic.cityName);
                    }
                    weatherCitiesList.get(index).setCityInfo(weather.now.more.info);
                    weatherCitiesList.get(index).setCityTmp(weather.now.temper);
                    weatherCitiesList.get(index).setCityGrade(weather.aqi.city.qlty);
                    weatherCitiesList.get(index).setCityHum(weather.now.hum);
                    weatherCitiesList.get(index).setCityWind(weather.now.wind.dir + weather.now.wind.spd);
                    for (int i = 0; i < weather.forecastList.size(); i++) {
                        String tmpMax = weather.forecastList.get(0).temperatrue.max;
                        String tmpMin = weather.forecastList.get(0).temperatrue.min;
                        weatherCitiesList.get(index).setCityMax(tmpMax);
                        weatherCitiesList.get(index).setCityMin(tmpMin);
                    }
                    adapter2.notifyDataSetChanged();
                }

            }
        });
    }

    /**
     * 获取所有城市的天气信息
     *
     * @param view
     */
    private void getAllWeather(View view) {
        //刷新时，防止重复添加
        weatherCitiesList.clear();
        //获取喜欢城市列表
        cityList = CityLab.getCityList(getActivity());

        if (cityList != null && cityList.size() > 0) {
            for (int i = 0; i < cityList.size(); i++) {
                String cityName = cityList.get(i).getCityName();
                final WeatherCity weatherCity = new WeatherCity(cityName);
                String info = Utility.getWeatherInfo(view.getContext(), "info");
                String temper = Utility.getWeatherInfo(view.getContext(), "temper");
                if (info == null) {
                    weatherCity.setId(i);
                    weatherCity.setCityInfo("多云");
                    weatherCity.setCityTmp("30");
                } else {
                    weatherCity.setId(i);
                    weatherCity.setCityInfo(info);
                    weatherCity.setCityTmp(temper);
                }
                //添加到集合中
                weatherCitiesList.add(weatherCity);
                weatherCitiesPositionList.add(weatherCity);
                requestWeather(Constant.getWeatherUrl() + cityName, i, view);
            }
        }
        adapter.notifyDataSetChanged();


    }

    /**
     * 请求天气信息数据
     */
    private void requestWeather(String url, final int index, final View view) {
        HttpUtility.AsynchttpRequest(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(view.getContext(), "获取数据失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Weather weather = GsonUtility.handleWeatherResponse(responseString);
                if (weather != null && "ok".equals(weather.status)) {
                    if (index == 0) {
                        if (Utility.getLocationStreetName(getActivity()) == null) {
                            Utility.setLocationStreetName(getActivity(), weather.basic.cityName);
                            weatherCitiesList.get(index).setCityName(weather.basic.cityName);
                        } else {
                            weatherCitiesList.get(index).setCityName(Utility.getLocationStreetName(getActivity()));
                        }
                    } else {
                        weatherCitiesList.get(index).setCityName(weather.basic.cityName);
                    }

                    weatherCitiesList.get(index).setCityInfo(weather.now.more.info);
                    weatherCitiesList.get(index).setCityTmp(weather.now.temper);

                    Utility.setWeatherInfo(view.getContext(), weather.basic.cityName, weather.now.more.info,
                            weather.now.temper);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }


    @Override
    public void onItemClick(View view, int position) {
        if (position == 0) {
            position = -2;
        }
        if(!Utility.getTmp(getActivity())){
            Utility.setTmp(getActivity(),false);
        }else {
            Utility.setTmp(getActivity(),true);
        }
        Intent intent = new Intent(getActivity(), WeatherActivity.class);
        intent.putExtra("page", position);
        getActivity().startActivity(intent);
        getActivity().finish();

        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void onDeleteBtnClick(View view, int position) {
        if (position == 0) {
            Toast.makeText(getActivity(), "定位城市不能删除", Toast.LENGTH_SHORT).show();
        } else {
            //得到删除城市的id
            long id = CityLab.getCityId(weatherCitiesList.get(position).getCityName(), getActivity());

            //（1）在删除之前做回收工作(取消或确定操作)
            String cityName = weatherCitiesList.get(position).getCityName();
            cityNameList.add(cityName);

            //（2）删除列表中的数据
            weatherCitiesList.remove(position);
            //适配器更新
            adapter.notifyItemRemoved(position);
            adapter2.notifyItemRemoved(position);
            Constant.fragmentList.remove(position);
            //删除数据库中的数据
            LitePal.delete(City.class, id);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * 返回
     */
    @OnClick(R.id.city_back)
    public void onClickBack(View view) {
        Intent intent = new Intent(getActivity(), WeatherActivity.class);
        intent.putExtra("page", Utility.getCurrrent(getActivity()));
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * 编辑
     */
    @OnClick(R.id.city_edit)
    public void onClickEdit(View view) {
        //防止重复添加
        cityNameList.clear();

        backImage.setVisibility(View.GONE);
        manageText.setVisibility(View.GONE);
        editImageView.setVisibility(View.GONE);

        cancleButton.setVisibility(View.VISIBLE);
        manage2Text.setVisibility(View.VISIBLE);
        sureButton.setVisibility(View.VISIBLE);

        //打开列表
        recyclerView2.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

    }

    //取消
    @OnClick(R.id.city_cancle)
    public void onClickCancle(View view) {
        //（1）隐藏或显示view
        backImage.setVisibility(View.VISIBLE);
        manageText.setVisibility(View.VISIBLE);
        editImageView.setVisibility(View.VISIBLE);

        cancleButton.setVisibility(View.GONE);
        manage2Text.setVisibility(View.GONE);
        sureButton.setVisibility(View.GONE);

        //打开列表
        recyclerView2.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        //（2）恢复回收集合中的所有城市信息
        for (int i = 0; i < cityNameList.size(); i++) {
            String cityName = cityNameList.get(i);

            WeatherCity weatherCity = new WeatherCity(cityName);
            weatherCity.setCityInfo("多云");
            weatherCity.setCityTmp("30");
            weatherCity.setCityGrade("空气良");
            weatherCity.setCityHum("87");
            weatherCity.setCityWind("东北风9.8km/h");
            weatherCitiesList.add(weatherCity);
            //获取网络数据

            WeatherFragment fragment = WeatherFragment.createInstance(cityName);
            Constant.fragmentList.add(fragment);

            List<City> cityList = CityLab.getCityList(getActivity());
            City city = new City();
            city.setCityName(cityName);
            city.save();
            //恢复回收城市名和序号
            cityList.add(cityList.size() - 1, city);

        }

        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();

        //重新加载数据
        getAllWeather2(view);

    }

    //确定
    @OnClick(R.id.city_sure)
    public void onClickSure(View view) {
        isEdit = true;
        cityNameList.clear();

        backImage.setVisibility(View.VISIBLE);
        manageText.setVisibility(View.VISIBLE);
        editImageView.setVisibility(View.VISIBLE);

        cancleButton.setVisibility(View.GONE);
        manage2Text.setVisibility(View.GONE);
        sureButton.setVisibility(View.GONE);

        //打开列表
        recyclerView2.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();

    }


}
