package com.example.lhq.weather.activity.db;

import android.content.Context;

import com.example.lhq.weather.activity.activity.WeatherActivity;
import com.example.lhq.weather.activity.common.Constant;
import com.example.lhq.weather.activity.data.Image360Url;
import com.example.lhq.weather.activity.fragment.WeatherFragment;
import com.example.lhq.weather.activity.utils.Utility;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

/**
 * 收藏的城市列表库
 */
public class CityLab {

    /**
     * 得到收藏城市数据库
     *
     * @return
     */
    public static List<City> getCityList(Context context) {
        //查询数据库中的城市所有数据
        List<City> cities = LitePal.findAll(City.class);
//        cities.add(0,new City("宝安"));

        String cityName = Utility.getLocationDistrictName(context);

        City city = new City();
        city.setCityName(cityName);
        cities.add(0, city);

        return cities;
    }

    /**
     * 360壁纸图片url集合
     * @return
     */
    public static List<Image360Url> getImageUrlList(){

        List<Image360Url> urlList = LitePal.findAll(Image360Url.class);

        return urlList;
    }



    /**
     * 根据城市名字找到对应ID
     * 可用于删除感兴趣的城市时，根据城市名字找到ID，然后对应删除数据库中的数据
     *
     * @param name
     * @return
     */
    public static long getCityId(String name, Context context) {

        int id = 0;
        List<City> cities = getCityList(context);

        for (City city : cities) {
            if (name.equals(city.getCityName())) {
                id = city.getId();
            }
        }
        return id;
    }

    /**
     * 判断搜索的城市名是否已存在
     *
     * @param name
     * @return
     */
    public static boolean CompreteCityName(String name, Context context) {

        List<City> cityList = getCityList(context);

        for (City city : cityList) {
            if (name.equals(city.getCityName())) {
                return false;
            }
        }
        return true;

    }

    /**
     * 获取下标为0城市名
     * @return
     */
    public static boolean isFirst(){

      for (int i = 0; i< Constant.fragmentList.size(); i++){
          if(i==0){
              return true;
          }
      }
        return false;
    }
}
