package com.yc.compare.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.R;
import com.yc.compare.bean.CollectInfo;
import com.yc.compare.bean.CollectInfoRet;
import com.yc.compare.presenter.CollectInfoPresenterImp;
import com.yc.compare.ui.adapter.CollectAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.view.CollectInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class CollectActivity extends BaseFragmentActivity implements CollectInfoView {

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.collect_list)
    RecyclerView mCollectListView;

    @BindView(R.id.tv_edit_goods)
    TextView mEditTextView;

    @BindView(R.id.layout_delete)
    RelativeLayout mDeteleLayout;

    @BindView(R.id.layout_check_all)
    LinearLayout mCheckAllLayout;

    @BindView(R.id.iv_all_check_box)
    ImageView mCheckAllImageView;

    private CollectAdapter collectAdapter;

    private CollectInfoPresenterImp collectInfoPresenterImp;

    private ProgressDialog progressDialog = null;

    private int pageSize = 12;

    private int currentPage = 1;

    private boolean isEdit = false;

    private boolean isCheckAll = false;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {
        collectInfoPresenterImp = new CollectInfoPresenterImp(this, this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在查询");

        List<CollectInfo> list = new ArrayList<>();
        list.add(new CollectInfo());
        list.add(new CollectInfo());
        list.add(new CollectInfo());
        list.add(new CollectInfo());
        list.add(new CollectInfo());
        list.add(new CollectInfo());
        list.add(new CollectInfo());
        list.add(new CollectInfo());
        list.add(new CollectInfo());
        list.add(new CollectInfo());
        list.add(new CollectInfo());
        list.add(new CollectInfo());
        list.add(new CollectInfo());
        list.add(new CollectInfo());
        list.add(new CollectInfo());
        list.add(new CollectInfo());
        avi.hide();
        collectAdapter = new CollectAdapter(this, list);
        collectAdapter.setEdit(isEdit);

        mCollectListView.setLayoutManager(new LinearLayoutManager(this));
        mCollectListView.setAdapter(collectAdapter);
//
//        avi.show();
//        brandInfoPresenterImp.getBrandInfoList(currentPage);
//
//        hotBrandAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                currentPage++;
//                brandInfoPresenterImp.getBrandInfoList(currentPage);
//            }
//        }, mHotBrandListView);

        collectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                collectAdapter.getData().get(position).setChecked(!collectAdapter.getData().get(position).isChecked());
                collectAdapter.notifyDataSetChanged();
            }
        });

    }

    @OnClick(R.id.tv_edit_goods)
    public void changeEdit() {
        isEdit = !isEdit;
        collectAdapter.setEdit(isEdit);
        collectAdapter.notifyDataSetChanged();
        mEditTextView.setText(isEdit ? "完成" : "编辑");
        mDeteleLayout.setVisibility(isEdit ? View.VISIBLE : View.GONE);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, isEdit ? SizeUtils.dp2px(48) : 0);
        mCollectListView.setLayoutParams(params);
    }

    @OnClick(R.id.layout_check_all)
    public void checkAll() {
        isCheckAll = !isCheckAll;
        for (CollectInfo collect : collectAdapter.getData()) {
            collect.setChecked(isCheckAll);
        }

        mCheckAllImageView.setBackgroundResource(isCheckAll ? R.mipmap.item_selected_icon : R.mipmap.item_normal_icon);
        collectAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(CollectInfoRet tData) {
        avi.hide();
        Logger.i(JSONObject.toJSONString(tData));
//        if (tData != null && tData.getCode() == Constants.SUCCESS) {
//            hotBrandAdapter.setNewData(tData.getData());
//            hotBrandAdapter.loadMoreEnd();
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
