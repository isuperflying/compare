package com.yc.compare.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMShareAPI;
import com.yc.compare.R;
import com.yc.compare.bean.UserInfo;
import com.yc.compare.common.Constants;
import com.yc.compare.ui.AboutActivity;
import com.yc.compare.ui.CollectActivity;
import com.yc.compare.ui.HistoryActivity;
import com.yc.compare.ui.LoginActivity;
import com.yc.compare.ui.SuggestActivity;
import com.yc.compare.ui.UserInfoActivity;
import com.yc.compare.ui.base.BaseFragment;
import com.yc.compare.ui.custom.GlideRoundTransform;
import com.yc.compare.util.GlideCacheUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by iflying on 2017/12/14.
 */

public class MyFragment extends BaseFragment {

    @BindView(R.id.iv_user_head)
    ImageView mUserHeadImageView;

    @BindView(R.id.tv_not_login)
    TextView mNickNameTextView;

    @BindView(R.id.tv_click_login)
    TextView mClickLoginTextView;

    @BindView(R.id.layout_tucao)
    RelativeLayout mTucaoLayout;

    @BindView(R.id.tv_cache_size)
    TextView mCacheTextView;

    @BindView(R.id.layout_login_out)
    LinearLayout mLoginOutLayout;

    private UserInfo userInfo;

    private Context mContext;

    private UMShareAPI mShareAPI = null;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this, root);
        mContext = getActivity();
        //QMUIStatusBarHelper.translucent(getActivity());
        initViews();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!StringUtils.isEmpty(SPUtils.getInstance().getString(Constants.USER_INFO))) {
            mLoginOutLayout.setVisibility(View.VISIBLE);
            mLoginOutLayout.setClickable(true);

            Logger.i(SPUtils.getInstance().getString(Constants.USER_INFO));

            userInfo = JSON.parseObject(SPUtils.getInstance().getString(Constants.USER_INFO), new TypeReference<UserInfo>() {
            });
            if (userInfo != null) {
                RequestOptions myOptions = new RequestOptions()
                        .transform(new GlideRoundTransform(getActivity(), 35));
                Glide.with(getActivity()).load(userInfo.getHeadImage()).apply(myOptions).into(mUserHeadImageView);
                mNickNameTextView.setText(StringUtils.isEmpty(userInfo.getNickName()) ? "火星用户" : userInfo.getNickName());
                mClickLoginTextView.setText(StringUtils.isEmpty(userInfo.getNickName()) ? "用户ID：xxxx" : "用户ID：" + userInfo.getUserId());
            }
        } else {
            mLoginOutLayout.setVisibility(View.GONE);
        }

        Logger.i(GlideCacheUtil.getInstance().getCacheSize(getActivity()) + "");
        mCacheTextView.setText(GlideCacheUtil.getInstance().getCacheSize(getActivity()) + "");
    }

    public void initViews() {
        mShareAPI = UMShareAPI.get(mContext);
    }

    @OnClick(R.id.layout_user_info)
    void userInfo() {
        if (userInfo == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.layout_my_collect)
    void collect() {
        Intent intent = new Intent(getActivity(), userInfo != null ? CollectActivity.class : LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_history)
    void history() {
        Intent intent = new Intent(getActivity(), userInfo != null ? HistoryActivity.class : LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_tucao)
    void suggest() {
        Intent intent = new Intent(getActivity(), userInfo != null ? SuggestActivity.class : LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_update_info)
    void updateUserInfo() {
        Intent intent = new Intent(getActivity(), userInfo != null ? UserInfoActivity.class : LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_about)
    void about() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_cache_size)
    void clearCache() {
        GlideCacheUtil.getInstance().clearImageDiskCache(getActivity());
        ToastUtils.showLong("已清除");
        mCacheTextView.setText("");
    }

    @OnClick(R.id.layout_login_out)
    void loginOut() {
        new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.config_txt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        SPUtils.getInstance().put(Constants.USER_INFO, "");
                        userInfo = null;
                        mNickNameTextView.setText("未登录");
                        mClickLoginTextView.setText("点击登录/注册");
                        Glide.with(getActivity()).load(R.mipmap.def_user_head).into(mUserHeadImageView);
                        mLoginOutLayout.setVisibility(View.GONE);
                    }
                })
                .setNegativeButton(R.string.cancel_txt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .setTitle("退出")
                .setMessage("是否退出当前账号?")
                .show();
    }
}
