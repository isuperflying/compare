package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.CollectInfoRet;
import com.yc.compare.model.CollectInfoModelImp;
import com.yc.compare.view.CollectInfoView;

/**
 * Created by iflying on 2018/1/9.
 */

public class CollectInfoPresenterImp extends BasePresenterImp<CollectInfoView, CollectInfoRet> implements CollectInfoPresenter {
    private Context context = null;
    private CollectInfoModelImp collectInfoModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public CollectInfoPresenterImp(CollectInfoView view, Context context) {
        super(view);
        collectInfoModelImp = new CollectInfoModelImp(context);
    }

    @Override
    public void getCollectInfoList(String uid,int type) {
        collectInfoModelImp.getCollectInfoList(uid,type, this);
    }
}
