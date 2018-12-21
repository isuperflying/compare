package com.yc.compare.presenter;

/**
 * Created by iflying on 2018/1/9.
 */

public interface NewsCommentPresenter {
    void getNewsCommentList(String uid, String gid,int page);

    void addNewsComment(String uid, String gid, String content);
}
