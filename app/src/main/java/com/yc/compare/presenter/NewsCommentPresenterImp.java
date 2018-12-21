package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.NewsCommentRet;
import com.yc.compare.model.NewsCommentModelImp;
import com.yc.compare.view.NewsInfoDetailView;

/**
 * Created by iflying on 2018/1/9.
 */

public class NewsCommentPresenterImp extends BasePresenterImp<NewsInfoDetailView, NewsCommentRet> implements NewsCommentPresenter {
    private Context context = null;
    private NewsCommentModelImp newsInfoDetailModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public NewsCommentPresenterImp(NewsInfoDetailView view, Context context) {
        super(view);
        newsInfoDetailModelImp = new NewsCommentModelImp(context);
    }

    @Override
    public void getNewsCommentList(String uid, String gid,int page) {
        newsInfoDetailModelImp.getNewsCommentList(uid,gid,page,this);
    }

    @Override
    public void addNewsComment(String uid, String gid, String content) {

        newsInfoDetailModelImp.addNewsComment(uid,gid,content,this);
    }
}
