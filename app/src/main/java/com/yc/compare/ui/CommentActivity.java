package com.yc.compare.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.R;
import com.yc.compare.bean.AddCommentInfoRet;
import com.yc.compare.bean.CommentInfoRet;
import com.yc.compare.bean.ResultInfo;
import com.yc.compare.bean.UserInfo;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.CommentInfoPresenterImp;
import com.yc.compare.ui.adapter.CommentAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.ui.custom.CommentDialog;
import com.yc.compare.view.CommentInfoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class CommentActivity extends BaseFragmentActivity implements CommentInfoView, CommentDialog.SendBackListener {

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.comment_list)
    RecyclerView mCommentListView;

    private CommentAdapter commentAdapter;

    private CommentInfoPresenterImp commentInfoPresenterImp;

    private ProgressDialog progressDialog = null;

    private int pageSize = 20;

    private int currentPage = 1;

    private CommentDialog commentDialog;

    private UserInfo userInfo;

    private String goodId;

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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getString("good_id") != null) {
            goodId = bundle.getString("good_id");
        }

        if (!StringUtils.isEmpty(SPUtils.getInstance().getString(Constants.USER_INFO))) {
            Logger.i(SPUtils.getInstance().getString(Constants.USER_INFO));
            userInfo = JSON.parseObject(SPUtils.getInstance().getString(Constants.USER_INFO), new TypeReference<UserInfo>() {
            });
        }

        commentInfoPresenterImp = new CommentInfoPresenterImp(this, this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在操作");

        commentAdapter = new CommentAdapter(this, null);
        mCommentListView.setLayoutManager(new LinearLayoutManager(this));
        mCommentListView.setAdapter(commentAdapter);

        avi.show();
        commentInfoPresenterImp.getCommentInfoList(goodId, currentPage);

        commentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                commentInfoPresenterImp.getCommentInfoList(goodId, currentPage);
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
    public void loadDataSuccess(ResultInfo tData) {
        avi.hide();
        avi.setVisibility(View.GONE);
        Logger.i(JSONObject.toJSONString(tData));

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (tData.getCode() == Constants.SUCCESS) {
            if (tData != null && tData instanceof CommentInfoRet) {
                if (currentPage == 1) {
                    commentAdapter.setNewData(((CommentInfoRet) tData).getData().getList());
                } else {
                    commentAdapter.addData(((CommentInfoRet) tData).getData().getList());
                }
                if (((CommentInfoRet) tData).getData().getList().size() == pageSize) {
                    commentAdapter.loadMoreComplete();
                } else {
                    commentAdapter.loadMoreEnd();
                }
            } else {
                if (tData instanceof AddCommentInfoRet) {
                    ToastUtils.showLong("评论成功");
                    if (commentDialog != null) {
                        commentDialog.hideProgressDialog();
                        commentDialog.dismiss();
                    }
                    commentInfoPresenterImp.getCommentInfoList(goodId, currentPage);
                }
            }
        } else {
            ToastUtils.showLong("数据异常,请重试");
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        avi.hide();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @OnClick(R.id.layout_bottom)
    void showDialog() {
        if (commentDialog == null) {
            commentDialog = new CommentDialog(CommentActivity.this, 1);
            commentDialog.setSendBackListener(this);
        }
        commentDialog.show(getFragmentManager(), "dialog");
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

    @Override
    public void sendContent(String content, int type) {

        if (userInfo == null) {
            new AlertDialog.Builder(this)
                    .setPositiveButton(R.string.config_txt, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            Intent intent = new Intent(CommentActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.cancel_txt, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {

                        }
                    })
                    .setCancelable(false)
                    .setTitle("登录")
                    .setMessage("未登录账号，请确认登录?")
                    .show();
            return;
        }

        Logger.i("content--->" + content);
        //ToastUtils.showLong("content--->" + content);
        commentInfoPresenterImp.addComment(userInfo != null ? userInfo.getUserId() : "", goodId, content);
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }
}
