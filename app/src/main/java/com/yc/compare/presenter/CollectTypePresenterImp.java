package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.CollectTypeRet;
import com.yc.compare.model.CollectTypeModelImp;
import com.yc.compare.view.CollectInfoView;

/**
 * Created by iflying on 2018/1/9.
 */

public class CollectTypePresenterImp extends BasePresenterImp<CollectInfoView, CollectTypeRet> implements CollectTypePresenter {
    private Context context = null;
    private CollectTypeModelImp collectTypeModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public CollectTypePresenterImp(CollectInfoView view, Context context) {
        super(view);
        collectTypeModelImp = new CollectTypeModelImp(context);
    }

    @Override
    public void collectType(String uid) {
        collectTypeModelImp.collectType(uid, this);
    }
}
