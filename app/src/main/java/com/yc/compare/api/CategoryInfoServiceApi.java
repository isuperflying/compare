package com.yc.compare.api;

import com.yc.compare.bean.CategoryWrapperRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by myflying on 2018/12/3.
 */
public interface CategoryInfoServiceApi {

    @POST("v1.goods/navigation1")
    Observable<CategoryWrapperRet> getCategoryInfoList(@Body RequestBody requestBody);

    @POST("v1.goods/navigation2")
    Observable<CategoryWrapperRet> getCategoryWrapperList(@Body RequestBody requestBody);
}
