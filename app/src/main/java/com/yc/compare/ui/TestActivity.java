package com.yc.compare.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.yc.compare.R;
import com.yc.compare.bean.UserInfoRet;
import com.yc.compare.presenter.UserPresenterImp;
import com.yc.compare.util.StringUtils;
import com.yc.compare.view.UserView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import permissions.dispatcher.PermissionRequest;

/**
 * Created by admin on 2017/3/14.
 */

public class TestActivity extends AppCompatActivity implements UserView {

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    @BindView(R.id.username_edt)
    EditText usernameEdt;

    @BindView(R.id.password_edt)
    EditText passwordEdt;

    @BindView(R.id.login_btn)
    Button loginBtn;

    @BindView(R.id.update_user_info_btn)
    Button updateUserInfoBtn;

    @BindView(R.id.more_files_upload_btn)
    Button moreFileUploadBtn;

    @BindView(R.id.more_files_upload_type1_btn)
    Button getMoreFileUploadBtn1;

    private UserPresenterImp userPresenterImp;

    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        initData();
        //LoginActivityPermissionsDispatcher.showReadStorateWithCheck(this);
        //LoginActivityPermissionsDispatcher.showWriteStorateWithCheck(this);
    }

    public void initData() {
        userPresenterImp = new UserPresenterImp(this, this);
        progressDialog = new ProgressDialog(TestActivity.this);
        progressDialog.setMessage("正在登录");
    }

    @OnClick(R.id.login_btn)
    public void login() {

        if (StringUtils.isEmpty(usernameEdt.getText())) {
            ToastUtils.showLong("请输入用户名");
            return;
        }

        if (StringUtils.isEmpty(passwordEdt.getText())) {
            ToastUtils.showLong("请输入密码");
            return;
        }

        //userPresenterImp.validateUserInfo(usernameEdt.getText().toString(), passwordEdt.getText().toString());
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(UserInfoRet tData) {

    }

    @Override
    public void loadDataError(Throwable throwable) {

    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        //File file = FileUtils.getFile(this, fileUri);
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/u1.png");

        // 为file建立RequestBody实例
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);

        // MultipartBody.Part借助文件名完成最终的上传
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, File file) {

        // 为file建立RequestBody实例
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);

        // MultipartBody.Part借助文件名完成最终的上传
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    @NonNull
    private Map<String, RequestBody> prepareFilePart(String partName, List<File> files) {

        Map<String, RequestBody> maps = new HashMap<String, RequestBody>();
        int i = 1;
        for (File file : files) {
            // 为file建立RequestBody实例
            RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
            maps.put("u" + i + ".png", requestFile);
            i++;
        }

        // MultipartBody.Part借助文件名完成最终的上传
        return maps;
    }


    @OnClick(R.id.update_user_info_btn)
    public void registerUserInfo() {

        File file1 = new File(Environment.getExternalStorageDirectory().getPath() + "/u1.png");
        File file2 = new File(Environment.getExternalStorageDirectory().getPath() + "/u2.png");

        RequestBody description = createPartFromString("hello, this is description speaking");

        MultipartBody.Part body1 = prepareFilePart("video1", file1);

        MultipartBody.Part body2 = prepareFilePart("video2", file2);

        //userPresenterImp.registerUserInfo(description,body1);

        List<MultipartBody.Part> list = new ArrayList<MultipartBody.Part>();
        list.add(body1);
        list.add(body2);
        //userPresenterImp.saveMorePics(description,list);

        /*MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("token", "abc123");
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("imgfile", file.getName(), imageBody);//imgfile 后台接收图片流的参数名

        List<MultipartBody.Part> parts = builder.build().parts();
        ApiUtil.uploadMemberIcon(parts).enqueue(new Callback<Result<String>>() {//返回结果
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                //AppUtil.showToastInfo(TestActivity.this, response.body().getMsg());
                //Toasty.normal(TestActivity.this,"11");
                Logger.e("onResponse--->");
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                t.printStackTrace();
                Logger.e("onFailure--->");
            }
        });*/

    }


    //方法有问题，待完善
    @OnClick(R.id.more_files_upload_btn)
    public void moreFilesUpload() {
        File file1 = new File(Environment.getExternalStorageDirectory().getPath() + "/u1.png");
        File file2 = new File(Environment.getExternalStorageDirectory().getPath() + "/u2.png");

        RequestBody description = createPartFromString("hello, this is description speaking");

        List<File> files = new ArrayList<File>();
        files.add(file1);
        files.add(file2);

        Map<String, RequestBody> maps = prepareFilePart("file", files);
        //userPresenterImp.saveMoreFiles(description,maps);
    }

    @OnClick(R.id.more_files_upload_type1_btn)
    public void saveMoreFilesType1() {
        List<String> pathList = new ArrayList<String>();//此处是伪代码，获取多张待上传图片的地址列表
        pathList.add(Environment.getExternalStorageDirectory().getPath() + "/u1.png");
        pathList.add(Environment.getExternalStorageDirectory().getPath() + "/u2.png");

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("love", "music");//自定义参数key常量类，即参数名
        //多张图片
        for (int i = 0; i < pathList.size(); i++) {
            File file = new File(pathList.get(i));//filePath 图片地址
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("imgFile" + i, file.getName(), imageBody);//"imgFile"+i 后台接收图片流的参数名
        }
        RequestBody description = createPartFromString("hello, this is description speaking saveMoreFilesType1 ");
        List<MultipartBody.Part> parts = builder.build().parts();
        //userPresenterImp.saveMorePics(description,parts);
    }

    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }
}
