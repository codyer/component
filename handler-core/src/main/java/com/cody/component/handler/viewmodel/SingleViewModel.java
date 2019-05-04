/*
 * ************************************************************
 * 文件：SingleViewModel.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月24日 13:27:47
 * 上次修改时间：2019年04月24日 13:27:47
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.interfaces.OnRequestListener;

/**
 * Created by xu.yi. on 2019/4/23.
 * component 用户友好的view model
 * 包含刷新，重试，出错默认提示页面
 */
public abstract class SingleViewModel<VD extends MaskViewData> extends FriendlyViewModel<VD> implements OnRequestListener {

    private MutableLiveData<RequestStatus> mRequestStatusLive;
    private RequestStatus mRequestStatus;

    public SingleViewModel(final VD friendlyViewData) {
        super(friendlyViewData);
    }

    @Override
    public void OnInit() {
        mRequestStatus = new RequestStatus();
        mRequestStatusLive = new MutableLiveData<>(mRequestStatus);
        setOperation(mRequestStatus);
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

    @Override
    public void onComplete() {
        if (mRequestStatus.isLoading()) {
            getRequestStatusLive().postValue(mRequestStatus = mRequestStatus.loaded());
        }
    }

    @Override
    public void onFailure(final String message) {
        if (mRequestStatus.isLoading()) {
            getRequestStatusLive().postValue(mRequestStatus = mRequestStatus.error(message));
        }
    }

    /**
     * 执行一个操作
     */
    protected void setOperation(RequestStatus requestStatus) {
        mRequestStatusLive.setValue(mRequestStatus = requestStatus);
        OnRequestData(requestStatus.getOperation(), this);
    }
}
