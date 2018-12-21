package com.yc.compare.presenter;

/**
 * Created by iflying on 2018/1/9.
 */

public interface CollectInfoPresenter {
    void getCollectInfoList(String uid,String cid,int page);
    void deleteCollect(String cids);
    void historyList(String uid,int page);
}
