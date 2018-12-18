package com.yc.compare.bean;

import java.util.List;

/**
 * Created by myflying on 2018/12/3.
 */
public class CollectInfoRet extends ResultInfo {
    private List<CollectInfo> data;

    public List<CollectInfo> getData() {
        return data;
    }

    public void setData(List<CollectInfo> data) {
        this.data = data;
    }
}
