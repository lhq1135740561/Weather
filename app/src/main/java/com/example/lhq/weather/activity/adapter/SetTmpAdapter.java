package com.example.lhq.weather.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.activity.SetActivity;
import com.example.lhq.weather.activity.db.Tmp;
import com.example.lhq.weather.activity.utils.SetUtility;

import java.util.List;

public class SetTmpAdapter extends BaseAdapter {

    private List<Tmp> tmpList;

    private Context context;

    public SetTmpAdapter(List<Tmp> tmpList, Context context) {
        this.tmpList = tmpList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return tmpList.size();
    }

    @Override
    public Object getItem(int i) {
        return tmpList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int i, View converView, ViewGroup viewGroup) {
        Tmp tmp = tmpList.get(i);
        View view;
        ViewHoder hoder;
        if(converView == null){
            view = LayoutInflater.from(context).inflate(R.layout.set_unit_tmp_item,viewGroup,false);
            hoder = new ViewHoder();
            hoder.tmpText = view.findViewById(R.id.tmp_textview);
            hoder.imageId = view.findViewById(R.id.tmp_imageview);
            view.setTag(hoder); //将ViewHoder存储到View中
        }else {
            view = converView;
            hoder = (ViewHoder) view.getTag();
        }

        hoder.tmpText.setText(tmp.getTmp());
        hoder.imageId.setImageResource(tmp.getImageId());

        return view;
    }

    class ViewHoder{
        TextView tmpText;
        ImageView imageId;
    }
}
