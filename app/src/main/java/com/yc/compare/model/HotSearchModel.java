package com.yc.compare.model;

import com.yc.compare.base.IBaseRequestCallBack;

/**
 * Created by iflying on 2018/1/9.
 */

public interface HotSearchModel<T> {
    void hotSearch(IBaseRequestCallBack<T> iBaseRequestCallBack);
}
