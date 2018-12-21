package com.yc.compare.model;

import com.yc.compare.base.IBaseRequestCallBack;

/**
 * Created by iflying on 2018/1/9.
 */

public interface CommentInfoModel<T> {
    void getCommentInfoList(String gid, int page, IBaseRequestCallBack<T> iBaseRequestCallBack);

    void addComment(String uid, String gid, String content, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
