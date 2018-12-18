package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.NewsInfoRet;
import com.yc.compare.model.NewsInfoModelImp;
import com.yc.compare.view.NewsInfoView;

/**
 * Created by iflying on 2018/1/9.
 */

public class NewsInfoPresenterImp extends BasePresenterImp<NewsInfoView, NewsInfoRet> implements NewsInfoPresenter {
    private Context context = null;
    private NewsInfoModelImp newsInfoModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public NewsInfoPresenterImp(NewsInfoView view, Context context) {
        super(view);
        newsInfoModelImp = new NewsInfoModelImp(context);
    }

    @Override
    public void getNewsInfoList(String cid,String keyWord,int page) {
        newsInfoModelImp.getNewsInfoList(cid,keyWord,page, this);
    }
}
