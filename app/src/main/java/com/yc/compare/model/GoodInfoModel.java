package com.yc.compare.model;

import com.yc.compare.base.IBaseRequestCallBack;
import com.yc.compare.bean.Condition;

/**
 * Created by iflying on 2018/1/9.
 */

public interface GoodInfoModel<T> {

    void getGoodInfoByType(int page,String cid,IBaseRequestCallBack<T> iBaseRequestCallBack);

    void getGoodInfoByParams(Condition condition, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
