package com.weilaiweather.android.gsons;

import java.util.List;

/**
 * Created by Lucky on 2017/6/27.
 */

public class Weather {
    public AQI aqi;
    public Basic basic;
    public List<DailyForecast> dailyForecastList;
    public List<HourlyForecast> hourlyForecastList;
    public Now now;
    public String status;
    public Suggestion suggestion;
}
