package com.yc.compare.bean;

/**
 * Created by myflying on 2018/12/3.
 */
public class CommentInfoRet extends ResultInfo {
    private CommentWrapper data;

    public CommentWrapper getData() {
        return data;
    }

    public void setData(CommentWrapper data) {
        this.data = data;
    }
}
