package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.OtherPriceInfoRet;
import com.yc.compare.model.OtherPriceInfoModelImp;
import com.yc.compare.view.OtherPriceInfoView;

/**
 * Created by iflying on 2018/1/9.
 */

public class OtherPriceInfoPresenterImp extends BasePresenterImp<OtherPriceInfoView, OtherPriceInfoRet> implements OtherPriceInfoPresenter {
    private Context context = null;
    private OtherPriceInfoModelImp otherPriceInfoModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public OtherPriceInfoPresenterImp(OtherPriceInfoView view, Context context) {
        super(view);
        otherPriceInfoModelImp = new OtherPriceInfoModelImp(context);
    }

    @Override
    public void getOtherPriceInfoList(String gid, String keyword) {
        otherPriceInfoModelImp.getOtherPriceInfoList(gid, keyword, this);
    }
}
