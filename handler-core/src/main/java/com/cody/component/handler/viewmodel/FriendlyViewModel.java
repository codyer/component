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

import com.cody.component.handler.data.FriendlyViewData;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.interfaces.OnFriendlyListener;

/**
 * Created by xu.yi. on 2019/4/23.
 * component 用户友好的view model
 * 包含刷新，重试，出错默认提示页面
 */
public abstract class FriendlyViewModel<VD extends FriendlyViewData> extends BaseViewModel implements OnFriendlyListener {
    final private VD mFriendlyViewData;

    MutableLiveData<RequestStatus> mRequestStatusLive;
    RequestStatus mRequestStatus;

    public FriendlyViewModel(final VD friendlyViewData) {
        mFriendlyViewData = friendlyViewData;
        if (mRequestStatus == null) {
            mRequestStatus = new RequestStatus();
        }
        if (mRequestStatusLive == null) {
            mRequestStatusLive = new MutableLiveData<>(mRequestStatus);
        }
    }

    public VD getFriendlyViewData() {
        return mFriendlyViewData;
    }

    @CallSuper
    @Override
    public void onInit() {
        startOperation(mRequestStatus.init());
    }

    @Override
    public void refresh() {
        startOperation(mRequestStatus.refresh());
    }

    @Override
    public void retry() {
        startOperation(mRequestStatus.retry());
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
     * #refreshUI()
     * #onFailure(String) ()
     */
    @Override
    public void refreshUI(RequestStatus status) {
        if (mRequestStatus.isLoading()) {
            mRequestStatus = status;
            mRequestStatusLive.postValue(status);
        }
    }

    /**
     * 做完一个operation需要将处理结果告知底层，完成或者失败
     * <p>
     * #refreshUI()
     * #onFailure(String) ()
     */
    @Override
    public void onFailure(final String message) {
        if (mRequestStatus.isLoading()) {
            getRequestStatusLive().postValue(mRequestStatus = mRequestStatus.error(message));
        }
    }

    @Override
    public void onCancel() {
        if (mRequestStatus.isLoading()) {
            getRequestStatusLive().postValue(mRequestStatus = mRequestStatus.cancel());
        }
    }

    /**
     * 执行一个操作
     */
    @CallSuper
    protected void startOperation(RequestStatus requestStatus) {
        setOperation(requestStatus);
    }

    /**
     * 执行一个操作
     */
    final public void setOperation(RequestStatus requestStatus) {
        mRequestStatusLive.setValue(mRequestStatus = requestStatus);
    }
}
