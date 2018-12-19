package com.yc.compare.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.R;
import com.yc.compare.bean.CommentInfoRet;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.CommentInfoPresenterImp;
import com.yc.compare.ui.adapter.CommentAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.view.CommentInfoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class CommentActivity extends BaseFragmentActivity implements CommentInfoView {

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.comment_list)
    RecyclerView mCommentListView;

    private CommentAdapter commentAdapter;

    private CommentInfoPresenterImp commentInfoPresenterImp;

    private ProgressDialog progressDialog = null;

    private int pageSize = 20;

    private int currentPage = 1;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {
        commentInfoPresenterImp = new CommentInfoPresenterImp(this, this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在查询");

        commentAdapter = new CommentAdapter(this, null);
        mCommentListView.setLayoutManager(new LinearLayoutManager(this));
        mCommentListView.setAdapter(commentAdapter);

        avi.show();
        commentInfoPresenterImp.getCommentInfoList("566", currentPage);

        commentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                commentInfoPresenterImp.getCommentInfoList("566", currentPage);
            }
        }, mCommentListView);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(CommentInfoRet tData) {
        avi.hide();
        Logger.i(JSONObject.toJSONString(tData));
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (currentPage == 1) {
                commentAdapter.setNewData(tData.getData().getList());
            } else {
                commentAdapter.addData(tData.getData().getList());
                if (tData.getData().getList().size() == pageSize) {
                    commentAdapter.loadMoreComplete();
                } else {
                    commentAdapter.loadMoreEnd();
                }
            }

        } else {
            ToastUtils.showLong("数据异常,请重试");
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        avi.hide();
    }

    @OnClick(R.id.iv_back)
    void back() {
        popBackStack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        popBackStack();
    }
}
