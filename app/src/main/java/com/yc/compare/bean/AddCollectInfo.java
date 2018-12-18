package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

public class AddCollectInfo {

    @SerializedName("is_collect")
    private int isCollect;

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }
}
