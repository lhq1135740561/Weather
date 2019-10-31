package com.example.lhq.weather.activity.data;

public class MusicMv {
    public static final String MV_PLAYURL = "https://api.bzqll.com/music/netease/topMvList?key=579621905&limit=100&offset=0";
    private String image;
    private int id;
    private String songs;
    //mv名字
    private String songName;
    private String url;

    //判断字体颜色
    private boolean isColor;

    public MusicMv(boolean isColor) {
        this.isColor = isColor;
    }

    public boolean isColor() {
        return isColor;
    }

    public void setColor(boolean color) {
        isColor = color;
    }

    public MusicMv(String image, int id, String songs, String url,boolean isColor,String songName) {
        this.image = image;
        this.id = id;
        this.songs = songs;
        this.url = url;
        this.isColor = isColor;
        this.songName = songName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongs() {
        return songs;
    }

    public void setSongs(String songs) {
        this.songs = songs;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
