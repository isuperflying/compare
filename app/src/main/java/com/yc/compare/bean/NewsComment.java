package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by myflying on 2018/12/21.
 */
public class NewsComment {

    @SerializedName("comment_id")
    private String commentId;

    @SerializedName("add_time")
    private String addTime;

    private String nickname;

    @SerializedName("head_pic")
    private String headPic;

    private String content;

    @SerializedName("zan_num")
    private int zanNum;

    @SerializedName("is_zan")
    private int isZan;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getZanNum() {
        return zanNum;
    }

    public void setZanNum(int zanNum) {
        this.zanNum = zanNum;
    }

    public int getIsZan() {
        return isZan;
    }

    public void setIsZan(int isZan) {
        this.isZan = isZan;
    }
}
