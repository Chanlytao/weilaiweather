package com.weilaiweather.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.weilaiweather.android.gsons.DailyForecastBean;
import com.weilaiweather.android.gsons.Weather;
import com.weilaiweather.android.services.AutoUpdateService;
import com.weilaiweather.android.util.HttpUtil;
import com.weilaiweather.android.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = "WeatherActivity";
    private static final String TAG1 = "WeatherActivity1";
    private ScrollView scrollView;
    private TextView textCity;
    private TextView textUpdateText;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forcastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;

    private Button btnHome;
    private ImageView mImageView;
    public SwipeRefreshLayout refreshLayout;
    private String mWeatherId;
    // 滑动菜单功能
    public DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);


        scrollView = (ScrollView) findViewById(R.id.weather_layout);
        textCity = (TextView) findViewById(R.id.title_city);
        textUpdateText = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forcastLayout = (LinearLayout) findViewById(R.id.forcast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
        btnHome = (Button) findViewById(R.id.btn_home);
        mImageView = (ImageView) findViewById(R.id.bing_pic_img);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        String bingPic = prefs.getString("bing_pic",null);
        if (bingPic !=null){
            Glide.with(this).load(bingPic).into(mImageView);
        }else{
            loadBingPic();
        }
        Log.e(TAG1, "111"+weatherString);
        if (weatherString != null) {
            // 有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.getBasic().getId();
            showWeatherInfo(weather);
        } else {
            // 无缓存时从服务器查询数据
            mWeatherId = getIntent().getStringExtra("WeatherId");
            scrollView.setVisibility(View.VISIBLE);
            requestWeather(mWeatherId);
        }
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "onRefresh: "+mWeatherId);
                requestWeather(mWeatherId);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    public void requestWeather(String weatherId) {
        String weatherUrl = "https://free-api.heweather.com/v5/weather?city=" + weatherId + "&key=59219d67ae834f5d8eee40683593b909";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气数据失败", Toast.LENGTH_SHORT).show();
                        refreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather wrathers = Utility.handleWeatherResponse(responseText);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (wrathers != null && "ok".equals(wrathers.getStatus())) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                           showWeatherInfo(wrathers);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气数据失败", Toast.LENGTH_SHORT).show();
                        }
                        refreshLayout.setRefreshing(false);
                    }
                });
                loadBingPic();
            }
        });
    }

    private void showWeatherInfo(Weather weather) {

        String cityName = weather.getBasic().getCity();
        Log.e(TAG, "showWeatherInfo: "+weather.getBasic().getUpdate().getLoc());
        String updateTime = weather.getBasic().getUpdate().getLoc().split(" ")[1];

        String degree = weather.getNow().getTmp() + "℃";
        String weatherInfo = weather.getNow().getCond().getTxt();
        textCity.setText(cityName);
        textUpdateText.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forcastLayout.removeAllViews();
        for (DailyForecastBean d : weather.getDaily_forecast()) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forcastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(d.getDate());
            infoText.setText(d.getCond().getTxt_d());
            maxText.setText(d.getTmp().getMax());
            minText.setText(d.getTmp().getMin());
            forcastLayout.addView(view);
        }
        if (weather.getAqi() != null) {
            aqiText.setText(weather.getAqi().getCity().getAqi());
            pm25Text.setText(weather.getAqi().getCity().getPm25());
        }
        String comfort = "舒适度" + weather.getSuggestion().getComf().getTxt();
        String carWash = "洗车指数" + weather.getSuggestion().getCw().getTxt();
        String sport = "运动建议" + weather.getSuggestion().getSport().getTxt();
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        scrollView.setVisibility(View.VISIBLE);

        if (weather !=null && "ok".equals(weather.getStatus())){
            // 启动服务
            Intent intent = new Intent(this, AutoUpdateService.class);
            startService(intent);
        }else{
            Toast.makeText(WeatherActivity.this, "获取天气失败", Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * 加载每日一图
     */
    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
               e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(mImageView);
                    }
                });
            }
        });
    }
}
