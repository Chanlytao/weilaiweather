package com.weilaiweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Lucky on 2017/6/26.
 *
 * 县的信息
 */

public class County extends DataSupport{

    private int id;
    // 县名
    private String countyName;
    // 县所对应的天气id
    private String WeatherId;
    // 记录当前县所属市的id
    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return WeatherId;
    }

    public void setWeatherId(String weatherId) {
        WeatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
