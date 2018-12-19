package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by myflying on 2018/12/7.
 */
public class OtherPriceInfo {
    private String id;

    @SerializedName("name")
    private String countryName;

    @SerializedName("logo")
    private String countryLogo;

    @SerializedName("local_price")
    private String localPrice;

    @SerializedName("market_price")
    private String chinaPrice;

    @SerializedName("exchange")
    private String rate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getLocalPrice() {
        return localPrice;
    }

    public void setLocalPrice(String localPrice) {
        this.localPrice = localPrice;
    }

    public String getChinaPrice() {
        return chinaPrice;
    }

    public void setChinaPrice(String chinaPrice) {
        this.chinaPrice = chinaPrice;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCountryLogo() {
        return countryLogo;
    }

    public void setCountryLogo(String countryLogo) {
        this.countryLogo = countryLogo;
    }
}
