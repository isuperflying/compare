package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.HotDataInfoRet;
import com.yc.compare.model.HotDataModelImp;
import com.yc.compare.view.HotDataView;

/**
 * Created by iflying on 2018/1/9.
 */

public class HotDataPresenterImp extends BasePresenterImp<HotDataView, HotDataInfoRet> implements HotDataPresenter {
    private Context context = null;
    private HotDataModelImp hotDataModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public HotDataPresenterImp(HotDataView view, Context context) {
        super(view);
        hotDataModelImp = new HotDataModelImp(context);
    }

    @Override
    public void getHotDataByPage(int page) {
        hotDataModelImp.getHotDataByPage(page, this);
    }
}
