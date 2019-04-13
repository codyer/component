/*
 * ************************************************************
 * 文件：JsLifeCycle.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月13日 08:43:55
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.core;

import android.webkit.WebView;

/**
 * Created by cody.yi on 2018/7/11.
 * js生命周期
 */
public class JsLifeCycle {
    interface callback {
        String onStart = "javascript:try{if(window.onStart){onStart()}}catch(e){};";
        String onResume = "javascript:try{if(window.onResume){onResume()}}catch(e){};";
        String onPause = "javascript:try{if(window.onPause){onPause()}}catch(e){};";
        String onDestroy = "javascript:try{if(window.onDestroy){onDestroy()}}catch(e){};";
    }

    public static void onStart(WebView webView) {
        if (webView == null)return;
        webView.loadUrl(callback.onStart);
    }

    public static void onResume(WebView webView) {
        if (webView == null)return;
        webView.loadUrl(callback.onResume);
    }

    public static void onPause(WebView webView) {
        if (webView == null)return;
        webView.loadUrl(callback.onPause);
    }

    public static void onDestroy(WebView webView) {
        if (webView == null)return;
        webView.loadUrl(callback.onDestroy);
        webView.stopLoading();
        webView.removeAllViews();
        webView.setTag(null);
        webView.clearHistory();
        webView.removeAllViews();
        webView.destroy();
    }
}
