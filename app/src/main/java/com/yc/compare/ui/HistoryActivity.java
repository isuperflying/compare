package com.yc.compare.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.R;
import com.yc.compare.bean.CollectInfoRet;
import com.yc.compare.bean.ResultInfo;
import com.yc.compare.bean.UserInfo;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.CollectInfoPresenterImp;
import com.yc.compare.ui.adapter.CollectAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.view.CollectInfoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/21.
 */
public class HistoryActivity extends BaseFragmentActivity implements CollectInfoView {
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.history_list)
    RecyclerView mCollectListView;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    private CollectAdapter collectAdapter;

    private CollectInfoPresenterImp collectInfoPresenterImp;

    private int pageSize = 20;

    private int currentPage = 1;

    private UserInfo userInfo;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_history;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {

        if (!StringUtils.isEmpty(SPUtils.getInstance().getString(Constants.USER_INFO))) {
            Logger.i(SPUtils.getInstance().getString(Constants.USER_INFO));
            userInfo = JSON.parseObject(SPUtils.getInstance().getString(Constants.USER_INFO), new TypeReference<UserInfo>() {
            });
        }

        collectInfoPresenterImp = new CollectInfoPresenterImp(this, this);

        collectAdapter = new CollectAdapter(this, null);

        mCollectListView.setLayoutManager(new LinearLayoutManager(this));
        mCollectListView.setAdapter(collectAdapter);

        collectAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                collectInfoPresenterImp.historyList(userInfo != null ? userInfo.getUserId() : "", currentPage);
            }
        }, mCollectListView);

        avi.show();
        collectInfoPresenterImp.historyList(userInfo != null ? userInfo.getUserId() : "", currentPage);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(ResultInfo tData) {
        avi.hide();
        Logger.i(JSONObject.toJSONString(tData));
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (((CollectInfoRet) tData).getData() != null && ((CollectInfoRet) tData).getData().size() > 0) {
                if (currentPage == 1) {
                    mNoDataLayout.setVisibility(View.GONE);
                    mCollectListView.setVisibility(View.VISIBLE);
                    collectAdapter.setNewData(((CollectInfoRet) tData).getData());
                } else {
                    collectAdapter.addData(((CollectInfoRet) tData).getData());
                    if (((CollectInfoRet) tData).getData().size() == pageSize) {
                        collectAdapter.loadMoreComplete();
                    } else {
                        collectAdapter.loadMoreEnd();
                    }
                }
            }else{
                collectAdapter.loadMoreEnd();
            }
        } else {
            mCollectListView.setVisibility(View.GONE);
            mNoDataLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

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
