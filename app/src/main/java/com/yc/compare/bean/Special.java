package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by myflying on 2018/12/19.
 */
public class Special {

    private int position;

    @SerializedName("go_type")
    private int goType; //点击图片跳转方式 0不跳转 1跳转到商品详情 2跳转到资讯详情

    @SerializedName("ad_img")
    private String adImage;

    @SerializedName("obj_id")
    private String objectId;//go_type为1时此字段为商品id；go_type为2时此字段为资讯id

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getGoType() {
        return goType;
    }

    public void setGoType(int goType) {
        this.goType = goType;
    }

    public String getAdImage() {
        return adImage;
    }

    public void setAdImage(String adImage) {
        this.adImage = adImage;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
