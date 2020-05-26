/*
 * ************************************************************
 * 文件：JsInteract.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:44:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.core;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cody.component.hybrid.JsBridge;
import com.cody.component.util.LogUtil;

import java.lang.reflect.Method;

/**
 * Created by Cody.yi on 17/4/12.
 * JsInteract
 */
public class JsInteract {
    private static final String JS_BRIDGE_PROTOCOL_SCHEMA = "js_bridge";
    private static final String DEFAULT_METHOD = "defaultMethod";
    private String mHandlerName;
    private String mMethodName;
    private String mPort;
    private JSONObject mParams;
    private JsCallback mJsCallback;

    private JsInteract() {
    }

    public static JsInteract newInstance() {
        return new JsInteract();
    }

    /**
     * @param webView WebView
     * @param message js_bridge://class:port/method?params
     * @return 结果
     */
    public boolean callNative(WebView webView, String message) {
        if (webView == null || TextUtils.isEmpty(message))
            return false;
        boolean result = parseMessage(webView, message);
        invokeNativeMethod(webView);
        return result;
    }

    private boolean parseMessage(WebView webView, String message) {
        if (!message.startsWith(JS_BRIDGE_PROTOCOL_SCHEMA))
            return false;
        Uri uri = Uri.parse(message);
        mHandlerName = uri.getHost();
        if (TextUtils.isEmpty(mHandlerName)) return true;

        String path = uri.getPath();
        if (!TextUtils.isEmpty(path)) {
            mMethodName = path.replace("/", "");
        } else {
            mMethodName = "";
        }

        String authority = uri.getAuthority();
        if (TextUtils.isEmpty(authority)) return true;
        mPort = authority.substring(mHandlerName.length() + 1);

        mJsCallback = JsBridge.getJsCallback(webView, mPort);
        try {
            if (message.contains("?")) {
                int beginIndex = message.indexOf("?");
                String query = message.substring(++beginIndex);
                mParams = JSON.parseObject(query);
            }
        } catch (JSONException
                | StringIndexOutOfBoundsException
                | IllegalStateException e) {

            LogUtil.e(Log.getStackTraceString(e));
            String msg = "";
            if (e.getCause() != null) {
                msg = "Query parameter is invalid json :" + e.getCause().toString();
            }
            mJsCallback.failure(msg);
        }
        return true;
    }

    private void invokeNativeMethod(WebView webView) {
        Method method = JsBridge.findMethod(mHandlerName, mMethodName);
        if (method != null) {
            Object[] args = new Object[3];
            args[0] = webView;
            args[1] = mParams;
            args[2] = mJsCallback;

            try {
                method.invoke(null, args);
            } catch (Exception e) {
                LogUtil.e(Log.getStackTraceString(e));
                String msg = e.getCause().toString();
                mJsCallback.failure(msg);
            }
            return;
        } else {
            Method defaultMethod = JsBridge.findMethod(mHandlerName, DEFAULT_METHOD);
            if (defaultMethod != null) {
                Object[] args = new Object[4];
                args[0] = webView;
                args[1] = mMethodName;
                args[2] = mParams;
                args[3] = mJsCallback;

                try {
                    defaultMethod.invoke(null, args);
                } catch (Exception e) {
                    LogUtil.e(Log.getStackTraceString(e));
                    String msg = e.getCause().toString();
                    mJsCallback.failure(msg);
                }
                return;
            }
        }
        String msg = "Method (" + mMethodName + ") in this class (" + mHandlerName + ") not found!";
        mJsCallback.failure(msg);
    }
}
