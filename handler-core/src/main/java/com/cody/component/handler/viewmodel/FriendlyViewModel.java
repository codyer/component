/*
 * ************************************************************
 * 文件：FriendlyViewModel.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:51:18
 * 上次修改时间：2019年04月23日 18:29:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.viewmodel;


import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.interfaces.OnFriendlyListener;
import com.cody.component.lib.bean.ListBean;

import java.util.List;

/**
 * Created by xu.yi. on 2019/4/23.
 * component 用户友好的view model
 * 包含刷新，重试，出错默认提示页面
 */
public abstract class FriendlyViewModel<VD extends MaskViewData> extends BaseViewModel implements OnFriendlyListener {
    final private VD mFriendlyViewData;

    MutableLiveData<RequestStatus> mRequestStatusLive;
    RequestStatus mRequestStatus;

    public FriendlyViewModel(final VD friendlyViewData) {
        mFriendlyViewData = friendlyViewData;
    }

    public VD getFriendlyViewData() {
        return mFriendlyViewData;
    }

    @CallSuper
    @Override
    public void onInit() {
        if (mRequestStatus == null) {
            mRequestStatus = new RequestStatus();
        }
        if (mRequestStatusLive == null) {
            mRequestStatusLive = new MutableLiveData<>(mRequestStatus);
        }
        setOperation(mRequestStatus.init());
    }

    @Override
    public void refresh() {
        setOperation(mRequestStatus.refresh());
    }

    @Override
    public void retry() {
        setOperation(mRequestStatus.retry());
    }

    @NonNull
    @Override
    public RequestStatus getRequestStatus() {
        return mRequestStatus;
    }

    @NonNull
    @Override
    public MutableLiveData<RequestStatus> getRequestStatusLive() {
        return mRequestStatusLive;
    }

    /**
     * 做完一个operation需要将处理结果告知底层，完成或者失败
     * <p>
     * #onComplete()
     * #onFailure(String) ()
     */
    @Override
    public void onComplete(Object result) {
        if (mRequestStatus.isLoading()) {
            boolean empty = result == null ||
                    (result instanceof List && ((List) result).isEmpty()) ||
                    result instanceof ListBean && (((ListBean) result).getItems() == null ||
                            ((ListBean) result).getItems().isEmpty());
            mRequestStatus = empty ? mRequestStatus.empty() : mRequestStatus.loaded();
            if (result instanceof ListBean) {
                boolean end = empty || !((ListBean) result).isMore();
                mRequestStatus = end ? mRequestStatus.end() : mRequestStatus;
            }
            mRequestStatusLive.postValue(mRequestStatus);
        }
    }

    /**
     * 做完一个operation需要将处理结果告知底层，完成或者失败
     * <p>
     * #onComplete()
     * #onFailure(String) ()
     */
    @Override
    public void onFailure(final String message) {
        if (mRequestStatus.isLoading()) {
            getRequestStatusLive().postValue(mRequestStatus = mRequestStatus.error(message));
        }
    }

    /**
     * 执行一个操作
     */
    @CallSuper
    protected void setOperation(RequestStatus requestStatus) {
        mRequestStatusLive.setValue(mRequestStatus = requestStatus);
    }
}
