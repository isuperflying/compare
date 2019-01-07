package com.yc.compare.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.yc.compare.R;
import com.yc.compare.bean.UpdateInfoRet;
import com.yc.compare.bean.UserInfo;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.UpdateInfoPresenterImp;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.view.UpdateInfoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class SuggestActivity extends BaseFragmentActivity implements UpdateInfoView {

    @BindView(R.id.et_suggest_content)
    EditText mSuggestContent;

    @BindView(R.id.btn_submit)
    Button mSubmitBuuton;

    UpdateInfoPresenterImp updateInfoPresenterImp;

    private ProgressDialog progressDialog = null;

    private UserInfo userInfo;

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
        if (!StringUtils.isEmpty(SPUtils.getInstance().getString(Constants.USER_INFO))) {
            Logger.i(SPUtils.getInstance().getString(Constants.USER_INFO));
            userInfo = JSON.parseObject(SPUtils.getInstance().getString(Constants.USER_INFO), new TypeReference<UserInfo>() {
            });
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在提交");
        updateInfoPresenterImp = new UpdateInfoPresenterImp(this, this);
    }

    @OnClick(R.id.btn_submit)
    void submit() {
        if (StringUtils.isEmpty(mSuggestContent.getText())) {
            ToastUtils.showLong("请填写内容");
            return;
        }
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        updateInfoPresenterImp.addSuggest(userInfo != null ? userInfo.getUserId() : "", mSuggestContent.getText().toString());
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
    public void loadDataSuccess(UpdateInfoRet tData) {
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            ToastUtils.showLong("提交成功");
            finish();
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        ToastUtils.showLong("提交失败");
    }
}
