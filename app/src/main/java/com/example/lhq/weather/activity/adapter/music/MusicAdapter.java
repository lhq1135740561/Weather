package com.example.lhq.weather.activity.adapter.music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lhq.weather.activity.activity.StepActivity;
import com.example.lhq.weather.activity.data.Music;
import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.data.MusicLabs;
import com.example.lhq.weather.activity.data.MyMusic;

import java.util.List;

public class MusicAdapter extends BaseAdapter {
    private List<Music> musicList;
    private Context context;
    private boolean isLove;

    private MusicOnListClickListener listener;

    class ViewHolder{
        ImageView image_add;
        ImageView image_point;
        TextView text_song;
        TextView text_songer;

        ImageView image_note;
        TextView text_my_id;

    }

    public MusicAdapter(List<Music> musicList, Context context, StepActivity activity) {
        this.musicList = musicList;
        this.context = context;
        this.listener = (MusicOnListClickListener) activity;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        //实例化内部类
        ViewHolder viewHolder = new ViewHolder();
        if(convertView==null){
            //找到布局
            convertView = LayoutInflater.from(context).inflate(R.layout.music_item,null);
            viewHolder.image_add = (ImageView) convertView.findViewById(R.id.music_item_image);
            viewHolder.image_point = (ImageView) convertView.findViewById(R.id.music_item_point);
            viewHolder.text_song = (TextView) convertView.findViewById(R.id.music_item_song);
            viewHolder.text_songer = (TextView) convertView.findViewById(R.id.music_item_songer);
            viewHolder.image_note = convertView.findViewById(R.id.music_note);
            viewHolder.text_my_id = convertView.findViewById(R.id.music_my_id);

            //设置标签
            convertView.setTag(viewHolder);
        }else {
            //获取标签
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //给控件赋值，获取实体类的属性
        final Music music = musicList.get(position);
        viewHolder.image_add.setImageResource(music.getImage_add());
        viewHolder.text_song.setText(music.getSong());
        viewHolder.text_songer.setText(music.getSonger());
        isLove = music.isIslove();
        if(isLove){
            viewHolder.image_point.setImageResource(R.mipmap.purple_love);
            viewHolder.image_note.setVisibility(View.VISIBLE);
            viewHolder.text_my_id.setText(music.getMyMusicId()+"");
        }else {
            viewHolder.image_point.setImageResource(R.mipmap.gray_love);
            viewHolder.image_note.setVisibility(View.GONE);
            viewHolder.text_my_id.setText("");
        }

        viewHolder.image_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String song = music.getSong();
                String songer = music.getSonger();
                String url = music.getUri();
                String pic = music.getPic();
                listener.onItemLoveMusic(view,url,song,songer,pic);
            }
        });

        final ViewHolder finalViewHolder = viewHolder;

        viewHolder.image_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String song = music.getSong();
                String songer = music.getSonger();
                String url = music.getUri();
                String pic = music.getPic();
                finalViewHolder.image_point.setImageResource(R                .mipmap.purple_love);
                listener.onItemMyMusic(view,url,song,songer,pic);
            }
        });

        return convertView;
    }

    /**
     * 创建一个接口点击添加下一首想播放的歌曲的回调接口
     */
    public interface MusicOnListClickListener{



        void onItemLoveMusic(View view,String url,String song,String songer,String pic);

        void onItemMyMusic(View view,String url,String song,String songer,String pic);

        void onRemoveItemMyMusic(View view,int position);
    }
}
