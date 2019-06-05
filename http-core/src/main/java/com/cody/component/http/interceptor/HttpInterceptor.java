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

package com.cody.component.http.interceptor;


import com.cody.component.http.GZIPUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by xu.yi. on 2019/4/6.
 */
public class HttpInterceptor implements Interceptor {

    public HttpInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
//        boolean checked = true;
        if (response.code() == 200) {
            //这里是网络拦截器，可以做错误处理
            MediaType mediaType = response.body().contentType();
            //当调用此方法java.lang.IllegalStateException: closed，原因为OkHttp请求回调中response.body().string()只能有效调用一次
            //String content = response.body().string();
            byte[] data = response.body().bytes();
            if (GZIPUtil.isGzip(response.headers())) {
                //请求头显示有gzip，需要解压
                data = GZIPUtil.uncompress(data);
            }
//            //获取签名
//            String sdkSign = response.header("sdkSign");
//            try {
//                //效验签名
//                checked = RSAUtils.verify(data, GlobalField.APP_SERVICE_KEY(), sdkSign);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (!checked) {
//                return null;
//            } else {
            //创建一个新的responseBody，返回进行处理
            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, data))
                    .build();
//            }
        } else {
            return response;
        }

    }
}