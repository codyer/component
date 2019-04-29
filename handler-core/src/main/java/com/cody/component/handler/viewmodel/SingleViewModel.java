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


import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.interfaces.DataCallBack;
import com.cody.component.handler.interfaces.OnRequestListener;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by xu.yi. on 2019/4/23.
 * component 用户友好的view model
 * 包含刷新，重试，出错默认提示页面
 */
public abstract class SingleViewModel<VD extends MaskViewData> extends FriendlyViewModel<VD> implements OnRequestListener{

    private MutableLiveData<Operation> mOperation;
    private MutableLiveData<RequestStatus> mRequestStatus;

    public SingleViewModel(final VD friendlyViewData) {
        super(friendlyViewData);
        mOperation = new MutableLiveData<>();
        mRequestStatus = new MutableLiveData<>();
    }

    @Override
    public void OnInit() {
        setOperation(Operation.INIT);
    }

    @Override
    public void refresh() {
        setOperation(Operation.REFRESH);
    }

    @Override
    public void retry() {
        setOperation(Operation.RETRY);
    }

    @Override
    public MutableLiveData<Operation> getOperation() {
        return mOperation;
    }

    @Override
    public MutableLiveData<RequestStatus> getRequestStatus() {
        return mRequestStatus;
    }

    @Override
    public void onSuccess() {
        mRequestStatus.postValue(RequestStatus.loaded());
    }

    @Override
    public void onFailure(final String message) {
        mRequestStatus.postValue(RequestStatus.error(message));
    }

    /**
     * 执行一个操作
     */
    private void setOperation(Operation operation) {
        mOperation.postValue(operation);
        mRequestStatus.postValue(RequestStatus.loading());
        OnRequestData(operation, this);
    }
}
