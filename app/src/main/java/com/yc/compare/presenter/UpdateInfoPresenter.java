package com.yc.compare.presenter;

import com.yc.compare.base.IBaseRequestCallBack;
import com.yc.compare.bean.OtherLoginBean;

import java.io.File;

/**
 * Created by admin on 2017/3/13.
 */

public interface UpdateInfoPresenter {

    void updateHead(String uid, File file);

    void updateNickName(String uid, String nickname);

    void addSuggest(String uid, String content);
}
