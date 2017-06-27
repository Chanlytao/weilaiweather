package com.weilaiweather.android.gsons;



/**
 * Created by Lucky on 2017/6/27.
 */

public class DailyForecast {

    public Astro astro;
    public Cond cond;
    public String date;
    public String hum;
    public float pcpn;
    public String pres;
    public String uv;
    public String vis;
    public Tmp tmp;
    public Wind wind;

    public class Astro{
        public String mr;
        public String ms;
        public String sr;
        public String ss;
    }

    public class Cond{
        public String code_d;
        public String code_n;
        public String txt_d;
        public String txt_n;
    }

    public class Tmp{
        public int max;
        public int min;
    }

    public class Wind{
        public String deg;
        public String dir;
        public String sc;
        public String spd;
    }
}
