package com.weilaiweather.android.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.weilaiweather.android.gsons.Weather;
import com.weilaiweather.android.util.HttpUtil;
import com.weilaiweather.android.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        updateWeather();
        updateBingPic();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anhour = 8 * 60 * 60 * 1000; // 8 小时毫秒数
        long triggerAtime = SystemClock.elapsedRealtime() + anhour;
        Intent i = new Intent(this,AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtime,pi);
        return super.onStartCommand(intent, flags, startId);
    }

    // 更新天气信息
    private void updateWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("werther",null);
        if (weatherString != null){
            final Weather weather = Utility.handleWeatherResponse(weatherString);
            String weatherId = weather.getBasic().getId();
            String weatherUrl = "https://free-api.heweather.com/v5/weather?city=" + weatherId + "&key=59219d67ae834f5d8eee40683593b909";
            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.i("AutoUpdateService","onResponse: 后台服务更新111");
                    String responseText = response.body().string();
                    Weather weather1 = Utility.handleWeatherResponse(responseText);
                    if (weather1 != null && "ok".equals(weather1.getStatus())) {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("weather", responseText);
                        editor.apply();
                    }
                }
            });
        }
    }


    // 更新图片信息
    private void updateBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("AutoUpdateService","onResponse: 后台服务更新222");
                String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
            }
        });
    }


}
