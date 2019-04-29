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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.PageInfo;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.interfaces.OnRequestPageListener;
import com.cody.component.handler.interfaces.OnRetryListener;
import com.cody.component.handler.interfaces.PageDataCallBack;
import com.cody.component.handler.interfaces.Refreshable;

import java.util.List;

/**
 * Created by xu.yi. on 2019/4/8.
 * 根据接口返回的信息进行分页加载处理基类
 * 泛型为分页Item的类类型
 */
public class PageListKeyedDataSource extends PageKeyedDataSource<PageInfo, ItemViewDataHolder>
        implements Refreshable, OnRetryListener {
    final private MutableLiveData<RequestStatus> mRequestStatusLive;
    private RequestStatus mRequestStatus;
    final private OnRequestPageListener mOnRequestPageListener;
    private OnRetryListener mOnRetryListener;

    public PageListKeyedDataSource(final OnRequestPageListener onRequestPageListener, final MutableLiveData<RequestStatus> operationStatusLive) {
        mOnRequestPageListener = onRequestPageListener;
        mRequestStatusLive = operationStatusLive;
        mRequestStatus = mRequestStatusLive.getValue();
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
        requestPageData(mRequestStatus.getOperation(), pageInfo, new PageDataCallBack() {
            @Override
            public void onSuccess(@NonNull List<ItemViewDataHolder> data, @Nullable PageInfo prePageKey, @Nullable PageInfo nextPageKey) {
                callback.onResult(data, prePageKey, nextPageKey);
                mRequestStatusLive.postValue(mRequestStatus = data.isEmpty() ? mRequestStatus.empty() : mRequestStatus.loaded());
                mOnRetryListener = null;
            }

            @Override
            public void onFailure(String message) {
                mRequestStatusLive.postValue(mRequestStatus = mRequestStatus.error(message));
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
                mRequestStatusLive.postValue(mRequestStatus = data.isEmpty() ? mRequestStatus.empty() : mRequestStatus.loaded());
                mOnRetryListener = null;
            }

            @Override
            public void onFailure(String message) {
                mRequestStatusLive.postValue(mRequestStatus = mRequestStatus.error(message));
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
                mRequestStatusLive.postValue(mRequestStatus = data.isEmpty() ? mRequestStatus.empty() : mRequestStatus.loaded());
                mOnRetryListener = null;
            }

            @Override
            public void onFailure(String message) {
                mRequestStatusLive.postValue(mRequestStatus = mRequestStatus.error(message));
            }
        });
    }

    /**
     * 请求一页数据
     */
    private void requestPageData(Operation operation, PageInfo pageInfo, PageDataCallBack callBack) {
        mRequestStatusLive.postValue(mRequestStatus = mRequestStatus.setOperation(operation));
        if (mOnRequestPageListener != null) {
            mOnRequestPageListener.OnRequestPageData(operation, pageInfo, callBack);
        }
    }

}