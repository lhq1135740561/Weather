package com.example.lhq.weather.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.db.Tmp;
import com.example.lhq.weather.activity.db.Wind;

import java.util.List;

public class SetWindAdapter extends BaseAdapter {

    private List<Wind> windList;

    private Context context;

    public SetWindAdapter(List<Wind> windList, Context context) {
        this.windList = windList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return windList.size();
    }

    @Override
    public Object getItem(int i) {
        return windList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View converView, ViewGroup viewGroup) {
        Wind wind = windList.get(i);
        View view1;
        ViewHoder hoder = new ViewHoder();
        if(converView == null){
            view1 = LayoutInflater.from(context).inflate(R.layout.set_unit_wind_item,viewGroup,false);
            hoder.windText = view1.findViewById(R.id.wind_textview);
            hoder.imageId = view1.findViewById(R.id.wind_imageview);
            view1.setTag(hoder);
        }else {
            view1 = converView;
            hoder = (ViewHoder) view1.getTag();
        }

        hoder.windText.setText(wind.getTmp());
        hoder.imageId.setImageResource(wind.getImageId());
        return view1;
    }

    public class ViewHoder{

        TextView windText;
        ImageView imageId;

    }
}
