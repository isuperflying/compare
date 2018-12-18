package com.yc.compare.model;


import com.yc.compare.base.IBaseRequestCallBack;

/**
 * Created by admin on 2017/3/13.
 */

public interface SuggestInfoModel<T> {
    void addSuggest(String userId, String content, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
