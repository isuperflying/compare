package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/3/13.
 */

public class UserInfo {

    @SerializedName("uid")
    private String userId;

    private String mobile;

    @SerializedName("head_pic")
    private String headImage;

    private String password;

    private String openid;

    @SerializedName("nickname")
    private String nickName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
