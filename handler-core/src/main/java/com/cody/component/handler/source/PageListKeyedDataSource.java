/*
 * ************************************************************
 * 文件：PageListKeyedDataSource.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.source;

import android.util.Log;

import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.interfaces.PageDataCallBack;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.PageInfo;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.interfaces.OnFriendlyListener;
import com.cody.component.handler.interfaces.OnRequestPageListener;
import com.cody.component.handler.interfaces.OnRetryListener;
import com.cody.component.handler.interfaces.Refreshable;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

/**
 * Created by xu.yi. on 2019/4/8.
 * 根据接口返回的信息进行分页加载处理基类
 * 泛型为分页Item的类类型
 */
public class PageListKeyedDataSource extends PageKeyedDataSource<PageInfo, ItemViewDataHolder>
        implements Refreshable, OnRetryListener {
    final private MutableLiveData<RequestStatus> mRequestStatus;
    final private MutableLiveData<Operation> mOperation;
    final private OnRequestPageListener mOnRequestPageListener;
    private OnRetryListener mOnRetryListener;

    public PageListKeyedDataSource(final OnRequestPageListener onRequestPageListener, final MutableLiveData<RequestStatus> requestStatus, final MutableLiveData<Operation> operation) {
        mOnRequestPageListener = onRequestPageListener;
        mRequestStatus = requestStatus;
        mOperation = operation;
    }

    @Override
    public void refresh() {
        this.invalidate();
    }

    @Override
    public void retry() {
        if (mOnRetryListener != null) {
            mOnRetryListener.retry();
        }
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<PageInfo> params, @NonNull LoadInitialCallback<PageInfo, ItemViewDataHolder> callback) {
        PageInfo pageInfo = new PageInfo(PageInfo.DEFAULT_PAGE_NO, params.requestedLoadSize, PageInfo.DEFAULT_POSITION);
        if (mOnRetryListener == null) {
            mOnRetryListener = () -> loadInitial(params, callback);
        }
        requestPageData(Operation.INIT, pageInfo, new PageDataCallBack() {
            @Override
            public void onSuccess(@NonNull List<ItemViewDataHolder> data, @Nullable PageInfo prePageKey, @Nullable PageInfo nextPageKey) {
                callback.onResult(data, prePageKey, nextPageKey);
                mRequestStatus.postValue(data.isEmpty() ? RequestStatus.empty() : RequestStatus.loaded());
                mOnRetryListener = null;
            }

            @Override
            public void onFailure(String message) {
                mRequestStatus.postValue(RequestStatus.error(message));
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<PageInfo> params, @NonNull LoadCallback<PageInfo, ItemViewDataHolder> callback) {
        if (mOnRetryListener == null) {
            mOnRetryListener = () -> loadBefore(params, callback);
        }
        requestPageData(Operation.LOAD_BEFORE, params.key, new PageDataCallBack() {
            @Override
            public void onSuccess(@NonNull List<ItemViewDataHolder> data, @Nullable PageInfo prePageKey, @Nullable PageInfo nextPageKey) {
                callback.onResult(data, prePageKey);
                mRequestStatus.postValue(data.isEmpty() ? RequestStatus.empty() : RequestStatus.loaded());
                mOnRetryListener = null;
            }

            @Override
            public void onFailure(String message) {
                mRequestStatus.postValue(RequestStatus.error(message));
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<PageInfo> params, @NonNull LoadCallback<PageInfo, ItemViewDataHolder> callback) {
        if (mOnRetryListener == null) {
            mOnRetryListener = () -> loadAfter(params, callback);
        }
        requestPageData(Operation.LOAD_AFTER, params.key, new PageDataCallBack() {
            @Override
            public void onSuccess(@NonNull List<ItemViewDataHolder> data, @Nullable PageInfo prePageKey, @Nullable PageInfo nextPageKey) {
                if (nextPageKey != null && data.size() > nextPageKey.getPositionByPageNo()) {
                    callback.onResult(data.subList(nextPageKey.getPositionByPageNo(), data.size()), nextPageKey);
                } else {
                    callback.onResult(data, nextPageKey);
                }
                mRequestStatus.postValue(data.isEmpty() ? RequestStatus.empty() : RequestStatus.loaded());
                mOnRetryListener = null;
            }

            @Override
            public void onFailure(String message) {
                mRequestStatus.postValue(RequestStatus.error(message));
            }
        });
    }

    /**
     * 请求一页数据
     */
    private void requestPageData(Operation operation, PageInfo pageInfo, PageDataCallBack callBack) {
        setOperation(operation);
        if (mOnRequestPageListener != null) {
            mOnRequestPageListener.OnRequestPageData(operation, pageInfo, callBack);
        }
    }

    /**
     * 执行一个操作
     */
    private void setOperation(Operation operation) {
        mOperation.postValue(operation);
        mRequestStatus.postValue(RequestStatus.loading());
    }
}