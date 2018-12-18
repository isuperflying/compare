package com.yc.compare.model;

import com.yc.compare.base.IBaseRequestCallBack;

/**
 * Created by iflying on 2018/1/9.
 */

public interface NewsInfoDetailModel<T> {
    void getNewsInfoDetail(String nid, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
