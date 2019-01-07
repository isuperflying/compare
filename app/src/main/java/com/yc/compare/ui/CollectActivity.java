package com.yc.compare.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.R;
import com.yc.compare.bean.CollectInfo;
import com.yc.compare.bean.CollectInfoRet;
import com.yc.compare.bean.CollectType;
import com.yc.compare.bean.CollectTypeRet;
import com.yc.compare.bean.ResultInfo;
import com.yc.compare.bean.UserInfo;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.CollectInfoPresenterImp;
import com.yc.compare.presenter.CollectTypePresenterImp;
import com.yc.compare.ui.adapter.CollectAdapter;
import com.yc.compare.ui.adapter.CollectTypeAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.view.CollectInfoView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class CollectActivity extends BaseFragmentActivity implements CollectInfoView {

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.layout_top)
    LinearLayout mTopLayout;

    @BindView(R.id.pop_bg_layout)
    LinearLayout mPopBgLayout;

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

    @BindView(R.id.iv_filter)
    ImageView mFilterImageView;

    @BindView(R.id.btn_delete)
    Button mDeleteButton;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    private CollectAdapter collectAdapter;

    private CollectInfoPresenterImp collectInfoPresenterImp;

    private CollectTypePresenterImp collectTypePresenterImp;

    private int pageSize = 20;

    private int currentPage = 1;

    private boolean isEdit = false;

    private boolean isCheckAll = false;

    private CustomPopWindow customPopWindow;

    private List<CollectType> typeList;

    private CollectTypeAdapter collectTypeAdapter;

    private int lastType = -1;

    private ProgressDialog progressDialog = null;

    private String queryType = "";

    private UserInfo userInfo;

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

        if (!StringUtils.isEmpty(SPUtils.getInstance().getString(Constants.USER_INFO))) {
            Logger.i(SPUtils.getInstance().getString(Constants.USER_INFO));
            userInfo = JSON.parseObject(SPUtils.getInstance().getString(Constants.USER_INFO), new TypeReference<UserInfo>() {
            });
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在删除");

        collectInfoPresenterImp = new CollectInfoPresenterImp(this, this);
        collectTypePresenterImp = new CollectTypePresenterImp(this, this);

        collectAdapter = new CollectAdapter(this, null);
        collectAdapter.setEdit(isEdit);
        mCollectListView.setLayoutManager(new LinearLayoutManager(this));
        mCollectListView.setAdapter(collectAdapter);

        collectAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                collectInfoPresenterImp.getCollectInfoList(userInfo != null ? userInfo.getUserId() : "", typeList.get(0).getCategoryId(), currentPage);
            }
        }, mCollectListView);

        collectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                collectAdapter.getData().get(position).setChecked(!collectAdapter.getData().get(position).isChecked());
                collectAdapter.notifyDataSetChanged();
            }
        });

        avi.show();
        collectTypePresenterImp.collectType(userInfo != null ? userInfo.getUserId() : "");
    }

    public void initPopWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.collect_type_top, null);
        //处理popWindow 显示内容
        handleListView(contentView);
        //创建并显示popWindow
        customPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create();

        customPopWindow.getPopupWindow().setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mPopBgLayout.setVisibility(View.GONE);
            }
        });
    }


    private void handleListView(View contentView) {
        //Logger.i(JSON.toJSONString(brandList));
        LinearLayout noDataLayout = contentView.findViewById(R.id.layout_no_data);
        RecyclerView collectTypeList = contentView.findViewById(R.id.collect_type_list);

        if (typeList != null) {
            collectTypeList.setVisibility(View.VISIBLE);
            noDataLayout.setVisibility(View.GONE);
            typeList.get(0).setSelected(true);
            collectTypeAdapter = new CollectTypeAdapter(this, typeList);
            collectTypeList.setLayoutManager(new GridLayoutManager(this, 2));
            collectTypeList.setAdapter(collectTypeAdapter);

            collectTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (lastType == position) {
                        return;
                    }

                    if (lastType > -1) {
                        collectTypeAdapter.getData().get(lastType).setSelected(false);
                    } else {
                        collectTypeAdapter.getData().get(0).setSelected(false);
                    }
                    collectTypeAdapter.getData().get(position).setSelected(true);
                    collectTypeAdapter.notifyDataSetChanged();
                    lastType = position;
                    currentPage = 1;
                    queryType = collectTypeAdapter.getData().get(position).getCategoryId();
                    collectInfoPresenterImp.getCollectInfoList(userInfo != null ? userInfo.getUserId() : "", queryType, currentPage);
                    if (customPopWindow != null && customPopWindow.getPopupWindow().isShowing()) {
                        customPopWindow.dissmiss();
                    }
                }
            });

        } else {
            collectTypeList.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        }
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

    @OnClick(R.id.iv_filter)
    void filter() {
        if (customPopWindow != null && !customPopWindow.getPopupWindow().isShowing()) {
            customPopWindow.showAsDropDown(mTopLayout, 0, 0);
            mPopBgLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_delete)
    void deleteCollect() {

        new AlertDialog.Builder(this)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        String cids = "";
                        for (int i = 0; i < collectAdapter.getData().size(); i++) {
                            if (collectAdapter.getData().get(i).isChecked()) {
                                cids += collectAdapter.getData().get(i).getCollectId() + ",";
                            }
                        }

                        if (StringUtils.isEmpty(cids)) {
                            ToastUtils.showLong("请选择记录删除");
                            return;
                        } else {
                            cids = cids.substring(0, cids.length() - 1);
                        }

                        collectInfoPresenterImp.deleteCollect(cids);
                        if (progressDialog != null && !progressDialog.isShowing()) {
                            progressDialog.show();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .setMessage("确认是否删除？")
                .show();
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

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (tData != null && tData.getCode() == Constants.SUCCESS) {

            if (tData instanceof CollectInfoRet) {
                avi.hide();
                if (((CollectInfoRet) tData).getData() != null && ((CollectInfoRet) tData).getData().size() > 0) {
                    if (currentPage == 1) {
                        collectAdapter.setNewData(((CollectInfoRet) tData).getData());
                    } else {
                        collectAdapter.addData(((CollectInfoRet) tData).getData());
                    }
                    if (((CollectInfoRet) tData).getData().size() == pageSize) {
                        collectAdapter.loadMoreComplete();
                    } else {
                        collectAdapter.loadMoreEnd();
                    }
                } else {
                    if (isEdit) {
                        ToastUtils.showLong("删除成功");
                        currentPage = 1;
                        collectInfoPresenterImp.getCollectInfoList(userInfo != null ? userInfo.getUserId() : "", queryType, currentPage);
                        changeEdit();
                    } else {
                        mNoDataLayout.setVisibility(View.VISIBLE);
                        mFilterImageView.setVisibility(View.INVISIBLE);
                        mEditTextView.setVisibility(View.INVISIBLE);
                    }
                }
            }

            if (tData instanceof CollectTypeRet) {
                if (((CollectTypeRet) tData).getData().getOptData() != null) {
                    typeList = ((CollectTypeRet) tData).getData().getOptData();
                    initPopWindow();
                    collectInfoPresenterImp.getCollectInfoList(userInfo != null ? userInfo.getUserId() : "", typeList.get(0).getCategoryId(), currentPage);
                }
            }
        } else {
            ToastUtils.showLong("操作异常");
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
