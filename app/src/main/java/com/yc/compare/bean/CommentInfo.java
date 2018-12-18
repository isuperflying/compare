package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by myflying on 2018/12/7.
 */
public class CommentInfo {

    private String id;
    @SerializedName("content")
    private String commentContent;
    @SerializedName("nickname")
    private String userNickName;
    @SerializedName("head_pic")
    private String userHeadImage;
    @SerializedName("add_time")
    private String commentDate;
    private String goodType;

    @SerializedName("imgs")
    private String[] goodImages;

    @SerializedName("sub_name")
    private String subTitle;

    @SerializedName("view_count")
    private String viewCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserHeadImage() {
        return userHeadImage;
    }

    public void setUserHeadImage(String userHeadImage) {
        this.userHeadImage = userHeadImage;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }

    public String[] getGoodImages() {
        return goodImages;
    }

    public void setGoodImages(String[] goodImages) {
        this.goodImages = goodImages;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }
}
