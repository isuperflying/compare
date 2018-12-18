package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by myflying on 2018/12/3.
 */
public class NewsInfoDetailWrapper extends ResultInfo {

    @SerializedName("article_info")
    private NewsInfo articleInfo;

    @SerializedName("article_recommend")
    private List<NewsInfo> articleRecommendList;

    @SerializedName("relevant_goods")
    private List<GoodInfo> relevantGoodsList;

    public NewsInfo getArticleInfo() {
        return articleInfo;
    }

    public void setArticleInfo(NewsInfo articleInfo) {
        this.articleInfo = articleInfo;
    }

    public List<NewsInfo> getArticleRecommendList() {
        return articleRecommendList;
    }

    public void setArticleRecommendList(List<NewsInfo> articleRecommendList) {
        this.articleRecommendList = articleRecommendList;
    }

    public List<GoodInfo> getRelevantGoodsList() {
        return relevantGoodsList;
    }

    public void setRelevantGoodsList(List<GoodInfo> relevantGoodsList) {
        this.relevantGoodsList = relevantGoodsList;
    }
}
