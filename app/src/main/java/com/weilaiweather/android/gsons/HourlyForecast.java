package com.weilaiweather.android.gsons;

import java.util.Date;

/**
 * Created by Lucky on 2017/6/27.
 */

public class HourlyForecast {

    public Cond cond;
    public Date date;
    public String hum;
    public String pop;
    public String pres;
    public String Tmp;
    public Wind wind;

    public class Cond{
        public String code;
        public String txt;
    }

    public class Wind{
        public String deg;
        public String dir;
        public String sc;
        public String spd;
    }
}
