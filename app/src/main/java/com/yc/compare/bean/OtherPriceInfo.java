package com.yc.compare.bean;

/**
 * Created by myflying on 2018/12/7.
 */
public class OtherPriceInfo {
    private String id;
    private String countryName;
    private String localPrice;
    private String chinaPrice;
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
}
