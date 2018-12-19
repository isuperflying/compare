package com.yc.compare.model;

import com.yc.compare.base.IBaseRequestCallBack;

import java.io.File;

/**
 * Created by iflying on 2018/1/9.
 */

public interface UpdateInfoModel<T> {
    void updateHead(String uid, File file, IBaseRequestCallBack<T> iBaseRequestCallBack);

    void updateNickName(String uid, String nickname, IBaseRequestCallBack<T> iBaseRequestCallBack);

    void addSuggest(String uid, String content, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
