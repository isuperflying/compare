package com.yc.compare.bean;

import java.util.List;

/**
 * Created by myflying on 2018/12/3.
 */
public class CommentInfoRet extends ResultInfo {
    private List<CommentInfo> data;

    public List<CommentInfo> getData() {
        return data;
    }

    public void setData(List<CommentInfo> data) {
        this.data = data;
    }
}
