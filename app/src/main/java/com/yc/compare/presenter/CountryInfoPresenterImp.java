package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.CountryInfoRet;
import com.yc.compare.model.CountryInfoModelImp;
import com.yc.compare.view.CountryInfoView;

/**
 * Created by iflying on 2018/1/9.
 */

public class CountryInfoPresenterImp extends BasePresenterImp<CountryInfoView, CountryInfoRet> implements CountryInfoPresenter {
    private Context context = null;
    private CountryInfoModelImp countryInfoModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public CountryInfoPresenterImp(CountryInfoView view, Context context) {
        super(view);
        countryInfoModelImp = new CountryInfoModelImp(context);
    }

    @Override
    public void getCountryInfoList(int type) {
        countryInfoModelImp.getCountryInfoList(type, this);
    }
}
