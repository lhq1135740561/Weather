package com.example.lhq.weather.activity.utils;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DateString {

    private static String mWay;

    private static String mYear;
    private static String mMonth;
    private static String mDay;
    private static String mWeek;

    /**
     * 获取当前星期几
     * @return
     */
    public static String getDateString(int id){
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK)+id); //获取当前星期几
        //6 7 8  7 8 9 10 11 12 13

        //0 1 2 3 4 5 6
        if("1".equals(mWay)){
            mWay = "天";
        }else if("2".equals(mWay)){
            mWay = "一";
        }else if("3".equals(mWay)){
            mWay = "二";
        }else if("4".equals(mWay)){
            mWay = "三";
        }else if("5".equals(mWay)){
            mWay = "四";
        }else if("6".equals(mWay)){
            mWay = "五";
        }else if("7".equals(mWay)){
            mWay = "六";
        }else if("8".equals(mWay)){
            mWay = "天";
        }else if("9".equals(mWay)){
            mWay = "一";
        }else if("10".equals(mWay)){
            mWay = "二";
        }else if("11".equals(mWay)){
            mWay = "三";
        }else if("12".equals(mWay)){
            mWay = "四";
        }else if("13".equals(mWay)){
            mWay = "五";
        }

        return "星期"+mWay;
    }


    /**
     * 获取当前几点
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getDateTime(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis()); //获取当前时间
        return format.format(date);
    }

    /**
     * 获取当前的年月日
     */
    public static List<String> getDateYyd(){

        List<String> dateLists = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR));
        mMonth = String.valueOf(c.get(Calendar.MONTH)+1);
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        mWeek = String.valueOf(c.get(Calendar.DAY_OF_WEEK));

        if("1".equals(mWeek)){
            mWeek = "天";
        }else if("2".equals(mWeek)){
            mWeek = "一";
        }else if("3".equals(mWeek)){
            mWeek = "二";
        }else if("4".equals(mWeek)){
            mWeek = "三";
        }else if("5".equals(mWeek)){
            mWeek = "四";
        }else if("6".equals(mWeek)){
            mWeek = "五";
        }else if("7".equals(mWeek)){
            mWeek = "六";
        }else if("8".equals(mWeek)){
            mWeek = "天";
        }else if("9".equals(mWeek)){
            mWeek = "一";
        }

        dateLists.add(mDay);
        dateLists.add(mYear+"."+mMonth);
        dateLists.add("星期"+mWeek);
        return dateLists;
    }

    /**
     * 获取当前系统的小时分钟
     */
    public static List<Integer> getHourMm(){
        List<Integer> hourMmLists = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        hourMmLists.add(hour);
        hourMmLists.add(minute);

        return hourMmLists;
    }

}
