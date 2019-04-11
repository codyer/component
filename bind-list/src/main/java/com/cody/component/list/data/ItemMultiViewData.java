/*
 * ************************************************************
 * 文件：ItemMultiViewData.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月09日 15:13:51
 * 上次修改时间：2019年04月08日 17:38:08
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.data;

import com.cody.component.lib.data.ItemViewData;
import com.cody.component.lib.safe.SafeMutableLiveData;
import com.cody.component.list.define.RequestStatus;


/**
 * Created by xu.yi. on 2019/4/8.
 * 列表底部数据类
 */
public class ItemMultiViewData extends ItemViewData {
    private static final long serialVersionUID = 6101620960031035326L;
    final private SafeMutableLiveData<Boolean> mNoMoreItem = new SafeMutableLiveData<>(false);
    final private SafeMutableLiveData<Boolean> mError = new SafeMutableLiveData<>(false);
    final private SafeMutableLiveData<Boolean> mLoading = new SafeMutableLiveData<>(false);
    final private SafeMutableLiveData<String> mErrorMessage = new SafeMutableLiveData<>("");

    public SafeMutableLiveData<Boolean> getNoMoreItem() {
        return mNoMoreItem;
    }

    public SafeMutableLiveData<Boolean> getError() {
        return mError;
    }

    public SafeMutableLiveData<Boolean> getLoading() {
        return mLoading;
    }

    public SafeMutableLiveData<String> getErrorMessage() {
        return mErrorMessage;
    }

    public void setRequestStatus(final RequestStatus status) {
        mNoMoreItem.postValue(status.isEmpty());
        mError.postValue(status.isError());
        mLoading.postValue(status.isLoading());
        mErrorMessage.postValue(status.getMessage());
    }
}