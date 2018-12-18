package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.AddCollectInfoRet;
import com.yc.compare.bean.CollectInfoRet;
import com.yc.compare.model.AddCollectInfoModelImp;
import com.yc.compare.model.CollectInfoModelImp;
import com.yc.compare.view.AddCollectInfoView;
import com.yc.compare.view.CollectInfoView;
import com.yc.compare.view.GoodDetailInfoView;

/**
 * Created by iflying on 2018/1/9.
 */

public class AddCollectInfoPresenterImp extends BasePresenterImp<GoodDetailInfoView, AddCollectInfoRet> implements AddCollectInfoPresenter {
    private Context context = null;
    private AddCollectInfoModelImp addCollectInfoModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public AddCollectInfoPresenterImp(GoodDetailInfoView view, Context context) {
        super(view);
        addCollectInfoModelImp = new AddCollectInfoModelImp(context);
    }

    @Override
    public void doCollect(String uid, String gid) {
        addCollectInfoModelImp.doCollect(uid, gid, this);
    }
}
