package com.yc.compare.ui;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.R;
import com.yc.compare.bean.NewsInfoDetailRet;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.NewsInfoDetailPresenterImp;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.view.NewsInfoDetailView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class NewsDetailActivity extends BaseFragmentActivity implements NewsInfoDetailView {

    private NewsInfoDetailPresenterImp newsInfoDetailPresenterImp;

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

    @BindView(R.id.layout_user_comment)
    LinearLayout mUserCommentLayout;

    WebView mWebView;

    BottomSheetDialog bottomSheetDialog;

    private String nid = "";

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

        avi.show();
        newsInfoDetailPresenterImp = new NewsInfoDetailPresenterImp(this, this);

        bottomSheetDialog = new BottomSheetDialog(this);
        View userCommentView = LayoutInflater.from(NewsDetailActivity.this).inflate(R.layout.user_comment_list_view, null);
        bottomSheetDialog.setContentView(userCommentView);

        newsInfoDetailPresenterImp.getNewsInfoDetail(nid);
    }

    @OnClick(R.id.layout_user_comment)
    void commentDialog() {
        bottomSheetDialog.show();
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
    public void loadDataSuccess(NewsInfoDetailRet tData) {
        Logger.i(com.alibaba.fastjson.JSONObject.toJSONString(tData));

        avi.hide();
        mContentLayout.setVisibility(View.VISIBLE);

        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData.getData() != null && tData.getData().getArticleInfo() != null) {
                mTitleTextView.setText(tData.getData().getArticleInfo().getTitle());
                mAuthorTextView.setText("作者：" + tData.getData().getArticleInfo().getAuthor());
                mDateTextView.setText(tData.getData().getArticleInfo().getPublishDate());

                String content = tData.getData().getArticleInfo().getContent();
                mWebView = new WebView(NewsDetailActivity.this);
                mWebView.loadData(Html.fromHtml(content).toString(), "text/html; charset=UTF-8", null);
                mWebViewLayout.addView(mWebView);
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        avi.hide();
        mContentLayout.setVisibility(View.VISIBLE);
    }
}
