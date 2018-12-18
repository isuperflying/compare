package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.NewsInfoDetailRet;
import com.yc.compare.model.NewsInfoDetailModelImp;
import com.yc.compare.view.NewsInfoDetailView;

/**
 * Created by iflying on 2018/1/9.
 */

public class NewsInfoDetailPresenterImp extends BasePresenterImp<NewsInfoDetailView, NewsInfoDetailRet> implements NewsInfoDetailPresenter {
    private Context context = null;
    private NewsInfoDetailModelImp newsInfoDetailModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public NewsInfoDetailPresenterImp(NewsInfoDetailView view, Context context) {
        super(view);
        newsInfoDetailModelImp = new NewsInfoDetailModelImp(context);
    }

    @Override
    public void getNewsInfoDetail(String nid) {
        newsInfoDetailModelImp.getNewsInfoDetail(nid, this);
    }
}
