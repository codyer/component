/*
 * ************************************************************
 * 文件：MultiPageKeyedDataSource.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.source;

import com.cody.component.handler.callback.PageDataCallBack;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.PageInfo;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.listener.OnFriendlyListener;
import com.cody.component.handler.listener.OnRequestPageListener;
import com.cody.component.handler.listener.OnRetryListener;

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
public class MultiPageKeyedDataSource<ItemBean> extends PageKeyedDataSource<PageInfo, ItemBean>
        implements OnFriendlyListener {
    private MutableLiveData<RequestStatus> mRequestStatus = new MutableLiveData<>();
    private MutableLiveData<Operation> mOperation = new MutableLiveData<>();
    private OnRequestPageListener<ItemBean> mOnRequestPageListener;
    private OnRetryListener mOnRetryListener;

    public MultiPageKeyedDataSource(OnRequestPageListener<ItemBean> onRequestPageListener) {
        mOnRequestPageListener = onRequestPageListener;
        setOperation(Operation.INIT);
    }

    @Override
    public MutableLiveData<RequestStatus> getRequestStatus() {
        return mRequestStatus;
    }

    @Override
    public MutableLiveData<Operation> getOperation() {
        return mOperation;
    }

    @Override
    public void refresh() {
        this.invalidate();
        setOperation(Operation.REFRESH);
    }

    @Override
    public void retry() {
        if (mOnRetryListener != null) {
            mOnRetryListener.retry();
        }
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<PageInfo> params, @NonNull LoadInitialCallback<PageInfo, ItemBean> callback) {
        PageInfo pageInfo = new PageInfo(PageInfo.DEFAULT_PAGE_NO, params.requestedLoadSize, PageInfo.DEFAULT_POSITION);
        if (mOnRetryListener == null) {
            mOnRetryListener = () -> loadInitial(params, callback);
        }
        requestPageData(pageInfo, new PageDataCallBack<ItemBean>() {
            @Override
            public void onSuccess(@NonNull List<ItemBean> data, @Nullable PageInfo prePageKey, @Nullable PageInfo nextPageKey) {
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
    public void loadBefore(@NonNull LoadParams<PageInfo> params, @NonNull LoadCallback<PageInfo, ItemBean> callback) {
        setOperation(Operation.LOAD_BEFORE);
        if (mOnRetryListener == null) {
            mOnRetryListener = () -> loadBefore(params, callback);
        }
        requestPageData(params.key, new PageDataCallBack<ItemBean>() {
            @Override
            public void onSuccess(@NonNull List<ItemBean> data, @Nullable PageInfo prePageKey, @Nullable PageInfo nextPageKey) {
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
    public void loadAfter(@NonNull LoadParams<PageInfo> params, @NonNull LoadCallback<PageInfo, ItemBean> callback) {
        setOperation(Operation.LOAD_AFTER);
        if (mOnRetryListener == null) {
            mOnRetryListener = () -> loadAfter(params, callback);
        }
        requestPageData(params.key, new PageDataCallBack<ItemBean>() {
            @Override
            public void onSuccess(@NonNull List<ItemBean> data, @Nullable PageInfo prePageKey, @Nullable PageInfo nextPageKey) {
                callback.onResult(data, nextPageKey);
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
    private void requestPageData(PageInfo pageInfo, PageDataCallBack<ItemBean> callBack) {
        if (mOnRequestPageListener != null) {
            mOnRequestPageListener.OnRequestPageData(pageInfo, callBack);
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
