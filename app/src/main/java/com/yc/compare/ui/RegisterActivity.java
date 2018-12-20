package com.yc.compare.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.yc.compare.R;
import com.yc.compare.bean.UserInfoRet;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.UserPresenterImp;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.util.StringUtils;
import com.yc.compare.view.UserView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by myflying on 2018/12/4.
 */
public class RegisterActivity extends BaseFragmentActivity implements UserView {

    public static String COUNTRY_CODE = "86";

    @BindView(R.id.tv_top_title)
    TextView mTitleTextView;

    @BindView(R.id.et_user_phone)
    EditText mMobileEditText;

    @BindView(R.id.et_sms_code)
    EditText mCodeEditText;

    @BindView(R.id.et_pw)
    EditText mPassWordEditText;

    @BindView(R.id.et_again_pw)
    EditText mAgainPwEditText;

    @BindView(R.id.btn_get_code)
    Button mGetCodeButton;

    @BindView(R.id.btn_register)
    Button mRegisterButton;

    private UserPresenterImp userPresenterImp;

    private ProgressDialog progressDialog = null;

    private int pageType = 1;//1:新用户注册，2:忘记密码

    private EventHandler eventHandler;

    private int seconds = 60;

    Runnable runnable;

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
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pageType = bundle.getInt("page_type");
        }
        if (pageType == 1) {
            mTitleTextView.setText(R.string.register_user_txt);
            mRegisterButton.setText(R.string.register_txt);
        } else {
            mTitleTextView.setText(R.string.forget_pw_txt);
            mRegisterButton.setText(R.string.config_info_txt);
        }
        initViews();
    }

    public void initViews() {
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        userPresenterImp = new UserPresenterImp(this, this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在登录");
        mMobileEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(charSequence.toString())) {
                    mGetCodeButton.setBackgroundResource(R.drawable.send_pre_bg);
                    mGetCodeButton.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.send_pre_color));
                    mRegisterButton.setBackgroundResource(R.mipmap.login_btn_pre);
                } else {
                    mGetCodeButton.setBackgroundResource(R.drawable.send_press_bg);
                    mGetCodeButton.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.white));
                    mRegisterButton.setBackgroundResource(R.drawable.login_selector);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void onResume() {
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
                                mCodeEditText.setFocusable(true);
                                Logger.i("send code success--->");
                                // TODO 处理成功得到验证码的结果
                                // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            } else {
                                Logger.i("send code fail--->");
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                register();
                                Logger.i("validate code success--->");
                                // TODO 处理验证码验证通过的结果
                            } else {

                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                ToastUtils.showLong("短信验证码错误，请重试");
                                Logger.i("validate code fail--->");
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
                    mGetCodeButton.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.white));
                    return;
                }
                mGetCodeButton.setEnabled(false);
                mGetCodeButton.setText(seconds + "s");
                mGetCodeButton.setBackgroundResource(R.drawable.send_pre_bg);
                mGetCodeButton.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.send_pre_color));
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 0);
    }

    @OnClick(R.id.btn_get_code)
    void getValidateCode() {
        if (!RegexUtils.isMobileExact(mMobileEditText.getText().toString())) {
            ToastUtils.showLong("请输入有效的手机号");
            return;
        }
        SMSSDK.getVerificationCode(COUNTRY_CODE, mMobileEditText.getText().toString());
    }

    @OnClick(R.id.btn_register)
    void submitCode() {

        if (StringUtils.isEmpty(mMobileEditText.getText())) {
            ToastUtils.showLong("请输入手机号");
            return;
        }

        if (StringUtils.isEmpty(mCodeEditText.getText())) {
            ToastUtils.showLong("请输入验证码");
            return;
        }

        if (StringUtils.isEmpty(mPassWordEditText.getText())) {
            ToastUtils.showLong("请输入新密码");
            return;
        }

        if (StringUtils.isEmpty(mAgainPwEditText.getText())) {
            ToastUtils.showLong("请再次输入新密码");
            return;
        }

        if (!mPassWordEditText.getText().toString().equals(mAgainPwEditText.getText().toString())) {
            ToastUtils.showLong("密码不一致,请重新输入");
            return;
        }

        progressDialog.show();
        SMSSDK.submitVerificationCode(COUNTRY_CODE, mMobileEditText.getText().toString(), mCodeEditText.getText().toString());
    }

    //注册用户
    public void register() {
        if(pageType == 1){
            userPresenterImp.register(mMobileEditText.getText().toString(), mPassWordEditText.getText().toString());
        }else{
            userPresenterImp.forget(mMobileEditText.getText().toString(), mPassWordEditText.getText().toString());
        }

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

        if (tData != null && tData.getCode() == Constants.OTHER_CODE) {
            ToastUtils.showLong("该手机号已注册，请直接登录");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return;
        }

        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData.getData() != null) {
                ToastUtils.showLong(StringUtils.isEmpty(tData.getMsg()) ? "注册成功" : tData.getMsg());

                SPUtils.getInstance().put(Constants.USER_INFO, JSONObject.toJSONString(tData.getData()));

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                ToastUtils.showLong(StringUtils.isEmpty(tData.getMsg()) ? "注册失败" : tData.getMsg());
            }
        }

        if (tData != null && tData.getCode() == Constants.FAIL) {
            ToastUtils.showLong(StringUtils.isEmpty(tData.getMsg()) ? "注册失败" : tData.getMsg());
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (eventHandler != null) {
            SMSSDK.unregisterEventHandler(eventHandler);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
            handler = null;
        }
        popBackStack();
    }

    @Override
    protected void onDestroy() {
        if (eventHandler != null) {
            SMSSDK.unregisterEventHandler(eventHandler);
        }

        if (handler != null && runnable != null) {
            seconds = 0;
            handler.removeCallbacks(runnable);
            handler = null;
        }

        super.onDestroy();
    }
}
