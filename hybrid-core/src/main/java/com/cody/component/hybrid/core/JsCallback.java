/*
 * ************************************************************
 * 文件：JsCallback.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月11日 11:51:07
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.core;

import android.webkit.WebView;

import com.cody.component.hybrid.core.async.AsyncTaskExecutor;
import com.cody.component.lib.bean.Result;
import com.cody.component.util.LogUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.ref.WeakReference;
import java.util.Locale;

/**
 * native结果数据返回格式:
 * <p>
 * var resultJs = {
 * code: '200',//200成功，400失败
 * message: '请求超时',//失败时候的提示，成功可为空
 * data: {}//数据
 * };
 * <p>
 * Created by Cody.yi on 17/4/12.
 */
public class JsCallback {
    private static final String CALLBACK_JS_FORMAT = "javascript:JsBridge.onComplete(%s,%s);";

    private WeakReference<WebView> mWebViewWeakRef;
    private String mPort;
    private Gson mJsonUtil;

    private JsCallback(WebView webView, String port) {
        this.mWebViewWeakRef = new WeakReference<>(webView);
        this.mPort = port;
        mJsonUtil = new Gson();
    }

    public static JsCallback newInstance(WebView webView, String port) {
        return new JsCallback(webView, port);
    }

    /**
     * 返回结果给Js
     */
    private void callJs(final String callbackJs) {
        final WebView webView = mWebViewWeakRef.get();
        if (webView == null) {
            LogUtil.d("JsCallback", "The WebView related to the JsCallback has been recycled!");
        } else {
            if (AsyncTaskExecutor.isMainThread()) {
                webView.loadUrl(callbackJs);
            } else {
                AsyncTaskExecutor.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl(callbackJs);
                    }
                });
            }
        }
    }

    /**
     * 直接返回失败消息，不需要包含data部分
     */
    public void failure(String message) {
        Result<Object> result = new Result<>(JsCode.FAILURE, message, null);
        callJs(getCallBackUrl(result));
        LogUtil.d("JsCallback", message);
    }

    /**
     * 直接返回成功消息，不需要包含data部分
     */
    public void success(String message) {
        Result<Object> result = new Result<>(JsCode.SUCCESS, message, null);
        callJs(getCallBackUrl(result));
    }

    /**
     * 直接返回成功消息，需要包含data部分
     */
    public void success(String message, JsonObject data) {
        Result<JsonObject> result = new Result<>(JsCode.SUCCESS, message, data);
        callJs(getCallBackUrl(result));
    }

    /**
     * 直接返回成功消息，需要包含data部分
     */
    public void success(JsonObject data) {
        Result<JsonObject> result = new Result<>(JsCode.SUCCESS, null, data);
        callJs(getCallBackUrl(result));
    }

    /**
     * 直接返回成功消息，需要包含data部分
     */
    public void callback(JsonObject result) {
        callJs(getCallBackUrl(result));
    }

    private String getCallBackUrl(Result result) {
        return String.format(Locale.getDefault(), CALLBACK_JS_FORMAT, mPort, mJsonUtil.toJson(result));
    }

    private String getCallBackUrl(JsonObject result) {
        return String.format(Locale.getDefault(), CALLBACK_JS_FORMAT, mPort, result);
    }
}
