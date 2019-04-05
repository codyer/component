/*
 * ************************************************************
 * 文件：BaseRequest.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月05日 23:42:57
 * 上次修改时间：2019年04月05日 23:19:29
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.http.request;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.cody.http.core.http.listener.IHeaderListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.Map;

import androidx.annotation.NonNull;

/**
 * Created by cody.yi on 2016/7/20.
 * 请求封装
 * <p>
 * 支持json格式参数和params格式参数(默认)
 * 支持返回头部，其实头部也可以通过#Request.getCacheEntry().responseHeaders
 */
class BaseRequest<T> extends Request<T> {
    /**
     * Default charset for JSON request.
     */
    private static final String PROTOCOL_CHARSET = "utf-8";

    /**
     * Content type for json request.
     */
    private static final String PROTOCOL_JSON_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    /**
     * Content type for form request.
     */
    private static final String PROTOCOL_FORM_CONTENT_TYPE =
            String.format("application/x-www-form-urlencoded; charset=%s", PROTOCOL_CHARSET);

    private static Map<String, String> mHeaders;//所有的请求用一个头部，只有在登录之后设置，否则没有头部

    private final Gson mParseTool = new Gson();
    /**
     * json格式的请求参数
     */
    private final String mRequestBody;
    /**
     * params格式的请求参数
     */
    private final Map<String, String> mParams;
    /**
     * response的返回监听器
     */
    private final WeakReference<Response.Listener<T>> mListener;
    /**
     * response header的返回监听器
     */
    private final WeakReference<IHeaderListener> mHeaderListener;
    /**
     * 是json参数还是params参数
     * json: true
     * params: false default
     */
    private Boolean mIsJsonParams = false;
    /**
     * response的bean类型
     */
    private Type mType;

    /**
     * 包含全部参数的构造函数
     *
     * @param method        方法
     * @param url           地址
     * @param requestParams 参数
     * @param requestBody   json格式参数
     * @param type          响应数据类型
     * @param listener      成功监听器
     * @param errorListener 错误监听器
     */
    BaseRequest(int method,
                @NonNull String url,
                @NonNull Map<String, String> headers,
                Map<String, String> requestParams,
                JsonObject requestBody,
                Type type,
                @NonNull Response.Listener<T> listener,
                Response.ErrorListener errorListener,
                IHeaderListener headerListener) {
        super(method, url, errorListener);
        mHeaderListener = new WeakReference<>(headerListener);
        mListener = new WeakReference<>(listener);
        mHeaders = headers;
        mParams = requestParams;
        mRequestBody = requestBody == null ? null : requestBody.toString();
        mType = type;
        mIsJsonParams = mRequestBody != null;
        setRetryPolicy(new DefaultRetryPolicy(15 * 1000, 0, 1f));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders == null ? super.getHeaders() : mHeaders;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams == null ? super.getParams() : mParams;
    }

    /**
     * Returns the content type of the POST or PUT body.
     */
    @Override
    public String getBodyContentType() {
        return mIsJsonParams ? PROTOCOL_JSON_CONTENT_TYPE : PROTOCOL_FORM_CONTENT_TYPE;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return mIsJsonParams ? mRequestBody.getBytes(PROTOCOL_CHARSET) : super.getBody();
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
            return null;
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            if (mHeaderListener != null && mHeaderListener.get() != null) {
                mHeaderListener.get().onHeaderResponse(response.headers);
            }
            jsonString = fixBugForDataMap(jsonString);
            T result = mParseTool.fromJson(jsonString, mType);
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    /**
     * FIXME bug from server
     */
    private String fixBugForDataMap(@NonNull String jsonString) {
        if (jsonString.contains("\"dataMap\":\"\"")) {
            jsonString = jsonString.replace("\"dataMap\":\"\"", "\"dataMap\":{}");
        }
        return jsonString;
    }

    @Override
    protected void deliverResponse(T response) {
        if (mListener != null && mListener.get() != null) {
            mListener.get().onResponse(response);
        }
    }
}