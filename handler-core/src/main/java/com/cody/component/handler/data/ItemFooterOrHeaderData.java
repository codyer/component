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

import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.livedata.BooleanLiveData;
import com.cody.component.handler.livedata.StringLiveData;


/**
 * Created by xu.yi. on 2019/4/8.
 * 列表底部数据类
 */
public class ItemFooterOrHeaderData extends ItemViewDataHolder {
    private boolean mShowFooter = true;
    private BooleanLiveData mNoMoreItem = new BooleanLiveData(false);
    private BooleanLiveData mError = new BooleanLiveData(false);
    private BooleanLiveData mLoading = new BooleanLiveData(false);
    private StringLiveData mErrorMessage = new StringLiveData("");

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
        mNoMoreItem.postValue(status.isEnd());
        mError.postValue(status.isError());
        mLoading.postValue(status.isLoading());
        mErrorMessage.postValue(status.getMessage());
    }
}