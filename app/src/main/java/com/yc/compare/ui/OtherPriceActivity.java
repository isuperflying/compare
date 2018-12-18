package com.yc.compare.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.R;
import com.yc.compare.bean.OtherPriceInfo;
import com.yc.compare.bean.OtherPriceInfoRet;
import com.yc.compare.presenter.OtherPriceInfoPresenterImp;
import com.yc.compare.ui.adapter.OtherPriceAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.view.OtherPriceInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class OtherPriceActivity extends BaseFragmentActivity implements OtherPriceInfoView {

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.other_price_list)
    RecyclerView mCommentListView;

    private OtherPriceAdapter otherPriceAdapter;

    private OtherPriceInfoPresenterImp otherPriceInfoPresenterImp;

    private ProgressDialog progressDialog = null;

    private int pageSize = 12;

    private int currentPage = 1;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_other_price;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {
        otherPriceInfoPresenterImp = new OtherPriceInfoPresenterImp(this, this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在查询");

        List<OtherPriceInfo> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(new OtherPriceInfo());
        }

        otherPriceAdapter = new OtherPriceAdapter(this, list);
        mCommentListView.setLayoutManager(new LinearLayoutManager(this));
        mCommentListView.setAdapter(otherPriceAdapter);
        avi.hide();

        View headView = LayoutInflater.from(this).inflate(R.layout.other_price_top,null);
        otherPriceAdapter.addHeaderView(headView);

        //avi.show();
        //otherPriceInfoPresenterImp.getOtherPriceInfoList(currentPage);

//        otherPriceAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                currentPage++;
//                otherPriceInfoPresenterImp.getOtherPriceInfoList(currentPage);
//            }
//        }, mCommentListView);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(OtherPriceInfoRet tData) {
        avi.hide();
//        Logger.i(JSONObject.toJSONString(tData));
//        if (tData != null && tData.getCode() == Constants.SUCCESS) {
//            otherPriceAdapter.setNewData(tData.getData());
//            otherPriceAdapter.loadMoreEnd();
//        } else {
//            ToastUtils.showLong("数据异常,请重试");
//        }
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
