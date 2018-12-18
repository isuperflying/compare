package com.yc.compare.bean;

import java.util.List;

/**
 * Created by myflying on 2018/12/3.
 */
public class CountryInfoRet extends ResultInfo {
    private List<CountryInfo> data;

    public List<CountryInfo> getData() {
        return data;
    }

    public void setData(List<CountryInfo> data) {
        this.data = data;
    }
}
