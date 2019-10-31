package com.example.lhq.weather.activity.data;

import android.content.Context;

import org.litepal.LitePal;

import java.util.List;

public class MusicLabs {

    /**
     * 查询我的歌单数据库
     * @return
     */
    public static List<MyMusic> getMyMusicList(){

        List<MyMusic> myMusicList = LitePal.findAll(MyMusic.class);

        return myMusicList;
    }

    /**
     * 判断歌单添加的歌曲是否相同。
     * @param name
     * @return
     */
    public static boolean CompreteMusicName(String name,String songer){
        List<MyMusic> myMusics = getMyMusicList();
        for (MyMusic music : myMusics){
            if(music.getSong().equals(name) && music.getSonger().equals(songer)){
                return false;
            }
        }
        return true;
    }



    /**
     * 找到我的歌单的id
     * @param name
     * @return
     */
    public static int getMyMusicSongId(String name,String songer){
        int id = -1;
        List<MyMusic> myMusics = getMyMusicList();
        for (MyMusic myMusic : myMusics){
            if(myMusic.getSong().equals(name) && myMusic.getSonger().equals(songer)){
                id = myMusic.getId();
                return id;
            }
        }
        return id;
    }
}



