/*
 * ************************************************************
 * 文件：DataSourceWrapper.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-list
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.source;

import com.cody.component.lib.safe.SafeMutableLiveData;
import com.cody.component.list.define.Operation;
import com.cody.component.list.define.RequestStatus;
import com.cody.component.list.listener.OnRefreshListener;
import com.cody.component.list.listener.OnRetryListener;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by xu.yi. on 2019/4/8.
 * dataSource wrapper
 */
public class DataSourceWrapper<ItemBean> implements OnRefreshListener, OnRetryListener {
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
