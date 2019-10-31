package com.example.lhq.weather.activity.adapter.music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.activity.StepActivity;
import com.example.lhq.weather.activity.data.MyMusic;
import com.example.lhq.weather.activity.data.SreachMusic;

import java.util.ArrayList;
import java.util.List;

public class MyMusicAdapter extends BaseAdapter {
    private List<MyMusic> musicList;
    private Context context;

    private MusicAdapter.MusicOnListClickListener listener;

    class ViewHolder{
        ImageView image_point;
        ImageView image_horn;
        TextView text_song_id;
        TextView text_song;
        TextView text_songer;

    }
    public MyMusicAdapter(List<MyMusic> musicList, Context context, StepActivity activity) {
        this.musicList = musicList;
        this.context = context;
        this.listener = activity;
    }

    @Override
    public int getCount() {
        return musicList.size();
    }

    @Override
    public Object getItem(int position) {
        return musicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //刷新数据
    public void refresh(List<MyMusic> music){
        musicList = music;
        notifyDataSetChanged();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //实例化内部类
         ViewHolder viewHolder = new ViewHolder();
        if(convertView==null){
            //找到布局
            convertView = LayoutInflater.from(context).inflate(R.layout.music_my_item,null);
            viewHolder.image_point = (ImageView) convertView.findViewById(R.id.music_Myitem_point);
            viewHolder.text_song_id = convertView.findViewById(R.id.music_Myitem_id);
            viewHolder.text_song = (TextView) convertView.findViewById(R.id.music_Myitem_song);
            viewHolder.text_songer = (TextView) convertView.findViewById(R.id.music_Myitem_songer);
            viewHolder.image_horn = convertView.findViewById(R.id.music_Myitem_horn);
            //设置标签
            convertView.setTag(viewHolder);
        }else {
            //获取标签
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //给控件赋值，获取实体类的属性
        final MyMusic music = musicList.get(position);
        viewHolder.image_point.setImageResource(music.getImage_point());
        viewHolder.text_song.setText(music.getSong());
        viewHolder.text_songer.setText(music.getSonger());
        viewHolder.text_song_id.setText(position+1+"");

        if(music.getPosition() == position){
            viewHolder.text_song_id.setVisibility(View.GONE);
            viewHolder.image_horn.setVisibility(View.VISIBLE);
        }else {
            viewHolder.text_song_id.setVisibility(View.VISIBLE);
            viewHolder.image_horn.setVisibility(View.GONE);
        }

        viewHolder.image_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //通过接口调用这个方法，删除这首歌曲
                if(listener != null)
                listener.onRemoveItemMyMusic(view,position);
            }
        });


        return convertView;
    }
}
