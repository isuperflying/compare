package com.yc.compare;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.liulishuo.filedownloader.FileDownloader;
import com.mob.MobSDK;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.yc.compare.util.AppContextUtil;

/**
 * Created by admin on 2017/3/24.
 */

public class App extends Application {
    public static Context applicationContext;

    public static boolean isShowBrand = true;

    public static Context getContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        UMConfigure.init(this,"5c0f80c2f1f556c767000080"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        UMConfigure.isDebugLog();
        PlatformConfig.setWeixin("wxda79bd5452cd1a56", "e6eb27a519e908400b9ec9f2368f1539");
        PlatformConfig.setQQZone("1107940377", "2I8ULllaGEmvLS5k");

        Utils.init(this);
        MobSDK.init(this);
        AppContextUtil.init(this);
        applicationContext = this;
        FileDownloader.setup(this);
    }

}
