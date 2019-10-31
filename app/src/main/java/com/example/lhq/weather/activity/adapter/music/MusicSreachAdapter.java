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
import com.example.lhq.weather.activity.data.Music;
import com.example.lhq.weather.activity.data.SreachMusic;

import java.util.List;

public class MusicSreachAdapter extends BaseAdapter {
    private List<SreachMusic> musicList;
    private Context context;

    private MusicAdapter.MusicOnListClickListener listener;

    class ViewHolder{
        ImageView image_add;
        ImageView image_point;
        TextView text_song;
        TextView text_songer;

    }

    public MusicSreachAdapter(List<SreachMusic> musicList, Context context, StepActivity activity) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //实例化内部类
        ViewHolder viewHolder = new ViewHolder();
        if(convertView==null){
            //找到布局
            convertView = LayoutInflater.from(context).inflate(R.layout.music_sreach_item,null);
            viewHolder.image_add = (ImageView) convertView.findViewById(R.id.music_Sitem_image);
            viewHolder.image_point = (ImageView) convertView.findViewById(R.id.music_Sitem_point);
            viewHolder.text_song = (TextView) convertView.findViewById(R.id.music_Sitem_song);
            viewHolder.text_songer = (TextView) convertView.findViewById(R.id.music_Sitem_songer);
            //设置标签
            convertView.setTag(viewHolder);
        }else {
            //获取标签
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //给控件赋值，获取实体类的属性
        final SreachMusic music = musicList.get(position);
        viewHolder.image_add.setImageResource(music.getImage_add());

        viewHolder.text_song.setText(music.getSong());
        viewHolder.text_songer.setText(music.getSonger());

        boolean isLove = music.isIslove();
        if(isLove){
            viewHolder.image_point.setImageResource(R.mipmap.purple_love);
        }else {
            viewHolder.image_point.setImageResource(R.mipmap.gray_love);
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.image_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String song = music.getSong();
                String songer = music.getSonger();
                String url = music.getUri();
                String pic = music.getPic();
                finalViewHolder.image_point.setImageResource(R.mipmap.purple_love);
                listener.onItemMyMusic(view,url,song,songer,pic);
            }
        });

        return convertView;
    }
}
