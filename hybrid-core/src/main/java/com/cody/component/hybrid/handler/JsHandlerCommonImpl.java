/*
 * ************************************************************
 * 文件：JsHandlerCommonImpl.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月11日 11:35:16
 * 上次修改时间：2019年01月16日 19:01:56
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.handler;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.webkit.WebView;

import com.cody.component.hybrid.activity.HtmlActivity;
import com.cody.component.hybrid.core.JsCallback;
import com.cody.component.hybrid.core.JsHandler;
import com.cody.component.util.NotProguard;
import com.google.gson.JsonObject;

/**
 * Created by Cody.yi on 16/4/19.
 * Js handler 实现类
 */
@NotProguard
public final class JsHandlerCommonImpl implements JsHandler {

    public static void getAppName(WebView webView, JsonObject params, JsCallback callback) {
        String appName;
        try {
            PackageManager packageManager = webView.getContext().getApplicationContext().getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(webView.getContext()
                    .getApplicationContext().getPackageName(), 0);
            appName = packageManager.getApplicationLabel(applicationInfo).toString();
        } catch (Exception e) {
            e.printStackTrace();
            appName = "";
        }
        JsonObject data = new JsonObject();
        data.addProperty("result", appName);
        callback.success(data);
    }

    public static void getOsSdk(WebView webView, JsonObject params, JsCallback callback) {
        JsonObject data = new JsonObject();
        data.addProperty("os_sdk", Build.VERSION.SDK_INT);
        callback.success(data);
    }

    public static void finish(WebView webView, JsonObject params, JsCallback callback) {
        if (webView.getContext() instanceof Activity) {
            ((Activity) webView.getContext()).finish();
        }
    }

    /**
     * 开新页
     */
    public static void openNewPage(final WebView webView, final JsonObject params, JsCallback callback) {
        if (params != null) {
            String url = "";
            if (params.has("url")) {
                url = params.getAsJsonPrimitive("url").getAsString();
            }
            HtmlActivity.startHtml(null, url);
        }
    }
}
