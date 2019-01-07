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
    private List<RelevantInfo> articleRecommendList;

    @SerializedName("relevant_goods")
    private List<GoodInfo> relevantGoodsList;

    @SerializedName("comment_num")
    private int commentNum;

    public NewsInfo getArticleInfo() {
        return articleInfo;
    }

    public void setArticleInfo(NewsInfo articleInfo) {
        this.articleInfo = articleInfo;
    }

    public List<RelevantInfo> getArticleRecommendList() {
        return articleRecommendList;
    }

    public void setArticleRecommendList(List<RelevantInfo> articleRecommendList) {
        this.articleRecommendList = articleRecommendList;
    }

    public List<GoodInfo> getRelevantGoodsList() {
        return relevantGoodsList;
    }

    public void setRelevantGoodsList(List<GoodInfo> relevantGoodsList) {
        this.relevantGoodsList = relevantGoodsList;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }
}
