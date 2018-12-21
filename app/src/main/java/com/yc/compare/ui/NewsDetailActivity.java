package com.yc.compare.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.R;
import com.yc.compare.bean.NewsCommentRet;
import com.yc.compare.bean.NewsInfoDetailRet;
import com.yc.compare.bean.ResultInfo;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.NewsCommentPresenterImp;
import com.yc.compare.presenter.NewsInfoDetailPresenterImp;
import com.yc.compare.ui.adapter.ReplyListAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.ui.custom.CommentDialog;
import com.yc.compare.view.NewsInfoDetailView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class NewsDetailActivity extends BaseFragmentActivity implements NewsInfoDetailView, CommentDialog.SendBackListener {

    private NewsInfoDetailPresenterImp newsInfoDetailPresenterImp;

    private NewsCommentPresenterImp newsCommentPresenterImp;

    @BindView(R.id.layout_content)
    LinearLayout mContentLayout;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.tv_news_title)
    TextView mTitleTextView;

    @BindView(R.id.tv_news_author)
    TextView mAuthorTextView;

    @BindView(R.id.tv_news_date)
    TextView mDateTextView;

    @BindView(R.id.layout_web_view)
    LinearLayout mWebViewLayout;

    @BindView(R.id.tv_send_commit)
    TextView mSendTextView;

    @BindView(R.id.iv_comment_share)
    ImageView mCommentShareImageView;

    @BindView(R.id.tv_comment_num)
    TextView mCommentNumTextView;

    WebView mWebView;

    BottomSheetDialog bottomSheetDialog;

    private String nid = "";

    private CommentDialog commentDialog;

    private ProgressDialog progressDialog = null;

    ReplyListAdapter replyListAdapter;

    private int currentPage = 1;

    private int pageSize = 20;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && !StringUtils.isEmpty(bundle.getString("nid"))) {
            nid = bundle.getString("nid");
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在操作");

        avi.show();
        newsInfoDetailPresenterImp = new NewsInfoDetailPresenterImp(this, this);
        newsCommentPresenterImp = new NewsCommentPresenterImp(this, this);

        //初始化评论列表
        bottomSheetDialog = new BottomSheetDialog(this);
        View userCommentView = LayoutInflater.from(NewsDetailActivity.this).inflate(R.layout.user_comment_list_view, null);
        RecyclerView mReplayListView = userCommentView.findViewById(R.id.rv_reply_list);
        final EditText replayEditText = userCommentView.findViewById(R.id.tv_reply_send);
        Button mSendButton = userCommentView.findViewById(R.id.btn_send);

        replyListAdapter = new ReplyListAdapter(this, null);
        mReplayListView.setLayoutManager(new LinearLayoutManager(this));
        mReplayListView.setAdapter(replyListAdapter);
        replyListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                newsCommentPresenterImp.getNewsCommentList("1", "1", currentPage);
            }
        }, mReplayListView);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isEmpty(replayEditText.getText())) {
                    ToastUtils.showLong("请输入回复内容");
                    return;
                }
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }
                newsCommentPresenterImp.addNewsComment("1", "566", replayEditText.getText().toString());
            }
        });

        bottomSheetDialog.setContentView(userCommentView);

        newsInfoDetailPresenterImp.getNewsInfoDetail(nid);
        newsCommentPresenterImp.getNewsCommentList("1", "1", currentPage);

    }

    @OnClick(R.id.layout_comment_info)
    void commentDialog() {
        bottomSheetDialog.show();
    }

    @OnClick(R.id.tv_send_commit)
    void sendComment() {
        if (commentDialog == null) {
            commentDialog = new CommentDialog(NewsDetailActivity.this, 1);
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
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(ResultInfo tData) {
        Logger.i(com.alibaba.fastjson.JSONObject.toJSONString(tData));

        avi.hide();
        mContentLayout.setVisibility(View.VISIBLE);

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }

        if (tData != null && tData.getCode() == Constants.SUCCESS) {

            if (tData instanceof NewsInfoDetailRet) {
                if (((NewsInfoDetailRet) tData).getData() != null && ((NewsInfoDetailRet) tData).getData().getArticleInfo() != null) {
                    mTitleTextView.setText(((NewsInfoDetailRet) tData).getData().getArticleInfo().getTitle());
                    mAuthorTextView.setText("作者：" + ((NewsInfoDetailRet) tData).getData().getArticleInfo().getAuthor());
                    mDateTextView.setText(((NewsInfoDetailRet) tData).getData().getArticleInfo().getPublishDate());

                    String content = ((NewsInfoDetailRet) tData).getData().getArticleInfo().getContent();
                    mWebView = new WebView(NewsDetailActivity.this);
                    mWebView.loadData(Html.fromHtml(content).toString(), "text/html; charset=UTF-8", null);
                    mWebViewLayout.addView(mWebView);
                    mCommentNumTextView.setText(((NewsInfoDetailRet) tData).getData().getCommentNum() + "");
                }
            }

            if (tData instanceof NewsCommentRet) {
                if (((NewsCommentRet) tData).getData() == null || ((NewsCommentRet) tData).getData().size() == 0) {
                    ToastUtils.showLong("评论成功");
                } else {
                    if (currentPage == 1) {
                        replyListAdapter.setNewData(((NewsCommentRet) tData).getData());
                    } else {
                        replyListAdapter.addData(((NewsCommentRet) tData).getData());
                        if (((NewsCommentRet) tData).getData().size() == pageSize) {
                            replyListAdapter.loadMoreComplete();
                        } else {
                            replyListAdapter.loadMoreEnd();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        avi.hide();
        mContentLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void sendContent(String content, int type) {
        newsCommentPresenterImp.addNewsComment("1", "566", content);

        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }
}
