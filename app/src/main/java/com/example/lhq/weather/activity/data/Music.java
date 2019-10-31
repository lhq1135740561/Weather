package com.example.lhq.weather.activity.data;

/**
 * Created by lhq1135740561 on 2018/2/6.
 */

public class Music {
    private int image_add;
    private int image_point;
    private String song;
    private String songer;
    private String uri;
    private String pic;
    private boolean islove;

    private int myMusicId;

    public Music(int image_add, int image_point, String song, String songer, String uri,String pic,boolean islove,int myMusicId) {
        this.image_add = image_add;
        this.image_point = image_point;
        this.song = song;
        this.songer = songer;
        this.uri = uri;
        this.pic = pic;
        this.islove = islove;
        this.myMusicId = myMusicId;
    }

    public int getMyMusicId() {
        return myMusicId;
    }

    public void setMyMusicId(int myMusicId) {
        this.myMusicId = myMusicId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getImage_add() {
        return image_add;
    }

    public void setImage_add(int image_add) {
        this.image_add = image_add;
    }

    public int getImage_point() {
        return image_point;
    }

    public void setImage_point(int image_point) {
        this.image_point = image_point;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSonger() {
        return songer;
    }

    public void setSonger(String songer) {
        this.songer = songer;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public boolean isIslove() {
        return islove;
    }

    public void setIslove(boolean islove) {
        this.islove = islove;
    }
}
