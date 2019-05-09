/*
 * ************************************************************
 * 文件：HtmlViewData.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月15日 23:04:22
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.data;

import android.os.Parcel;

import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.livedata.BooleanLiveData;
import com.cody.component.handler.livedata.IntegerLiveData;
import com.cody.component.handler.livedata.StringLiveData;

/**
 * Created by xu.yi. on 2019/4/11.
 * html页面数据
 */
public class HtmlViewData extends MaskViewData {
    public final static int MAX_PROGRESS = 100;
    private boolean mIgnoreError = false;
    private BooleanLiveData mShowHeader = new BooleanLiveData(true);
    private IntegerLiveData mProgress = new IntegerLiveData(0);
    private StringLiveData mHeader = new StringLiveData("");
    private StringLiveData mUrl = new StringLiveData("");

    public BooleanLiveData getShowHeader() {
        return mShowHeader;
    }

    public void setIgnoreError(final boolean ignoreError) {
        mIgnoreError = ignoreError;
    }

    public boolean isIgnoreError() {
        return mIgnoreError;
    }

    public IntegerLiveData getProgress() {
        return mProgress;
    }

    public StringLiveData getHeader() {
        return mHeader;
    }

    public void setUrl(String url) {
        setProgress(0);
        mUrl.setValue(url);
    }

    public StringLiveData getUrl() {
        return mUrl;
    }

    public void setProgress(final int progress) {
        mProgress.setValue(progress);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte(this.mIgnoreError ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.mShowHeader, flags);
        dest.writeParcelable(this.mProgress, flags);
        dest.writeParcelable(this.mHeader, flags);
        dest.writeParcelable(this.mUrl, flags);
    }

    public HtmlViewData() {
    }

    protected HtmlViewData(Parcel in) {
        super(in);
        this.mIgnoreError = in.readByte() != 0;
        this.mShowHeader = in.readParcelable(BooleanLiveData.class.getClassLoader());
        this.mProgress = in.readParcelable(IntegerLiveData.class.getClassLoader());
        this.mHeader = in.readParcelable(StringLiveData.class.getClassLoader());
        this.mUrl = in.readParcelable(StringLiveData.class.getClassLoader());
    }

    public static final Creator<HtmlViewData> CREATOR = new Creator<HtmlViewData>() {
        @Override
        public HtmlViewData createFromParcel(Parcel source) {
            return new HtmlViewData(source);
        }

        @Override
        public HtmlViewData[] newArray(int size) {
            return new HtmlViewData[size];
        }
    };
}
