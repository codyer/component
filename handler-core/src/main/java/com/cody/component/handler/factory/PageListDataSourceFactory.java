/*
 * ************************************************************
 * 文件：PageListDataSourceFactory.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.factory;

import android.util.Log;

import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.livedata.SafeMutableLiveData;
import com.cody.component.handler.define.PageInfo;
import com.cody.component.handler.interfaces.OnRequestPageListener;
import com.cody.component.handler.source.PageListKeyedDataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

/**
 * Created by xu.yi. on 2019/4/8.
 * 根据接口返回的信息进行分页加载数据工厂基类
 * 泛型为分页Item的类类型
 */
public class PageListDataSourceFactory extends DataSource.Factory<PageInfo, ItemViewDataHolder> {
    private SafeMutableLiveData<PageListKeyedDataSource> mDataSource = new SafeMutableLiveData<>();
    private OnRequestPageListener mOnRequestPageListener;
    private MutableLiveData<RequestStatus> mRequestStatus;
    private MutableLiveData<Operation> mOperation;

    public PageListDataSourceFactory(OnRequestPageListener onRequestPageListener) {
        mOnRequestPageListener = onRequestPageListener;
        mRequestStatus = new MutableLiveData<>(RequestStatus.loading());
        mOperation = new MutableLiveData<>(Operation.INIT);
    }

    @NonNull
    @Override
    public DataSource<PageInfo, ItemViewDataHolder> create() {
        PageListKeyedDataSource dataSource = new PageListKeyedDataSource(mOnRequestPageListener, mRequestStatus, mOperation);
        mDataSource.postValue(dataSource);
        return dataSource;
    }

    public SafeMutableLiveData<PageListKeyedDataSource> getDataSource() {
        return mDataSource;
    }

    public MutableLiveData<RequestStatus> getRequestStatus() {
        return mRequestStatus;
    }

    public MutableLiveData<Operation> getOperation() {
        return mOperation;
    }
}
