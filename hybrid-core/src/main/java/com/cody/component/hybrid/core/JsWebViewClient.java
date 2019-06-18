/*
 * ************************************************************
 * 文件：JsWebViewClient.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:44:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.core;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

import com.cody.component.hybrid.HtmlViewModel;


/**
 * Created by Cody.yi on 17/4/12.
 * JsWebViewClient
 */
public class JsWebViewClient extends WebViewClient {
    private HtmlViewModel mHtmlViewModel;

    public JsWebViewClient(HtmlViewModel htmlViewModel) {
        mHtmlViewModel = htmlViewModel;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return mHtmlViewModel.shouldOverrideUrl(url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        mHtmlViewModel.onPageStarted(url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        mHtmlViewModel.onPageFinished(url);
        JsLifeCycle.onStart(view);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
        super.onPageCommitVisible(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return super.shouldInterceptRequest(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        super.onTooManyRedirects(view, cancelMsg, continueMsg);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        mHtmlViewModel.onPageFailure(description + ":\n" + failingUrl);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        super.onFormResubmission(view, dontResend, resend);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        mHtmlViewModel.onPageFailure(error.toString() + ":\n" + view.getUrl());
    }

    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        super.onReceivedClientCertRequest(view, request);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return super.shouldOverrideKeyEvent(view, event);
    }

    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        super.onUnhandledKeyEvent(view, event);
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        super.onReceivedLoginRequest(view, realm, account, args);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return mHtmlViewModel.shouldOverrideUrl(request.getUrl().toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        mHtmlViewModel.onPageFailure(error.getDescription().toString() + ":\n" + request.getUrl());
    }
}
