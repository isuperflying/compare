package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.CommentInfoRet;
import com.yc.compare.model.CommentInfoModelImp;
import com.yc.compare.view.CommentInfoView;

/**
 * Created by iflying on 2018/1/9.
 */

public class CommentInfoPresenterImp extends BasePresenterImp<CommentInfoView, CommentInfoRet> implements CommentInfoPresenter {
    private Context context = null;
    private CommentInfoModelImp commentInfoModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public CommentInfoPresenterImp(CommentInfoView view, Context context) {
        super(view);
        commentInfoModelImp = new CommentInfoModelImp(context);
    }

    @Override
    public void getCommentInfoList(String gid, int type) {
        commentInfoModelImp.getCommentInfoList(gid, type, this);
    }
}
