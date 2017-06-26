package com.weilaiweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Lucky on 2017/6/26.
 * //市的信息
 */

public class City extends DataSupport{

    // 当前市所属省的id值
    private int provinceId;
    // 城市名
    private String cityName;
    // 城市代号
    private int cityCore;

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCore() {
        return cityCore;
    }

    public void setCityCore(int cityCore) {
        this.cityCore = cityCore;
    }
}
