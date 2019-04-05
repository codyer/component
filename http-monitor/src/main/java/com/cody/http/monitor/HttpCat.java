/*
 * ************************************************************
 * 文件：HttpCat.java  模块：http-monitor  项目：component
 * 当前修改时间：2019年04月05日 18:45:24
 * 上次修改时间：2019年04月05日 17:27:09
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.monitor;

import androidx.lifecycle.LiveData;
import okhttp3.OkHttpClient;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import com.cody.http.monitor.db.MonitorHttpInformationDatabase;
import com.cody.http.monitor.db.data.ItemMonitorData;
import com.cody.http.monitor.holder.ContextHolder;
import com.cody.http.monitor.holder.NotificationHolder;
import com.cody.http.monitor.ui.MonitorMainActivity;
import com.cody.http.monitor.utils.LauncherUtil;

/**
 * Created by xu.yi. on 2019/4/5.
 * HttpCat
 */
public class HttpCat {
    private boolean mActiveCat;

    private HttpCat() {
        mActiveCat = true;
    }

    private static class HttpCatHolder {
        private static HttpCat INSTANCE = new HttpCat();
    }

    public static HttpCat getInstance() {
        return HttpCatHolder.INSTANCE;
    }

    /**
     * 在创建OkHttpClient时候进行拦截器添加
     */
    public void install(Context context, OkHttpClient.Builder builder) {
        builder.addInterceptor(new MonitorInterceptor(context));
        LauncherUtil.launcherVisible(context, MonitorMainActivity.class, true);
    }

    /**
     * 只是单纯的删除图标，实际还在继续拦截
     */
    public void unInstall(Context context) {
        LauncherUtil.launcherVisible(context, MonitorMainActivity.class, false);
        showNotification(false);
    }

    public void clearNotification() {
        NotificationHolder.getInstance(ContextHolder.getContext()).clearBuffer();
        NotificationHolder.getInstance(ContextHolder.getContext()).dismiss();
    }

    public void showNotification(boolean showNotification) {
        NotificationHolder.getInstance(ContextHolder.getContext()).showNotification(showNotification);
    }

    public void clearCache() {
        MonitorHttpInformationDatabase.getInstance(ContextHolder.getContext()).getHttpInformationDao().deleteAll();
    }

    public LiveData<List<ItemMonitorData>> queryAllRecord(int limit) {
        return MonitorHttpInformationDatabase.getInstance(ContextHolder.getContext()).getHttpInformationDao().queryAllRecordObservable(limit);
    }

    public LiveData<List<ItemMonitorData>> queryAllRecord() {
        return MonitorHttpInformationDatabase.getInstance(ContextHolder.getContext()).getHttpInformationDao().queryAllRecordObservable();
    }

}