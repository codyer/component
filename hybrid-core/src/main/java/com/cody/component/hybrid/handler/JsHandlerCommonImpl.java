/*
 * ************************************************************
 * 文件：JsHandlerCommonImpl.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:44:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.handler;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;

import com.alibaba.fastjson.JSONObject;
import com.cody.component.hybrid.activity.HtmlActivity;
import com.cody.component.hybrid.core.JsCallback;
import com.cody.component.hybrid.core.JsHandler;
import com.cody.component.util.LogUtil;
import com.cody.component.util.NotProguard;

/**
 * Created by Cody.yi on 16/4/19.
 * Js handler 实现类
 */
@NotProguard
public final class JsHandlerCommonImpl implements JsHandler {

    public static void getAppName(WebView webView, JSONObject params, JsCallback callback) {
        String appName;
        try {
            PackageManager packageManager = webView.getContext().getApplicationContext().getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(webView.getContext()
                    .getApplicationContext().getPackageName(), 0);
            appName = packageManager.getApplicationLabel(applicationInfo).toString();
        } catch (Exception e) {
            LogUtil.e(Log.getStackTraceString(e));
            appName = "";
        }
        JSONObject data = new JSONObject();
        data.put("result", appName);
        callback.success(data);
    }

    public static void getOsSdk(WebView webView, JSONObject params, JsCallback callback) {
        JSONObject data = new JSONObject();
        data.put("os_sdk", Build.VERSION.SDK_INT);
        callback.success(data);
    }

    public static void finish(WebView webView, JSONObject params, JsCallback callback) {
        if (webView.getContext() instanceof Activity) {
            ((Activity) webView.getContext()).finish();
        }
    }

    /**
     * 开新页
     */
    public static void openNewPage(final WebView webView, final JSONObject params, JsCallback callback) {
        if (params != null) {
            String url = "";
            if (params.containsKey("url")) {
                url = params.getString("url");
            }
            HtmlActivity.startHtml(null, url);
        }
    }
}
