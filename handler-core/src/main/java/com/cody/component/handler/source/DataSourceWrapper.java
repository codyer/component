/*
 * ************************************************************
 * 文件：DataSourceWrapper.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.source;

import com.cody.component.handler.livedata.SafeMutableLiveData;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.interfaces.Refreshable;
import com.cody.component.handler.interfaces.OnRetryListener;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by xu.yi. on 2019/4/8.
 * dataSource wrapper
 */
public class DataSourceWrapper<ItemBean> implements Refreshable, OnRetryListener {
    final private MutableLiveData<RequestStatus> mRequestStatus;
    final private MutableLiveData<Operation> mOperation;
    final private SafeMutableLiveData<MultiPageKeyedDataSource<ItemBean>> mDataSource;

    public DataSourceWrapper(final LiveData<RequestStatus> requestStatus, final LiveData<Operation> operation,
                             final SafeMutableLiveData<MultiPageKeyedDataSource<ItemBean>> dataSource) {
        mRequestStatus = (MutableLiveData<RequestStatus>) requestStatus;
        mOperation = (MutableLiveData<Operation>) operation;
        mDataSource = dataSource;
    }

    public MutableLiveData<RequestStatus> getRequestStatus() {
        return mRequestStatus;
    }

    public MutableLiveData<Operation> getOperation() {
        return mOperation;
    }

    @Override
    public void refresh() {
        if (mDataSource != null && mDataSource.getValue() != null) {
            mDataSource.getValue().refresh();
        }
    }

    @Override
    public void retry() {
        if (mDataSource != null && mDataSource.getValue() != null) {
            mDataSource.getValue().retry();
        }
    }
}
