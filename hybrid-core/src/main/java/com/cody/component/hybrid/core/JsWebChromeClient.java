/*
 * ************************************************************
 * 文件：JsWebChromeClient.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:44:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.core;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.cody.component.hybrid.HtmlViewModel;
import com.cody.component.hybrid.JsBridge;
import com.cody.component.hybrid.R;
import com.cody.component.util.ActivityUtil;
import com.cody.component.util.LogUtil;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Cody.yi on 17/4/12.
 * JsWebChromeClient
 */
public class JsWebChromeClient extends WebChromeClient {
    private HtmlViewModel mHtmlViewModel;
    private View mCustomView;
    private WebView mWebView;
    private OpenFileChooserCallBack mOpenFileChooserCallBack;

    public JsWebChromeClient(WebView webView, HtmlViewModel htmlViewModel, OpenFileChooserCallBack chooserCallBack) {
        mWebView = webView;
        mHtmlViewModel = htmlViewModel;
        mOpenFileChooserCallBack = chooserCallBack;
    }

    @Override
    public final boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult
            result) {
        result.confirm();
        if ((UrlUtil.isInnerLink(url)) && JsBridge.callNative(view, message)) {
            LogUtil.d(message);
            return true;
        }
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public void onReceivedTitle(final WebView view, final String title) {
        super.onReceivedTitle(view, title);
        mHtmlViewModel.getFriendlyViewData().getHeader().setValue(title);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        // 增加Javascript异常监控
        CrashReport.setJavascriptMonitor(view, true);
        super.onProgressChanged(view, newProgress);
        mHtmlViewModel.getFriendlyViewData().setProgress(newProgress);
    }

    @Override
    public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
        super.onShowCustomView(view, requestedOrientation, callback);
        onShowCustomView(view, callback);
        // 设置全屏的相关属性,获取当前的屏幕状态,然后设置全屏
        Activity activity = ActivityUtil.getCurrentActivity();
        if (activity == null) return;
        activity.setRequestedOrientation(requestedOrientation);
    }

    // 播放网络视频时全屏会被调用的方法
    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        super.onShowCustomView(view, callback);
        // if a view already exists then immediately terminate the new one
        if (mCustomView != null) {
            callback.onCustomViewHidden();
            return;
        }
        ViewGroup parent = (ViewGroup) mWebView.getParent();
        parent.removeView(mWebView);
        // 设置背景色为黑色
        view.setBackgroundColor(view.getResources().getColor(R.color.uiColorBlack));
        parent.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mCustomView = view;
        setFullScreen();
    }

    // 视频播放退出全屏会被调用的
    @Override
    public void onHideCustomView() {
        super.onHideCustomView();
        if (mCustomView != null) {
            ViewGroup parent = (ViewGroup) mCustomView.getParent();
            // 设置背景色为黑色
            parent.removeView(mCustomView);
            parent.addView(mWebView);
            mCustomView = null;
            quitFullScreen();
        }
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "");
    }

    //For Android 3.0 - 4.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        if (mOpenFileChooserCallBack != null) {
            mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);
        }
    }

    // For Android 4.0 - 5.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        openFileChooser(uploadMsg, acceptType);
    }

    // For Android > 5.0
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//        super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        if (mOpenFileChooserCallBack != null) {
            mOpenFileChooserCallBack.showFileChooserCallBack(filePathCallback, fileChooserParams);
        }
        return true;
    }

    public interface OpenFileChooserCallBack {

        void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);

        void showFileChooserCallBack(ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams);
    }

    /**
     * 设置全屏
     */
    private void setFullScreen() {
        // 设置全屏的相关属性,获取当前的屏幕状态,然后设置全屏
        Activity activity = ActivityUtil.getCurrentActivity();
        if (activity == null) return;
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 退出全屏
     */
    private void quitFullScreen() {
        // 声明当前屏幕状态的参数并获取
        Activity activity = ActivityUtil.getCurrentActivity();
        if (activity == null) return;
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}
