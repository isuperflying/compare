package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by myflying on 2018/12/13.
 */
public class HomeDataWrapper {
    private List<BannerInfo> banner;

    private List<NewsInfo> news;

    @SerializedName("p_category")
    private List<CategoryInfo> categoryInfoList;

    private List<Special> special;

    public List<BannerInfo> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerInfo> banner) {
        this.banner = banner;
    }

    public List<NewsInfo> getNews() {
        return news;
    }

    public void setNews(List<NewsInfo> news) {
        this.news = news;
    }

    public List<CategoryInfo> getCategoryInfoList() {
        return categoryInfoList;
    }

    public void setCategoryInfoList(List<CategoryInfo> categoryInfoList) {
        this.categoryInfoList = categoryInfoList;
    }

    public List<Special> getSpecial() {
        return special;
    }

    public void setSpecial(List<Special> special) {
        this.special = special;
    }
}
