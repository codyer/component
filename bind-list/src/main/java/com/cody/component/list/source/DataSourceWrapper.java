/*
 * ************************************************************
 * 文件：DataSourceWrapper.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月09日 15:27:03
 * 上次修改时间：2019年04月09日 15:11:17
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.source;

import com.cody.component.list.define.Operation;
import com.cody.component.list.define.RequestStatus;
import com.cody.component.list.exception.ParameterNullPointerException;
import com.cody.component.list.listener.OnListListener;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by xu.yi. on 2019/4/8.
 * dataSource wrapper
 */
public class DataSourceWrapper<ItemBean> implements OnListListener {
    private MultiPageKeyedDataSource<ItemBean> mDataSource;

    public DataSourceWrapper(MultiPageKeyedDataSource<ItemBean> dataSource) {
        if (dataSource == null) {
            throw new ParameterNullPointerException("DataSourceWrapper");
        }
        mDataSource = dataSource;
    }

    @Override
    public void refresh() {
        if (mDataSource != null) {
            mDataSource.refresh();
        }
    }

    @Override
    public void retry() {
        if (mDataSource != null) {
            mDataSource.retry();
        }
    }

    @Override
    public MutableLiveData<Operation> getOperation() {
        if (mDataSource != null) {
            return mDataSource.getOperation();
        }
        throw new ParameterNullPointerException("DataSourceWrapper");
    }

    @Override
    public MutableLiveData<RequestStatus> getRequestStatus() {
        if (mDataSource != null) {
            return mDataSource.getRequestStatus();
        }
        throw new ParameterNullPointerException("DataSourceWrapper");
    }
}
