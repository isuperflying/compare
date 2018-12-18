package com.yc.compare.model;

import android.content.Context;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.StringUtils;
import com.yc.compare.api.GoodInfoServiceApi;
import com.yc.compare.base.BaseModel;
import com.yc.compare.base.IBaseRequestCallBack;
import com.yc.compare.bean.Condition;
import com.yc.compare.bean.GoodInfoRet;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by iflying on 2018/1/9.
 */

public class GoodInfoModelImp extends BaseModel implements GoodInfoModel<GoodInfoRet> {

    private Context context = null;
    private GoodInfoServiceApi goodInfoServiceApi;
    private CompositeSubscription mCompositeSubscription;

    public GoodInfoModelImp(Context mContext) {
        super();
        context = mContext;
        goodInfoServiceApi = mRetrofit.create(GoodInfoServiceApi.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void getGoodInfoByType(int page, String cid, final IBaseRequestCallBack<GoodInfoRet> iBaseRequestCallBack) {

        JSONObject params = new JSONObject();
        try {
            params.put("page", page == 1 ? "" : page + "");
            params.put("category_id", cid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());

        mCompositeSubscription.add(goodInfoServiceApi.getGoodInfoByType(requestBody)  //将subscribe添加到subscription，用于注销subscribe
                .observeOn(AndroidSchedulers.mainThread())//指定事件消费线程
                .subscribeOn(Schedulers.io())  //指定 subscribe() 发生在 IO 线程
                .subscribe(new Subscriber<GoodInfoRet>() {

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
                    public void onNext(GoodInfoRet goodInfoRet) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(goodInfoRet);
                    }
                }));
    }

    @Override
    public void getGoodInfoByParams(Condition condition, final IBaseRequestCallBack<GoodInfoRet> iBaseRequestCallBack) {

        JSONObject params = new JSONObject();
        try {
            params.put("country_id", !StringUtils.isEmpty(condition.getCountryId()) ? condition.getCountryId() : "");
            params.put("category_id", !StringUtils.isEmpty(condition.getCategoryId()) ? condition.getCategoryId() : "");
            params.put("brand_id", !StringUtils.isEmpty(condition.getBrandId()) ? condition.getBrandId() : "");
            params.put("condition", !StringUtils.isEmpty(condition.getSearchCondition()) ? condition.getSearchCondition() : "new");
            params.put("key_word", !StringUtils.isEmpty(condition.getKeyWord()) ? condition.getKeyWord() : "");
            params.put("page", !StringUtils.isEmpty(condition.getPage()) ? condition.getPage() : "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());

        mCompositeSubscription.add(goodInfoServiceApi.getGoodInfoByParams(requestBody)  //将subscribe添加到subscription，用于注销subscribe
                .observeOn(AndroidSchedulers.mainThread())//指定事件消费线程
                .subscribeOn(Schedulers.io())  //指定 subscribe() 发生在 IO 线程
                .subscribe(new Subscriber<GoodInfoRet>() {

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
                    public void onNext(GoodInfoRet goodInfoRet) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(goodInfoRet);
                    }
                }));
    }
}
