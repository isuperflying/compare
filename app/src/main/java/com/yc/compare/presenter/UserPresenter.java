package com.yc.compare.presenter;

import com.yc.compare.bean.OtherLoginBean;

/**
 * Created by admin on 2017/3/13.
 */

public interface UserPresenter {

    void userLogin(String name, String password, String type);

    void register(String name, String password);

    void sendSms(String name, String smsCode);

    void otherLogin(OtherLoginBean otherLoginBean);
}
