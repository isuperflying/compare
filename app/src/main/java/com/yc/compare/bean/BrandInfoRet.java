package com.yc.compare.bean;

import java.util.List;

/**
 * Created by myflying on 2018/12/3.
 */
public class BrandInfoRet extends ResultInfo {
    private List<BrandInfo> data;

    public List<BrandInfo> getData() {
        return data;
    }

    public void setData(List<BrandInfo> data) {
        this.data = data;
    }
}
