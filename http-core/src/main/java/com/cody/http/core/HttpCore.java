/*
 * ************************************************************
 * 文件：HttpCore.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月10日 15:58:25
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core;

import android.content.Context;

import com.cody.http.lib.exception.HttpCoreNotInitializedException;

import java.lang.ref.WeakReference;

import okhttp3.Interceptor;

/**
 * Created by xu.yi. on 2019/4/6.
 */
public class HttpCore {

    private static class InstanceHolder {
        private static final HttpCore INSTANCE = new HttpCore();
    }

    public static HttpCore getInstance() {
        if (InstanceHolder.INSTANCE.getContext() == null) {
            throw new HttpCoreNotInitializedException();
        }
        return InstanceHolder.INSTANCE;
    }

    private WeakReference<Context> mContextWeakReference;

    public Context getContext() {
        if (mContextWeakReference == null) return null;
        return mContextWeakReference.get();
    }

    /**
     * 必须在application中初始化
     */
    public static HttpCore init(Context context) {
        InstanceHolder.INSTANCE.mContextWeakReference = new WeakReference<>(context);
        return getInstance();
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

    public void done() {
        // do nothing
    }
}
