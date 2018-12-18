package com.yc.compare.ui.fragment.sub;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.R;
import com.yc.compare.bean.GoodInfoRet;
import com.yc.compare.presenter.GoodInfoPresenterImp;
import com.yc.compare.ui.adapter.GoodInfoAdapter;
import com.yc.compare.ui.base.BaseFragment;
import com.yc.compare.ui.custom.GridSpacingItemDecoration;
import com.yc.compare.view.GoodInfoView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iflying on 2018/2/6.
 */

public class MainAllFragment extends BaseFragment implements GoodInfoView {

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.main_all_list)
    RecyclerView mHotRecyclerView;

    private GoodInfoAdapter goodInfoAdapter;

    private GoodInfoPresenterImp goodInfoPresenterImp;

    private ProgressDialog progressDialog = null;

    private int pageSize = 20;

    private int currentPage = 1;

    private String categoryId;

    /**
     * onCreateView
     */
    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main_all, null);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    public static MainAllFragment newInstance(String cid) {
        MainAllFragment fragment = new MainAllFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category_id", cid);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initViews() {
        categoryId = getArguments().getString("category_id");

        goodInfoPresenterImp = new GoodInfoPresenterImp(this, getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("正在查询");

        goodInfoAdapter = new GoodInfoAdapter(getActivity(), null);
        mHotRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mHotRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(5), true));
        mHotRecyclerView.setHasFixedSize(true);
        mHotRecyclerView.setAdapter(goodInfoAdapter);

        avi.show();

        goodInfoPresenterImp.getGoodInfoByType(currentPage, categoryId);

        goodInfoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                goodInfoPresenterImp.getGoodInfoByType(currentPage, categoryId);
            }
        }, mHotRecyclerView);
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
    public void loadDataSuccess(GoodInfoRet tData) {
        Logger.i("home data--->");
        Logger.i(JSONObject.toJSONString(tData));
        avi.hide();
        try {
            if (currentPage == 1) {
                goodInfoAdapter.setNewData(tData.getData().getGoodsList());
            } else {
                goodInfoAdapter.addData(tData.getData().getGoodsList());
                if (tData.getData().getGoodsList().size() == pageSize) {
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

    /**
     * @param throwable 异常类型
     * @descriptoin 请求数据错误
     */
    @Override
    public void loadDataError(Throwable throwable) {
        avi.hide();
        goodInfoAdapter.loadMoreEnd();
    }

}
