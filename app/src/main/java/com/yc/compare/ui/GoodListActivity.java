package com.yc.compare.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;
import com.yc.compare.App;
import com.yc.compare.R;
import com.yc.compare.bean.BrandInfo;
import com.yc.compare.bean.Condition;
import com.yc.compare.bean.CountryWrapper;
import com.yc.compare.bean.GoodInfoRet;
import com.yc.compare.presenter.CategoryWrapperPresenterImp;
import com.yc.compare.presenter.GoodInfoPresenterImp;
import com.yc.compare.ui.adapter.GoodInfoAdapter;
import com.yc.compare.ui.adapter.SearchBrandAdapter;
import com.yc.compare.ui.adapter.SearchCountryAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.ui.custom.GridSpacingItemDecoration;
import com.yc.compare.view.GoodInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class GoodListActivity extends BaseFragmentActivity implements GoodInfoView, View.OnClickListener {

    public static Context mContent;

    @BindView(R.id.draw)
    DrawerLayout drawerLayout;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.good_list)
    RecyclerView mGoodListView;

    @BindView(R.id.search_layout)
    LinearLayout mSearchLayout;

    @BindView(R.id.condition_layout)
    LinearLayout mConditionLayout;

    @BindView(R.id.pop_bg_layout)
    LinearLayout popBgLayout;

    @BindView(R.id.search_country_list)
    SwipeMenuRecyclerView mTypeContentListView;

    @BindView(R.id.hot_country_list)
    RecyclerView mSearchCountryListView;

    @BindView(R.id.layout_new)
    LinearLayout mNewLayout;

    @BindView(R.id.layout_hot)
    LinearLayout mHotLayout;

    @BindView(R.id.layout_price)
    LinearLayout mPriceLayout;

    @BindView(R.id.layout_brand)
    LinearLayout mBrandLayout;

    @BindView(R.id.layout_country)
    LinearLayout mCountryLayout;

    @BindView(R.id.tv_new)
    TextView mNewTextView;

    @BindView(R.id.tv_hot)
    TextView mHotTextView;

    @BindView(R.id.tv_price)
    TextView mPriceTextView;

    @BindView(R.id.iv_price_top)
    ImageView mPriceTopImageView;

    @BindView(R.id.iv_price_bottom)
    ImageView mPriceBottomImageView;

    @BindView(R.id.tv_brand)
    TextView mBrandTextView;

    @BindView(R.id.tv_country)
    TextView mCountryTextView;

    @BindView(R.id.et_key_word)
    EditText mKeyWordEditText;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    @BindView(R.id.layout_country_clear)
    LinearLayout mCountryClearLayout;

    @BindView(R.id.layout_country_config)
    LinearLayout mCountryConfigLayout;

    private GoodInfoAdapter goodInfoAdapter;

    private GoodInfoPresenterImp goodInfoPresenterImp;

    private CategoryWrapperPresenterImp categoryWrapperPresenterImp;

    private ProgressDialog progressDialog = null;

    private CustomPopWindow customPopWindow;

    private int pageSize = 12;

    private int currentPage = 1;

    private Condition condition;

    private int searchType = 0;//（可以传的值：'new'(最新)1,'hot'(热门)2,'price_asc'(价格升序)3,'price_desc'(价格降序)）4

    private String[] searchTypeValues = {"new", "hot", "price_asc", "price_desc"};

    private boolean isPriceAsc;//设置价格查询初始值

    private int lastSearchType = -1;

    private List<BrandInfo> brandList;

    private SearchCountryAdapter searchCountryAdapter;

    private SearchBrandAdapter searchBrandAdapter;

    private List<CountryWrapper> countryWrappers;

    private String cid;

    private int lastBrandPosition = -1;

    private int lastCountryPosition = -1;

    private String queryBrandId = "";

    private String queryCountryId = "";

    private GroupAdapter gAdapter;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_good_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = this;
        initViews();
    }

    public void initViews() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && !StringUtils.isEmpty(bundle.getString("cid"))) {
            cid = bundle.getString("cid");
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth() - SizeUtils.dp2px(30), SizeUtils.dp2px(30));
        params.setMargins(SizeUtils.dp2px(10), BarUtils.getStatusBarHeight(), 0, 0);
        mSearchLayout.setLayoutParams(params);

        initCountry();

        mKeyWordEditText.setOnEditorActionListener(new EditorActionListener());
        goodInfoPresenterImp = new GoodInfoPresenterImp(this, this);
        categoryWrapperPresenterImp = new CategoryWrapperPresenterImp(this, this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在查询");

        goodInfoAdapter = new GoodInfoAdapter(this, null);
        mGoodListView.setLayoutManager(new GridLayoutManager(this, 2));

        mGoodListView.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(5), true));
        mGoodListView.setHasFixedSize(true);
        mGoodListView.setAdapter(goodInfoAdapter);

        avi.show();

        goodInfoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(GoodListActivity.this,GoodDetailActivity.class);
                startActivity(intent);
            }
        });

        goodInfoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                condition.setPage(currentPage + "");
                goodInfoPresenterImp.getGoodInfoByParams(condition);
            }
        }, mGoodListView);

        //查询城市列表
        //categoryWrapperPresenterImp.getCategoryWrapperList(2, "-2", "2");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.isShowBrand) {
            mBrandLayout.setVisibility(View.VISIBLE);
        } else {
            mBrandLayout.setVisibility(View.GONE);
        }
        initParams(searchType);
    }

    public void initParams(int type) {

        if (lastSearchType == type && type < 4) {
            return;
        }

        if (condition == null) {
            condition = new Condition();
        }
        currentPage = 1;//充值查询条件时，修改页码=1
        switch (type) {
            case 0:
                mNewTextView.setTextColor(ContextCompat.getColor(this, R.color.tab_selected_color));
                mHotTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                mPriceTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                mBrandTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                mCountryTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                mPriceTopImageView.setImageResource(R.mipmap.price_top_normal);
                mPriceBottomImageView.setImageResource(R.mipmap.price_bottom_normal);
                break;
            case 1:
                mNewTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                mHotTextView.setTextColor(ContextCompat.getColor(this, R.color.tab_selected_color));
                mPriceTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                mBrandTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                mCountryTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                mPriceTopImageView.setImageResource(R.mipmap.price_top_normal);
                mPriceBottomImageView.setImageResource(R.mipmap.price_bottom_normal);
                break;
            case 2:
                mNewTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                mHotTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                mPriceTextView.setTextColor(ContextCompat.getColor(this, R.color.tab_selected_color));
                mBrandTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                mCountryTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                mPriceTopImageView.setImageResource(R.mipmap.price_top_selected);
                mPriceBottomImageView.setImageResource(R.mipmap.price_bottom_normal);
                break;
            case 3:
                mNewTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                mHotTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                mPriceTextView.setTextColor(ContextCompat.getColor(this, R.color.tab_selected_color));
                mBrandTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                mCountryTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                mPriceTopImageView.setImageResource(R.mipmap.price_top_normal);
                mPriceBottomImageView.setImageResource(R.mipmap.price_bottom_selected);
                break;
            case 4:
                break;
            default:
                break;
        }

        condition.setPage(currentPage + "");
        condition.setCategoryId(cid);
        if (type < 4) {
            condition.setSearchCondition(searchTypeValues[type]);
        }
        goodInfoPresenterImp.getGoodInfoByParams(condition);
        lastSearchType = type;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    public void initCountry() {
        mTypeContentListView.setNestedScrollingEnabled(false);
        mTypeContentListView.setLayoutManager(new LinearLayoutManager(this));
        mTypeContentListView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.line_color)));

        mTypeContentListView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

                if (gAdapter != null && !gAdapter.getmListItems().get(position).id.equals("-1")) {
                    Logger.i("country info--->" + gAdapter.getmListItems().get(position).id + "---" + gAdapter.getmListItems().get(position).cname);
                    if (lastCountryPosition == position) {
                        return;
                    }
                    if (lastCountryPosition > -1) {
                        gAdapter.getmListItems().get(lastCountryPosition).setSelected(false);
                    }
                    gAdapter.getmListItems().get(position).setSelected(true);
                    queryCountryId = gAdapter.getmListItems().get(position).id;
                    gAdapter.notifyDataSetChanged();
                    lastCountryPosition = position;
                }
            }
        });
    }

    @Override
    public void loadDataSuccess(GoodInfoRet goodInfoRet) {
        avi.hide();

        try {
            if (currentPage == 1) {
                if (goodInfoRet.getData().getGoodsList() != null && goodInfoRet.getData().getGoodsList().size() > 0) {
                    mGoodListView.setVisibility(View.VISIBLE);
                    mNoDataLayout.setVisibility(View.GONE);
                    goodInfoAdapter.setNewData(goodInfoRet.getData().getGoodsList());
                } else {
                    mGoodListView.setVisibility(View.GONE);
                    mNoDataLayout.setVisibility(View.VISIBLE);
                }

                if (brandList == null && goodInfoRet.getData().getBrandList() != null && goodInfoRet.getData().getBrandList().size() > 0) {
                    brandList = goodInfoRet.getData().getBrandList();
                    //初始化品牌弹窗
                    initPopWindow();
                }

                if (goodInfoRet.getData().getHotCountry() != null && goodInfoRet.getData().getHotCountry().size() > 0) {
                    searchCountryAdapter = new SearchCountryAdapter(GoodListActivity.this, goodInfoRet.getData().getHotCountry());
                    mSearchCountryListView.setLayoutManager(new GridLayoutManager(GoodListActivity.this, 3));
                    mSearchCountryListView.setAdapter(searchCountryAdapter);
                }

                if (countryWrappers == null && goodInfoRet.getData().getAllCountry() != null && goodInfoRet.getData().getAllCountry().size() > 0) {
                    countryWrappers = goodInfoRet.getData().getAllCountry();
                    gAdapter = new GroupAdapter();
                    mTypeContentListView.setAdapter(gAdapter);
                    gAdapter.setListItems(countryWrappers);
                }

            } else {
                goodInfoAdapter.addData(goodInfoRet.getData().getGoodsList());
                if (goodInfoRet.getData().getGoodsList().size() == pageSize) {
                    goodInfoAdapter.loadMoreComplete();
                } else {
                    goodInfoAdapter.loadMoreEnd();
                }
            }
        } catch (NullPointerException e) {
            goodInfoAdapter.loadMoreEnd();
            LogUtils.e("loadDataSuccess error --->" + e.getMessage());
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        avi.hide();
    }

    public void initPopWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.search_brand_top, null);
        //处理popWindow 显示内容
        handleListView(contentView);
        //创建并显示popWindow
        customPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(248))//显示大小
                .create();

    }


    private void handleListView(View contentView) {
        Logger.i(JSON.toJSONString(brandList));

        LinearLayout noDataLayout = contentView.findViewById(R.id.layout_no_data);
        RecyclerView brandListView = contentView.findViewById(R.id.search_brand_list);

        LinearLayout mClearLayout = contentView.findViewById(R.id.layout_clear);
        LinearLayout mConfigLayout = contentView.findViewById(R.id.layout_config);
        mClearLayout.setOnClickListener(this);
        mConfigLayout.setOnClickListener(this);

        if (brandList != null) {
            brandListView.setVisibility(View.VISIBLE);
            noDataLayout.setVisibility(View.GONE);
            searchBrandAdapter = new SearchBrandAdapter(this, brandList);
            brandListView.setLayoutManager(new GridLayoutManager(this, 2));
            brandListView.setAdapter(searchBrandAdapter);

            searchBrandAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (lastBrandPosition == position) {
                        return;
                    }
                    queryBrandId = searchBrandAdapter.getData().get(position).getId();
                    searchBrandAdapter.getData().get(position).setSelected(true);
                    if (lastBrandPosition > -1) {
                        searchBrandAdapter.getData().get(lastBrandPosition).setSelected(false);
                    }
                    searchBrandAdapter.notifyDataSetChanged();
                    lastBrandPosition = position;
                }
            });
        } else {
            brandListView.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_clear:
                for (BrandInfo brandInfo : searchBrandAdapter.getData()) {
                    brandInfo.setSelected(false);
                }
                searchBrandAdapter.notifyDataSetChanged();
                queryBrandId = "";
                lastBrandPosition = -1;
                break;
            case R.id.layout_config:

                if (customPopWindow != null && customPopWindow.getPopupWindow().isShowing()) {
                    customPopWindow.dissmiss();
                }

                if (StringUtils.isEmpty(queryBrandId)) {
                    mBrandTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                    searchType = 4;
                } else {
                    mBrandTextView.setTextColor(ContextCompat.getColor(this, R.color.tab_selected_color));
                    searchType = 5;
                }

                if (!queryBrandId.equals(condition.getBrandId())) {
                    condition.setBrandId(queryBrandId);
                    initParams(searchType);
                }

                break;
            default:
                break;
        }
    }

    private static class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {

        static final int VIEW_TYPE_NON_STICKY = R.layout.item_menu_main;
        static final int VIEW_TYPE_NON_STICKY_SELECTED = R.layout.item_menu_main_selected;
        static final int VIEW_TYPE_STICKY = R.layout.item_menu_sticky;

        private List<ListItem> mListItems = new ArrayList<>();

        @Override
        public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(viewType, parent, false);
            return new GroupViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GroupViewHolder holder, int position) {
            holder.bind(mListItems.get(position));
        }

        @Override
        public int getItemViewType(int position) {
            if (mListItems.get(position) instanceof StickyListItem) {
                return VIEW_TYPE_STICKY;
            }

            if (mListItems.get(position).isSelected) {
                return VIEW_TYPE_NON_STICKY_SELECTED;
            } else {
                return VIEW_TYPE_NON_STICKY;
            }
        }

        @Override
        public int getItemCount() {
            return mListItems.size();
        }

        public List<ListItem> getmListItems() {
            return mListItems;
        }

        void setListItems(List<CountryWrapper> wrappers) {
            mListItems.clear();

            for (int i = 0; i < wrappers.size(); i++) {
                CountryWrapper countryWrapper = wrappers.get(i);
                for (int j = 0; j < countryWrapper.getList().size(); j++) {
                    ListItem item = new ListItem(countryWrapper.getList().get(j).getId(), countryWrapper.getList().get(j).getCountryName());
                    mListItems.add(item);
                }
            }

            //在特定位置增加分组索引
            StickyListItem firstSticky = new StickyListItem("-1", wrappers.get(0).getName());
            mListItems.add(0, firstSticky);

            int tempIndex = 0;
            for (int m = 1; m < wrappers.size(); m++) {
                CountryWrapper countryWrapper = wrappers.get(m);
                tempIndex += wrappers.get(m - 1).getList().size() + 1;
                Logger.i("temp index--->" + tempIndex);
                StickyListItem stickyListItem = new StickyListItem("-1", countryWrapper.getName());
                mListItems.add(tempIndex, stickyListItem);
            }

            notifyDataSetChanged();
        }
    }

    private static class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        GroupViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tv_title);
        }

        void bind(ListItem item) {
            text.setText(item.cname);
        }
    }

    private static class ListItem {
        private String id;
        private String cname;

        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        ListItem(String id, String name) {
            this.id = id;
            this.cname = name;
        }
    }

    private static class StickyListItem extends ListItem {
        StickyListItem(String id, String text) {
            super(id, text);
        }
    }


    @OnClick({R.id.layout_new, R.id.layout_hot, R.id.layout_price, R.id.layout_brand, R.id.layout_country, R.id.layout_country_clear, R.id.layout_country_config})
    public void paramsChoose(View view) {
        switch (view.getId()) {
            case R.id.layout_new:
                searchType = 0;
                break;
            case R.id.layout_hot:
                searchType = 1;
                break;
            case R.id.layout_price:
                isPriceAsc = !isPriceAsc;
                if (isPriceAsc) {
                    searchType = 2;
                } else {
                    searchType = 3;
                }
                break;
            case R.id.layout_brand:
                if (customPopWindow != null) {
                    customPopWindow.showAsDropDown(mConditionLayout, 0, 0);
                }
                popBgLayout.setVisibility(View.VISIBLE);

                customPopWindow.getPopupWindow().setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        popBgLayout.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.layout_country:
                drawerLayout.openDrawer(Gravity.RIGHT);
                break;
            case R.id.layout_country_clear:
                if (gAdapter != null) {
                    for (ListItem item : gAdapter.getmListItems()) {
                        item.setSelected(false);
                    }
                    gAdapter.notifyDataSetChanged();
                }
                queryCountryId = "";
                break;
            case R.id.layout_country_config:
                drawerLayout.closeDrawers();

                if (StringUtils.isEmpty(queryCountryId)) {
                    mCountryTextView.setTextColor(ContextCompat.getColor(this, R.color.top_type_text_color));
                    searchType = 6;
                } else {
                    mCountryTextView.setTextColor(ContextCompat.getColor(this, R.color.tab_selected_color));
                    searchType = 7;
                }

                if (!queryCountryId.equals(condition.getCountryId())) {
                    condition.setCountryId(queryCountryId);
                    initParams(searchType);
                }

                break;
            default:
                break;
        }

        initParams(searchType);
    }

    private class EditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            switch (textView.getId()) {
                case R.id.et_key_word:
                    if (i == EditorInfo.IME_ACTION_SEARCH) {
                        if (StringUtils.isEmpty(textView.getText())) {
                            ToastUtils.showLong("请输入关键词后搜索");
                            break;
                        }
                        condition.setPage(currentPage + "");
                        condition.setKeyWord(textView.getText().toString());
                        goodInfoPresenterImp.getGoodInfoByParams(condition);
                        App.isShowBrand = false;
                        mBrandLayout.setVisibility(View.GONE);
                        KeyboardUtils.hideSoftInput(GoodListActivity.this);
                    }
                    break;
                default:
                    break;
            }
            return false;
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
}
