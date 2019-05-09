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

import android.os.Parcel;

import com.cody.component.handler.livedata.BooleanLiveData;
import com.cody.component.handler.livedata.StringLiveData;
import com.cody.component.handler.define.RequestStatus;


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
        mNoMoreItem.postValue(status.isEmpty());
        mError.postValue(status.isError());
        mLoading.postValue(status.isLoading());
        mErrorMessage.postValue(status.getMessage());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte(this.mShowFooter ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.mNoMoreItem, flags);
        dest.writeParcelable(this.mError, flags);
        dest.writeParcelable(this.mLoading, flags);
        dest.writeParcelable(this.mErrorMessage, flags);
    }

    protected ItemFooterOrHeaderData(Parcel in) {
        super(in);
        this.mShowFooter = in.readByte() != 0;
        this.mNoMoreItem = in.readParcelable(BooleanLiveData.class.getClassLoader());
        this.mError = in.readParcelable(BooleanLiveData.class.getClassLoader());
        this.mLoading = in.readParcelable(BooleanLiveData.class.getClassLoader());
        this.mErrorMessage = in.readParcelable(StringLiveData.class.getClassLoader());
    }

    public static final Creator<ItemFooterOrHeaderData> CREATOR = new Creator<ItemFooterOrHeaderData>() {
        @Override
        public ItemFooterOrHeaderData createFromParcel(Parcel source) {
            return new ItemFooterOrHeaderData(source);
        }

        @Override
        public ItemFooterOrHeaderData[] newArray(int size) {
            return new ItemFooterOrHeaderData[size];
        }
    };
}