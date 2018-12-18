package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by myflying on 2018/12/3.
 */
public class GoodInfo {

    @SerializedName("goods_id")
    private String id;

    @SerializedName("goods_name")
    private String goodName;

    @SerializedName("img")
    private String goodImage;

    @SerializedName("market_price")
    private String goodPrice;

    private String goodType;

    private String buyCountryCount;

    @SerializedName("goods_country_num")
    private String goodsCountryNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodImage() {
        return goodImage;
    }

    public void setGoodImage(String goodImage) {
        this.goodImage = goodImage;
    }

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }

    public String getBuyCountryCount() {
        return buyCountryCount;
    }

    public void setBuyCountryCount(String buyCountryCount) {
        this.buyCountryCount = buyCountryCount;
    }

    public String getGoodsCountryNum() {
        return goodsCountryNum;
    }

    public void setGoodsCountryNum(String goodsCountryNum) {
        this.goodsCountryNum = goodsCountryNum;
    }
}
