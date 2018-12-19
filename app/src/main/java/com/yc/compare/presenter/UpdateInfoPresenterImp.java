package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.OtherLoginBean;
import com.yc.compare.bean.UpdateInfoRet;
import com.yc.compare.bean.UserInfoRet;
import com.yc.compare.model.UpdateInfoModelImp;
import com.yc.compare.model.UserModelImp;
import com.yc.compare.view.UpdateInfoView;
import com.yc.compare.view.UserView;

import java.io.File;

/**
 * Created by admin on 2017/3/13.
 */

public class UpdateInfoPresenterImp extends BasePresenterImp<UpdateInfoView, UpdateInfoRet> implements UpdateInfoPresenter {

    private Context context = null;
    private UpdateInfoModelImp updateInfoModelImp = null;

    public UpdateInfoPresenterImp(UpdateInfoView view, Context context) {
        super(view);
        this.context = context;
        this.updateInfoModelImp = new UpdateInfoModelImp(context);
    }

    @Override
    public void updateHead(String uid, File file) {
        updateInfoModelImp.updateHead(uid, file, this);
    }

    @Override
    public void updateNickName(String uid, String nickname) {
        updateInfoModelImp.updateNickName(uid, nickname, this);
    }

    @Override
    public void addSuggest(String uid, String content) {
        updateInfoModelImp.addSuggest(uid, content, this);
    }
}
