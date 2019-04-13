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
import com.cody.component.list.exception.ParameterNullPointerException;
import com.cody.component.list.listener.OnListListener;

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
    public SafeMutableLiveData<Operation> getOperation() {
        if (mDataSource != null) {
            return mDataSource.getOperation();
        }
        throw new ParameterNullPointerException("DataSourceWrapper");
    }

    @Override
    public SafeMutableLiveData<RequestStatus> getRequestStatus() {
        if (mDataSource != null) {
            return mDataSource.getRequestStatus();
        }
        throw new ParameterNullPointerException("DataSourceWrapper");
    }
}
