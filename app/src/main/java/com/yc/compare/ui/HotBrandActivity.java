package com.yc.compare.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.App;
import com.yc.compare.R;
import com.yc.compare.bean.BrandInfoRet;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.BrandInfoPresenterImp;
import com.yc.compare.ui.adapter.HotBrandAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.view.BrandInfoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class HotBrandActivity extends BaseFragmentActivity implements BrandInfoView {

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.hot_brand_list)
    RecyclerView mHotBrandListView;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    private HotBrandAdapter hotBrandAdapter;

    private BrandInfoPresenterImp brandInfoPresenterImp;

    private ProgressDialog progressDialog = null;

    private int pageSize = 12;

    private int currentPage = 1;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_hot_brand;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {
        brandInfoPresenterImp = new BrandInfoPresenterImp(this, this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在查询");

        hotBrandAdapter = new HotBrandAdapter(this, null);
        mHotBrandListView.setLayoutManager(new LinearLayoutManager(this));
        mHotBrandListView.setAdapter(hotBrandAdapter);

        avi.show();
        brandInfoPresenterImp.getBrandInfoList(currentPage);
        hotBrandAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                App.isShowBrand = false;
                Intent intent = new Intent(HotBrandActivity.this, GoodListActivity.class);
                intent.putExtra("brand_id", hotBrandAdapter.getData().get(position).getId());
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
    public void loadDataSuccess(BrandInfoRet tData) {
        avi.hide();
        Logger.i(JSONObject.toJSONString(tData));
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData.getData() != null && tData.getData().size() > 0) {
                hotBrandAdapter.setNewData(tData.getData());
            } else {
                mHotBrandListView.setVisibility(View.GONE);
                mNoDataLayout.setVisibility(View.VISIBLE);
            }
        } else {
            mHotBrandListView.setVisibility(View.GONE);
            mNoDataLayout.setVisibility(View.VISIBLE);
            ToastUtils.showLong("数据异常,请重试");
        }
    }

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
