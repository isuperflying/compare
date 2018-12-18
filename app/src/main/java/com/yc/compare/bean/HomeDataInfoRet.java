package com.yc.compare.bean;

/**
 * Created by admin on 2018/1/8.
 */

public class HomeDataInfoRet extends ResultInfo{
    private HomeDataWrapper data;

    public HomeDataWrapper getData() {
        return data;
    }

    public void setData(HomeDataWrapper data) {
        this.data = data;
    }
}
