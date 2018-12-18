package com.yc.compare.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.R;
import com.yc.compare.bean.HotDataInfoRet;
import com.yc.compare.presenter.HotDataPresenterImp;
import com.yc.compare.ui.adapter.HotDatasAdapter;
import com.yc.compare.ui.base.BaseFragment;
import com.yc.compare.ui.custom.GridSpacingItemDecoration;
import com.yc.compare.view.HotDataView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iflying on 2018/2/6.
 */

public class HotFragment extends BaseFragment implements HotDataView {

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.rv_hot_list)
    RecyclerView mHotRecyclerView;

    private HotDatasAdapter hotDatasAdapter;

    private HotDataPresenterImp hotDataPresenterImp;

    private ProgressDialog progressDialog = null;

    private int pageSize = 12;

    private int currentPage = 1;

    /**
     * onCreateView
     */
    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_hot, null);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }


    public static HotFragment newInstance(int i) {
        HotFragment fragment = new HotFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", i);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initViews() {
        hotDataPresenterImp = new HotDataPresenterImp(this, getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("正在查询");

        hotDatasAdapter = new HotDatasAdapter(getActivity(), null);
        mHotRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mHotRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(5), true));
        mHotRecyclerView.setHasFixedSize(true);

        mHotRecyclerView.setAdapter(hotDatasAdapter);

        avi.show();

        hotDataPresenterImp.getHotDataByPage(currentPage);

        hotDatasAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                hotDataPresenterImp.getHotDataByPage(currentPage);
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
    public void loadDataSuccess(HotDataInfoRet tData) {
        avi.hide();
        try {
            if (currentPage == 1) {
                hotDatasAdapter.setNewData(tData.getData().getSubject().getRecords());
            } else {
                hotDatasAdapter.addData(tData.getData().getSubject().getRecords());
                if (tData.getData().getSubject().getRecords().size() == pageSize) {
                    hotDatasAdapter.loadMoreComplete();
                } else {
                    hotDatasAdapter.loadMoreEnd();
                }
            }
        } catch (NullPointerException e) {
            hotDatasAdapter.loadMoreEnd();
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
        hotDatasAdapter.loadMoreEnd();
    }

}
