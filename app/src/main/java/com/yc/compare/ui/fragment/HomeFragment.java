package com.yc.compare.ui.fragment;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.orhanobut.logger.Logger;
import com.yc.compare.R;
import com.yc.compare.bean.BannerInfo;
import com.yc.compare.bean.CategoryInfo;
import com.yc.compare.bean.ResultInfo;
import com.yc.compare.presenter.HomeDataPresenterImp;
import com.yc.compare.ui.GoodDetailActivity;
import com.yc.compare.ui.GoodListActivity;
import com.yc.compare.ui.HotBrandActivity;
import com.yc.compare.ui.HotCountryActivity;
import com.yc.compare.ui.NewsDetailActivity;
import com.yc.compare.ui.SearchActivity;
import com.yc.compare.ui.adapter.SubFragmentAdapter;
import com.yc.compare.ui.base.BaseFragment;
import com.yc.compare.ui.custom.GlideImageLoader;
import com.yc.compare.ui.fragment.sub.MainAllFragment;
import com.yc.compare.view.HomeDataView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by iflying on 2017/12/14.
 */

public class HomeFragment extends BaseFragment implements HomeDataView,AppBarLayout.OnOffsetChangedListener{

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.top_content_layout)
    RelativeLayout mTopContentLayout;

    @BindView(R.id.banner)
    Banner mBanner;

    @BindView(R.id.search_layout)
    LinearLayout mSearchLayout;

    @BindView(R.id.layout_brand)
    LinearLayout mBrandLayout;

    @BindView(R.id.layout_country)
    LinearLayout mCountryLayout;

    @BindView(R.id.layout_sale)
    LinearLayout mSaleLayout;

    @BindView(R.id.tv_hot_news)
    TextView mNewsTextView;

    @BindView(R.id.iv_rec1)
    ImageView mRec1ImageView;

    @BindView(R.id.iv_rec2)
    ImageView mRec2ImageView;

    @BindView(R.id.iv_rec3)
    ImageView mRec3ImageView;

    List<String> mTitleDataList;

    private HomeDataPresenterImp homeDataPresenterImp;

    private String newsId;

    private String hotLfGoodId;

    private String hotRtGoodId;

    private String hotRbGoodId;

    private int specialLfJumpType;

    private int specialRtJumpType;

    private int specialRbJumpType;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    public void initViews() {
        homeDataPresenterImp = new HomeDataPresenterImp(this, getActivity());

        CollapsingToolbarLayout.LayoutParams search = new CollapsingToolbarLayout.LayoutParams(CollapsingToolbarLayout.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(36));
        search.setMargins(SizeUtils.dp2px(15), BarUtils.getStatusBarHeight(), SizeUtils.dp2px(15), 0);
        mSearchLayout.setLayoutParams(search);

        mTopContentLayout.setLayoutParams(new CollapsingToolbarLayout.LayoutParams(CollapsingToolbarLayout.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(484)));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //LogUtil.msg("TAG  " + verticalOffset + "--" + appBarLayout.getHeight() + " --" + collapsingToolbarLayout.getHeight());
                Logger.i("bar height --->" + appBarLayout.getHeight() + "---verticalOffset--->" + verticalOffset);
                if (-verticalOffset >= appBarLayout.getHeight() - SizeUtils.dp2px(130)) {
                    toolbar.setVisibility(View.VISIBLE);
                    mTopContentLayout.setVisibility(View.INVISIBLE);
                } else {
                    mTopContentLayout.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.INVISIBLE);
                }

            }
        });

        appBarLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    for (int i = 0; i < mViewPager.getChildCount(); i++) {
                        View view = mViewPager.getChildAt(i);
                        if (view instanceof RecyclerView) {
                            ViewCompat.stopNestedScroll(view);
                        }
                    }
                }
                return false;
            }
        });

        homeDataPresenterImp.initData();
    }

    public void initFragments(List<CategoryInfo> clist) {
        List<Fragment> fragments = new ArrayList<>();
        mTitleDataList = new ArrayList<>();
        for (int i = 0; i < clist.size(); i++) {
            mTitleDataList.add(clist.get(i).getName());
            fragments.add(MainAllFragment.newInstance(clist.get(i).getId()));
        }

        if (mTitleDataList.size() > 0) {
            SubFragmentAdapter viewPageAdapter = new SubFragmentAdapter(getChildFragmentManager(), fragments, mTitleDataList);
            mViewPager.setAdapter(viewPageAdapter);

            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            tabLayout.setupWithViewPager(mViewPager);
        }
    }

    public void initBanner(final List<BannerInfo> blist) {
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < blist.size(); i++) {
            urls.add(blist.get(i).getAdImage());
        }
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(urls);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerInfo bannerInfo = blist.get(position);

                int tempId = bannerInfo.getGoType();

                if (tempId == 0) {
                    return;
                }

                if (tempId == 1) {
                    Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
                    intent.putExtra("good_id", bannerInfo.getObjectId());
                    startActivity(intent);
                }

                if (tempId == 2) {
                    Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                    intent.putExtra("nid", bannerInfo.getObjectId());
                    startActivity(intent);
                }
            }
        });
    }

    @OnClick(R.id.layout_sale)
    void salePage() {
        Intent intent = new Intent(getActivity(), GoodListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_country)
    void countryPage() {
        Intent intent = new Intent(getActivity(), HotCountryActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_brand)
    void brandPage() {
        Intent intent = new Intent(getActivity(), HotBrandActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_hot_news)
    void newsDetail() {
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra("nid", StringUtils.isEmpty(newsId) ? "" : newsId);
        startActivity(intent);
    }

    @OnClick(R.id.search_layout)
    void toSearch() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.iv_rec1)
    void hotLfGoodDetail() {
        if (StringUtils.isEmpty(hotLfGoodId)) {
            return;
        }

        if (specialLfJumpType == 0) {
            return;
        }

        if (specialLfJumpType == 1) {
            Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
            intent.putExtra("good_id", hotLfGoodId);
            startActivity(intent);
        }
        if (specialLfJumpType == 2) {
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra("nid", StringUtils.isEmpty(hotLfGoodId) ? "" : hotLfGoodId);
            startActivity(intent);
        }
    }

    @OnClick(R.id.iv_rec2)
    void hotRtGoodDetail() {
        if (StringUtils.isEmpty(hotRtGoodId)) {
            return;
        }

        if (specialRtJumpType == 0) {
            return;
        }

        if (specialRtJumpType == 1) {
            Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
            intent.putExtra("good_id", hotRtGoodId);
            startActivity(intent);
        }
        if (specialRtJumpType == 2) {
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra("nid", StringUtils.isEmpty(hotRtGoodId) ? "" : hotRtGoodId);
            startActivity(intent);
        }
    }

    @OnClick(R.id.iv_rec3)
    void hotRbGoodDetail() {
        if (StringUtils.isEmpty(hotRbGoodId)) {
            return;
        }

        if (specialRbJumpType == 0) {
            return;
        }

        if (specialRbJumpType == 1) {
            Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
            intent.putExtra("good_id", hotRbGoodId);
            startActivity(intent);
        }
        if (specialRbJumpType == 2) {
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra("nid", StringUtils.isEmpty(hotRbGoodId) ? "" : hotRbGoodId);
            startActivity(intent);
        }
    }

    /**
     * @descriptoin 请求前加载progress
     */
    @Override
    public void showProgress() {

    }

    /**
     * @descriptoin 请求结束之后隐藏progress
     */
    @Override
    public void dismissProgress() {

    }

    /**
     * @param tData 数据类型
     * @descriptoin 请求数据成功
     */
    @Override
    public void loadDataSuccess(ResultInfo tData) {
//        if (tData != null && tData.getCode() == Constants.SUCCESS) {
//            if (tData.getData() != null) {
//                initBanner(tData.getData().getBanner());
//                initFragments(tData.getData().getCategoryInfoList());
//                if (tData.getData().getNews() != null && tData.getData().getNews().size() > 0) {
//                    mNewsTextView.setText(tData.getData().getNews().get(0).getTitle());
//                    newsId = tData.getData().getNews().get(0).getId();
//                }
//
//                if (tData.getData().getSpecial() != null) {
//                    if (tData.getData().getSpecial().size() >= 1) {
//                        specialLfJumpType = tData.getData().getSpecial().get(0).getGoType();
//                        hotLfGoodId = tData.getData().getSpecial().get(0).getObjectId();
//                        Glide.with(this).load(tData.getData().getSpecial().get(0).getAdImage()).into(mRec1ImageView);
//                    }
//                    if (tData.getData().getSpecial().size() >= 2) {
//                        specialRtJumpType = tData.getData().getSpecial().get(1).getGoType();
//                        hotRtGoodId = tData.getData().getSpecial().get(1).getObjectId();
//                        Glide.with(this).load(tData.getData().getSpecial().get(1).getAdImage()).into(mRec2ImageView);
//                    }
//                    if (tData.getData().getSpecial().size() >= 3) {
//                        specialRbJumpType = tData.getData().getSpecial().get(2).getGoType();
//                        hotRbGoodId = tData.getData().getSpecial().get(2).getObjectId();
//                        Glide.with(this).load(tData.getData().getSpecial().get(2).getAdImage()).into(mRec3ImageView);
//                    }
//                }
//            }
//        }
    }

    /**
     * @param throwable 异常类型
     * @descriptoin 请求数据错误
     */
    @Override
    public void loadDataError(Throwable throwable) {

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        Logger.i("verticalOffset--->" + verticalOffset);
    }
}
