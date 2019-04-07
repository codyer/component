/*
 * ************************************************************
 * 文件：CatViewModel.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月05日 18:45:24
 * 上次修改时间：2019年04月05日 17:27:09
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import com.cody.component.handler.BaseViewModel;
import com.cody.http.cat.HttpCat;
import com.cody.http.cat.db.HttpCatDatabase;
import com.cody.http.cat.db.data.ItemHttpData;
import com.cody.http.cat.notification.NotificationManagement;

/**
 * Created by xu.yi. on 2019/3/31.
 * CatViewModel
 */
public class CatViewModel extends BaseViewModel {

    private final LiveData<List<ItemHttpData>> mAllRecordLiveData;

    private LiveData<ItemHttpData> mRecordLiveData = new MutableLiveData<>();

    private static final int LIMIT = 300;

    public CatViewModel() {
        mAllRecordLiveData = HttpCatDatabase.getInstance(HttpCat.getInstance().getContext()).getHttpInformationDao().queryAllRecordObservable(LIMIT);
    }

    public void clearAllCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpCatDatabase.getInstance(HttpCat.getInstance().getContext()).getHttpInformationDao().deleteAll();
            }
        }).start();
    }

    public void clearNotification() {
        NotificationManagement.getInstance(HttpCat.getInstance().getContext()).dismiss();
    }

    public void queryRecordById(long id) {
        mRecordLiveData = HttpCatDatabase.getInstance(HttpCat.getInstance().getContext()).getHttpInformationDao().queryRecordObservable(id);
    }

    public LiveData<List<ItemHttpData>> getAllRecordLiveData() {
        return mAllRecordLiveData;
    }

    public LiveData<ItemHttpData> getRecordLiveData() {
        return mRecordLiveData;
    }

}
