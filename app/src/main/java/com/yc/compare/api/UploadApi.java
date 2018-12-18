package com.yc.compare.api;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by admin on 2017/3/13.
 */

public interface UploadApi {

    @Multipart
    @POST("registerUserInfo?")
    Observable<RequestBody> registerUserInfo(@Part("description") RequestBody body, @Part MultipartBody.Part parts);

    @Multipart
    @POST("saveMorePics?")
    Observable<RequestBody> saveMorePics(@Part("description") RequestBody body, @Part List<MultipartBody.Part> parts);

    @Multipart
    @POST("saveMoreFiles?")
    Observable<RequestBody> saveMoreFiles(@Part("description") RequestBody body, @PartMap Map<String, RequestBody> files);
}
