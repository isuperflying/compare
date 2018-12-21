package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.HotSearchRet;
import com.yc.compare.model.HotSearchModelImp;
import com.yc.compare.view.HotSearchView;

/**
 * Created by iflying on 2018/1/9.
 */

public class HotSearchPresenterImp extends BasePresenterImp<HotSearchView, HotSearchRet> implements HotSearchPresenter {
    private Context context = null;
    private HotSearchModelImp hotSearchModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public HotSearchPresenterImp(HotSearchView view, Context context) {
        super(view);
        hotSearchModelImp = new HotSearchModelImp(context);
    }

    @Override
    public void hotSearch() {
        hotSearchModelImp.hotSearch(this);
    }
}
