/*
 * ************************************************************
 * 文件：HttpInterceptor.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.interceptor;


import com.cody.http.lib.exception.ConnectionException;
import com.cody.http.lib.exception.ResultInvalidException;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * Created by xu.yi. on 2019/4/6.
 *
 */
public class HttpInterceptor implements Interceptor {

    public HttpInterceptor() {
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse;
        try {
            originalResponse = chain.proceed(request);
        } catch (Exception e) {
            throw new ConnectionException();
        }
        if (originalResponse.code() != 200) {
            throw new ResultInvalidException();
        }
        String byteString = "";
        if (originalResponse.body() != null) {
            BufferedSource source = originalResponse.body().source();
            source.request(Integer.MAX_VALUE);
            byteString = source.buffer().snapshot().utf8();
        }
        ResponseBody responseBody = ResponseBody.create(null, byteString);
        return originalResponse.newBuilder().body(responseBody).build();
    }

}