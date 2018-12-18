package com.yc.compare.bean;

import java.util.List;

/**
 * Created by myflying on 2018/12/5.
 */
public class CountryWrapper {
    private String name;
    private List<CountryInfo> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CountryInfo> getList() {
        return list;
    }

    public void setList(List<CountryInfo> list) {
        this.list = list;
    }
}
