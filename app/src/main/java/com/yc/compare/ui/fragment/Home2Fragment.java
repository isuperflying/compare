package com.yc.compare.ui.fragment;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.yc.compare.R;
import com.yc.compare.bean.BannerInfo;
import com.yc.compare.bean.CategoryInfo;
import com.yc.compare.bean.GoodInfoRet;
import com.yc.compare.bean.HomeDataInfoRet;
import com.yc.compare.bean.ResultInfo;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.GoodInfoPresenterImp;
import com.yc.compare.presenter.HomeDataPresenterImp;
import com.yc.compare.ui.GoodDetailActivity;
import com.yc.compare.ui.NewsDetailActivity;
import com.yc.compare.ui.adapter.GoodInfoAdapter;
import com.yc.compare.ui.adapter.SubFragmentAdapter;
import com.yc.compare.ui.base.BaseFragment;
import com.yc.compare.ui.custom.GlideImageLoader;
import com.yc.compare.ui.custom.GridSpacingItemDecoration;
import com.yc.compare.ui.fragment.sub.MainAllFragment;
import com.yc.compare.view.HomeDataView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iflying on 2017/12/14.
 */

public class Home2Fragment extends BaseFragment implements HomeDataView, AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.layout_appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    LinearLayout mSearchLayout;

    @BindView(R.id.home_list)
    RecyclerView mHomeDataListView;

    Banner mBanner;

    LinearLayout mBrandLayout;

    LinearLayout mCountryLayout;

    LinearLayout mSaleLayout;

    TextView mNewsTextView;

    ImageView mRec1ImageView;

    ImageView mRec2ImageView;

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

    private GoodInfoAdapter goodInfoAdapter;

    private GoodInfoPresenterImp goodInfoPresenterImp;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home2, null);
        ButterKnife.bind(this, root);
        initTop();
        initViews();
        return root;
    }

    public void initTop() {
        View topView = LayoutInflater.from(getActivity()).inflate(R.layout.home_top, null);
        mSearchLayout = topView.findViewById(R.id.search_layout);
        mBanner = topView.findViewById(R.id.banner);
        mBrandLayout = topView.findViewById(R.id.layout_brand);
        mCountryLayout = topView.findViewById(R.id.layout_country);
        mSaleLayout = topView.findViewById(R.id.layout_sale);
        mNewsTextView = topView.findViewById(R.id.tv_hot_news);
        mRec1ImageView = topView.findViewById(R.id.iv_rec1);
        mRec2ImageView = topView.findViewById(R.id.iv_rec2);
        mRec3ImageView = topView.findViewById(R.id.iv_rec3);

        goodInfoAdapter = new GoodInfoAdapter(getActivity(), null);
        mHomeDataListView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        goodInfoAdapter.setHeaderView(topView);
        mHomeDataListView.setAdapter(goodInfoAdapter);
    }

    public void initViews() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //LogUtil.msg("TAG  " + verticalOffset + "--" + appBarLayout.getHeight() + " --" + collapsingToolbarLayout.getHeight());
                Logger.i("bar height --->" + appBarLayout.getHeight() + "---verticalOffset--->" + verticalOffset);
                if (-verticalOffset >= appBarLayout.getHeight() - SizeUtils.dp2px(130)) {
                    toolbar.setVisibility(View.VISIBLE);
                    mSearchLayout.setVisibility(View.INVISIBLE);
                } else {
                    mSearchLayout.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.INVISIBLE);
                }
            }
        });

        goodInfoPresenterImp = new GoodInfoPresenterImp(this, getActivity());

        mHomeDataListView.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(5), true));

        LinearLayout.LayoutParams search = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(36));
        search.setMargins(SizeUtils.dp2px(15), BarUtils.getStatusBarHeight(), SizeUtils.dp2px(15), 0);
        mSearchLayout.setLayoutParams(search);
        homeDataPresenterImp = new HomeDataPresenterImp(this, getActivity());
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

//    @OnClick(R.id.layout_sale)
//    void salePage() {
//        Intent intent = new Intent(getActivity(), GoodListActivity.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.layout_country)
//    void countryPage() {
//        Intent intent = new Intent(getActivity(), HotCountryActivity.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.layout_brand)
//    void brandPage() {
//        Intent intent = new Intent(getActivity(), HotBrandActivity.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.tv_hot_news)
//    void newsDetail() {
//        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
//        intent.putExtra("nid", StringUtils.isEmpty(newsId) ? "" : newsId);
//        startActivity(intent);
//    }
//
//
//    void toSearch() {
//        Intent intent = new Intent(getActivity(), SearchActivity.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.iv_rec1)
//    void hotLfGoodDetail() {
//        if (StringUtils.isEmpty(hotLfGoodId)) {
//            return;
//        }
//
//        if (specialLfJumpType == 0) {
//            return;
//        }
//
//        if (specialLfJumpType == 1) {
//            Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
//            intent.putExtra("good_id", hotLfGoodId);
//            startActivity(intent);
//        }
//        if (specialLfJumpType == 2) {
//            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
//            intent.putExtra("nid", StringUtils.isEmpty(hotLfGoodId) ? "" : hotLfGoodId);
//            startActivity(intent);
//        }
//    }
//
//    @OnClick(R.id.iv_rec2)
//    void hotRtGoodDetail() {
//        if (StringUtils.isEmpty(hotRtGoodId)) {
//            return;
//        }
//
//        if (specialRtJumpType == 0) {
//            return;
//        }
//
//        if (specialRtJumpType == 1) {
//            Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
//            intent.putExtra("good_id", hotRtGoodId);
//            startActivity(intent);
//        }
//        if (specialRtJumpType == 2) {
//            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
//            intent.putExtra("nid", StringUtils.isEmpty(hotRtGoodId) ? "" : hotRtGoodId);
//            startActivity(intent);
//        }
//    }
//
//    @OnClick(R.id.iv_rec3)
//    void hotRbGoodDetail() {
//        if (StringUtils.isEmpty(hotRbGoodId)) {
//            return;
//        }
//
//        if (specialRbJumpType == 0) {
//            return;
//        }
//
//        if (specialRbJumpType == 1) {
//            Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
//            intent.putExtra("good_id", hotRbGoodId);
//            startActivity(intent);
//        }
//        if (specialRbJumpType == 2) {
//            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
//            intent.putExtra("nid", StringUtils.isEmpty(hotRbGoodId) ? "" : hotRbGoodId);
//            startActivity(intent);
//        }
//    }

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
        if (tData != null && tData.getCode() == Constants.SUCCESS) {

            if (tData instanceof HomeDataInfoRet) {
                if (((HomeDataInfoRet) tData).getData() != null) {
                    initBanner(((HomeDataInfoRet) tData).getData().getBanner());
                    //initFragments(((HomeDataInfoRet) tData).getData().getCategoryInfoList());
                    if (((HomeDataInfoRet) tData).getData().getNews() != null && ((HomeDataInfoRet) tData).getData().getNews().size() > 0) {
                        mNewsTextView.setText(((HomeDataInfoRet) tData).getData().getNews().get(0).getTitle());
                        newsId = ((HomeDataInfoRet) tData).getData().getNews().get(0).getId();
                    }

                    if (((HomeDataInfoRet) tData).getData().getSpecial() != null) {
                        if (((HomeDataInfoRet) tData).getData().getSpecial().size() >= 1) {
                            specialLfJumpType = ((HomeDataInfoRet) tData).getData().getSpecial().get(0).getGoType();
                            hotLfGoodId = ((HomeDataInfoRet) tData).getData().getSpecial().get(0).getObjectId();
                            Glide.with(this).load(((HomeDataInfoRet) tData).getData().getSpecial().get(0).getAdImage()).into(mRec1ImageView);
                        }
                        if (((HomeDataInfoRet) tData).getData().getSpecial().size() >= 2) {
                            specialRtJumpType = ((HomeDataInfoRet) tData).getData().getSpecial().get(1).getGoType();
                            hotRtGoodId = ((HomeDataInfoRet) tData).getData().getSpecial().get(1).getObjectId();
                            Glide.with(this).load(((HomeDataInfoRet) tData).getData().getSpecial().get(1).getAdImage()).into(mRec2ImageView);
                        }
                        if (((HomeDataInfoRet) tData).getData().getSpecial().size() >= 3) {
                            specialRbJumpType = ((HomeDataInfoRet) tData).getData().getSpecial().get(2).getGoType();
                            hotRbGoodId = ((HomeDataInfoRet) tData).getData().getSpecial().get(2).getObjectId();
                            Glide.with(this).load(((HomeDataInfoRet) tData).getData().getSpecial().get(2).getAdImage()).into(mRec3ImageView);
                        }
                    }

                    String tempCid = ((HomeDataInfoRet) tData).getData().getCategoryInfoList().get(0).getId();
                    goodInfoPresenterImp.getGoodInfoByType(1, "0");
                }
            }
            if (tData instanceof GoodInfoRet) {
                goodInfoAdapter.setNewData(((GoodInfoRet) tData).getData().getGoodsList());
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

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        Logger.i("verticalOffset--->" + verticalOffset);
    }
}
