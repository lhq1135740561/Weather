package com.example.lhq.weather.activity.common;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    //添加活动
    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    //删除活动
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    //结束所以的活动
    public static void finishAll(){
        for (Activity activity : activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
