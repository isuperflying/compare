package com.yc.compare.ui.fragment;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
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
import com.yc.compare.bean.HomeDataInfoRet;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.HomeDataPresenterImp;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by iflying on 2017/12/14.
 */

public class HomeFragment extends BaseFragment implements HomeDataView {

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

    List<String> mTitleDataList;

    private HomeDataPresenterImp homeDataPresenterImp;

    private String newsId;

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

    public void initBanner(List<BannerInfo> blist) {
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
    public void loadDataSuccess(HomeDataInfoRet tData) {
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData.getData() != null) {
                initBanner(tData.getData().getBanner());
                initFragments(tData.getData().getCategoryInfoList());
                if (tData.getData().getNews() != null && tData.getData().getNews().size() > 0) {
                    mNewsTextView.setText(tData.getData().getNews().get(0).getTitle());
                    newsId = tData.getData().getNews().get(0).getId();
                }
            }
        }
    }

    /**
     * @param throwable 异常类型
     * @descriptoin 请求数据错误
     */
    @Override
    public void loadDataError(Throwable throwable) {

    }
}
