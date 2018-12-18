package com.yc.compare.model;

import com.yc.compare.base.IBaseRequestCallBack;

/**
 * Created by iflying on 2018/1/9.
 */

public interface NewsInfoModel<T> {
    void getNewsInfoList(String cid,String keyWord,int page, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
