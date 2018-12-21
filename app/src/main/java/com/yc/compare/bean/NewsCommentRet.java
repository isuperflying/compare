package com.yc.compare.bean;

import java.util.List;

/**
 * Created by myflying on 2018/12/3.
 */
public class NewsCommentRet extends ResultInfo {

    private List<NewsComment> data;

    public List<NewsComment> getData() {
        return data;
    }

    public void setData(List<NewsComment> data) {
        this.data = data;
    }
}
