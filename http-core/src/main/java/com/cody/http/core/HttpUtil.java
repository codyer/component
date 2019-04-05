/*
 * ************************************************************
 * 文件：HttpUtil.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月05日 23:12:21
 * 上次修改时间：2019年01月16日 19:06:08
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cody.http.core.http.HttpCode;
import com.cody.http.core.http.request.HttpRequestFactory;
import com.cody.http.core.http.listener.IHeaderListener;
import com.cody.http.core.http.listener.OnUploadListener;
import com.cody.http.core.http.bean.Result;
import com.cody.http.core.http.bean.SimpleBean;
import com.cody.http.core.http.exception.InvalidParameterException;
import com.cody.http.core.http.util.CommonUtil;
import com.cody.http.core.http.util.NetworkUtil;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

import static com.cody.http.core.http.request.HttpRequestFactory.getContext;

/**
 * Created by cody.yi on 2016/7/18.
 * <p>
 * 管理所有数据，对应用提供一致的数据接口，隐藏数据来源
 * 内存、SD卡、网络
 * 网络请求工具
 */
@SuppressWarnings("unused")
public class HttpUtil {
    private HttpUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("HttpUtil cannot be instantiated");
    }

    /**
     * 无请求参数调用方法
     *
     * @param tag      请求的tag 用于标识该请求 可据此取消请求
     * @param method   请求的方法  {@link Method}
     * @param url      请求的URL
     * @param clazz    请求返回解析的bean类
     * @param callback 请求回调
     */
    public static <T> void getData(Object tag,
                                   int method,
                                   String url,
                                   Map<String, String> token,
                                   Class<T> clazz,
                                   final Callback<T> callback) {
        HttpUtil.getData(tag, method, url, token, null, null, clazz, null, callback);
    }

    /**
     * 有请求参数调用方法 {content-Type:application/x-www-form-urlencoded}
     *
     * @param tag      请求的tag 用于标识该请求 可据此取消请求
     * @param method   请求的方法  {@link Method }
     * @param url      请求的URL
     * @param params   请求的参数
     * @param clazz    请求返回解析的bean类
     * @param callback 请求回调
     */
    public static <T> void getData(Object tag,
                                   int method,
                                   String url,
                                   Map<String, String> token,
                                   Map<String, String> params,
                                   Class<T> clazz,
                                   final Callback<T> callback) {
        HttpUtil.getData(tag, method, url, token, params, null, clazz, null, callback);
    }

    /**
     * 有请求参数调用方法 {content-Type:application/x-www-form-urlencoded}
     *
     * @param tag            请求的tag 用于标识该请求 可据此取消请求
     * @param method         请求的方法  {@link Method }
     * @param url            请求的URL
     * @param params         请求的参数
     * @param clazz          请求返回解析的bean类
     * @param headerListener 返回头监听，用于登录接口
     * @param callback       请求回调
     */
    public static <T> void login(Object tag,
                                 int method,
                                 String url,
                                 Map<String, String> token,
                                 Map<String, String> params,
                                 Class<T> clazz,
                                 IHeaderListener headerListener,
                                 final Callback<T> callback) {
        HttpUtil.getData(tag, method, url, token, params, null, clazz, headerListener, callback);
    }

    /**
     * 有请求参数调用方法 {content-Type:application/json}
     *
     * @param tag        请求的tag 用于标识该请求 可据此取消请求
     * @param method     请求的方法  {@link Method }
     * @param url        请求的URL
     * @param jsonParams 请求的参数
     * @param clazz      请求返回解析的bean类
     * @param callback   请求回调
     */
    public static <T> void getData(Object tag,
                                   int method,
                                   String url,
                                   Map<String, String> token,
                                   JsonObject jsonParams,
                                   Class<T> clazz,
                                   final Callback<T> callback) {
        HttpUtil.getData(tag, method, url, token, null, jsonParams, clazz, null, callback);
    }

    /**
     * 获取返回对象中的data对象
     *
     * @param tag      当前页面的tag，用于取消request
     * @param url      接口Api
     * @param params   参数
     *                 //     * @param clazz    类型
     * @param callback 数据返回的回调
     * @param <T>      泛型
     */
    public static <T> void getData(Object tag,
                                   int method,
                                   String url,
                                   Map<String, String> token,
                                   Map<String, String> params,
                                   JsonObject jsonParams,
                                   Class<T> clazz,
                                   IHeaderListener headerListener,
                                   final Callback<T> callback) {
        Type type = CommonUtil.getType(Result.class, clazz);
        doNormalRequest(tag, method, url, token, params, jsonParams, type, headerListener, callback);
    }

    /**
     * 无请求参数调用方法 取到的list<T>
     *
     * @param tag      请求的tag 用于标识该请求 可据此取消请求
     * @param method   请求的方法  {@link Method }
     * @param url      请求的URL
     * @param clazz    请求返回解析的bean类
     * @param callback 请求回调
     */
    public static <T> void getListData(Object tag,
                                       int method,
                                       String url,
                                       Map<String, String> token,
                                       Class<T> clazz,
                                       final Callback<List<T>> callback) {
        HttpUtil.getListData(tag, method, url, token, null, null, clazz, callback);
    }

    /**
     * 有请求参数调用方法 取到的list<T> {content-Type:application/x-www-form-urlencoded}
     *
     * @param tag      请求的tag 用于标识该请求 可据此取消请求
     * @param method   请求的方法  {@link Method }
     * @param url      请求的URL
     * @param params   请求的参数
     * @param clazz    请求返回解析的bean类
     * @param callback 请求回调
     */
    public static <T> void getListData(Object tag,
                                       int method,
                                       String url,
                                       Map<String, String> token,
                                       Map<String, String> params,
                                       Class<T> clazz,
                                       final Callback<List<T>> callback) {
        HttpUtil.getListData(tag, method, url, token, params, null, clazz, callback);
    }

    /**
     * 有请求参数调用方法 取到的list<T> {content-Type:application/json}
     *
     * @param tag        请求的tag 用于标识该请求 可据此取消请求
     * @param method     请求的方法  {@link Method }
     * @param url        请求的URL
     * @param jsonParams 请求的参数
     * @param clazz      请求返回解析的bean类
     * @param callback   请求回调
     */
    public static <T> void getListData(Object tag,
                                       int method,
                                       String url,
                                       Map<String, String> token,
                                       JsonObject jsonParams,
                                       Class<T> clazz,
                                       final Callback<List<T>> callback) {
        HttpUtil.getListData(tag, method, url, token, null, jsonParams, clazz, callback);
    }

    /**
     * 有请求参数调用方法 取到的list<T> {content-Type:application/json}
     *
     * @param tag        请求的tag 用于标识该请求 可据此取消请求
     * @param method     请求的方法  {@link Method }
     * @param url        请求的URL
     * @param jsonParams 请求的参数
     * @param clazz      请求返回解析的bean类
     * @param callback   请求回调
     */
    public static <T> void getListData(Object tag,
                                       int method,
                                       String url,
                                       Map<String, String> token,
                                       Map<String, String> params,
                                       JsonObject jsonParams,
                                       Class<T> clazz,
                                       final Callback<List<T>> callback) {
        HttpUtil.getListData(tag, method, url, token, params, jsonParams, clazz, null, callback);
    }

    /**
     * 获取返回对象中的data对象
     *
     * @param tag      当前页面的tag，用于取消request
     * @param url      接口Api
     * @param params   参数
     *                 //     * @param clazz    类型
     * @param callback 数据返回的回调
     * @param <T>      泛型
     */
    public static <T> void getListData(Object tag,
                                       int method,
                                       String url,
                                       Map<String, String> token,
                                       Map<String, String> params,
                                       JsonObject jsonParams,
                                       Class<T> clazz,
                                       IHeaderListener headerListener,
                                       final Callback<List<T>> callback) {
        //解析Type
        Type type = CommonUtil.getType(Result.class, CommonUtil.getType(List.class, clazz));
        doNormalRequest(tag, method, url, token, params, jsonParams, type, headerListener, callback);
    }

    /**
     * 无请求参数调用方法 默认bean是SimpleBean
     *
     * @param tag      请求的tag 用于标识该请求 可据此取消请求
     * @param method   请求的方法  {@link Method }
     * @param url      请求的URL
     * @param callback 请求回调
     */
    public static void getResult(Object tag,
                                 int method,
                                 String url,
                                 Map<String, String> token,
                                 final Callback<SimpleBean> callback) {
        HttpUtil.getResult(tag, method, url, token, null, null, null, callback);
    }

    /**
     * 有请求参数调用方法 默认bean是SimpleBean {content-Type:application/x-www-form-urlencoded}
     *
     * @param tag      请求的tag 用于标识该请求 可据此取消请求
     * @param method   请求的方法  {@link Method }
     * @param url      请求的URL
     * @param params   请求的参数
     * @param callback 请求回调
     */
    public static void getResult(Object tag,
                                 int method,
                                 String url,
                                 Map<String, String> token,
                                 Map<String, String> params,
                                 final Callback<SimpleBean> callback) {
        HttpUtil.getResult(tag, method, url, token, params, null, null, callback);
    }

    /**
     * 有请求参数调用方法 默认bean是SimpleBean {content-Type:application/json}
     *
     * @param tag        请求的tag 用于标识该请求 可据此取消请求
     * @param method     请求的方法  {@link Method }
     * @param url        请求的URL
     * @param jsonParams 请求的参数
     * @param callback   请求回调
     */
    public static void getResult(Object tag,
                                 int method,
                                 String url,
                                 Map<String, String> token,
                                 JsonObject jsonParams,
                                 final Callback<SimpleBean> callback) {
        HttpUtil.getResult(tag, method, url, token, null, jsonParams, null, callback);
    }

    /**
     * 获取整个返回对象，会进行code判断
     *
     * @param tag        当前页面的tag，用于取消request
     * @param url        接口Api
     * @param params     参数
     * @param jsonParams json格式参数
     * @param callback   数据返回的回调
     */
    public static void getResult(Object tag,
                                 int method,
                                 String url,
                                 Map<String, String> token,
                                 Map<String, String> params,
                                 JsonObject jsonParams,
                                 IHeaderListener headerListener,
                                 final Callback<SimpleBean> callback) {
        doSimpleBeanRequest(tag, method, url, token, params, jsonParams, headerListener, callback);
    }

    /**
     * 获取整个返回对象，不进行任何判断
     *
     * @param tag        当前页面的tag，用于取消request
     * @param url        接口Api
     * @param params     参数
     * @param jsonParams json格式参数
     * @param callback   数据返回的回调
     */
    public static <T> void getOriginalResult(Object tag,
                                             int method,
                                             String url,
                                             Map<String, String> token,
                                             Map<String, String> params,
                                             JsonObject jsonParams,
                                             @NonNull Class<T> clazz,
                                             IHeaderListener headerListener,
                                             @NonNull final Callback<T> callback) {
        Type type = CommonUtil.getType(clazz);
        doOriginalBeanRequest(tag, method, url, token, params, jsonParams, type, headerListener, callback);
    }

    /**
     * 使用系统的下载管理器下载文件
     *
     * @param tag         tag
     * @param url         文件的下载地址
     * @param description 下载过程显示的描述
     * @param title       下载上显示的标题
     * @param path        下载后存放的路径
     * @param fileName    下载后文件的名字，需要带文件格式后缀,如：filename.txt
     */
    public static long getFile(Object tag,
                               @NonNull String url,
                               @NonNull String description,
                               @NonNull String title,
                               String path,
                               @NonNull String fileName) {
        return HttpRequestFactory.getInstance().createDownloadRequest(tag, url, description, title, path, fileName);
    }

    /**
     * 获取图片
     */
    public static void getImage(Object tag,
                                String url,
                                int maxWidth,
                                int maxHeight,
                                ImageView.ScaleType scaleType,
                                Bitmap.Config decodeConfig,
                                final Callback<Bitmap> callback) {
        doGetImage(tag, url, maxWidth, maxHeight, scaleType, decodeConfig, callback);
    }

    /**
     * 上传图片 base64
     */
    public static void uploadImageBase64(Object tag,
                                         String url,
                                         String imageName,
                                         Bitmap bitmap,
                                         Map<String, String> token,
                                         Map<String, String> params,
                                         final Callback<SimpleBean> callback) {
        doUploadImageBase64(tag, url, imageName, bitmap, token, params, callback);
    }

    /**
     * 上传图片 表单 multipart  单张图片上传
     */
    public static <T> void uploadImageMultipart(Object tag,
                                                String url,
                                                String name,
                                                String bitmapPath,
                                                Map<String, String> token,
                                                Map<String, String> params,
                                                Class<T> clazz,
                                                final Callback<T> callback) {
        List<String> bitmapList = new ArrayList<>();
        bitmapList.add(bitmapPath);
        Type type = CommonUtil.getType(Result.class, clazz);
        doUploadImageMultipart(tag, url, name, bitmapList, token, params, type, callback);
    }

    /**
     * 上传图片 表单 multipart  多张图片上传，成功后返回图片地址列表
     */
    public static <T> void uploadImagesWithUrlsMultipart(Object tag,
                                                         String url,
                                                         String name,
                                                         List<String> bitmapList,
                                                         Map<String, String> token,
                                                         Map<String, String> params,
                                                         Class<T> clazz,
                                                         final Callback<T> callback) {
//        Type type = CommonUtil.getType(Result.class, CommonUtil.getType(List.class, clazz));
        Type type = CommonUtil.getType(Result.class, clazz);
        doUploadImageMultipart(tag, url, name, bitmapList, token, params, type, callback);
    }

    /**
     * 上传图片 表单 multipart 多张图片上传，成功后返回其他信息
     */
    public static <T> void uploadImagesMultipart(Object tag,
                                                 String url,
                                                 String name,
                                                 List<String> bitmaps,
                                                 Map<String, String> token,
                                                 Map<String, String> params,
                                                 Class<T> clazz,
                                                 final Callback<T> callback) {
        Type type = CommonUtil.getType(Result.class, clazz);
        doUploadImageMultipart(tag, url, name, bitmaps, token, params, type, callback);
    }

    /**
     * 获取图片
     */
    private static void doGetImage(Object tag,
                                   String url,
                                   int maxWidth,
                                   int maxHeight,
                                   ImageView.ScaleType scaleType,
                                   Bitmap.Config decodeConfig,
                                   final Callback<Bitmap> callback) {
        //检查参数
        if (checkParameters(tag, url, callback)) {
            return;
        }

        ResponseListener<Bitmap> listener = new ResponseListener<>(callback);

        HttpRequestFactory.getInstance().createImageRequest(tag,
                url,
                maxWidth,
                maxHeight,
                scaleType,
                decodeConfig,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        callback.onSuccess(bitmap);
                    }
                },
                listener);
    }

    /**
     * 上传图片 表单 multipart  多张图片上传
     */
    private static <T> void doUploadImageMultipart(Object tag,
                                                   String url,
                                                   String name,
                                                   List<String> bitmapList,
                                                   Map<String, String> token,
                                                   Map<String, String> params,
                                                   Type type,
                                                   final Callback<T> callback) {
        //检查参数
        if (checkParameters(tag, url, callback)) return;
        //添加公用参数
        params = initParams(params);
        //执行请求
        if (url.contains("{")) {
            url = CommonUtil.buildRestFulPathUrl(url, params);
        }

        ResponseListener<T> listener = new ResponseListener<>(callback);

        HttpRequestFactory.getInstance().createMultipartRequest(tag,
                url,
                name,
                bitmapList,
                token,
                params,
                type,
                listener,
                listener,
                callback);
    }

    /**
     * 上传图片 base64
     */
    private static void doUploadImageBase64(Object tag,
                                            String url,
                                            String imageName,
                                            Bitmap bitmap,
                                            Map<String, String> token,
                                            Map<String, String> params,
                                            final Callback<SimpleBean> callback) {
        //检查参数
        if (checkParameters(tag, url, callback)) {
            return;
        }

        //添加公用参数
        params = initParams(params);

        //加密，解密

        if (url.contains("{")) {
            url = CommonUtil.buildRestFulPathUrl(url, params);
        }

        Type type = CommonUtil.getType(SimpleBean.class);
        SimpleResponseListener listener = new SimpleResponseListener(callback);

        HttpRequestFactory.getInstance().createUploadBase64ImageRequest(tag,
                url,
                imageName,
                bitmap,
                token,
                params,
                type,
                listener,
                listener);
    }

    /**
     * 取消请求 酌情在Activity或其他组件的onStop中调用
     */
    public static void cancel(Object tag) {
        HttpRequestFactory.getInstance().cancel(tag);
    }

    /**
     * 初始化通用参数
     */
    private static Map<String, String> initParams(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
//        params.put("deviceId", AppTools.getDeviceId());
//        params.put("sessionId", AppTools.getSessionId());
//        params.put("timeStamp", AppTools.getTimeStamp());
//        params.put("format", "JSON");
//        params.put("version", "1.0");
//        params.put("signMethod", "method");
//        params.put("appKey", "100000");

//        params.put("appVersion", AppUtil.getAppVersionName(Foundation.getContext()));
        return params;
    }

    /**
     * 检查参数
     */
    private static <T> boolean checkParameters(Object tag, String url, Callback<T> callback) {
        if (tag == null || TextUtils.isEmpty(url) || callback == null) {
            if (HttpCode.DEBUG || callback == null) {
                throw new InvalidParameterException();
            } else {
                callback.onError(new SimpleBean(HttpCode.PARAMETER_ERROR, "参数错误"));
                return true;
            }
        }
        //检查网络情况
        if (NetworkUtil.isDisConnected(getContext())) {
            callback.onError(new SimpleBean(HttpCode.NETWORK_DISCONNECTED, "网络无连接"));
            return true;
        }
        return false;
    }

    /**
     * 执行请求
     * <p/>
     * T是Result的data部分，返回data
     */
    private static <T> void doNormalRequest(Object tag,
                                            int method,
                                            @NonNull String url,
                                            Map<String, String> token,
                                            Map<String, String> params,
                                            JsonObject jsonParams,
                                            Type type,
                                            IHeaderListener headerListener,
                                            @NonNull final Callback<T> callback) {

        if (checkParameters(tag, url, callback)) {
            return;
        }

        //添加公用参数
        params = initParams(params);

        if (url.contains("{")) {
            url = CommonUtil.buildRestFulPathUrl(url, params);
        }
        //执行请求
        switch (method) {
            case Method.GET:
                //get请求重建url，拼接参数
                url = CommonUtil.buildPathUrl(url, params);
                break;
            case Method.POST:
                if (params != null) {
                }
                if (jsonParams != null) {
                }
                break;
        }

        ResponseListener<T> listener = new ResponseListener<>(callback);

        HttpRequestFactory.getInstance().createNormalRequest(tag,
                method,
                url,
                token,
                params,
                jsonParams,
                type,
                headerListener,
                listener,
                listener);
    }

    /**
     * 执行请求
     * <p/>
     * 返回T
     */
    private static void doSimpleBeanRequest(Object tag,
                                            int method,
                                            @NonNull String url,
                                            Map<String, String> token,
                                            Map<String, String> params,
                                            JsonObject jsonParams,
                                            IHeaderListener headerListener,
                                            @NonNull final Callback<SimpleBean> callback) {
        if (checkParameters(tag, url, callback)) {
            return;
        }
        //添加公用参数
        params = initParams(params);

        if (url.contains("{")) {
            url = CommonUtil.buildRestFulPathUrl(url, params);
        }

        //执行请求
        switch (method) {
            case Method.GET:
                //get请求重建url，拼接参数
                url = CommonUtil.buildPathUrl(url, params);
                break;
            case Method.POST:
                if (params != null) {
                }
                if (jsonParams != null) {
                }
                break;
        }

        Type type = CommonUtil.getType(SimpleBean.class);
        SimpleResponseListener listener = new SimpleResponseListener(callback);

        HttpRequestFactory.getInstance().createNormalRequest(tag,
                method,
                url,
                token,
                params,
                jsonParams,
                type,
                headerListener,
                listener,
                listener);
    }

    private static <T> void doOriginalBeanRequest(Object tag,
                                                  int method,
                                                  @NonNull String url,
                                                  Map<String, String> token,
                                                  Map<String, String> params,
                                                  JsonObject jsonParams,
                                                  @NonNull Type type,
                                                  IHeaderListener headerListener,
                                                  @NonNull final Callback<T> callback) {
        if (checkParameters(tag, url, callback)) {
            return;
        }
        //添加公用参数
        params = initParams(params);

        if (url.contains("{")) {
            url = CommonUtil.buildRestFulPathUrl(url, params);
        }

        //执行请求
        switch (method) {
            case Method.GET:
                //get请求重建url，拼接参数
                url = CommonUtil.buildPathUrl(url, params);
                break;
            case Method.POST:
                if (params != null) {
                }
                if (jsonParams != null) {
                }
                break;
        }

        OriginalResponseListener listener = new OriginalResponseListener<>(callback);

        HttpRequestFactory.getInstance().createNormalRequest(tag,
                method,
                url,
                token,
                params,
                jsonParams,
                type,
                headerListener,
                listener,
                listener);
    }

    private static void parseVolleyError(Callback callback, VolleyError error) {
        if (error == null) {
            callback.onError(new SimpleBean(HttpCode.REQUEST_ERROR, "VolleyError"));
        } else if (error.networkResponse != null && error.getCause() != null) {
            callback.onError(new SimpleBean(error.networkResponse.statusCode + "", error.getCause().toString()));
        } else {
            callback.onError(new SimpleBean(HttpCode.REQUEST_ERROR, error.toString()));
        }
    }

    /**
     * 调用方式
     */
    public interface Method {
        int GET = 0;
        int POST = 1;
    }

    /**
     * 上层和httpUtil回调使用的接口
     *
     * @param <T> 返回数据类型
     */
    public interface Callback<T> extends OnUploadListener {
        void onSuccess(T data);

        void onFailure(SimpleBean result);

        void onError(SimpleBean error);
    }

    private static class OriginalResponseListener<T> implements Response.Listener<T>, Response.ErrorListener {
        private Callback<T> callback;

        OriginalResponseListener(Callback<T> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(T response) {
            callback.onSuccess(response);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            parseVolleyError(callback, error);
        }

    }

    private static class ResponseListener<T> implements Response.Listener<Result<T>>, Response.ErrorListener {
        private Callback<T> callback;

        ResponseListener(Callback<T> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Result<T> response) {
            if (HttpCode.SUCCESS.equals((response).getCode())) {
                callback.onSuccess(response.getData());
            } else {
                callback.onFailure(new SimpleBean(response.getCode(), response.getMessage()));
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            parseVolleyError(callback, error);
        }
    }

    private static class SimpleResponseListener implements Response.Listener<SimpleBean>, Response.ErrorListener {
        private Callback<SimpleBean> callback;

        SimpleResponseListener(Callback<SimpleBean> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(SimpleBean response) {
            if (HttpCode.EMPTY.equals((response).getCode()) || HttpCode.SUCCESS.equals((response).getCode())) {
                callback.onSuccess(response);
            } else {
                callback.onFailure(response);
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            parseVolleyError(callback, error);
        }
    }
}
