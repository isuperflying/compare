package com.yc.compare.common.interceptor;

import com.yc.compare.common.Constants;
import com.yc.compare.util.Base64Utils;
import com.yc.compare.util.RSAUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by admin on 2017/3/13.
 */

public class EncryptionInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        try {
            Request request = chain.request();
            RequestBody oldBody = request.body();

            Buffer buffer = new Buffer();
            oldBody.writeTo(buffer);
            byte[] strOldBody = buffer.readByteArray();
            Logger.e("加密之前的参数--->" + new String(strOldBody,"UTF-8"));

            MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
            byte[] strNewBody = RSAUtils.encryptByPublicKey(strOldBody, Constants.DEFAULT_PUBLIC_KEY);
            //Logger.e("加密之后的参数--->" + new String(strNewBody,"UTF-8"));

            //byte[] res = RSAUtils.decryptByPrivateKey(strNewBody,Constants.DEFAULT_PRIVATE_KEY);
            //Logger.e("加密之后再解密的参数--->" + new String(res,"UTF-8"));

            RequestBody body = RequestBody.create(mediaType, Base64Utils.encode(strNewBody));
            request = request.newBuilder().header("Content-Type", body.contentType().toString()).header("Content-Length", String.valueOf(body.contentLength())).method(request.method(), body).build();

            //request = request.newBuilder().post(RequestBody.create(mediaType, strNewBody)).build();
            return chain.proceed(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
