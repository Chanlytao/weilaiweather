package com.weilaiweather.android.gsons;

/**
 * Created by Lucky on 2017/6/27.
 */

public class Now {

    public Cond cond;
    public String fl;
    public String hum;
    public String pcpn;
    public String pres;
    public String tmp;
    public String vis;
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
