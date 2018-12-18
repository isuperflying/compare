package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.base.IBaseView;
import com.yc.compare.bean.CategoryWrapperRet;
import com.yc.compare.model.CategoryWrapperModelImp;

/**
 * Created by iflying on 2018/1/9.
 */

public class CategoryWrapperPresenterImp extends BasePresenterImp<IBaseView, CategoryWrapperRet> implements CategoryWrapperPresenter {
    private Context context = null;
    private CategoryWrapperModelImp categoryInfoModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public CategoryWrapperPresenterImp(IBaseView view, Context context) {
        super(view);
        categoryInfoModelImp = new CategoryWrapperModelImp(context);
    }

    @Override
    public void getCategoryInfoList(int level, String pid) {
        categoryInfoModelImp.getCategoryInfoList(level, pid, this);
    }

    @Override
    public void getCategoryWrapperList(int level, String pid,String type) {
        categoryInfoModelImp.getCategoryWrapperList(level, pid, type,this);
    }
}
