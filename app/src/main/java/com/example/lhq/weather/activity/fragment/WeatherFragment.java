package com.example.lhq.weather.activity.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.activity.SetActivity;
import com.example.lhq.weather.activity.activity.StepActivity;
import com.example.lhq.weather.activity.activity.WeatherCityActivity;
import com.example.lhq.weather.activity.common.Constant;
import com.example.lhq.weather.activity.data.CityLitepal;
import com.example.lhq.weather.activity.db.StepBean;
import com.example.lhq.weather.activity.entity.Forecast;
import com.example.lhq.weather.activity.entity.Hourly;
import com.example.lhq.weather.activity.entity.Weather;
import com.example.lhq.weather.activity.json.image360api.weather.GsonZhWeather;
import com.example.lhq.weather.activity.json.image360api.weather.H3;
import com.example.lhq.weather.activity.json.image360api.weather.ZhWeather;
import com.example.lhq.weather.activity.utils.BingPicUtility;
import com.example.lhq.weather.activity.utils.DateString;
import com.example.lhq.weather.activity.utils.GsonUtility;
import com.example.lhq.weather.activity.utils.HttpUtility;
import com.example.lhq.weather.activity.utils.SetUtility;
import com.example.lhq.weather.activity.utils.StepUtillty;
import com.example.lhq.weather.activity.utils.Utility;
import com.example.lhq.weather.activity.view.DYLoadingView;
import com.example.lhq.weather.activity.view.StepsView;
import com.example.lhq.weather.activity.view.SunView;
import com.loopj.android.http.TextHttpResponseHandler;
import com.meis.widget.evaporate.EvaporateTextView;
import com.scwang.smartrefresh.header.DropboxHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * 显示所有的天气状况信息，每一个Fragment都是一个实体类
 */
public class WeatherFragment extends Fragment {


    public static final String TAG = "WeatherFragment";

    //刷新
    @BindView(R.id.weather_refresh)
    RefreshLayout weather_refresh;
    @BindView(R.id.weather_layout)
    NestedScrollView weatherLayout;
    //城市名
    @BindView(R.id.title_city)
    TextView titleCity;

    //图标
    @BindView(R.id.title_logo)
    ImageView titleImageview;

    @BindView(R.id.city_add)
    ImageView cityAddImageview;

    @BindView(R.id.city_set)
    ImageView citySetImageview;

    /**
     * 当前气温(now)
     */
    @BindView(R.id.degree_text)
    TextView nowDgreeText;
    //当前天气状况
    @BindView(R.id.weather_info_text)
    TextView nowInfoText;
    //当前风的状况
    @BindView(R.id.dirText)
    TextView nowDirText;
    //当前更新时间
    @BindView(R.id.update_time)
    TextView nowUpdateTimeText;
    //高温预警
    @BindView(R.id.now_warning)
    EvaporateTextView nowWarningText;

    //文字跳动定时
    public final int period = 1000 * 10;

    private PlayEvaptextThread playEvaptextThread;
    private String[] nowWarningTexts = new String[3];
    private int index = -1;
    private boolean isPlaying = false;   //判断是否在跳动
    private boolean isWarning;  //判断是否符合预警

    /**
     * 未来天气（forecast）
     */
    @BindView(R.id.forecast_layout)
    LinearLayout forecastLayout;

    /**
     * 空气质量（aqi)
     */
    @BindView(R.id.aqi_text)
    TextView aqiText;
    @BindView(R.id.pm25_text)
    TextView aqiPm25Text;
    @BindView(R.id.wea_aqi_view)
    View weaAqiView;
    /**
     * 日出日落效果
     */
    @BindView(R.id.sv)
    SunView sunView;
    @BindView(R.id.sun_title)
    TextView sunTitleTextview;

    @BindView(R.id.wea_sun_view)
    View weaSunView;
    /**
     * 生活建议（suggestion)
     */
    //舒适度
    @BindView(R.id.comfort_text)
    TextView suConfortText;
    @BindView(R.id.wea_suggestion_comfort)
    View weaComfortView;
    //洗车
    @BindView(R.id.car_wash_text)
    TextView suCarWashText;
    @BindView(R.id.wea_suggestion_wash)
    View weaWashView;
    //运动
    @BindView(R.id.sport_text)
    TextView suSportText;
    @BindView(R.id.wea_suggestion_sport)
    View weaSportView;
    //紫外线
    @BindView(R.id.vu_text)
    TextView suVuText;
    @BindView(R.id.wea_suggestion_vu)
    View weaVuView;
    /**
     * 每个小时的天气状况
     */
    @BindView(R.id.wea_hourly_layout)
    LinearLayout hourlyLayout;

    @BindView(R.id.wea_hourly_point)
    View weahourlyView;

    @BindView(R.id.wea_hourly_view)
    View weahourlyTop;
    /**
     * 判断星期几
     */
    private int count = 0;


    /**
     * fragment的城市名；
     */
    String city = "";


    /**
     * 仿抖音v2.5  加载框
     */
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private View view;

    /**
     * 打卡
     */
    @BindView(R.id.title_clocktext)
    TextView clockTextView;


    //判断解析智慧接口用完后
    private boolean isWeatherInterface;


    public WeatherFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView");

        final View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);//绑定框架

        city = getArguments().getString("cityName");
        final String url = Constant.getWeatherUrl() + city;

        final String url2 = Constant.getWeatherUrl2() + city;

        weatherLayout.setVisibility(View.INVISIBLE);

//        //显示加载控件
//        dyLoadingView.setVisibility(View.VISIBLE);
//        dyLoadingView.start(); //开始动画

        weather_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                //重新刷新联网，获取最新的天气状况
//                requestZhWeather(url2, view);
                requestWeather(url, view);
            }
        });

        //请求数据方法
//        requestZhWeather(url2, view);
        isWeatherInterface = true;
        requestWeather(url, view);

        //设置 Header 为 贝塞尔雷达 样式
        weather_refresh.setRefreshHeader(new DropboxHeader(view.getContext()));
        //设置 Footer 为 球脉冲 样式
        weather_refresh.setRefreshFooter(new BallPulseFooter(view.getContext()).setSpinnerStyle(SpinnerStyle.Scale));
//        GsonUtility.showRefreshLayout(city,  weather_refresh);
        startThreadEvapText();

        return view;
    }

    private void startThreadEvapText() {

 /*       if (mTimer == null) {
            mTimer = new Timer(true);
        }
        mTimertask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
            }
        };
        mTimer.schedule(mTimertask, period);  */

    }


    /**
     * 添加城市和设置
     *
     * @param view
     */
    @SuppressLint("WrongConstant")
    @OnClick(R.id.city_add)
    public void onAddClick(View view) {
        WeatherCityActivity.actionStart(getActivity(), GsonUtility.getWeaType(getActivity()));
    }

    /**
     * 设置
     *
     * @param view
     */
    @OnClick(R.id.city_set)
    public void onSetClick(View view) {
        SetActivity.actionStart(getActivity(), GsonUtility.getWeaType(getActivity()));
    }

    /**
     * 打卡
     *
     * @param view
     */
    @OnClick(R.id.title_clocktext)
    public void onStepClick(View view) {
        StepActivity.actionStart(getActivity(), GsonUtility.getWeaType(getActivity()));
    }

    private void clocktext(View view) {
        String date = StepUtillty.getDate(view.getContext());
        if (BingPicUtility.getNowDate().equals(date)) {
            clockTextView.setText(R.string.finish_step_clock);
        } else {
            clockTextView.setText(R.string.step_clock);
        }
    }

    /**
     * 判断定位城市的图标
     *
     * @param city
     */
    private void showImageIcon(String city, View view) {
        if (city == null) {
            city = "北京";
        }

        if (city.equals(Utility.getLocationDistrictName(view.getContext()))) {
            titleImageview.setVisibility(View.VISIBLE);
            clockTextView.setVisibility(View.VISIBLE);
            clocktext(view);
        } else {
            titleImageview.setVisibility(View.INVISIBLE);
            clockTextView.setVisibility(View.INVISIBLE);
        }

        cityAddImageview.setVisibility(View.VISIBLE);
        citySetImageview.setVisibility(View.VISIBLE);
    }

    /**
     * 请求实时的天气状况信息
     *
     * @param url
     * @param view
     */
    private void requestZhWeather(String url, final View view) {
        HttpUtility.AsynchttpRequest(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ZhWeather weather = GsonZhWeather.handleZhWeatherResponse(responseString);
                if (weather != null) {
                    showZhWeatherInfo(weather, view);
                } else {
                    isWeatherInterface = true;
                }
            }
        });
    }

    private void showZhWeatherInfo(ZhWeather weather, View v) {
        /**
         * 每个小时的天气状况
         */
        hourlyLayout.removeAllViews();
        boolean tmp1 = Utility.getTmp(v.getContext());
        for (H3 h3 : weather.weatherInfo.h3List) {
            //找到子布局
            View view = LayoutInflater.from(v.getContext()).inflate(R.layout.wea_hourly_item, hourlyLayout, false);
            TextView hourlyDateText = view.findViewById(R.id.wea_hourly_item_date);
            TextView hourlyHumText = view.findViewById(R.id.wea_hourly_item_humidity);
            ImageView hourlyImageview = view.findViewById(R.id.wea_hourly_item_img);
            TextView hourlyTmpText = view.findViewById(R.id.wea_hourly_item_tmp);
            TextView tomorrowText = view.findViewById(R.id.wea_hourly_item_tomorrow);

            String tomorrow = h3.date.split(" ")[0];  //日期
            String dateText = h3.time;  //时间段
            String tmpText = h3.tem + "℃";
            double hum = Double.valueOf(h3.humidity); //湿度
            double tmp = Double.valueOf(h3.tem);  //温度
            int tem = (int) Math.round(tmp);
            String weatherInfo = h3.weather; //小雨

            if (!tmp1) {
                hourlyTmpText.setText(tmpText);   //温度
            } else {
                hourlyTmpText.setText(Constant.getTmp(tem) + "℉");   //温度
            }
            hourlyDateText.setText(dateText); //时间点

            //相对湿度的逻辑
            showHumTextview(hum, hourlyHumText);
            //加载天气状况图片
            showInfoImageView2(weatherInfo, hourlyImageview);
            //明天时间点
            showTomorrowTextView(tomorrow, tomorrowText);

            if (weather.weatherInfo.h3List.get(0).weather.contains("雨")) {
                nowWarningText.setText("未来几小时有" + weather.weatherInfo.h3List.get(0).weather + "^ω^");
            }
            hourlyLayout.addView(view);
        }


        if (SetUtility.getFloatBg(v.getContext())) {
            weahourlyView.setBackgroundResource(R.color.colorWeather);
            weahourlyTop.setBackgroundResource(R.color.colorWeather);
        }
    }


    /**
     * 请求天气状况信息
     *
     * @param url
     * @param view
     */
    private void requestWeather(String url, final View view) {
        HttpUtility.AsynchttpRequest(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                weather_refresh.finishRefresh(2000, false);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Weather weather = GsonUtility.handleWeatherResponse(responseString);
                if (weather != null && weather.status.equals("ok")) {
                    showWeatherInfo(weather, view);
                    weather_refresh.finishRefresh(2000, false);

//                    dyLoadingView.stop();
//                    dyLoadingView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                    //判断定位城市的图标方法
                    showImageIcon(city, view);
                }
            }
        });
    }


    /**
     * 处理并显示Weather实体类中的信息
     *
     * @param weather
     */
    @TargetApi(28)
    private void showWeatherInfo(Weather weather, View v) {
        //创建数据库
        CityLitepal cityLitepal = new CityLitepal();

        String cityName = weather.basic.cityName;
        String nowDgreeTexts = weather.now.temper + "℃"; //30℃
        String nowInfoTexts = weather.now.more.info; //多云
        String nowDirTexts = weather.suggestion.air.brf;   //东风

        String nowUpdateYMD = weather.basic.update.updateTime.split(" ")[0];  //2018-8-31
        String nowUpdateTexts = weather.basic.update.updateTime.split(" ")[1];  //15::45

        //数据库保存数据
        cityLitepal.setCityName(cityName);
        cityLitepal.save();

        titleCity.setText(cityName);
//        nowDgreeText.setText(nowDgreeTexts);
        nowInfoText.setText(nowInfoTexts);
        nowDirText.setText("空气" + nowDirTexts);
        int info = Integer.valueOf(weather.now.temper);
        //获取当前更新的时间点
        showNowUpadateTime(nowUpdateYMD, nowUpdateTexts, nowUpdateTimeText);
        //高温预警

        boolean tmp1 = Utility.getTmp(v.getContext());

        showNowWarnning(info, nowDgreeText, nowWarningText, nowDgreeTexts, tmp1);


        //先移除所有View
        forecastLayout.removeAllViews();
        /**
         * 未来三天的天气状况
         */
        for (Forecast forecast : weather.forecastList) {

            //找到子布局
            View view = LayoutInflater.from(v.getContext()).inflate(R.layout.wea_forecast_item
                    , forecastLayout, false);
            TextView dateText = view.findViewById(R.id.date_text);
            TextView dateWay = view.findViewById(R.id.date_way); //星期几
            TextView infoText = view.findViewById(R.id.info_text);
            TextView minText = view.findViewById(R.id.min_text);
            TextView maxText = view.findViewById(R.id.max_text);
            View weaForecastView = view.findViewById(R.id.wea_forecast_item_view);
            //天气状况图标
            ImageView infoImageView = view.findViewById(R.id.info_img);
            //获取天气状况code
            int infoCode = Integer.valueOf(forecast.more.code);
            //加载天气状况图片
            showInfoImageView(infoCode, infoImageView);

            dateText.setText(forecast.date); //日期
            infoText.setText(forecast.more.info); //天气状况
            //显示温度
            int min = Integer.valueOf(forecast.temperatrue.min);
            int max = Integer.valueOf(forecast.temperatrue.max);
            if (!tmp1) {
                minText.setText(forecast.temperatrue.min); //温度
                maxText.setText(forecast.temperatrue.max + "℃");
            } else {
                minText.setText(Constant.getTmp(min)); //温度
                maxText.setText(Constant.getTmp(max) + "℉");
            }

            //判断星期几的逻辑
            count++;
            if (count > 0 && count <= 7) {
                if (count == 1) {
                    dateWay.setText(DateString.getDateString(0));
                } else if (count == 2) {
                    dateWay.setText(DateString.getDateString(1));
                } else if (count == 3) {
                    dateWay.setText(DateString.getDateString(2));
                } else if (count == 4) {
                    dateWay.setText(DateString.getDateString(3));
                } else if (count == 5) {
                    dateWay.setText(DateString.getDateString(4));
                } else if (count == 6) {
                    dateWay.setText(DateString.getDateString(5));
                } else if (count == 7) {
                    dateWay.setText(DateString.getDateString(6));
                    count = 0;
                }
            }


            if (SetUtility.getFloatBg(v.getContext())) {
                weaForecastView.setBackgroundResource(R.color.colorWeather);
            }
            forecastLayout.addView(view); //添加View

        }

        /**
         * 每个小时的天气信息
         */
        if (isWeatherInterface) {
            for (Hourly hourly : weather.hourlyList) {
                //找到子布局
                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.wea_hourly_item, hourlyLayout, false);
                TextView hourlyDateText = view.findViewById(R.id.wea_hourly_item_date);
                TextView hourlyHumText = view.findViewById(R.id.wea_hourly_item_humidity);
                ImageView hourlyImageview = view.findViewById(R.id.wea_hourly_item_img);
                TextView hourlyTmpText = view.findViewById(R.id.wea_hourly_item_tmp);
                TextView tomorrowText = view.findViewById(R.id.wea_hourly_item_tomorrow);

                String tomorrow = hourly.date.split(" ")[0];  //日期
                String dateText = hourly.date.split(" ")[1];  //时间段
                String tmpText = hourly.tmp + "℃";
                double hum = Double.valueOf(hourly.hum); //湿度
                int tmp = Integer.valueOf(hourly.tmp);  //温度
                String weatherInfo = hourly.cond.info; //小雨

                if (!tmp1) {
                    hourlyTmpText.setText(tmpText);   //温度
                } else {
                    hourlyTmpText.setText(Constant.getTmp(tmp) + "℉");   //温度
                }
                hourlyDateText.setText(dateText); //时间点

                //相对湿度的逻辑
                showHumTextview(hum, hourlyHumText);
                //加载天气状况图片
                showInfoImageView2(weatherInfo, hourlyImageview);
                //明天时间点
                showTomorrowTextView(tomorrow, tomorrowText);

                hourlyLayout.addView(view);  //把每个获取到的子布局添加到View中
            }
        }

        String fileTieInfo = weather.hourlyList.get(0).cond.info;
        if (fileTieInfo.contains("雨")) {
            String text = "未来几小时有" + weather.hourlyList.get(0).cond.info;

            if (fileTieInfo.contains("大")) {
                addNowWarningText(R.color.red);
                nowWarningTexts[2] =  "暴雨天气尽量注意安全哦";
            } else if (fileTieInfo.contains("雷")) {
                addNowWarningText(R.color.yellow);
                nowWarningTexts[2] =  "雷阵雨天气尽量待在室内哦";
            } else if (fileTieInfo.contains("阵")) {
                addNowWarningText(R.color.red);
                nowWarningTexts[2] = "阵雨天气多变记得带雨伞哦";
            } else {
                addNowWarningText(R.color.orange);
                nowWarningTexts[2] = "雨天尽量不要淋湿感冒哦";
            }
            nowWarningTexts[0] = text;
            nowWarningTexts[1] =  "出门时记得带雨伞哦";
            Log.d(TAG, text);
            if(playEvaptextThread == null) {
                isPlaying = true;
                index = -1;
                playEvaptextThread = new PlayEvaptextThread();
                playEvaptextThread.start();
            }
        }else {
            if(isWarning){
                if(playEvaptextThread == null) {
                    isPlaying = true;
                    index = -1;
                    playEvaptextThread = new PlayEvaptextThread();
                    playEvaptextThread.start();
                }
            }else {
                isPlaying = false;
            }

        }

        /**
         * 空气质量
         */
        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            aqiPm25Text.setText(weather.aqi.city.pm25);
        }


        /**
         * 日出日落
         */
        //获取日出日落时间点
        String sunrise = weather.forecastList.get(0).astro.sunrise;
        String sunset = weather.forecastList.get(0).astro.sunset;
        int srHour = Integer.valueOf(sunrise.substring(0, 2));
        int srMinute = Integer.valueOf(sunrise.substring(3, 5));

        int ssHour = Integer.valueOf(sunset.substring(0, 2));
        int ssMinute = Integer.valueOf(sunset.substring(3, 5));

        Log.d(TAG, srHour + "---/---" + srMinute);
        Log.d(TAG, ssHour + "---/---" + ssMinute);

        //设置日出时间
        sunView.setSunrise(srHour, srMinute);
        //设置日落时间
        sunView.setSunset(ssHour, ssMinute);

        //获取系统时间
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        if (hour >= srHour && hour < srHour + 1) {
            sunTitleTextview.setText("日出");
        } else if (hour >= srHour + 1 && hour < ssHour) {
            sunTitleTextview.setText("白天");
        } else if (hour >= ssHour && hour <= ssHour + 1) {
            sunTitleTextview.setText("日落");
        } else {
            sunTitleTextview.setText("夜晚");
        }

        sunView.setCurrentTime(hour, minute);


        /**
         * 生活建议
         */
        String suComfort = "舒适度：" + weather.suggestion.comfort.info;
        String suCarwash = "洗车指数：" + weather.suggestion.carWash.info;
        String suSport = "运动建议：" + weather.suggestion.sport.info;
        String suUv = "紫外线：" + weather.suggestion.ulight.info;
        suConfortText.setText(suComfort);
        suCarWashText.setText(suCarwash);
        suSportText.setText(suSport);
        suVuText.setText(suUv);

        showBackgroundView(v);

        weatherLayout.setVisibility(View.VISIBLE);

    }

    //改变字体的颜色
    private void addNowWarningText(int colorType) {
        if(isAdded()) { //判断当前的Fragment是否添加到Activity中
            nowWarningText.setTextColor(getResources().getColor(colorType));
        }
    }

    private void showBackgroundView(View v) {

        //改变view的背景色
        if (SetUtility.getFloatBg(v.getContext())) {
            weahourlyView.setBackgroundResource(R.color.colorWeather);
            weahourlyTop.setBackgroundResource(R.color.colorWeather);
            weaAqiView.setBackgroundResource(R.color.colorWeather);
            weaSunView.setBackgroundResource(R.color.colorWeather);

            weaComfortView.setBackgroundResource(R.color.colorWeather);
            weaWashView.setBackgroundResource(R.color.colorWeather);
            weaSportView.setBackgroundResource(R.color.colorWeather);
            weaVuView.setBackgroundResource(R.color.colorWeather);
        }


    }

    /**
     * 获取当前更新的时间点
     *
     * @param nowUpdateYMD
     * @param nowUpdateTexts
     * @param nowUpdateTimeText
     */
    private void showNowUpadateTime(String nowUpdateYMD, String nowUpdateTexts, TextView nowUpdateTimeText) {
        if (nowUpdateYMD.equals(BingPicUtility.getNowDate())) {
            nowUpdateTimeText.setText(nowUpdateTexts + '\t' + "更新发布");
        } else {
            nowUpdateTimeText.setText("昨天" + nowUpdateTexts + '\t' + "更新");
        }
    }

    private void showTomorrowTextView(String tomorrow, TextView tomorrowText) {
        if (BingPicUtility.getNowDate().equals(tomorrow)) {
            tomorrowText.setText("");
        } else {
            tomorrowText.setText("明");
        }
    }

    /**
     * 预警
     *
     * @param info
     * @param nowDgreeText
     * @param nowWarningText
     */
    private void showNowWarnning(int info, TextView nowDgreeText, EvaporateTextView nowWarningText, String nowDgreeTexts, boolean tmp) {
        if (info <= 0) {
            addNowWarningText(R.color.red);
            nowWarningText.animateText("寒冷红色预警 >");
            nowWarningTexts[0] = "温度降至零度以下";
            nowWarningTexts[1] = "尽量待在室内哦";
            nowWarningTexts[2] = "寒冷红色预警 >";
            isWarning = true;
        } else if (info > 0 && info <= 5) {
            addNowWarningText(R.color.orange);
            nowWarningText.animateText("寒冷橙色预警 >");
            nowWarningTexts[0] = "温度降至五度以下";
            nowWarningTexts[1] = "请做好保暖措施哦";
            nowWarningTexts[2] =  "寒冷橙色预警 >";
            isWarning = true;
        } else if (info > 5 && info <= 10) {
            addNowWarningText(R.color.yellow);
            nowWarningText.animateText("寒冷黄色预警 >");
            nowWarningTexts[0] = "温度降至十度以下";
            nowWarningTexts[1] = "注意衣服保暖哦";
            nowWarningTexts[2] = "寒冷黄色预警 >";
            isWarning = true;
        } else if (info > 32 && info <= 34) {
            addNowWarningText(R.color.yellow);
            nowWarningText.animateText("高温黄色预警 >");
            nowWarningTexts[0] = "夏天温度偏高";
            nowWarningTexts[1] =  "出门时注意防暑哦";
            nowWarningTexts[2] = "高温黄色预警 >";
            isWarning = true;
        } else if (info > 34 && info <= 36) {
            addNowWarningText(R.color.orange);
            nowWarningText.animateText("高温橙色预警 >");
            nowWarningTexts[0] = "夏天温度较高";
            nowWarningTexts[1] = "出门时可以带防晒伞防暑哦";
            nowWarningTexts[2] = "高温橙色预警 >";
            isWarning = true;
        } else if (info > 36) {
            addNowWarningText(R.color.red);
            nowWarningText.animateText("高温红色预警 >");
            nowWarningTexts[0] = "夏天温度很高";
            nowWarningTexts[1] = "尽量不要待在剧烈的阳光下哦";
            nowWarningTexts[2] = "高温红色预警 >";
            isWarning = true;
        }

        //显示当前温度
        if (!tmp) {
            nowDgreeText.setText(nowDgreeTexts);
        } else {
            nowDgreeText.setText(Constant.getTmp(info) + "℉");
        }
    }

    /**
     * 相对湿度
     *
     * @param hum
     * @param hourlyHumText
     */
    private void showHumTextview(Double hum, TextView hourlyHumText) {
        if (hum >= 80f && hum <= 100f) {
            hourlyHumText.setText(hum + "%");
        }
    }

    private void showInfoImageView(int infoCode, ImageView infoImageView) {
        //判断存放的图片
        if (infoCode == 100) { //晴
            infoImageView.setImageResource(R.mipmap.w100);
//                Animation animation1 = AnimationUtils.loadAnimation(getActivity(),R.anim.sun_rotate);
//                infoImageView.setAnimation(animation1);

        } else if (infoCode >= 101 && infoCode <= 103) {  //多云
            infoImageView.setImageResource(R.mipmap.w103);
        } else if (infoCode == 104 || (infoCode >= 500 && infoCode <= 508)) { //阴或者雾霾
            infoImageView.setImageResource(R.mipmap.w101);
        } else if (infoCode >= 300 && infoCode <= 305) { //小雨
            infoImageView.setImageResource(R.drawable.rain_small);
            AnimationDrawable drawable = (AnimationDrawable) infoImageView.getDrawable();
            drawable.start();
        } else if (infoCode > 305 && infoCode <= 313) { //大雨
            infoImageView.setImageResource(R.drawable.rain_anim);
            AnimationDrawable drawable1 = (AnimationDrawable) infoImageView.getDrawable();
            drawable1.start();
        } else if (infoCode >= 400 && infoCode <= 407) {//下雪
            infoImageView.setImageResource(R.drawable.snow_anim);
            AnimationDrawable drawable2 = (AnimationDrawable) infoImageView.getDrawable();
            drawable2.start();

        }
    }

    private void showInfoImageView2(String infoCode, ImageView infoImageView) {
        //判断存放的图片
        if (infoCode.equals("晴")) { //晴
            infoImageView.setImageResource(R.mipmap.w100);
//                Animation animation1 = AnimationUtils.loadAnimation(getActivity(),R.anim.sun_rotate);
//                infoImageView.setAnimation(animation1);

        } else if (infoCode.equals("多云")) {  //多云
            infoImageView.setImageResource(R.mipmap.w103);
        } else if (infoCode.equals("阴") || infoCode.equals("雾霾")) { //阴或者雾霾
            infoImageView.setImageResource(R.mipmap.w101);
        } else if (infoCode.equals("小雨")) { //小雨
            infoImageView.setImageResource(R.drawable.rain_small);
            AnimationDrawable drawable = (AnimationDrawable) infoImageView.getDrawable();
            drawable.start();
        } else if (infoCode.equals("中雨") || infoCode.equals("雷阵雨") || infoCode.equals("大雨")) { //大雨
            infoImageView.setImageResource(R.drawable.rain_anim);
            AnimationDrawable drawable1 = (AnimationDrawable) infoImageView.getDrawable();
            drawable1.start();
        } else if (infoCode.contains("雪")) {//下雪
            infoImageView.setImageResource(R.drawable.snow_anim);
            AnimationDrawable drawable2 = (AnimationDrawable) infoImageView.getDrawable();
            drawable2.start();
        }
    }

    // TODO: Rename and change types and number of parameters
    public static WeatherFragment createInstance(String weatherCity) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putString("cityName", weatherCity);
//        bundle.putInt("type",WEATHER_TPYE);
        fragment.setArguments(bundle);

        return fragment;
    }


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 1:
                    if (nowWarningTexts != null && nowWarningTexts.length > 0) {
                        if (index >= (nowWarningTexts.length -1)) {
                            index = 0;
                        }else {
                            index = index + 1;
                        }
                        nowWarningText.animateText(nowWarningTexts[index]);
//                        Toast.makeText(getActivity(), "打印第" +indexOf+"次", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 文字播放线程
     */
    public class PlayEvaptextThread extends Thread{
        @Override
        public void run() {
            while (isPlaying){
                try {
                    sleep(period);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume---激活碎片");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop---暂停碎片");
        if(playEvaptextThread != null) {
            isPlaying = false;
            index = -1;
            synchronized (playEvaptextThread) {
                playEvaptextThread.interrupt();
                playEvaptextThread = null;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.d(TAG, "onDetach--解除关联");
    }
}
