package com.yc.compare.bean;

/**
 * Created by myflying on 2018/12/7.
 */
public class CommentInfo {

    private String id;
    private String commentContent;
    private String userNickName;
    private String userHeadImage;
    private String commentDate;
    private String goodType;
    private String goodImages;

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

    public String getGoodImages() {
        return goodImages;
    }

    public void setGoodImages(String goodImages) {
        this.goodImages = goodImages;
    }
}
