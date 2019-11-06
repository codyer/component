/*
 * ************************************************************
 * 文件：HttpCore.java  模块：http-core  项目：component
 * 当前修改时间：2019年06月05日 13:59:03
 * 上次修改时间：2019年05月08日 13:34:35
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http;

import android.content.Context;

import com.cody.component.http.db.HttpCacheDatabase;
import com.cody.component.lib.exception.NotInitializedException;

import java.lang.ref.WeakReference;

import okhttp3.Interceptor;

/**
 * Created by xu.yi. on 2019/4/6.
 */
public class HttpCore {
    public static final String CACHE_KEY = "cache-once-time";
    private String mVersion = BuildConfig.VERSION_NAME;

    private static class InstanceHolder {
        private static final HttpCore INSTANCE = new HttpCore();
    }

    public static HttpCore getInstance() {
        if (InstanceHolder.INSTANCE.getContext() == null) {
            throw new NotInitializedException("HttpCore");
        }
        return InstanceHolder.INSTANCE;
    }

    private WeakReference<Context> mContextWeakReference;

    public Context getContext() {
        if (mContextWeakReference == null) return null;
        return mContextWeakReference.get();
    }

    public String getVersion() {
        return mVersion;
    }

    /**
     * 必须在application中初始化
     */
    public static HttpCore init(Context context) {
        InstanceHolder.INSTANCE.mContextWeakReference = new WeakReference<>(context);
        HttpCacheDatabase.init(context);
        return getInstance();
    }

    /**
     * 设置App的版本，缓存需要使用
     */
    public HttpCore withVersion(String version) {
        getInstance().mVersion = version;
        return this;
    }

    /**
     * 默认关闭log
     */
    public HttpCore withLog(boolean log) {
        RetrofitManagement.getInstance().setLog(log);
        return this;
    }

    /**
     * 杀死HttpCat
     */
    public HttpCore killHttpCat() {
        RetrofitManagement.getInstance().setHttpCatInterceptor(null);
        return this;
    }

    /**
     * 默认关闭log
     */
    public HttpCore withHttpCat(Interceptor cat) {
        RetrofitManagement.getInstance().setHttpCatInterceptor(cat);
        return this;
    }

    /**
     * 默认关闭log
     */
    public HttpCore withHttpHeader(Interceptor interceptor) {
        RetrofitManagement.getInstance().addInterceptor(interceptor);
        return this;
    }


    /**
     * 获取Service
     */
    public <T> T getService(String url, Class<T> clz) {
        return RetrofitManagement.getInstance().getService(url, clz);
    }

    public void done() {
        // do nothing
    }
}
