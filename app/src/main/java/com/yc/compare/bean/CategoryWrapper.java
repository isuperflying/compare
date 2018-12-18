package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by myflying on 2018/12/3.
 */
public class CategoryWrapper extends ResultInfo {

    private List<CategoryInfo> list;

    @SerializedName("hot_country")
    private List<CountryInfo> hotCountry;

    @SerializedName("all_country")
    private List<CountryWrapper> allCountry;

    public List<CategoryInfo> getList() {
        return list;
    }

    public void setList(List<CategoryInfo> list) {
        this.list = list;
    }

    public List<CountryInfo> getHotCountry() {
        return hotCountry;
    }

    public void setHotCountry(List<CountryInfo> hotCountry) {
        this.hotCountry = hotCountry;
    }

    public List<CountryWrapper> getAllCountry() {
        return allCountry;
    }

    public void setAllCountry(List<CountryWrapper> allCountry) {
        this.allCountry = allCountry;
    }
}
