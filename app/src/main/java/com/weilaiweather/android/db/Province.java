package com.weilaiweather.android.db;


import org.litepal.crud.DataSupport;

/**
 * Created by Lucky on 2017/6/26.
 * 存放省份信息
 */

public class Province extends DataSupport {

    // 省份id
    private int id;
    //  省份名
    private String provinceName;
    // 省份代号
    private int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
