package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.GoodDetailInfoRet;
import com.yc.compare.model.GoodDetailInfoModelImp;
import com.yc.compare.view.GoodDetailInfoView;

/**
 * Created by iflying on 2018/1/9.
 */

public class GoodDetailInfoPresenterImp extends BasePresenterImp<GoodDetailInfoView, GoodDetailInfoRet> implements GoodDetailInfoPresenter {
    private Context context = null;
    private GoodDetailInfoModelImp goodDetailInfoModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public GoodDetailInfoPresenterImp(GoodDetailInfoView view, Context context) {
        super(view);
        goodDetailInfoModelImp = new GoodDetailInfoModelImp(context);
    }

    @Override
    public void getGoodDetailInfoByParams(String uid, String gid) {
        goodDetailInfoModelImp.getGoodDetailInfoByParams(uid, gid, this);
    }
}
