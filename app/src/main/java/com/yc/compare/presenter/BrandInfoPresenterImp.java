package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.BrandInfoRet;
import com.yc.compare.model.BrandInfoModelImp;
import com.yc.compare.view.BrandInfoView;

/**
 * Created by iflying on 2018/1/9.
 */

public class BrandInfoPresenterImp extends BasePresenterImp<BrandInfoView, BrandInfoRet> implements BrandInfoPresenter {
    private Context context = null;
    private BrandInfoModelImp brandInfoModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public BrandInfoPresenterImp(BrandInfoView view, Context context) {
        super(view);
        brandInfoModelImp = new BrandInfoModelImp(context);
    }

    @Override
    public void getBrandInfoList(int type) {
        brandInfoModelImp.getBrandInfoList(type, this);
    }
}
