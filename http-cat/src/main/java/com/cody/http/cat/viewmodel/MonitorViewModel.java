/*
 * ************************************************************
 * 文件：MonitorViewModel.java  模块：http-monitor  项目：component
 * 当前修改时间：2019年04月05日 18:45:24
 * 上次修改时间：2019年04月05日 17:27:09
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.viewmodel;

import androidx.lifecycle.LiveData;

import java.util.List;

import com.cody.component.handler.BaseViewModel;
import com.cody.http.cat.db.MonitorHttpInformationDatabase;
import com.cody.http.cat.db.data.ItemMonitorData;
import com.cody.http.cat.holder.ContextHolder;
import com.cody.http.cat.holder.NotificationHolder;

/**
 * Created by xu.yi. on 2019/3/31.
 * MonitorViewModel
 */
public class MonitorViewModel extends BaseViewModel {

    private LiveData<List<ItemMonitorData>> mAllRecordLiveData;

    private LiveData<ItemMonitorData> mRecordLiveData;

    private static final int LIMIT = 300;

    public MonitorViewModel() {
        mAllRecordLiveData = MonitorHttpInformationDatabase.getInstance(ContextHolder.getContext()).getHttpInformationDao().queryAllRecordObservable(LIMIT);
    }

    public void clearAllCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MonitorHttpInformationDatabase.getInstance(ContextHolder.getContext()).getHttpInformationDao().deleteAll();
            }
        }).start();
    }

    public void clearNotification() {
        NotificationHolder.getInstance(ContextHolder.getContext()).dismiss();
    }

    public void queryRecordById(long id) {
        mRecordLiveData = MonitorHttpInformationDatabase.getInstance(ContextHolder.getContext()).getHttpInformationDao().queryRecordObservable(id);
    }

    public LiveData<List<ItemMonitorData>> getAllRecordLiveData() {
        return mAllRecordLiveData;
    }

    public LiveData<ItemMonitorData> getRecordLiveData() {
        return mRecordLiveData;
    }

}
