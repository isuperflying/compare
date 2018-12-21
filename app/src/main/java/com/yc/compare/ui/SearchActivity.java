package com.yc.compare.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.compare.R;
import com.yc.compare.bean.HotSearchRet;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.HotSearchPresenterImp;
import com.yc.compare.ui.adapter.HotSearchAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.view.HotSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/14.
 */
public class SearchActivity extends BaseFragmentActivity implements HotSearchView {

    @BindView(R.id.search_layout)
    LinearLayout mSearchLayout;

    @BindView(R.id.hot_words_list)
    RecyclerView mHotListView;

    @BindView(R.id.et_key_word)
    EditText mKeyWordTextView;

    @BindView(R.id.tv_search)
    TextView mSearchTextView;

    private HotSearchAdapter hotSearchAdapter;

    private HotSearchPresenterImp hotSearchPresenterImp;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {
        LinearLayout.LayoutParams search = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(36));
        search.setMargins(SizeUtils.dp2px(15), BarUtils.getStatusBarHeight(), SizeUtils.dp2px(15), 0);
        mSearchLayout.setLayoutParams(search);

        hotSearchPresenterImp = new HotSearchPresenterImp(this, this);
        hotSearchAdapter = new HotSearchAdapter(this, null);
        mHotListView.setLayoutManager(new GridLayoutManager(this, 3));
        mHotListView.setAdapter(hotSearchAdapter);

        hotSearchPresenterImp.hotSearch();

        hotSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SearchActivity.this, GoodListActivity.class);
                intent.putExtra("key_word", hotSearchAdapter.getData().get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(HotSearchRet tData) {
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData.getData() != null) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < tData.getData().length; i++) {
                    list.add(tData.getData()[i]);
                }
                hotSearchAdapter.setNewData(list);
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }

    @OnClick(R.id.tv_search)
    void search() {
        if (StringUtils.isEmpty(mKeyWordTextView.getText())) {
            ToastUtils.showLong("请输入关键词搜索");
            return;
        }

        Intent intent = new Intent(this, GoodListActivity.class);
        intent.putExtra("key_word", mKeyWordTextView.getText().toString());
        startActivity(intent);
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
