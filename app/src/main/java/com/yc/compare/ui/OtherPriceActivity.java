package com.yc.compare.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.R;
import com.yc.compare.bean.OtherPriceInfoRet;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.OtherPriceInfoPresenterImp;
import com.yc.compare.ui.adapter.OtherPriceAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.view.OtherPriceInfoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class OtherPriceActivity extends BaseFragmentActivity implements OtherPriceInfoView {

    @BindView(R.id.et_key_word)
    EditText mKeyWordEditText;

    @BindView(R.id.iv_country_img)
    ImageView mCountryImageView;

    @BindView(R.id.tv_country_name)
    TextView mCountryNameTextView;

    @BindView(R.id.tv_country_price)
    TextView mCountryPriceTextView;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.other_price_list)
    RecyclerView mCommentListView;

    @BindView(R.id.layout_search)
    LinearLayout mSearchResultLayout;

    private OtherPriceAdapter otherPriceAdapter;

    private OtherPriceInfoPresenterImp otherPriceInfoPresenterImp;

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

        otherPriceAdapter = new OtherPriceAdapter(this, null);
        mCommentListView.setLayoutManager(new LinearLayoutManager(this));
        mCommentListView.setAdapter(otherPriceAdapter);
        avi.hide();

        View headView = LayoutInflater.from(this).inflate(R.layout.other_price_top, null);
        otherPriceAdapter.addHeaderView(headView);

        avi.show();
        otherPriceInfoPresenterImp.getOtherPriceInfoList("566", "");

        otherPriceAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                otherPriceInfoPresenterImp.getOtherPriceInfoList("566", "");
            }
        }, mCommentListView);
        mKeyWordEditText.setOnEditorActionListener(new EditorActionListener());
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
        Logger.i(JSONObject.toJSONString(tData));

        if (tData != null && tData.getCode() == Constants.SUCCESS) {
//            if (tData.getList() != null) {
//                otherPriceAdapter.setNewData(tData.getList());
//            }
//            if (tData.getSearchList() != null && tData.getSearchList().size() > 0) {
//                mSearchResultLayout.setVisibility(View.VISIBLE);
//                OtherPriceInfo otherPriceInfo = tData.getSearchList().get(0);
//                mCountryNameTextView.setText(otherPriceInfo.getCountryName());
//                mCountryPriceTextView.setText("¥" + otherPriceInfo.getLocalPrice());
//                Glide.with(this).load(otherPriceInfo.getCountryLogo()).into(mCountryImageView);
//            } else {
//                mSearchResultLayout.setVisibility(View.GONE);
//            }
        } else {
            ToastUtils.showLong("数据异常,请重试");
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        avi.hide();
    }

    private class EditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            switch (textView.getId()) {
                case R.id.et_key_word:
                    if (i == EditorInfo.IME_ACTION_SEARCH) {
                        if (StringUtils.isEmpty(textView.getText())) {
                            ToastUtils.showLong("请输入国家名称后搜索");
                            break;
                        }

                        KeyboardUtils.hideSoftInput(OtherPriceActivity.this);
                        otherPriceInfoPresenterImp.getOtherPriceInfoList("1", mKeyWordEditText.getText().toString());
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
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
