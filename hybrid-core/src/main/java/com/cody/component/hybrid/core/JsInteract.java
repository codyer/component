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
import android.webkit.WebView;

import com.cody.component.hybrid.JsBridge;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.lang.reflect.Method;

/**
 * Created by Cody.yi on 17/4/12.
 * JsInteract
 */
public class JsInteract {
    private static final String JS_BRIDGE_PROTOCOL_SCHEMA = "js_bridge";
    private String mHandlerName;
    private String mMethodName;
    private String mPort;
    private JsonObject mParams;
    private JsCallback mJsCallback;

    private JsInteract() {
    }

    public static JsInteract newInstance() {
        return new JsInteract();
    }

    /**
     * @param webView WebView
     * @param message js_bridge://class:port/method?params
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
                mParams = new JsonParser().parse(query).getAsJsonObject();
            }
        } catch (JsonParseException
                | StringIndexOutOfBoundsException
                | IllegalStateException e) {
            e.printStackTrace();
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

        if (method == null) {
            String msg = "Method (" + mMethodName + ") in this class (" + mHandlerName + ") not found!";
            mJsCallback.failure(msg);
            return;
        }

        Object[] args = new Object[3];
        args[0] = webView;
        args[1] = mParams;
        args[2] = mJsCallback;

        try {
            method.invoke(null, args);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getCause().toString();
            mJsCallback.failure(msg);
        }
    }
}
