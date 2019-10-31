package com.example.lhq.weather.activity.data;

import org.litepal.crud.LitePalSupport;

public class MyMusic extends LitePalSupport{

    private int id;

    private int position;

    private int image_point;
    private String song;
    private String songer;
    private String uri;

    private String pic;

    public MyMusic() {

    }

    public MyMusic(int image_point, String song, String songer, String uri, String pic,int id,int position) {
        this.image_point = image_point;
        this.song = song;
        this.songer = songer;
        this.uri = uri;
        this.pic = pic;
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
