package com.yc.compare.presenter;

import com.yc.compare.bean.Condition;

/**
 * Created by iflying on 2018/1/9.
 */

public interface GoodInfoPresenter {

    void getGoodInfoByType(int type,String cid);
    void getGoodInfoByParams(Condition condition);
}
