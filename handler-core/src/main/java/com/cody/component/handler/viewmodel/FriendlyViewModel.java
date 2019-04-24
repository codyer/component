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


import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.interfaces.OnFriendlyListener;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by xu.yi. on 2019/4/23.
 * component 用户友好的view model
 * 包含刷新，重试，出错默认提示页面
 */
public abstract class FriendlyViewModel<VD extends MaskViewData> extends BaseViewModel implements OnFriendlyListener {
    private MaskViewData mFriendlyViewData;
    protected MutableLiveData<Operation> mOperation;
    protected MutableLiveData<RequestStatus> mRequestStatus;

    public FriendlyViewModel() {
        mFriendlyViewData = new MaskViewData();
        initFriendly();
    }

    public FriendlyViewModel(final VD friendlyViewData) {
        mFriendlyViewData = friendlyViewData;
        initFriendly();
    }

    public void setFriendlyViewData(final MaskViewData friendlyViewData) {
        mFriendlyViewData = friendlyViewData;
    }

    public MaskViewData getFriendlyViewData() {
        return mFriendlyViewData;
    }

    @Override
    public MutableLiveData<Operation> getOperation() {
        return mOperation;
    }

    @Override
    public MutableLiveData<RequestStatus> getRequestStatus() {
        return mRequestStatus;
    }
}
