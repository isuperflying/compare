package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.OtherLoginBean;
import com.yc.compare.bean.UserInfoRet;
import com.yc.compare.model.UserModelImp;
import com.yc.compare.view.UserView;

/**
 * Created by admin on 2017/3/13.
 */

public class UserPresenterImp extends BasePresenterImp<UserView, UserInfoRet> implements UserPresenter {

    private Context context = null;
    private UserModelImp userModelImp = null;

    public UserPresenterImp(UserView view, Context context) {
        super(view);
        this.context = context;
        this.userModelImp = new UserModelImp(context);
    }

    @Override
    public void userLogin(String name, String password, String type) {
        userModelImp.userLogin(name, password, type, this);
    }

    @Override
    public void register(String name, String password) {
        userModelImp.register(name, password, this);
    }

    @Override
    public void otherLogin(OtherLoginBean otherLoginBean) {
        userModelImp.otherLogin(otherLoginBean, this);
    }

    @Override
    public void sendSms(String name, String smsCode) {
        userModelImp.sendSms(name, smsCode, this);
    }
}
