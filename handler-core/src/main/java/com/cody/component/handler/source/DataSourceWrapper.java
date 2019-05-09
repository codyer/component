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

import androidx.lifecycle.MutableLiveData;

import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.interfaces.OnRetryListener;
import com.cody.component.handler.interfaces.Refreshable;

/**
 * Created by xu.yi. on 2019/4/8.
 * dataSource wrapper
 */
public class DataSourceWrapper implements Refreshable, OnRetryListener {
    final private MutableLiveData<RequestStatus> mRequestStatusLive;
    final private MutableLiveData<PageListKeyedDataSource> mDataSource;
    private RequestStatus mRequestStatus;

    public DataSourceWrapper(final MutableLiveData<RequestStatus> operationStatusLive,
                             final MutableLiveData<PageListKeyedDataSource> dataSource) {
        mRequestStatusLive = operationStatusLive;
        mDataSource = dataSource;
        mRequestStatus = mRequestStatusLive.getValue();
    }

    public MutableLiveData<RequestStatus> getRequestStatusLive() {
        return mRequestStatusLive;
    }

    public RequestStatus getRequestStatus() {
        return mRequestStatus;
    }

    @Override
    public void refresh() {
        mRequestStatusLive.setValue(mRequestStatus = mRequestStatus.refresh());
        if (mDataSource != null && mDataSource.getValue() != null) {
            mDataSource.getValue().refresh();
        }
    }

    @Override
    public void retry() {
        // TODO 暂时不需要判断是不是重试，保留原来的操作状态不覆盖
//        mRequestStatusLive.setValue(mRequestStatus.setOperation(Operation.RETRY));
        if (mDataSource != null && mDataSource.getValue() != null) {
            mDataSource.getValue().retry();
        }
    }

    public void onSuccess() {
        mRequestStatusLive.postValue(mRequestStatus = getRequestStatus().loaded());
    }

    public void onFailure(final String message) {
        mRequestStatusLive.postValue(mRequestStatus = getRequestStatus().error(message));
    }
}
