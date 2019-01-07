package com.yc.compare.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.R;
import com.yc.compare.bean.NewsInfoRet;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.NewsInfoPresenterImp;
import com.yc.compare.ui.adapter.NewsInfoAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.ui.custom.RecycleViewDivider;
import com.yc.compare.view.NewsInfoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/27.
 */
public class NewsSearchResultActivity extends BaseFragmentActivity implements NewsInfoView {

    private NewsInfoPresenterImp newsInfoPresenterImp;

    private NewsInfoAdapter newsInfoAdapter;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.search_result_list)
    RecyclerView mNewsListView;

    private String keyWord = "";

    private int currentPage = 1;

    private int pageSize = 20;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_news_search_result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && !StringUtils.isEmpty(bundle.getString("key_word"))) {
            keyWord = bundle.getString("key_word");
        }

        newsInfoPresenterImp = new NewsInfoPresenterImp(this, this);

        newsInfoAdapter = new NewsInfoAdapter(this, null);
        mNewsListView.setLayoutManager(new LinearLayoutManager(this));
        mNewsListView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.line_color)));
        mNewsListView.setAdapter(newsInfoAdapter);

        newsInfoPresenterImp.getNewsInfoList("", keyWord, currentPage);

        newsInfoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                newsInfoPresenterImp.getNewsInfoList("", keyWord, currentPage);
            }
        }, mNewsListView);

        newsInfoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(NewsSearchResultActivity.this, NewsDetailActivity.class);
                intent.putExtra("nid", newsInfoAdapter.getData().get(position).getId());
                startActivity(intent);
            }
        });

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
    public void loadDataSuccess(NewsInfoRet tData) {
        avi.hide();
        if (tData != null && tData.getCode() == Constants.SUCCESS) {

            if (currentPage == 1) {
                if (tData.getData() != null && tData.getData().getArticle_list() != null) {
                    newsInfoAdapter.setNewData(tData.getData().getArticle_list());
                }
            } else {
                newsInfoAdapter.addData(tData.getData().getArticle_list());
                if (tData.getData().getArticle_list().size() == pageSize) {
                    newsInfoAdapter.loadMoreComplete();
                } else {
                    newsInfoAdapter.loadMoreEnd();
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
        avi.hide();
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
