package com.yc.compare.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;
import com.yc.compare.R;
import com.yc.compare.bean.UpdateInfoRet;
import com.yc.compare.bean.UserInfo;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.UpdateInfoPresenterImp;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.ui.custom.ConfirmDialog;
import com.yc.compare.ui.custom.Glide4Engine;
import com.yc.compare.ui.custom.GlideRoundTransform;
import com.yc.compare.view.UpdateInfoView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by myflying on 2018/12/3.
 */
@RuntimePermissions
public class UserInfoActivity extends BaseFragmentActivity implements UpdateInfoView, View.OnClickListener {

    private static final int TAKE_BIG_PICTURE = 1000;

    private static final int REQUEST_CODE_CHOOSE = 23;

    private static final int CROP_SMALL_PICTURE = 1003;

    @BindView(R.id.iv_user_head)
    ImageView mUserHeadImageView;

    @BindView(R.id.tv_nick_name)
    TextView mNickNameTextView;

    BottomSheetDialog bottomSheetDialog;

    private File outputImage;

    private Uri imageUri;

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    private UpdateInfoPresenterImp updateInfoPresenterImp;

    private ProgressDialog progressDialog = null;

    private String resultNickName;

    private UserInfo userInfo;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {
        View paramsView = LayoutInflater.from(this).inflate(R.layout.photo_select_view, null);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(paramsView);

        LinearLayout cameraItem = paramsView.findViewById(R.id.layout_camera);
        LinearLayout photoItem = paramsView.findViewById(R.id.layout_local_photo);
        LinearLayout cancelItem = paramsView.findViewById(R.id.layout_cancel);
        cameraItem.setOnClickListener(this);
        photoItem.setOnClickListener(this);
        cancelItem.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在修改");

        updateInfoPresenterImp = new UpdateInfoPresenterImp(this, this);


        if (!StringUtils.isEmpty(SPUtils.getInstance().getString(Constants.USER_INFO))) {
            Logger.i(SPUtils.getInstance().getString(Constants.USER_INFO));
            userInfo = JSON.parseObject(SPUtils.getInstance().getString(Constants.USER_INFO), new TypeReference<UserInfo>() {
            });
            if (userInfo != null) {
                RequestOptions myOptions = new RequestOptions()
                        .transform(new GlideRoundTransform(this, 35));
                Glide.with(this).load(userInfo.getHeadImage()).apply(myOptions).into(mUserHeadImageView);
                mNickNameTextView.setText(userInfo.getNickName());
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        UserInfoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void showCamera() {
        //ToastUtils.showLong("允许使用存储权限");
        if(bottomSheetDialog != null && !bottomSheetDialog.isShowing()){
            bottomSheetDialog.show();
        }
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied() {
        Toast.makeText(this, R.string.permission_camera_denied, Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(PermissionRequest request) {
        showRationaleDialog(R.string.permission_camera_rationale, request);
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNeverAskAgain() {
        Toast.makeText(this, R.string.permission_camera_never_ask_again, Toast.LENGTH_SHORT).show();
    }

    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    @OnClick(R.id.layout_photo)
    public void showPhotoSelect() {
        UserInfoActivityPermissionsDispatcher.showCameraWithCheck(this);
    }

    @OnClick(R.id.layout_nickname)
    public void updateNickName() {
        final ConfirmDialog confirmDialog = new ConfirmDialog(this);
        confirmDialog.show();
        confirmDialog.setClicklistener(new ConfirmDialog.ClickListenerInterface() {
            @Override
            public void doConfirm(String nickName) {
                Logger.i("nick name" + nickName);
                resultNickName = nickName;
                confirmDialog.dismiss();
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }
                updateInfoPresenterImp.updateNickName(userInfo != null ? userInfo.getUserId() : "", nickName);
            }

            @Override
            public void doCancel() {
                confirmDialog.dismiss();
            }
        });
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

    @Override
    public void loadDataSuccess(UpdateInfoRet tData) {
        Logger.i(JSONObject.toJSONString(tData));

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            ToastUtils.showLong("修改成功");
            if (!StringUtils.isEmpty(resultNickName)) {
                mNickNameTextView.setText(resultNickName);
                if (userInfo != null) {
                    userInfo.setNickName(resultNickName);
                    SPUtils.getInstance().put(Constants.USER_INFO, JSONObject.toJSONString(userInfo));
                }
            }

            if (tData.getData() != null && tData.getData().size() > 0) {
                if (userInfo != null) {
                    userInfo.setHeadImage(tData.getData().get(0).getPic());
                    SPUtils.getInstance().put(Constants.USER_INFO, JSONObject.toJSONString(userInfo));
                }
            }
        } else {
            ToastUtils.showLong("修改失败");
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_camera:
                takeCamera();
                break;
            case R.id.layout_local_photo:
                Matisse.from(UserInfoActivity.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new Glide4Engine())
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
            case R.id.layout_cancel:
                if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 使用相机
     */
    private void takeCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        outputImage = new File(PathUtils.getExternalAppPicturesPath(), TimeUtils.getNowMills() + ".png");
        outputImage.getParentFile().mkdirs();

        Uri uri = null;
        Logger.i("currentApiVersion--->" + android.os.Build.VERSION.SDK_INT);
        if (android.os.Build.VERSION.SDK_INT < 24) {
            uri = Uri.fromFile(outputImage);
            imageUri = uri;
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, TAKE_BIG_PICTURE);
        } else {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, outputImage.getAbsolutePath());
            uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            imageUri = uri;
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, TAKE_BIG_PICTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_BIG_PICTURE:
                    if (imageUri == null) {
                        ToastUtils.showLong("数据异常，请重试");
                        break;
                    }
                    cropImageUri(imageUri, 300, 300, CROP_SMALL_PICTURE);
                    break;
                case REQUEST_CODE_CHOOSE:
                    Logger.i(JSONObject.toJSONString(Matisse.obtainPathResult(data)));
                    if (Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                        outputImage = new File(PathUtils.getExternalAppPicturesPath(), TimeUtils.getNowMills() + ".png");
                        imageUri = Uri.fromFile(outputImage);
                        cropImageUri(Matisse.obtainResult(data).get(0), 300, 300, CROP_SMALL_PICTURE);
                    }
                    break;
                case CROP_SMALL_PICTURE:
                    Logger.i("crop out path--->" + outputImage.getAbsolutePath());
                    if (outputImage.exists()) {
                        RequestOptions myOptions = new RequestOptions()
                                .transform(new GlideRoundTransform(this, 33));
                        Glide.with(this).load(outputImage.getAbsolutePath()).apply(myOptions).into(mUserHeadImageView);
                    }
                    if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                        bottomSheetDialog.dismiss();
                    }
                    resultNickName = "";
                    updateInfoPresenterImp.updateHead(userInfo != null ? userInfo.getUserId() : "", outputImage);
                    break;
            }
        }

    }

    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        //是否裁剪
        intent.putExtra("crop", "true");
        //设置xy的裁剪比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //设置输出的宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        //是否缩放
        intent.putExtra("scale", false);
        //输入图片的Uri，指定以后，可以在这个uri获得图片
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        //是否返回图片数据，可以不用，直接用uri就可以了
        intent.putExtra("return-data", false);
        //设置输入图片格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        //是否关闭面部识别
        intent.putExtra("noFaceDetection", true); // no face detection
        //启动
        startActivityForResult(intent, requestCode);
    }
}
