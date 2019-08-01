/*
 * ************************************************************
 * 文件：JsCallback.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:44:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.core;

import android.webkit.WebView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cody.component.hybrid.core.async.AsyncTaskExecutor;
import com.cody.component.lib.bean.Result;
import com.cody.component.util.LogUtil;

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

    private JsCallback(WebView webView, String port) {
        this.mWebViewWeakRef = new WeakReference<>(webView);
        this.mPort = port;
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
    public void failure() {
        Result<Object> result = new Result<>(JsCode.FAILURE, "失败", null);
        callJs(getCallBackUrl(result));
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
     * 直接返回成功消息，不需要包含data部分
     */
    public void success() {
        Result<Object> result = new Result<>(JsCode.SUCCESS, "成功", null);
        callJs(getCallBackUrl(result));
    }

    /**
     * 直接返回成功消息，需要包含data部分
     */
    public void success(String message, JSONObject data) {
        Result<JSONObject> result = new Result<>(JsCode.SUCCESS, message, data);
        callJs(getCallBackUrl(result));
    }

    /**
     * 直接返回成功消息，需要包含data部分
     */
    public void success(JSONObject data) {
        Result<JSONObject> result = new Result<>(JsCode.SUCCESS, null, data);
        callJs(getCallBackUrl(result));
    }

    /**
     * 直接返回成功消息，需要包含data部分
     */
    public void callback(JSONObject result) {
        callJs(getCallBackUrl(result));
    }

    private String getCallBackUrl(Result result) {
        return String.format(Locale.getDefault(), CALLBACK_JS_FORMAT, mPort, JSON.toJSON(result));
    }

    private String getCallBackUrl(JSONObject result) {
        return String.format(Locale.getDefault(), CALLBACK_JS_FORMAT, mPort, result);
    }
}
