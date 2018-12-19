package com.yc.compare.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMShareAPI;
import com.yc.compare.R;
import com.yc.compare.bean.UserInfo;
import com.yc.compare.common.Constants;
import com.yc.compare.ui.AboutActivity;
import com.yc.compare.ui.CollectActivity;
import com.yc.compare.ui.LoginActivity;
import com.yc.compare.ui.SuggestActivity;
import com.yc.compare.ui.UserInfoActivity;
import com.yc.compare.ui.base.BaseFragment;
import com.yc.compare.ui.custom.GlideRoundTransform;

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
        }
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
        Intent intent = new Intent(getActivity(), CollectActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_tucao)
    void suggest() {
        Intent intent = new Intent(getActivity(), SuggestActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_update_info)
    void updateUserInfo() {
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_about)
    void about() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }

}
