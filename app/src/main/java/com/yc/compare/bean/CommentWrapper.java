package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by myflying on 2018/12/19.
 */
public class CommentWrapper {

    @SerializedName("list_count")
    private String listCount;

    private List<CommentInfo> list;

    public String getListCount() {
        return listCount;
    }

    public void setListCount(String listCount) {
        this.listCount = listCount;
    }

    public List<CommentInfo> getList() {
        return list;
    }

    public void setList(List<CommentInfo> list) {
        this.list = list;
    }
}
