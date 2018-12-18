package com.yc.compare.bean;

/**
 * Created by iflying on 2018/2/6.
 */

public class RecordItem {
    private String id;
    private String coverWidth;
    private String coverHeight;
    private String fullCover;
    private String coverType;
    private String clickNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoverWidth() {
        return coverWidth;
    }

    public void setCoverWidth(String coverWidth) {
        this.coverWidth = coverWidth;
    }

    public String getCoverHeight() {
        return coverHeight;
    }

    public void setCoverHeight(String coverHeight) {
        this.coverHeight = coverHeight;
    }

    public String getFullCover() {
        return fullCover;
    }

    public void setFullCover(String fullCover) {
        this.fullCover = fullCover;
    }

    public String getCoverType() {
        return coverType;
    }

    public void setCoverType(String coverType) {
        this.coverType = coverType;
    }

    public String getClickNum() {
        return clickNum;
    }

    public void setClickNum(String clickNum) {
        this.clickNum = clickNum;
    }
}
