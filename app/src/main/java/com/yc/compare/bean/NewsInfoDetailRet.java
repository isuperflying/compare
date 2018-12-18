package com.yc.compare.bean;

/**
 * Created by myflying on 2018/12/3.
 */
public class NewsInfoDetailRet extends ResultInfo {

    private NewsInfoDetailWrapper data;

    public NewsInfoDetailWrapper getData() {
        return data;
    }

    public void setData(NewsInfoDetailWrapper data) {
        this.data = data;
    }
}
