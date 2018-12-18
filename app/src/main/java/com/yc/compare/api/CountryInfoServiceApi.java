package com.yc.compare.api;

import com.yc.compare.bean.CountryInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by myflying on 2018/12/3.
 */
public interface CountryInfoServiceApi {

    @POST("v1.common/hotCountryList")
    Observable<CountryInfoRet> getCountryInfoList(@Body RequestBody requestBody);
}
