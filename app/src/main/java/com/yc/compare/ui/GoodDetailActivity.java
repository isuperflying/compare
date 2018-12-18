package com.yc.compare.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.zhouwei.library.CustomPopWindow;
import com.orhanobut.logger.Logger;
import com.yc.compare.R;
import com.yc.compare.bean.GoodDetailInfo;
import com.yc.compare.bean.GoodDetailInfoRet;
import com.yc.compare.bean.ImageInfo;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.GoodDetailInfoPresenterImp;
import com.yc.compare.ui.adapter.ImageInfoAdapter;
import com.yc.compare.ui.adapter.ParamsAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.ui.custom.GlideImageLoader;
import com.yc.compare.ui.custom.LineChartView;
import com.yc.compare.view.GoodDetailInfoView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/6.
 */
public class GoodDetailActivity extends BaseFragmentActivity implements GoodDetailInfoView {

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

    @BindView(R.id.tv_comment_count)
    TextView mCountTextView;

    @BindView(R.id.iv_comment_user_head)
    ImageView mCommentHeadImageView;

    @BindView(R.id.tv_comment_user_name)
    TextView mCommentUserNameTextView;

    @BindView(R.id.tv_comment_content)
    TextView mCommentContentTextView;

    ImageInfoAdapter imageInfoAdapter;

    private CustomPopWindow customPopWindow;

    private GoodDetailInfoPresenterImp goodDetailInfoPresenterImp;

    private int[] dataArr = new int[]{150, 120, 200, 88, 139, 66};

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
        initGoodParams();
        goodDetailInfoPresenterImp = new GoodDetailInfoPresenterImp(this, this);

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

        goodDetailInfoPresenterImp.getGoodDetailInfoByParams("1", "1");
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

    @OnClick(R.id.iv_share)
    void share() {
        View paramsView = LayoutInflater.from(this).inflate(R.layout.share_view, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(paramsView);
        bottomSheetDialog.show();
    }

    public void initLineView(List<GoodDetailInfo.PriceItem> priceItems) {
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

        lineChart.setRulerYSpace(100);
        lineChart.setStepSpace((int) ((ScreenUtils.getScreenWidth() / ScreenUtils.getScreenDensity() - 36) / 6));
        lineChart.setShowTable(true);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(GoodDetailInfoRet tData) {
        Logger.i(JSONObject.toJSONString(tData));
        if (tData != null && tData.getCode() == Constants.SUCCESS) {

            mIsFollowGood.setImageResource(tData.getData().getIsCollect() == 0 ? R.mipmap.follow_good_normal : R.mipmap.follow_good_selected);
            mIsFollowTextView.setText(tData.getData().getIsCollect() == 0 ? R.string.is_not_follow : R.string.is_follow);

            if (tData.getData().getInfo() != null) {

                mGoodNameTextView.setText(tData.getData().getInfo().getGoodName());
                mGoodPriceTextView.setText(tData.getData().getInfo().getGoodPrice() + "");

                if (tData.getData().getInfo().getGoodsImages() != null && tData.getData().getInfo().getGoodsImages().length > 0) {
                    initBanner(tData.getData().getInfo().getGoodsImages());
                }

                if (!StringUtils.isEmpty(tData.getData().getInfo().getGoodsContent())) {
                    String[] tempImgs = tData.getData().getInfo().getGoodsContent().split(";");
                    List<ImageInfo> list = new ArrayList<>();
                    for (int i = 0; i < tempImgs.length; i++) {
                        list.add(new ImageInfo(i + "", tempImgs[i]));
                    }
                    imageInfoAdapter.setNewData(list);
                }

                if (tData.getData().getInfo().getAttrValue() != null && tData.getData().getInfo().getAttrValue().size() > 0) {
                    paramsAdapter.setNewData(tData.getData().getInfo().getAttrValue());
                }
            }

            if (tData.getData().getPrices() != null && tData.getData().getPrices().getList() != null && tData.getData().getPrices().getList().size() > 0) {
                initLineView(tData.getData().getPrices().getList());
            }

        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

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
