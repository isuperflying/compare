package com.yc.compare.model;

import com.yc.compare.base.IBaseRequestCallBack;

/**
 * Created by iflying on 2018/1/9.
 */

public interface CollectTypeModel<T> {
    void collectType(String uid,IBaseRequestCallBack<T> iBaseRequestCallBack);
}
