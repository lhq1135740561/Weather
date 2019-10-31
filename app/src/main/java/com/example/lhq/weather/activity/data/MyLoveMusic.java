package com.example.lhq.weather.activity.data;

/**
 * 喜欢的音乐暂时存储对象
 */
public class MyLoveMusic {
    private String song;
    private String songer;
    private String uri;
    private String pic;

    public MyLoveMusic(String song, String songer, String uri, String pic) {
        this.song = song;
        this.songer = songer;
        this.uri = uri;
        this.pic = pic;
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
}
