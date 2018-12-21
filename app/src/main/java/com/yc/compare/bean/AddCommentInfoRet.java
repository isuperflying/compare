package com.yc.compare.bean;

import java.util.List;

/**
 * Created by myflying on 2018/12/3.
 */
public class AddCommentInfoRet extends ResultInfo {

    private List<Object> data;

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
