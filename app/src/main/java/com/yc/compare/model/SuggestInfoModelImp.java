package com.yc.compare.model;

import android.content.Context;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yc.compare.api.UploadApi;
import com.yc.compare.api.UserServiceApi;
import com.yc.compare.base.BaseModel;
import com.yc.compare.base.IBaseRequestCallBack;
import com.yc.compare.bean.ResultInfo;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 2017/3/13.
 */

public class SuggestInfoModelImp extends BaseModel implements SuggestInfoModel<ResultInfo> {

    private Context context = null;
    private UserServiceApi userServiceApi;
    private UploadApi uploadApi;
    private CompositeSubscription mCompositeSubscription;

    public SuggestInfoModelImp(Context mContext) {
        super();
        context = mContext;
        userServiceApi = mRetrofit.create(UserServiceApi.class);
        uploadApi = mRetrofit.create(UploadApi.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void addSuggest(String userId, String content, final IBaseRequestCallBack<ResultInfo> iBaseRequestCallBack) {
        JSONObject params = new JSONObject();
        try {
            params.put("userId", userId);
            params.put("content", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
        mCompositeSubscription.add(userServiceApi.sendSms(requestBody)  //将subscribe添加到subscription，用于注销subscribe
                .observeOn(AndroidSchedulers.mainThread())//指定事件消费线程
                .subscribeOn(Schedulers.io())  //指定 subscribe() 发生在 IO 线程
                .subscribe(new Subscriber<ResultInfo>() {

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
                    public void onNext(ResultInfo resultInfo) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(resultInfo);
                    }
                }));
    }

}
