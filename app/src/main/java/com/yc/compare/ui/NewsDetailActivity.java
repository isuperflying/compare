package com.yc.compare.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.R;
import com.yc.compare.bean.NewsComment;
import com.yc.compare.bean.NewsCommentRet;
import com.yc.compare.bean.NewsInfoDetailRet;
import com.yc.compare.bean.ResultInfo;
import com.yc.compare.bean.UserInfo;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.NewsCommentPresenterImp;
import com.yc.compare.presenter.NewsInfoDetailPresenterImp;
import com.yc.compare.ui.adapter.GoodInfoAdapter;
import com.yc.compare.ui.adapter.RelevantAdapter;
import com.yc.compare.ui.adapter.ReplyListAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.ui.custom.CommentDialog;
import com.yc.compare.view.NewsInfoDetailView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.shaohui.bottomdialog.BottomDialog;

/**
 * Created by myflying on 2018/12/3.
 */
public class NewsDetailActivity extends BaseFragmentActivity implements NewsInfoDetailView, CommentDialog.SendBackListener, View.OnClickListener {

    private NewsInfoDetailPresenterImp newsInfoDetailPresenterImp;

    private NewsCommentPresenterImp newsCommentPresenterImp;

    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;

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

    @BindView(R.id.relevant_list)
    RecyclerView mRelevantListView;

    @BindView(R.id.good_list)
    RecyclerView mGoodListView;

    @BindView(R.id.layout_relevant)
    LinearLayout mRelevantLayout;

    @BindView(R.id.layout_rec_good_list)
    LinearLayout mGoodListLayout;

    EditText replayEditText;

    WebView mWebView;

    private String nid = "";

    private CommentDialog commentDialog;

    private ProgressDialog progressDialog = null;

    ReplyListAdapter replyListAdapter;

    private int currentPage = 1;

    private int pageSize = 20;

    private UserInfo userInfo;

    private int commentCount;//评论数

    private boolean isInputContent;

    BottomSheetDialog shareDialog;

    private ShareAction shareAction;

    BottomDialog commentListDialog;

    List<NewsComment> newsCommentList;

    RelevantAdapter relevantAdapter;

    GoodInfoAdapter goodInfoAdapter;

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

        if (!StringUtils.isEmpty(SPUtils.getInstance().getString(Constants.USER_INFO))) {
            Logger.i(SPUtils.getInstance().getString(Constants.USER_INFO));
            userInfo = JSON.parseObject(SPUtils.getInstance().getString(Constants.USER_INFO), new TypeReference<UserInfo>() {
            });
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中");

        avi.show();
        newsInfoDetailPresenterImp = new NewsInfoDetailPresenterImp(this, this);
        newsCommentPresenterImp = new NewsCommentPresenterImp(this, this);

        newsInfoDetailPresenterImp.getNewsInfoDetail(nid);
        newsCommentPresenterImp.getNewsCommentList(userInfo != null ? userInfo.getUserId() : "", nid, currentPage);
        initShareDialog();

        if (shareAction == null) {
            UMImage image = new UMImage(this, R.drawable.share_logo);//资源文件
            shareAction = new ShareAction(this);

            UMWeb web = new UMWeb("http://m.8688g.com/u/5305.html");
            web.setTitle("全球比价");//标题
            web.setThumb(image);  //缩略图
            web.setDescription(getResources().getString(R.string.share_content));//描述

            shareAction.withMedia(web).setCallback(shareListener);//回调监听器
        }

        relevantAdapter = new RelevantAdapter(this, null);
        mRelevantListView.setLayoutManager(new LinearLayoutManager(this));
        mRelevantListView.setAdapter(relevantAdapter);

        relevantAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }
                String tempNid = relevantAdapter.getData().get(position).getArticleId();
                currentPage = 1;
                newsInfoDetailPresenterImp.getNewsInfoDetail(tempNid);
                newsCommentPresenterImp.getNewsCommentList(userInfo != null ? userInfo.getUserId() : "", tempNid, currentPage);
            }
        });

        goodInfoAdapter = new GoodInfoAdapter(this, null);
        mGoodListView.setLayoutManager(new GridLayoutManager(this, 2));
        mGoodListView.setAdapter(goodInfoAdapter);
        goodInfoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(NewsDetailActivity.this, GoodDetailActivity.class);
                intent.putExtra("good_id", goodInfoAdapter.getData().get(position).getId());
                startActivity(intent);
            }
        });
    }

    public void initCommentView() {

        commentListDialog = BottomDialog.create(getSupportFragmentManager())
                .setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View userCommentView) {
                        //初始化评论列表
                        //View userCommentView = LayoutInflater.from(NewsDetailActivity.this).inflate(commentListDialog.getLayoutRes(), null);
                        RecyclerView mReplayListView = userCommentView.findViewById(R.id.rv_reply_list);
                        replayEditText = userCommentView.findViewById(R.id.tv_reply_send);
                        Button mSendButton = userCommentView.findViewById(R.id.btn_send);

                        replyListAdapter = new ReplyListAdapter(NewsDetailActivity.this, newsCommentList);
                        mReplayListView.setLayoutManager(new LinearLayoutManager(NewsDetailActivity.this));
                        mReplayListView.setAdapter(replyListAdapter);
                        replyListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                            @Override
                            public void onLoadMoreRequested() {
                                currentPage++;
                                newsCommentPresenterImp.getNewsCommentList(userInfo != null ? userInfo.getUserId() : "", nid, currentPage);
                            }
                        }, mReplayListView);

                        if (newsCommentList.size() == pageSize) {
                            replyListAdapter.loadMoreComplete();
                        } else {
                            replyListAdapter.loadMoreEnd();
                        }

                        mSendButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (StringUtils.isEmpty(replayEditText.getText())) {
                                    ToastUtils.showLong("请输入回复内容");
                                    return;
                                }

                                if (userInfo == null) {
                                    new AlertDialog.Builder(NewsDetailActivity.this)
                                            .setPositiveButton(R.string.config_txt, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(@NonNull DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(NewsDetailActivity.this, LoginActivity.class);
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

                                if (progressDialog != null && !progressDialog.isShowing()) {
                                    progressDialog.show();
                                }
                                isInputContent = true;
                                newsCommentPresenterImp.addNewsComment(userInfo != null ? userInfo.getUserId() : "", nid, replayEditText.getText().toString());
                            }
                        });
                    }
                })
                .setLayoutRes(R.layout.user_comment_list_view)
                .setDimAmount(0.3f)            // Dialog window dim amount(can change window background color）, range：0 to 1，default is : 0.2f
                .setCancelOutside(true)     // click the external area whether is closed, default is : true
                .setTag("comment");   // setting the DialogFragment tag

        commentListDialog.show();
    }

    @OnClick(R.id.layout_comment_info)
    void commentDialog() {
        if (commentCount > 0) {
            initCommentView();
        }
    }

    @OnClick(R.id.tv_send_commit)
    void sendComment() {

        if (userInfo == null) {
            new AlertDialog.Builder(this)
                    .setPositiveButton(R.string.config_txt, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            Intent intent = new Intent(NewsDetailActivity.this, LoginActivity.class);
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

        if (commentDialog == null) {
            commentDialog = new CommentDialog(NewsDetailActivity.this, 1);
            commentDialog.setSendBackListener(this);
        }
        commentDialog.show(getFragmentManager(), "dialog");
    }

    public void initShareDialog() {
        View shareView = LayoutInflater.from(this).inflate(R.layout.share_view, null);
        LinearLayout weixinLayout = shareView.findViewById(R.id.layout_weixin);
        LinearLayout cicleLayout = shareView.findViewById(R.id.layout_circle);
        LinearLayout qqLayout = shareView.findViewById(R.id.layout_qq);
        LinearLayout qzoneLayout = shareView.findViewById(R.id.layout_qzone);

        weixinLayout.setOnClickListener(this);
        cicleLayout.setOnClickListener(this);
        qqLayout.setOnClickListener(this);
        qzoneLayout.setOnClickListener(this);

        if (shareDialog == null) {
            shareDialog = new BottomSheetDialog(this);
            shareDialog.setContentView(shareView);
        }
    }

    @OnClick(R.id.iv_comment_share)
    void share() {
        if (shareDialog != null && !shareDialog.isShowing()) {
            shareDialog.show();
        }
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

        mContentLayout.setVisibility(View.VISIBLE);

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (commentDialog != null) {
            commentDialog.dismiss();
        }

        if (tData != null && tData.getCode() == Constants.SUCCESS) {

            if (tData instanceof NewsInfoDetailRet) {
                scrollView.fling(0);
                scrollView.smoothScrollTo(0, 0);
                if (((NewsInfoDetailRet) tData).getData() != null) {

                    if (((NewsInfoDetailRet) tData).getData().getArticleInfo() != null) {
                        mTitleTextView.setText(((NewsInfoDetailRet) tData).getData().getArticleInfo().getTitle());
                        mAuthorTextView.setText("作者：" + ((NewsInfoDetailRet) tData).getData().getArticleInfo().getAuthor());
                        mDateTextView.setText(((NewsInfoDetailRet) tData).getData().getArticleInfo().getPublishDate());

                        String content = ((NewsInfoDetailRet) tData).getData().getArticleInfo().getContent();
                        mWebView = new WebView(NewsDetailActivity.this);
                        mWebView.loadData(Html.fromHtml(content).toString(), "text/html; charset=UTF-8", null);
                        mWebViewLayout.addView(mWebView);
                        commentCount = ((NewsInfoDetailRet) tData).getData().getCommentNum();
                        mCommentNumTextView.setText(commentCount + "");
                        mWebView.setWebViewClient(new WebViewClient(){

                            @Override
                            public void onPageFinished(WebView view, String url) {
                                super.onPageFinished(view, url);
                                avi.hide();
                            }
                        });
                    }

                    if (((NewsInfoDetailRet) tData).getData().getArticleRecommendList() != null) {
                        mRelevantLayout.setVisibility(View.VISIBLE);
                        relevantAdapter.setNewData(((NewsInfoDetailRet) tData).getData().getArticleRecommendList());
                    }

                    if (((NewsInfoDetailRet) tData).getData().getRelevantGoodsList() != null) {
                        mGoodListLayout.setVisibility(View.VISIBLE);
                        goodInfoAdapter.setNewData(((NewsInfoDetailRet) tData).getData().getRelevantGoodsList());
                    }
                }
            }

            if (tData instanceof NewsCommentRet) {
                avi.hide();
                if (((NewsCommentRet) tData).getData() == null || ((NewsCommentRet) tData).getData().size() == 0) {
                    if (isInputContent) {

                        ToastUtils.showLong("评论成功");

                        isInputContent = false;
                        newsCommentPresenterImp.getNewsCommentList(userInfo != null ? userInfo.getUserId() : "", nid, currentPage);
                        if (commentListDialog != null && commentListDialog.getDialog().isShowing()) {
                            //KeyboardUtils.hideSoftInput(commentListDialog.getView());
                            commentListDialog.getDialog().dismiss();
                        }
                        mCommentNumTextView.setText((commentCount + 1) + "");
                    }
                } else {
                    if (currentPage == 1) {
                        newsCommentList = ((NewsCommentRet) tData).getData();
                        //replyListAdapter.setNewData(newsCommentList);
                        if (commentListDialog.getDialog().isShowing()) {
                            replyListAdapter.setNewData(newsCommentList);
                        }

                    } else {
//                        if (newsCommentList != null) {
//                            newsCommentList.addAll(((NewsCommentRet) tData).getData());
//                        }
                        replyListAdapter.addData(((NewsCommentRet) tData).getData());
                    }

                    if (newsCommentList.size() == pageSize) {
                        replyListAdapter.loadMoreComplete();
                    } else {
                        replyListAdapter.loadMoreEnd();
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

        if (userInfo == null) {
            new AlertDialog.Builder(this)
                    .setPositiveButton(R.string.config_txt, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            Intent intent = new Intent(NewsDetailActivity.this, LoginActivity.class);
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
        isInputContent = true;
        newsCommentPresenterImp.addNewsComment(userInfo != null ? userInfo.getUserId() : "", nid, content);

        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(NewsDetailActivity.this, "分享成功", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(NewsDetailActivity.this, "分享失败", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(NewsDetailActivity.this, "取消分享", Toast.LENGTH_LONG).show();

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_weixin:
                shareAction.setPlatform(SHARE_MEDIA.WEIXIN).share();
                break;
            case R.id.layout_circle:
                shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).share();
                break;
            case R.id.layout_qq:
                shareAction.setPlatform(SHARE_MEDIA.QQ).share();
                break;
            case R.id.layout_qzone:
                shareAction.setPlatform(SHARE_MEDIA.QZONE).share();
                break;
            default:
                break;
        }

        if (shareDialog != null && shareDialog.isShowing()) {
            shareDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
