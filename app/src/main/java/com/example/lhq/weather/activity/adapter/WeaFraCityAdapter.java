package com.example.lhq.weather.activity.adapter;
/**
 * 收藏喜欢城市适配器Rec
 * yclerView列出城市
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.db.WeatherCity;
import com.example.lhq.weather.activity.fragment.WeatherCityFragment;

import java.util.List;

public class WeaFraCityAdapter extends RecyclerView.Adapter<WeaFraCityAdapter.ViewHolder>{

    private List<WeatherCity> weatherCitiesList;

    private MyOnSlidingViewClickListener myclickListener;

    private Context context;

    public WeaFraCityAdapter(List<WeatherCity> weatherCitiesList, WeatherCityFragment fragment,Context context) {
        this.weatherCitiesList = weatherCitiesList;
        this.myclickListener = fragment;
        this.context = context;
    }

    @NonNull
    @Override
    public WeaFraCityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fra_weather_city_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final WeaFraCityAdapter.ViewHolder holder, int i) {
        WeatherCity weatherCities = weatherCitiesList.get(i);
        //设置text
        holder.cityNameText.setText(weatherCities.getCityName());
        holder.cityInfoText.setText(weatherCities.getCityInfo());
        holder.cityTmpText.setText(weatherCities.getCityTmp() + "℃");

//        holder.relativeLayout.getLayoutParams().width = Utility.getWindowWidth(context)-40;

        //删除控件监听
        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myclickListener.onDeleteBtnClick(view, holder.getLayoutPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return weatherCitiesList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityNameText;
        TextView cityInfoText;
        TextView cityTmpText;
        ImageView deleteImage;

        View allView;

        public ViewHolder(@NonNull View view) {
            super(view);
            //添加id
            cityNameText = view.findViewById(R.id.add_city_cities);
            cityInfoText = view.findViewById(R.id.add_city_info);
            cityTmpText = view.findViewById(R.id.add_city_tmp);
            deleteImage = view.findViewById(R.id.add_city_delete);
            allView = view;
        }
    }



    /**
     * 创建一个接口点击or删除的回调接口，在Fragment中使用此适配器时需要实现这个接口
     */
    public interface MyOnSlidingViewClickListener {

        void onItemClick(View view, int position);

        void onDeleteBtnClick(View view, int position);

    }
}
