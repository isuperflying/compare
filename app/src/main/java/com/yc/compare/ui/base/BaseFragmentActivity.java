package com.yc.compare.ui.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * 基础的 Activity，配合 {@link BaseFragment} 使用。
 * Created by cgspine on 15/9/14.
 */
public abstract class BaseFragmentActivity extends AppCompatActivity {
    private static final String TAG = "BaseFragmentActivity";

    @SuppressWarnings("SameReturnValue")
    protected abstract int getContextViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //QMUIStatusBarHelper.translucent(this);
        setContentView(getContextViewId());
        ButterKnife.bind(this);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = this.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = this.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onBackPressed() {
        BaseFragment fragment = getCurrentFragment();
        if (fragment != null) {
            popBackStack();
        }
    }

    /**
     * 获取当前的 Fragment。
     */
    public BaseFragment getCurrentFragment() {
        return (BaseFragment) getSupportFragmentManager().findFragmentById(getContextViewId());
    }

    public void startFragment(BaseFragment fragment) {
        Log.i(TAG, "startFragment");
        BaseFragment.TransitionConfig transitionConfig = fragment.onFetchTransitionConfig();
        String tagName = fragment.getClass().getSimpleName();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(transitionConfig.enter, transitionConfig.exit, transitionConfig.popenter, transitionConfig.popout)
                .replace(getContextViewId(), fragment, tagName)
                .addToBackStack(tagName)
                .commit();
    }

    /**
     * 退出当前的 Fragment。
     */
    public void popBackStack() {
        Log.i(TAG, "popBackStack: getSupportFragmentManager().getBackStackEntryCount() = " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            BaseFragment fragment = getCurrentFragment();
            if (fragment == null) {
                finish();
                return;
            }
            BaseFragment.TransitionConfig transitionConfig = fragment.onFetchTransitionConfig();
            Object toExec = fragment.onLastFragmentFinish();
            if (toExec != null) {
                if (toExec instanceof BaseFragment) {
                    BaseFragment mFragment = (BaseFragment) toExec;
                    startFragment(mFragment);
                } else if (toExec instanceof Intent) {
                    Intent intent = (Intent) toExec;
                    finish();
                    startActivity(intent);
                    overridePendingTransition(transitionConfig.popenter, transitionConfig.popout);
                } else {
                    throw new Error("can not handle the result in onLastFragmentFinish");
                }
            } else {
                finish();
                overridePendingTransition(transitionConfig.popenter, transitionConfig.popout);
            }
        } else {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    /**
     * <pre>
     * 返回到clazz类型的Fragment，
     * 如 Home --> List --> Detail，
     * popBackStack(Home.class)之后，就是Home
     *
     * 如果堆栈没有clazz或者就是当前的clazz（如上例的popBackStack(Detail.class)），就相当于popBackStack()
     * </pre>
     */
    public void popBackStack(Class<? extends BaseFragment> clazz) {
        getSupportFragmentManager().popBackStack(clazz.getSimpleName(), 0);
    }

    /**
     * <pre>
     * 返回到非clazz类型的Fragment
     *
     * 如果上一个是目标clazz，则会继续pop，直到上一个不是clazz。
     * </pre>
     */
    public void popBackStackInclusive(Class<? extends BaseFragment> clazz) {
        getSupportFragmentManager().popBackStack(clazz.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}