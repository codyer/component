/*
 * ************************************************************
 * 文件：ItemFooterOrHeaderData.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月24日 09:39:11
 * 上次修改时间：2019年04月24日 09:38:27
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.data;

import com.cody.component.handler.livedata.BooleanLiveData;
import com.cody.component.handler.livedata.StringLiveData;
import com.cody.component.handler.define.RequestStatus;


/**
 * Created by xu.yi. on 2019/4/8.
 * 列表底部数据类
 */
public class ItemFooterOrHeaderData extends ItemViewDataHolder {
    private static final long serialVersionUID = 6101620960031035326L;
    private boolean mShowFooter = true;
    final private BooleanLiveData mNoMoreItem = new BooleanLiveData(false);
    final private BooleanLiveData mError = new BooleanLiveData(false);
    final private BooleanLiveData mLoading = new BooleanLiveData(false);
    final private StringLiveData mErrorMessage = new StringLiveData("");

    public ItemFooterOrHeaderData() {
    }

    public ItemFooterOrHeaderData(int itemType) {
        super(itemType);
    }

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