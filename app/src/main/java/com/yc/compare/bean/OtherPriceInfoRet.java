package com.yc.compare.bean;

import java.util.List;

/**
 * Created by myflying on 2018/12/3.
 */
public class OtherPriceInfoRet extends ResultInfo {
    private List<OtherPriceInfo> data;

    public List<OtherPriceInfo> getData() {
        return data;
    }

    public void setData(List<OtherPriceInfo> data) {
        this.data = data;
    }
}
