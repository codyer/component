/*
 * ************************************************************
 * 文件：FriendlyViewModel.java  模块：component.handler-core  项目：component
 * 当前修改时间：2021年03月07日 17:23:38
 * 上次修改时间：2021年03月07日 16:22:52
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：component.handler-core
 * Copyright (c) 2021
 * ************************************************************
 */

package com.cody.component.handler.viewmodel;


import com.cody.component.handler.data.FriendlyViewData;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.define.Status;
import com.cody.component.handler.interfaces.OnFriendlyListener;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by xu.yi. on 2019/4/23.
 * component 用户友好的view model
 * 包含刷新，重试，出错默认提示页面
 */
public abstract class FriendlyViewModel<VD extends FriendlyViewData> extends BaseViewModel implements OnFriendlyListener {
    final private VD mFriendlyViewData;

    protected MutableLiveData<RequestStatus> mRequestStatusLive;
    protected RequestStatus mRequestStatus;

    /**
     * 只初始化一次，后面数据变化自动监听，适合本地数据库主动监听
     *
     * @return 是否只需要初始化一次
     */
    protected boolean isInitOnce() {
        return false;
    }

    public FriendlyViewModel(final VD friendlyViewData) {
        super();
        mFriendlyViewData = friendlyViewData;
        if (mRequestStatus == null) {
            mRequestStatus = new RequestStatus();
            if (isInitOnce()) {
                mRequestStatus.setStatus(Status.SUCCESS);
            }
        }
        if (mRequestStatusLive == null) {
            mRequestStatusLive = new MutableLiveData<>(mRequestStatus);
        }
    }

    @Override
    protected void onCleared() {
        onCancel();
        super.onCleared();
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
     * #submitStatus()
     * #onFailure(String) ()
     */
    @Override
    public void submitStatus(RequestStatus status) {
        if (mRequestStatus.isLoading() || isInitOnce()) {
            mRequestStatus = status;
            mRequestStatusLive.postValue(status);
        }
    }

    /**
     * 执行一个操作
     */
    @CallSuper
    protected void startOperation(RequestStatus requestStatus) {
        if (isInitOnce() && !mRequestStatus.isLoading()) {
            requestStatus.setStatus(mRequestStatus.getStatus());
        }
        setOperation(requestStatus);
    }

    /**
     * 执行一个操作
     */
    final public void setOperation(RequestStatus requestStatus) {
        mRequestStatusLive.setValue(mRequestStatus = requestStatus);
    }
}
