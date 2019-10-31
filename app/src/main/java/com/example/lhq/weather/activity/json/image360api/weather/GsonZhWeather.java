package com.example.lhq.weather.activity.json.image360api.weather;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class GsonZhWeather {

    public static ZhWeather handleZhWeatherResponse(String response){

        try {
            JSONObject object = new JSONObject(response);
            String data = object.getJSONObject("RESULT").toString();

            Gson gson = new Gson();

            return gson.fromJson(data,ZhWeather.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
