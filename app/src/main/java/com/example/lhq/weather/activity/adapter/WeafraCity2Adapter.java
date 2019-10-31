package com.example.lhq.weather.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.common.Constant;
import com.example.lhq.weather.activity.db.WeatherCity;
import com.example.lhq.weather.activity.fragment.WeatherCityFragment;
import com.example.lhq.weather.activity.utils.SetUtility;

import java.util.List;

public class WeafraCity2Adapter extends RecyclerView.Adapter<WeafraCity2Adapter.ViewHolder> {

    private List<WeatherCity> weatherCityList;

    private WeaFraCityAdapter.MyOnSlidingViewClickListener myclickListener;

    private Context context;

    private String[] data = {"km/h", "千米/每小时", "m/s"};

    public WeafraCity2Adapter(List<WeatherCity> weatherCityList, WeatherCityFragment fragment, Context context) {
        this.weatherCityList = weatherCityList;
        this.myclickListener = fragment;
        this.context = context;
    }

    @NonNull
    @Override
    public WeafraCity2Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fra_weather_city_item2,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        WeatherCity weatherCity = weatherCityList.get(i);
        //设置数据
        holder.cityNameText.setText(weatherCity.getCityName());
        holder.cityInfoText.setText(weatherCity.getCityInfo());

        holder.cityGradeText.setText("空气"+weatherCity.getCityGrade());
        holder.cityHumText.setText("湿度"+weatherCity.getCityHum()+"%");

        int cityMax = Integer.valueOf(weatherCity.getCityMax());
        int cityMin = Integer.valueOf(weatherCity.getCityMin());
        int cityTmp = Integer.valueOf(weatherCity.getCityTmp());

        if(SetUtility.getWind(context)==0){
            holder.cityWindText.setText(weatherCity.getCityWind()+data[0]);
        }else if(SetUtility.getWind(context)==1){
            holder.cityWindText.setText(weatherCity.getCityWind()+data[1]);
        }

        //摄氏度-华氏度
        if(SetUtility.getTmp(context) == 0){
            holder.cityMax.setText(weatherCity.getCityMax());
            holder.cityMin.setText(weatherCity.getCityMin()+"℃");
            holder.cityTmpText.setText(weatherCity.getCityTmp()+"℃");
            SetUtility.setTmp(context,0);
        }else if(SetUtility.getTmp(context) == 1){
            holder.cityMax.setText(Constant.getTmp( cityMax)+"");
            holder.cityMin.setText(Constant.getTmp( cityMin)+"℉");
            holder.cityTmpText.setText(Constant.getTmp( cityTmp)+"℉");
            SetUtility.setTmp(context,1);
        }

        String info = weatherCity.getCityInfo();
        if(info.equals("晴")){
            holder.cityInfoImage.setImageResource(R.mipmap.f103);
        }else if(info.equals("多云")){
            holder.cityInfoImage.setImageResource(R.mipmap.f100);
        }else if(info.equals("阴")){
            holder.cityInfoImage.setImageResource(R.mipmap.f102);
        }else if(info.contains("雨")){
            holder.cityInfoImage.setImageResource(R.mipmap.f101);
        }else {
            holder.cityInfoImage.setImageResource(R.mipmap.f100);
        }

        if(i == 0){
            holder.cityLocationImage.setVisibility(View.VISIBLE);
        }else {
            holder.cityLocationImage.setVisibility(View.INVISIBLE);
        }

        //Item控件的点击事件
        holder.allView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getLayoutPosition();
                myclickListener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return weatherCityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cityNameText; //深圳
        TextView cityInfoText; //多云
        TextView cityTmpText;  //30℃
        ImageView cityInfoImage;
        ImageView cityLocationImage;
        TextView cityGradeText; //空气良
        TextView cityHumText;   //湿度
        TextView cityWindText;  //风力
        TextView cityMax;   //高温度
        TextView cityMin;   //低温
        View allView;

        public ViewHolder(@NonNull View view) {
            super(view);
            cityNameText = view.findViewById(R.id.fra_city_cities);
            cityInfoText = view.findViewById(R.id.fra_city_info);
            cityTmpText = view.findViewById(R.id.fra_city_tmp);
            cityInfoImage = view.findViewById(R.id.fra_city_info_image);
            cityLocationImage = view.findViewById(R.id.fra_city_location_image);
            cityGradeText = view.findViewById(R.id.fra_city_grade);
            cityHumText = view.findViewById(R.id.fra_city_hum);
            cityWindText = view.findViewById(R.id.fra_city_wind);
            cityMax = view.findViewById(R.id.fra_city_max);
            cityMin = view.findViewById(R.id.fra_city_min);
            allView = view;
        }
    }
}
