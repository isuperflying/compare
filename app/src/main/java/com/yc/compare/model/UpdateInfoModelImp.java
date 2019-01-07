package com.yc.compare.model;

import android.content.Context;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yc.compare.api.UpdateServiceApi;
import com.yc.compare.api.UploadApi;
import com.yc.compare.base.BaseNoRsaModel;
import com.yc.compare.base.IBaseRequestCallBack;
import com.yc.compare.bean.UpdateInfoRet;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 2017/3/13.
 */

public class UpdateInfoModelImp extends BaseNoRsaModel implements UpdateInfoModel<UpdateInfoRet> {

    private Context context = null;
    private UpdateServiceApi updateServiceApi;
    private UploadApi uploadApi;
    private CompositeSubscription mCompositeSubscription;

    public UpdateInfoModelImp(Context mContext) {
        super();
        context = mContext;
        updateServiceApi = mRetrofit.create(UpdateServiceApi.class);
        uploadApi = mRetrofit.create(UploadApi.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void updateHead(String userId, File file, final IBaseRequestCallBack<UpdateInfoRet> iBaseRequestCallBack) {

        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part fileBody = MultipartBody.Part.createFormData("pic", file.getName(), requestFile);

//        JSONObject params = new JSONObject();
//        try {
//            params.put("uid", userId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());

        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), userId);

        mCompositeSubscription.add(updateServiceApi.updateHead(description, fileBody)  //将subscribe添加到subscription，用于注销subscribe
                .observeOn(AndroidSchedulers.mainThread())//指定事件消费线程
                .subscribeOn(Schedulers.io())  //指定 subscribe() 发生在 IO 线程
                .subscribe(new Subscriber<UpdateInfoRet>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        //onStart它总是在 subscribe 所发生的线程被调用 ,如果你的subscribe不是主线程，则会出错，则需要指定线程;
                        iBaseRequestCallBack.beforeRequest();
                    }

                    @Override
                    public void onCompleted() {
                        //回调接口：请求已完成，可以隐藏progress
                        iBaseRequestCallBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //回调接口：请求异常
                        iBaseRequestCallBack.requestError(e);
                    }

                    @Override
                    public void onNext(UpdateInfoRet updateInfoRet) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(updateInfoRet);
                    }
                }));
    }

    @Override
    public void updateNickName(String uid, String nickname, final IBaseRequestCallBack<UpdateInfoRet> iBaseRequestCallBack) {
        JSONObject params = new JSONObject();
        try {
            params.put("uid", uid);
            params.put("nickname", nickname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
        mCompositeSubscription.add(updateServiceApi.updateNickName(requestBody)  //将subscribe添加到subscription，用于注销subscribe
                .observeOn(AndroidSchedulers.mainThread())//指定事件消费线程
                .subscribeOn(Schedulers.io())  //指定 subscribe() 发生在 IO 线程
                .subscribe(new Subscriber<UpdateInfoRet>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        //onStart它总是在 subscribe 所发生的线程被调用 ,如果你的subscribe不是主线程，则会出错，则需要指定线程;
                        iBaseRequestCallBack.beforeRequest();
                    }

                    @Override
                    public void onCompleted() {
                        //回调接口：请求已完成，可以隐藏progress
                        iBaseRequestCallBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //回调接口：请求异常
                        iBaseRequestCallBack.requestError(e);
                    }

                    @Override
                    public void onNext(UpdateInfoRet updateInfoRet) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(updateInfoRet);
                    }
                }));
    }

    @Override
    public void addSuggest(String uid, String content, final IBaseRequestCallBack<UpdateInfoRet> iBaseRequestCallBack) {
        JSONObject params = new JSONObject();
        try {
            params.put("uid", uid);
            params.put("content", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
        mCompositeSubscription.add(updateServiceApi.addSuggest(requestBody)  //将subscribe添加到subscription，用于注销subscribe
                .observeOn(AndroidSchedulers.mainThread())//指定事件消费线程
                .subscribeOn(Schedulers.io())  //指定 subscribe() 发生在 IO 线程
                .subscribe(new Subscriber<UpdateInfoRet>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        //onStart它总是在 subscribe 所发生的线程被调用 ,如果你的subscribe不是主线程，则会出错，则需要指定线程;
                        iBaseRequestCallBack.beforeRequest();
                    }

                    @Override
                    public void onCompleted() {
                        //回调接口：请求已完成，可以隐藏progress
                        iBaseRequestCallBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //回调接口：请求异常
                        iBaseRequestCallBack.requestError(e);
                    }

                    @Override
                    public void onNext(UpdateInfoRet updateInfoRet) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(updateInfoRet);
                    }
                }));
    }
}
