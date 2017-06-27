package com.weilaiweather.android.gsons;




/**
 * Created by Lucky on 2017/6/27.
 */

public class Basic {
    public String city;
    public String cnty;
    public String id;
    public float lat;
    public float lon;
    public Update update;

    public class Update{
        public String loc;
        public String utc;
    }
}
