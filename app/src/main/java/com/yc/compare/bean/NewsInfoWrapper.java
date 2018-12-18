package com.yc.compare.bean;

import java.util.List;

/**
 * Created by myflying on 2018/12/3.
 */
public class NewsInfoWrapper extends ResultInfo {

    private List<CategoryInfo> category_list;

    private List<NewsInfo> article_list;

    public List<CategoryInfo> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(List<CategoryInfo> category_list) {
        this.category_list = category_list;
    }

    public List<NewsInfo> getArticle_list() {
        return article_list;
    }

    public void setArticle_list(List<NewsInfo> article_list) {
        this.article_list = article_list;
    }
}
