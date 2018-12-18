package com.yc.compare.ui.fragment.sub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.R;
import com.yc.compare.bean.NewsInfoRet;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.NewsInfoPresenterImp;
import com.yc.compare.ui.NewsDetailActivity;
import com.yc.compare.ui.adapter.NewsInfoAdapter;
import com.yc.compare.ui.base.BaseFragment;
import com.yc.compare.ui.custom.RecycleViewDivider;
import com.yc.compare.view.NewsInfoView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iflying on 2018/2/6.
 */

public class NewsItemFragment extends BaseFragment implements NewsInfoView {

    private NewsInfoPresenterImp newsInfoPresenterImp;

    private NewsInfoAdapter newsInfoAdapter;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.news_list)
    RecyclerView mNewsListView;

    private String cid = "";

    private int currentPage = 1;

    private int pageSize = 20;

    /**
     * onCreateView
     */
    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_item_page, null);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    public static NewsItemFragment newInstance(String cid) {
        NewsItemFragment fragment = new NewsItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category_id", StringUtils.isEmpty(cid) ? "" : cid);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initViews() {

        Bundle bundle = getArguments();
        if (bundle != null && !StringUtils.isEmpty(bundle.getString("category_id"))) {
            cid = bundle.getString("category_id");
        }

        newsInfoPresenterImp = new NewsInfoPresenterImp(this, getActivity());

        newsInfoAdapter = new NewsInfoAdapter(getActivity(), null);
        mNewsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNewsListView.addItemDecoration(new RecycleViewDivider(
                getActivity(), LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.line_color)));
        mNewsListView.setAdapter(newsInfoAdapter);

        newsInfoPresenterImp.getNewsInfoList(cid, "", currentPage);

        newsInfoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                newsInfoPresenterImp.getNewsInfoList(cid, "", currentPage);
            }
        }, mNewsListView);

        newsInfoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("nid",newsInfoAdapter.getData().get(position).getId());
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
}
