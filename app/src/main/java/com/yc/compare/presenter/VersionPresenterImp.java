package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.VersionInfoRet;
import com.yc.compare.model.VersionModelImp;
import com.yc.compare.view.VersionInfoView;

/**
 * Created by admin on 2017/3/13.
 */

public class VersionPresenterImp extends BasePresenterImp<VersionInfoView, VersionInfoRet> implements VersionPresenter {

    private Context context = null;
    private VersionModelImp versionModelImp = null;

    public VersionPresenterImp(VersionInfoView view, Context context) {
        super(view);
        this.context = context;
        this.versionModelImp = new VersionModelImp(context);
    }

    @Override
    public void checkVersion() {
        versionModelImp.checkVersion(this);
    }
}
