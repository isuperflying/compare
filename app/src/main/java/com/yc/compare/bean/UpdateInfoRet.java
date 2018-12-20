package com.yc.compare.bean;

import java.util.List;

public class UpdateInfoRet extends ResultInfo{
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
