package com.yc.compare.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.orhanobut.logger.Logger;
import com.yc.compare.R;
import com.yc.compare.bean.VersionInfoRet;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.VersionPresenterImp;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.view.VersionInfoView;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class AboutActivity extends BaseFragmentActivity implements VersionInfoView {

    private VersionPresenterImp versionPresenterImp;

    @BindView(R.id.tv_current_version)
    TextView mCurrentVersionTextView;

    private NotificationManager notificationManager;

    private NotificationCompat.Builder builder;

    private Notification notification;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_about;
    }

    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在查询");
        versionPresenterImp = new VersionPresenterImp(this, this);
        mCurrentVersionTextView.setText(AppUtils.getAppVersionName());
        initNotification();
    }

    @OnClick(R.id.layout_version)
    void checkVersion() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        versionPresenterImp.checkVersion();
    }

    @OnClick(R.id.layout_score)
    void openMarket() {
        openApplicationMarket("com.yc.compare");
    }

    /**
     * 通过包名 在应用商店打开应用
     *
     * @param packageName 包名
     */
    private void openApplicationMarket(String packageName) {
        try {
            String str = "market://details?id=" + packageName;
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            localIntent.setData(Uri.parse(str));
            startActivity(localIntent);
        } catch (Exception e) {
            // 打开应用商店失败 可能是没有手机没有安装应用市场
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "打开应用商店失败", Toast.LENGTH_SHORT).show();
            // 调用系统浏览器进入商城
            String url = "http://app.mi.com/detail/163525?ref=search";
            openLinkBySystem(url);
        }
    }

    /**
     * 调用系统浏览器打开网页
     *
     * @param url 地址
     */
    private void openLinkBySystem(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
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

    private void showUpdateDialog(final String url, String content) {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.update_txt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        if (StringUtils.isEmpty(url)) {
                            ToastUtils.showLong("下载地址有误,请重试");
                            return;
                        }
                        downEnglishFile(url);
                    }
                })
                .setNegativeButton(R.string.cancel_txt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .setTitle("版本更新")
                .setMessage(content)
                .show();
    }

    @Override
    public void loadDataSuccess(VersionInfoRet tData) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (tData != null && tData.getData() != null && tData.getCode() == Constants.SUCCESS) {
            if (AppUtils.getAppVersionCode() < tData.getData().getVersionCode()) {
                showUpdateDialog(tData.getData().getVersionUrl(), tData.getData().getUpdateContent());
            } else {
                ToastUtils.showLong("已经是最新版本");
            }
        }
    }


    //初始化通知
    private void initNotification() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this, "down_apk");
        builder.setContentTitle("正在更新...") //设置通知标题
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo)) //设置通知的大图标
                .setDefaults(Notification.DEFAULT_LIGHTS) //设置通知的提醒方式： 呼吸灯
                .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知的优先级：最大
                .setAutoCancel(false)//设置通知被点击一次是否自动取消
                //.setContentText("下载进度:" + "0%")
                .setProgress(100, 0, false);
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }

    public void downEnglishFile(String downUrl) {

        String downDir = PathUtils.getExternalAppFilesPath();

        Logger.i("downPath--->" + downDir);

        //如果SD卡已挂载并且可读写
        if (downDir != null) {
            final String filePath = downDir + "/compare.apk";
            FileDownloader.getImpl().create(downUrl)
                    .setPath(filePath)
                    .setListener(new FileDownloadListener() {
                        @Override
                        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            ToastUtils.showLong("正在下载新版本");
                        }

                        @Override
                        protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        }

                        @Override
                        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            builder.setProgress(totalBytes, soFarBytes, false);  //顺序为总大小，实时大小
                            builder.setContentText("下载进度:" + (int) ((double) soFarBytes / totalBytes * 100) + "%");
                            notification = builder.build();
                            notificationManager.notify(1, notification);
                        }

                        @Override
                        protected void blockComplete(BaseDownloadTask task) {
                        }

                        @Override
                        protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                        }

                        @Override
                        protected void completed(BaseDownloadTask task) {
                            ToastUtils.showLong("新版下载完成");
                            install(filePath);
                        }

                        @Override
                        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        }

                        @Override
                        protected void error(BaseDownloadTask task, Throwable e) {
                        }

                        @Override
                        protected void warn(BaseDownloadTask task) {
                        }
                    }).start();
        }
    }

    private void install(String filePath) {

        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, "com.yc.compare.fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        startActivity(intent);
        notificationManager.cancel(1);//取消通知
    }

}
