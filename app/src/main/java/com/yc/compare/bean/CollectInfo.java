package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by myflying on 2018/12/12.
 */
public class CollectInfo {
    @SerializedName("collect_id")
    private String collectId;

    @SerializedName("goods_id")
    private String goodId;

    @SerializedName("goods_name")
    private String goodName;

    @SerializedName("market_price")
    private String price;

    @SerializedName("img")
    private String goodImage;

    @SerializedName("goods_country_num")
    private String goodsCountryNum;

    @SerializedName("visit_id")
    private String visitId;

    private boolean isChecked;

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoodImage() {
        return goodImage;
    }

    public void setGoodImage(String goodImage) {
        this.goodImage = goodImage;
    }

    public String getGoodsCountryNum() {
        return goodsCountryNum;
    }

    public void setGoodsCountryNum(String goodsCountryNum) {
        this.goodsCountryNum = goodsCountryNum;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }
}
