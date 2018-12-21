package com.yc.compare.model;

import com.yc.compare.base.IBaseRequestCallBack;

/**
 * Created by iflying on 2018/1/9.
 */

public interface CollectInfoModel<T> {
    void getCollectInfoList(String uid,String cid,int page, IBaseRequestCallBack<T> iBaseRequestCallBack);

    void deleteCollect(String cids, IBaseRequestCallBack<T> iBaseRequestCallBack);

    void historyList(String uid,int page, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
