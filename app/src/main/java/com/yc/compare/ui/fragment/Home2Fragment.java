package com.yc.compare.ui.fragment;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.yc.compare.ui.GoodListActivity;
import com.yc.compare.ui.HotBrandActivity;
import com.yc.compare.ui.HotCountryActivity;
import com.yc.compare.ui.NewsDetailActivity;
import com.yc.compare.ui.SearchActivity;
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

public class Home2Fragment extends BaseFragment implements HomeDataView, AppBarLayout.OnOffsetChangedListener,View.OnClickListener {

    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;

    @BindView(R.id.search_wrapper)
    LinearLayout mSearchWrapperLayout;

    @BindView(R.id.search_layout)
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

    private int pageSize = 20;

    private int currentPage = 1;

    private String categoryId;

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
        mBanner = topView.findViewById(R.id.banner);
        mBrandLayout = topView.findViewById(R.id.layout_brand);
        mCountryLayout = topView.findViewById(R.id.layout_country);
        mSaleLayout = topView.findViewById(R.id.layout_sale);
        mNewsTextView = topView.findViewById(R.id.tv_hot_news);
        mRec1ImageView = topView.findViewById(R.id.iv_rec1);
        mRec2ImageView = topView.findViewById(R.id.iv_rec2);
        mRec3ImageView = topView.findViewById(R.id.iv_rec3);

        mBrandLayout.setOnClickListener(this);
        mCountryLayout.setOnClickListener(this);
        mSaleLayout.setOnClickListener(this);
        mNewsTextView.setOnClickListener(this);
        mRec1ImageView.setOnClickListener(this);
        mRec2ImageView.setOnClickListener(this);
        mRec3ImageView.setOnClickListener(this);
        mSearchWrapperLayout.setOnClickListener(this);

        goodInfoAdapter = new GoodInfoAdapter(getActivity(), null);
        mHomeDataListView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        goodInfoAdapter.setHeaderView(topView);
        mHomeDataListView.setAdapter(goodInfoAdapter);
        mHomeDataListView.setNestedScrollingEnabled(false);

//        goodInfoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                currentPage++;
//                goodInfoPresenterImp.getGoodInfoByType(currentPage, categoryId);
//            }
//        }, mHomeDataListView);
        goodInfoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
                intent.putExtra("good_id", goodInfoAdapter.getData().get(position).getId());
                startActivity(intent);
            }
        });
    }

    public void initViews() {
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int y, int i2, int i3) {
                int tempY = SizeUtils.px2dp(y);
                if (tempY > 487) {
                    mSearchWrapperLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                } else {
                    mSearchWrapperLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.transparent));
                }
            }
        });

        //mHomeDataListView.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(5), true));
        LinearLayout.LayoutParams searchParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(36));
        searchParams.setMargins(SizeUtils.dp2px(15), BarUtils.getStatusBarHeight(), SizeUtils.dp2px(15), 0);
        mSearchLayout.setLayoutParams(searchParams);

        homeDataPresenterImp = new HomeDataPresenterImp(this, getActivity());
        goodInfoPresenterImp = new GoodInfoPresenterImp(this, getActivity());

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


    void salePage() {
        Intent intent = new Intent(getActivity(), GoodListActivity.class);
        startActivity(intent);
    }

    void countryPage() {
        Intent intent = new Intent(getActivity(), HotCountryActivity.class);
        startActivity(intent);
    }

    void brandPage() {
        Intent intent = new Intent(getActivity(), HotBrandActivity.class);
        startActivity(intent);
    }

    void newsDetail() {
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra("nid", StringUtils.isEmpty(newsId) ? "" : newsId);
        startActivity(intent);
    }


    void toSearch() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }

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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_sale:
                salePage();
                break;
            case R.id.layout_country:
                countryPage();
                break;
            case R.id.layout_brand:
                brandPage();
                break;
            case R.id.tv_hot_news:
                newsDetail();
                break;
            case R.id.search_wrapper:
                toSearch();
                break;
            case R.id.iv_rec1:
                hotLfGoodDetail();
                break;
            case R.id.iv_rec2:
                hotRtGoodDetail();
                break;
            case R.id.iv_rec3:
                hotRbGoodDetail();
                break;
        }
    }
}
