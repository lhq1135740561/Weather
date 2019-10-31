package com.example.lhq.weather.activity.adapter.music;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.activity.mv.MvActivity;
import com.example.lhq.weather.activity.data.MusicMv;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MusicMvAdapter extends RecyclerView.Adapter<MusicMvAdapter.ViewHoder> {

    private List<MusicMv> musicMvList;
    private Context mContext;

    public MusicMvAdapter(List<MusicMv> musicMvList, Context mContext) {
        this.musicMvList = musicMvList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MusicMvAdapter.ViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.music_mv_item, viewGroup, false);
        ViewHoder hoder = new ViewHoder(view);
        return hoder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MusicMvAdapter.ViewHoder hoder, int i) {
        MusicMv mv = musicMvList.get(i);
        //设置数据到控件上
        boolean iscolor  = mv.isColor();
        if (iscolor) {
            hoder.mvIdTextview.setTextColor(mContext.getResources().getColor(R.color.music_bg));
        } else {
            hoder.mvIdTextview.setTextColor(mContext.getResources().getColor(R.color.colorWeather));
        }
        Glide.with(mContext).load(mv.getImage()).into(hoder.mvImageView);
        hoder.mvSongsTextview.setText(mv.getSongs());
        hoder.mvIdTextview.setText(mv.getId() + "");

        //跳转到MV视频界面
        hoder.allView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //把MV的信息传值过去
                String mvUrl = musicMvList.get(hoder.getLayoutPosition()).getUrl();
                String mvPic = musicMvList.get(hoder.getLayoutPosition()).getImage();
                String songName = musicMvList.get(hoder.getLayoutPosition()).getSongName();

                MvActivity.actionStart(mContext, mvUrl,mvPic,songName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicMvList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        ImageView mvImageView;
        TextView mvIdTextview;
        TextView mvSongsTextview;
        View allView;

        public ViewHoder(@NonNull View view) {
            super(view);
            mvImageView = view.findViewById(R.id.mv_item_image);
            mvIdTextview = view.findViewById(R.id.mv_item_id);
            mvSongsTextview = view.findViewById(R.id.mv_item_songs);
            allView = view;
        }
    }
}
