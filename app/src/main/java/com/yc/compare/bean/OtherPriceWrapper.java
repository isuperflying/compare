package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by myflying on 2018/12/19.
 */
public class OtherPriceWrapper {
    private List<OtherPriceInfo> list;

    @SerializedName("search_list")
    private List<OtherPriceInfo> searchList;

    public List<OtherPriceInfo> getList() {
        return list;
    }

    public void setList(List<OtherPriceInfo> list) {
        this.list = list;
    }

    public List<OtherPriceInfo> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<OtherPriceInfo> searchList) {
        this.searchList = searchList;
    }
}
