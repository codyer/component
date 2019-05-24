/*
 * ************************************************************
 * 文件：HttpCat.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:54
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat;

import androidx.lifecycle.LiveData;

import okhttp3.Interceptor;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.List;

import com.cody.component.app.local.Repository;
import com.cody.http.cat.db.HttpCatDatabase;
import com.cody.http.cat.db.data.ItemHttpData;
import com.cody.http.cat.exception.NoCatCreatedException;
import com.cody.http.cat.notification.NotificationManagement;
import com.cody.http.cat.interceptor.HttpCatInterceptor;
import com.cody.http.cat.ui.CatMainActivity;
import com.cody.http.cat.utils.LauncherUtil;

/**
 * Created by xu.yi. on 2019/4/5.
 * HttpCat
 */
public class HttpCat {
    private WeakReference<Context> mContext;

    public Context getContext() {
        if (mContext == null) return null;
        return mContext.get();
    }

    private HttpCat() {
    }

    private static class HttpCatHolder {
        private static final HttpCat INSTANCE = new HttpCat();
    }

    public static HttpCat getInstance() {
        if (HttpCatHolder.INSTANCE.getContext() != null) {
            return HttpCatHolder.INSTANCE;
        }
        throw new NoCatCreatedException();
    }

    /**
     * 在创建OkHttpClient时候进行拦截器添加
     */
    public static Interceptor create(Context context) {
        LauncherUtil.launcherVisible(context, CatMainActivity.class);
        HttpCatHolder.INSTANCE.mContext = new WeakReference<>(context);
        return new HttpCatInterceptor(context);
    }

    /**
     * 只是单纯的删除图标，实际还在继续拦截
     */
    public void show() {
        LauncherUtil.launcherVisible(getContext(), CatMainActivity.class, true);
    }

    /**
     * 只是单纯的删除图标，实际还在继续拦截
     */
    public void showWithNotification() {
        LauncherUtil.launcherVisible(getContext(), CatMainActivity.class, true);
        showNotification(true);
    }

    /**
     * 只是单纯的删除图标，实际还在继续拦截
     */
    public void hide() {
        LauncherUtil.launcherVisible(getContext(), CatMainActivity.class, false);
        showNotification(false);
    }

    /**
     * 让猫静音
     */
    public void mute() {
        NotificationManagement.getInstance(getContext()).clearBuffer();
        NotificationManagement.getInstance(getContext()).dismiss();
        showNotification(false);
    }

    public void showNotification(boolean showNotification) {
        NotificationManagement.getInstance(getContext()).showNotification(showNotification);
    }

    public void clearCache() {
        HttpCatDatabase.getInstance(getContext()).getHttpInformationDao().deleteAll();
    }

    public LiveData<List<ItemHttpData>> queryAllRecord(int limit) {
        return HttpCatDatabase.getInstance(getContext()).getHttpInformationDao().queryAllRecordObservable(limit);
    }

    public LiveData<List<ItemHttpData>> queryAllRecord() {
        return HttpCatDatabase.getInstance(getContext()).getHttpInformationDao().queryAllRecordObservable();
    }
}