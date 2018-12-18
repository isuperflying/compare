package com.yc.compare.ui;

import android.os.Bundle;

import com.yc.compare.R;
import com.yc.compare.bean.ResultInfo;
import com.yc.compare.presenter.SuggestInfoPresenterImp;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.view.SuggestInfoView;

import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class SuggestActivity extends BaseFragmentActivity implements SuggestInfoView {

    SuggestInfoPresenterImp suggestInfoPresenterImp;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_suggest;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {
        suggestInfoPresenterImp = new SuggestInfoPresenterImp(this, this);
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

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(ResultInfo tData) {

    }

    @Override
    public void loadDataError(Throwable throwable) {

    }
}
