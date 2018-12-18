package com.yc.compare.bean;

/**
 * Created by myflying on 2018/12/3.
 */
public class NewsInfoRet extends ResultInfo {

    private NewsInfoWrapper data;

    public NewsInfoWrapper getData() {
        return data;
    }

    public void setData(NewsInfoWrapper data) {
        this.data = data;
    }
}
