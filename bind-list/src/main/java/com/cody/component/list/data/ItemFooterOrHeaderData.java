/*
 * ************************************************************
 * 文件：ItemFooterOrHeaderData.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-list
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.data;

import com.cody.component.lib.data.ViewData;
import com.cody.component.lib.safe.BooleanLiveData;
import com.cody.component.lib.safe.StringLiveData;
import com.cody.component.list.define.RequestStatus;


/**
 * Created by xu.yi. on 2019/4/8.
 * 列表底部数据类
 */
public class ItemFooterOrHeaderData extends ViewData {
    private static final long serialVersionUID = 6101620960031035326L;
    private boolean mShowFooter = true;
    final private BooleanLiveData mNoMoreItem = new BooleanLiveData(false);
    final private BooleanLiveData mError = new BooleanLiveData(false);
    final private BooleanLiveData mLoading = new BooleanLiveData(false);
    final private StringLiveData mErrorMessage = new StringLiveData("");

    public boolean isShowFooter() {
        return mShowFooter;
    }

    public void setShowFooter(final boolean showFooter) {
        mShowFooter = showFooter;
    }

    public BooleanLiveData getNoMoreItem() {
        return mNoMoreItem;
    }

    public BooleanLiveData getError() {
        return mError;
    }

    public BooleanLiveData getLoading() {
        return mLoading;
    }

    public StringLiveData getErrorMessage() {
        return mErrorMessage;
    }

    public void setRequestStatus(final RequestStatus status) {
        mNoMoreItem.postValue(status.isEmpty());
        mError.postValue(status.isError());
        mLoading.postValue(status.isLoading());
        mErrorMessage.postValue(status.getMessage());
    }
}