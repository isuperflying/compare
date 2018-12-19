package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by myflying on 2018/12/12.
 */
public class CollectTypeWrapper {
    @SerializedName("opt_data")

    private List<CollectType> optData;

    public List<CollectType> getOptData() {
        return optData;
    }

    public void setOptData(List<CollectType> optData) {
        this.optData = optData;
    }
}
