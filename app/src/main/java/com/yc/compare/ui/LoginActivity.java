package com.yc.compare.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.compare.R;
import com.yc.compare.bean.OtherLoginBean;
import com.yc.compare.bean.UserInfoRet;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.UserPresenterImp;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.util.StringUtils;
import com.yc.compare.view.UserView;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by myflying on 2018/12/4.
 */
public class LoginActivity extends BaseFragmentActivity implements UserView {

    public static String COUNTRY_CODE = "86";

    private Context mContext;

    @BindView(R.id.et_user_name)
    EditText userNameEditText;

    @BindView(R.id.et_user_pw)
    EditText passWordEditText;

    @BindView(R.id.tv_login_type)
    TextView mLoginTypeTextView;

    @BindView(R.id.tv_forget_pw)
    TextView mForgetTextView;

    @BindView(R.id.tv_register)
    TextView mRegisterTextView;

    @BindView(R.id.btn_get_code)
    Button mGetCodeButton;

    @BindView(R.id.btn_login)
    Button mLoginButton;

    @BindView(R.id.iv_weixin_login)
    ImageView mWeiXinLogin;

    @BindView(R.id.iv_qq_login)
    ImageView mQQLogin;

    private UserPresenterImp userPresenterImp;

    private ProgressDialog progressDialog = null;

    private int loginType = 1; //账号密码登录,2手机号登录

    private EventHandler eventHandler;

    private int seconds = 60;

    private String validateNum;//随机验证码

    private boolean isSendSms;//是否是发送验证码的请求

    private Runnable runnable;

    private UMShareAPI mShareAPI = null;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getContextViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mShareAPI = UMShareAPI.get(mContext);
        initViews();
    }

    public void initViews() {
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        userPresenterImp = new UserPresenterImp(this, this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在登录");
        userNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(charSequence.toString())) {
                    mGetCodeButton.setBackgroundResource(R.drawable.send_pre_bg);
                    mGetCodeButton.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.send_pre_color));
                    mLoginButton.setBackgroundResource(R.mipmap.login_btn_pre);
                } else {
                    mGetCodeButton.setBackgroundResource(R.drawable.send_press_bg);
                    mGetCodeButton.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.white));
                    mLoginButton.setBackgroundResource(R.drawable.login_selector);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //初始时设置按钮可以点击
        mGetCodeButton.setEnabled(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                new Handler(Looper.getMainLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        int event = msg.arg1;
                        int result = msg.arg2;
                        Object data = msg.obj;
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                smsButtonRefresh();
                                Logger.i("send code success--->");
                                // TODO 处理成功得到验证码的结果
                                // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            } else {
                                ToastUtils.showLong("验证码发送失败，请重试");
                                Logger.i("send code fail--->");
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                Logger.i("validate code success--->");
                                // TODO 处理验证码验证通过的结果

                                userPresenterImp.userLogin(userNameEditText.getText().toString(), passWordEditText.getText().toString(), String.valueOf(loginType));
                            } else {
                                Logger.i("validate code fail--->");
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                ToastUtils.showLong("短信验证码错误，请重试");
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        }
                        // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                        return false;
                    }
                }).sendMessage(msg);
            }
        };

        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);
    }

    /**
     * 刷新验证码倒计时
     */
    private void smsButtonRefresh() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (seconds-- <= 0) {
                    mGetCodeButton.setEnabled(true);
                    mGetCodeButton.setText("获取验证码");
                    mGetCodeButton.setBackgroundResource(R.drawable.send_press_bg);
                    mGetCodeButton.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.white));
                    return;
                }
                mGetCodeButton.setEnabled(false);
                mGetCodeButton.setText(seconds + "s");
                mGetCodeButton.setBackgroundResource(R.drawable.send_pre_bg);
                mGetCodeButton.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.send_pre_color));
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 0);
    }

    @OnClick(R.id.btn_get_code)
    void sendCode() {
        if (StringUtils.isEmpty(userNameEditText.getText())) {
            ToastUtils.showLong("请输入手机号");
            return;
        }

        if (!RegexUtils.isMobileExact(userNameEditText.getText().toString())) {
            ToastUtils.showLong("请输入有效的手机号");
            return;
        }

        SMSSDK.getVerificationCode(COUNTRY_CODE, userNameEditText.getText().toString());
        isSendSms = true;
    }

    @OnClick(R.id.btn_login)
    void login() {

        if (StringUtils.isEmpty(userNameEditText.getText())) {
            ToastUtils.showLong(loginType == 1 ? "请输入账号" : "请输入手机号");
            return;
        }

        if (StringUtils.isEmpty(passWordEditText.getText())) {
            ToastUtils.showLong(loginType == 1 ? "请输入密码" : "请输入验证码");
            return;
        }

        progressDialog.show();
        if (loginType == 1) {
            userPresenterImp.userLogin(userNameEditText.getText().toString(), passWordEditText.getText().toString(), String.valueOf(loginType));
        } else {
            SMSSDK.submitVerificationCode(COUNTRY_CODE, userNameEditText.getText().toString(), passWordEditText.getText().toString());
        }
    }

    @OnClick(R.id.tv_login_type)
    void changeType() {
        if (mForgetTextView.getVisibility() == View.VISIBLE) {
            loginType = 2;
            mForgetTextView.setVisibility(View.GONE);
            mGetCodeButton.setVisibility(View.VISIBLE);
            passWordEditText.setHint(R.string.user_input_sms_code);
            passWordEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            loginType = 1;
            mForgetTextView.setVisibility(View.VISIBLE);
            mGetCodeButton.setVisibility(View.GONE);
            passWordEditText.setHint(R.string.user_input_password);
            passWordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    @OnClick(R.id.tv_forget_pw)
    void forgetPassWord() {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("page_type", 2);
        startActivity(intent);
    }

    @OnClick(R.id.tv_register)
    void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("page_type", 1);
        startActivity(intent);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(UserInfoRet tData) {
        Logger.i(JSONObject.toJSONString(tData));

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (tData != null) {
            if (tData.getCode() == Constants.SUCCESS && tData.getData() != null) {
                ToastUtils.showLong("登录成功");
                SPUtils.getInstance().put(Constants.USER_INFO, JSONObject.toJSONString(tData.getData()));
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

            if (tData.getCode() == Constants.FAIL) {
                ToastUtils.showLong(StringUtils.isEmpty(tData.getMsg()) ? "登录失败" : tData.getMsg());
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (isSendSms) {
            isSendSms = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (eventHandler != null) {
            SMSSDK.unregisterEventHandler(eventHandler);
        }
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Logger.i(JSONObject.toJSONString(data));
            //Toast.makeText(mContext, "授权成功了", Toast.LENGTH_LONG).show();

            if (data != null) {
                progressDialog.show();
                OtherLoginBean otherLoginBean = new OtherLoginBean();
                otherLoginBean.setType(platform == SHARE_MEDIA.WEIXIN ? "1" : "2");
                otherLoginBean.setHeadPic(data.get("iconurl"));
                otherLoginBean.setNickname(data.get("name"));
                otherLoginBean.setOpenid(data.get("openid"));
                otherLoginBean.setUnionid(data.get("unionid"));
                userPresenterImp.otherLogin(otherLoginBean);
            }

        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            //Toast.makeText(mContext, "授权失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            //Toast.makeText(mContext, "授权取消了", Toast.LENGTH_LONG).show();
        }
    };

    @OnClick(R.id.iv_weixin_login)
    public void wxLogin() {
        mShareAPI.getPlatformInfo(this, SHARE_MEDIA.WEIXIN, authListener);
    }

    @OnClick(R.id.iv_qq_login)
    public void qqLogin() {
        mShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, authListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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
    protected void onDestroy() {
        if (handler != null && runnable != null) {
            seconds = 0;
            handler.removeCallbacks(runnable);
            handler = null;
        }
        super.onDestroy();
    }
}
