package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by myflying on 2019/1/3.
 */
public class RelevantInfo {
    @SerializedName("article_id")
    private String articleId;
    private String title;
    @SerializedName("publish_time")
    private String publishTime;
    private String thumb;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
