package com.example.lhq.weather.activity.db;

/**
 * 用于存储收藏城市的实体类，配合adapter
 */
public class WeatherCity {

    private int id;

    private String cityName;
    private String cityTmp;
    private String cityInfo;
    //新属性
    private String cityGrade; //良
    private String cityHum;   //湿度
    private String cityWind;   //风力
    private String cityMax;   //30
    private String cityMin;  //25
    //类型
    private int type;


    public WeatherCity(){

    }
    public WeatherCity(String cityName){
        this.cityName = cityName;
    }

    public WeatherCity(String cityName, String cityTmp, String cityInfo) {
        this.cityName = cityName;
        this.cityTmp = cityTmp;
        this.cityInfo = cityInfo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityTmp() {
        return cityTmp;
    }

    public void setCityTmp(String cityTmp) {
        this.cityTmp = cityTmp;
    }

    public String getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(String cityInfo) {
        this.cityInfo = cityInfo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getCityGrade() {
        return cityGrade;
    }

    public void setCityGrade(String cityGrade) {
        this.cityGrade = cityGrade;
    }

    public String getCityHum() {
        return cityHum;
    }

    public void setCityHum(String cityHum) {
        this.cityHum = cityHum;
    }

    public String getCityWind() {
        return cityWind;
    }

    public void setCityWind(String cityWind) {
        this.cityWind = cityWind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityMax() {
        return cityMax;
    }

    public void setCityMax(String cityMax) {
        this.cityMax = cityMax;
    }

    public String getCityMin() {
        return cityMin;
    }

    public void setCityMin(String cityMin) {
        this.cityMin = cityMin;
    }
}
