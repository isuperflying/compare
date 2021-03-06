package com.yc.compare.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yc.compare.R;
import com.yc.compare.bean.AddCollectInfoRet;
import com.yc.compare.bean.GoodDetailInfo;
import com.yc.compare.bean.GoodDetailInfoRet;
import com.yc.compare.bean.ImageInfo;
import com.yc.compare.bean.ResultInfo;
import com.yc.compare.bean.UserInfo;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.AddCollectInfoPresenterImp;
import com.yc.compare.presenter.GoodDetailInfoPresenterImp;
import com.yc.compare.ui.adapter.GoodInfoAdapter;
import com.yc.compare.ui.adapter.ImageInfoAdapter;
import com.yc.compare.ui.adapter.ParamsAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.ui.custom.GlideImageLoader;
import com.yc.compare.ui.custom.LineChartView;
import com.yc.compare.view.GoodDetailInfoView;
import com.youth.banner.Banner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/6.
 */
public class GoodDetailActivity extends BaseFragmentActivity implements GoodDetailInfoView, View.OnClickListener {

    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;

    @BindView(R.id.content_layout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.banner)
    Banner mBanner;

    @BindView(R.id.tv_good_name)
    TextView mGoodNameTextView;

    @BindView(R.id.tv_good_price)
    TextView mGoodPriceTextView;

    @BindView(R.id.iv_follow_good)
    ImageView mIsFollowGood;

    @BindView(R.id.tv_is_follow)
    TextView mIsFollowTextView;

    @BindView(R.id.tv_detail_title)
    TextView mTitleTextView;

    @BindView(R.id.iv_back)
    ImageView mBackImageView;

    @BindView(R.id.iv_follow)
    ImageView mFollowImageView;

    @BindView(R.id.iv_share)
    ImageView mShareImageView;

    @BindView(R.id.line_chart_view)
    LineChartView lineChart;

    @BindView(R.id.good_image_list)
    RecyclerView mGoodImageListView;

    @BindView(R.id.layout_web_view)
    LinearLayout mGoodDetailView;

    @BindView(R.id.tv_comment_count)
    TextView mCountTextView;

    @BindView(R.id.iv_comment_user_head)
    ImageView mCommentHeadImageView;

    @BindView(R.id.tv_comment_user_name)
    TextView mCommentUserNameTextView;

    @BindView(R.id.tv_comment_content)
    TextView mCommentContentTextView;

    @BindView(R.id.rec_list)
    RecyclerView mRecListView;

    @BindView(R.id.tv_other_country)
    TextView mOtherCountryTextView;

    @BindView(R.id.tv_min_price)
    TextView mMinPriceTextView;

    ImageInfoAdapter imageInfoAdapter;

    GoodInfoAdapter recGoodInfoAdapter;

    private CustomPopWindow customPopWindow;

    private GoodDetailInfoPresenterImp goodDetailInfoPresenterImp;

    private AddCollectInfoPresenterImp addCollectInfoPresenterImp;

    private int[] dataArr = new int[]{150, 120, 200, 88, 139, 66};

    private ShareAction shareAction;

    BottomSheetDialog shareDialog;

    WebView mWebView;

    private String goodId;

    private UserInfo userInfo;

    private ProgressDialog progressDialog = null;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_good_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initGoodImageList();
    }

    public void initViews() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中");

        if (!StringUtils.isEmpty(SPUtils.getInstance().getString(Constants.USER_INFO))) {
            Logger.i(SPUtils.getInstance().getString(Constants.USER_INFO));
            userInfo = JSON.parseObject(SPUtils.getInstance().getString(Constants.USER_INFO), new TypeReference<UserInfo>() {
            });
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getString("good_id") != null) {
            goodId = bundle.getString("good_id");
        }

        initGoodParams();

        recGoodInfoAdapter = new GoodInfoAdapter(this, null);
        mRecListView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecListView.setAdapter(recGoodInfoAdapter);

        recGoodInfoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }

                String recGoodId = recGoodInfoAdapter.getData().get(position).getId();
                goodDetailInfoPresenterImp.getGoodDetailInfoByParams(userInfo != null ? userInfo.getUserId() : "", recGoodId);
            }
        });

        goodDetailInfoPresenterImp = new GoodDetailInfoPresenterImp(this, this);
        addCollectInfoPresenterImp = new AddCollectInfoPresenterImp(this, this);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Logger.i("bar height --->" + appBarLayout.getHeight() + "---verticalOffset--->" + verticalOffset);
                if (-verticalOffset >= appBarLayout.getHeight() - SizeUtils.dp2px(72)) {
                    mTitleTextView.setVisibility(View.VISIBLE);
                    mBackImageView.setImageResource(R.mipmap.back_icon);
                    mFollowImageView.setImageResource(R.mipmap.white_follow);
                    mShareImageView.setImageResource(R.mipmap.white_share);
                } else {
                    mTitleTextView.setVisibility(View.INVISIBLE);
                    mBackImageView.setImageResource(R.mipmap.detail_back);
                    mFollowImageView.setImageResource(R.mipmap.detail_follow);
                    mShareImageView.setImageResource(R.mipmap.detail_share);
                }
            }
        });

        Logger.i("width--->" + ScreenUtils.getScreenWidth() / ScreenUtils.getScreenDensity() / 6);
        goodDetailInfoPresenterImp.getGoodDetailInfoByParams(userInfo != null ? userInfo.getUserId() : "", goodId);

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
    }

    public void initBanner(String[] blist) {
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < blist.length; i++) {
            urls.add(blist[i]);
        }
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(urls);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    public void initGoodImageList() {
        imageInfoAdapter = new ImageInfoAdapter(this, null);
        mGoodImageListView.setLayoutManager(new LinearLayoutManager(this));
        mGoodImageListView.setAdapter(imageInfoAdapter);
        mGoodImageListView.setNestedScrollingEnabled(false);
    }

    BottomSheetDialog bottomSheetDialog;
    ParamsAdapter paramsAdapter;

    public void initGoodParams() {
        View paramsView = LayoutInflater.from(this).inflate(R.layout.good_params_view, null);
        RecyclerView paramsList = paramsView.findViewById(R.id.params_list);
        Button paramsDoneBtn = paramsView.findViewById(R.id.btn_params_done);
        paramsAdapter = new ParamsAdapter(this, null);
        paramsList.setLayoutManager(new LinearLayoutManager(this));
        paramsList.setAdapter(paramsAdapter);

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(paramsView);
        paramsDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
            }
        });
    }

    @OnClick(R.id.layout_params)
    void showParams() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
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

    @OnClick(R.id.iv_share)
    void share() {
        if (shareDialog != null && !shareDialog.isShowing()) {
            shareDialog.show();
        }
    }

    @OnClick(R.id.layout_follow)
    void follow() {
        addCollectInfoPresenterImp.doCollect(userInfo != null ? userInfo.getUserId() : "", goodId);
    }

    @OnClick(R.id.tv_all_comment)
    void allComment() {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("good_id", goodId);
        startActivity(intent);
    }

    @OnClick(R.id.tv_other_country)
    void otherCountry() {
        Intent intent = new Intent(this, OtherPriceActivity.class);
        intent.putExtra("good_id", goodId);
        startActivity(intent);
    }

    public void initLineView(List<GoodDetailInfo.PriceItem> priceItems, int ySpace) {
        List<LineChartView.Data> datas = new ArrayList<>();
        List<String> xDatas = new ArrayList<>();
        for (GoodDetailInfo.PriceItem item : priceItems) {
            if (!StringUtils.isEmpty(item.getMarketPrice())) {
                LineChartView.Data data = new LineChartView.Data((int) Double.parseDouble(item.getMarketPrice()));
                datas.add(data);
            }
            if (!StringUtils.isEmpty(item.getName())) {
                xDatas.add(item.getName());
            }
        }

        lineChart.setData(datas);
        lineChart.setXLineData(xDatas);

        int tempXSpaceNum = 1;
        if (xDatas != null && xDatas.size() > 0) {
            tempXSpaceNum = xDatas.size() - 1;
            tempXSpaceNum = tempXSpaceNum <= 0 ? 1 : tempXSpaceNum;
        }

        lineChart.setRulerYSpace(ySpace);
        lineChart.setStepSpace((int) ((ScreenUtils.getScreenWidth() / ScreenUtils.getScreenDensity() - 80) / tempXSpaceNum));
        lineChart.setShowTable(true);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(ResultInfo tData) {
        Logger.i(JSONObject.toJSONString(tData));
        if (tData != null && tData.getCode() == Constants.SUCCESS) {

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if (tData instanceof GoodDetailInfoRet) {
                scrollView.fling(0);
                scrollView.smoothScrollTo(0, 0);

                mIsFollowGood.setImageResource(((GoodDetailInfoRet) tData).getData().getIsCollect() == 0 ? R.mipmap.follow_good_normal : R.mipmap.follow_good_selected);
                mIsFollowTextView.setText(((GoodDetailInfoRet) tData).getData().getIsCollect() == 0 ? R.string.is_not_follow : R.string.is_follow);

                if (((GoodDetailInfoRet) tData).getData().getInfo() != null) {

                    mGoodNameTextView.setText(((GoodDetailInfoRet) tData).getData().getInfo().getGoodName());
                    mGoodPriceTextView.setText(((GoodDetailInfoRet) tData).getData().getInfo().getGoodPrice() + "");

                    if (((GoodDetailInfoRet) tData).getData().getInfo().getGoodsImages() != null && ((GoodDetailInfoRet) tData).getData().getInfo().getGoodsImages().length > 0) {
                        initBanner(((GoodDetailInfoRet) tData).getData().getInfo().getGoodsImages());
                    }

                    if (((GoodDetailInfoRet) tData).getData().getInfo().getAttrValue() != null && ((GoodDetailInfoRet) tData).getData().getInfo().getAttrValue().size() > 0) {
                        paramsAdapter.setNewData(((GoodDetailInfoRet) tData).getData().getInfo().getAttrValue());
                    }


                    if (!StringUtils.isEmpty(((GoodDetailInfoRet) tData).getData().getInfo().getGoodsContentImgs())) {
                        mGoodImageListView.setVisibility(View.VISIBLE);
                        mGoodDetailView.setVisibility(View.GONE);
                        String[] tempImgs = ((GoodDetailInfoRet) tData).getData().getInfo().getGoodsContentImgs().split(";");
                        List<ImageInfo> list = new ArrayList<>();
                        for (int i = 0; i < tempImgs.length; i++) {
                            list.add(new ImageInfo(i + "", tempImgs[i]));
                        }
                        imageInfoAdapter.setNewData(list);
                    } else {
                        mGoodImageListView.setVisibility(View.GONE);
                        mGoodDetailView.setVisibility(View.VISIBLE);

                        mWebView = new WebView(GoodDetailActivity.this);
                        WebSettings webSettings = mWebView.getSettings();//获取webview设置属性
                        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
                        webSettings.setJavaScriptEnabled(true);//支持js
                        webSettings.setBuiltInZoomControls(true); // 显示放大缩小
                        webSettings.setSupportZoom(true); // 可以缩放

                        Logger.i("content--->" + ((GoodDetailInfoRet) tData).getData().getInfo().getGoodsContent());
                        //mWebView.loadData(Html.fromHtml(((GoodDetailInfoRet) tData).getData().getInfo().getGoodsContent()).toString(), "text/html; charset=UTF-8", null);
                        String content = Html.fromHtml(((GoodDetailInfoRet) tData).getData().getInfo().getGoodsContent()).toString();
                        mWebView.loadDataWithBaseURL(null, getNewContent(content), "text/html", "utf-8", null);
                        mGoodDetailView.addView(mWebView);
                    }
                }

                if (((GoodDetailInfoRet) tData).getData().getPrices() != null && ((GoodDetailInfoRet) tData).getData().getPrices().getList() != null && ((GoodDetailInfoRet) tData).getData().getPrices().getList().size() > 0) {
                    mMinPriceTextView.setText(((GoodDetailInfoRet) tData).getData().getPrices().getMin() + "");
                    double max = ((GoodDetailInfoRet) tData).getData().getPrices().getMax();
                    double min = ((GoodDetailInfoRet) tData).getData().getPrices().getMin();
                    int ySpace = (int) ((max - min) / 5);
                    initLineView(((GoodDetailInfoRet) tData).getData().getPrices().getList(), ySpace == 0 ? 60 : ySpace);
                }

                if (((GoodDetailInfoRet) tData).getData().getComments() != null) {
                    mCountTextView.setText("全部评价(" + ((GoodDetailInfoRet) tData).getData().getComments().getListCount() + ")");
                    if (((GoodDetailInfoRet) tData).getData().getComments().getList() != null && ((GoodDetailInfoRet) tData).getData().getComments().getList().size() > 0) {
                        GoodDetailInfo.GdCommentItem commentItem = ((GoodDetailInfoRet) tData).getData().getComments().getList().get(0);
                        Glide.with(this).load(commentItem.getHeadPic()).into(mCommentHeadImageView);
                        mCommentUserNameTextView.setText(commentItem.getNickName());
                        mCommentContentTextView.setText(commentItem.getContent());
                    }
                }
                if (((GoodDetailInfoRet) tData).getData().getSimilar() != null && ((GoodDetailInfoRet) tData).getData().getSimilar().size() > 0) {
                    recGoodInfoAdapter.setNewData(((GoodDetailInfoRet) tData).getData().getSimilar());
                }
            }

            if (tData instanceof AddCollectInfoRet) {
                Logger.i("AddCollectInfoRet--->");
                Logger.i(JSONObject.toJSONString(tData));
                if (((AddCollectInfoRet) tData).getData().getIsCollect() == 0) {
                    ToastUtils.showLong("已取消");
                } else {
                    ToastUtils.showLong("已关注");
                }
                mIsFollowGood.setImageResource(((AddCollectInfoRet) tData).getData().getIsCollect() == 0 ? R.mipmap.follow_good_normal : R.mipmap.follow_good_selected);
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }

    public static String getNewContent(String htmltext) {
        try {
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width", "100%").attr("height", "auto");
            }
            return doc.toString();
        } catch (Exception e) {
            return htmltext;
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
            Toast.makeText(GoodDetailActivity.this, "分享成功", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(GoodDetailActivity.this, "分享失败", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(GoodDetailActivity.this, "取消分享", Toast.LENGTH_LONG).show();

        }
    };

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
