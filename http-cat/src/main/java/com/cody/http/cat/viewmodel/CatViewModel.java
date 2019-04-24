/*
 * ************************************************************
 * 文件：CatViewModel.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月23日 11:55:12
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.viewmodel;

import com.cody.component.handler.viewmodel.BaseViewModel;
import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.livedata.SafeMutableLiveData;
import com.cody.http.cat.HttpCat;
import com.cody.http.cat.db.HttpCatDatabase;
import com.cody.http.cat.db.data.ItemHttpData;
import com.cody.http.cat.notification.NotificationManagement;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

/**
 * Created by xu.yi. on 2019/3/31.
 * CatViewModel
 */
public class CatViewModel extends BaseViewModel {

    private final LiveData<List<ItemViewDataHolder>> mAllRecordLiveData;

    private LiveData<ItemHttpData> mRecordLiveData = new SafeMutableLiveData<>();

    private static final int LIMIT = 300;

    public CatViewModel() {
        mAllRecordLiveData = Transformations.map(
                HttpCatDatabase.getInstance(HttpCat.getInstance().getContext()).getHttpInformationDao().queryAllRecordObservable(LIMIT),
                input -> {
                    List<ItemViewDataHolder> list = new ArrayList<>();
                    for (int i = 0; i < input.size(); i++) {
                        list.add(new ItemViewDataHolder(1, input.get(i)));
                    }
                    return list;
                }
        );
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

    public LiveData<List<ItemViewDataHolder>> getAllRecordLiveData() {
        return mAllRecordLiveData;
    }

    public LiveData<ItemHttpData> getRecordLiveData() {
        return mRecordLiveData;
    }

}
