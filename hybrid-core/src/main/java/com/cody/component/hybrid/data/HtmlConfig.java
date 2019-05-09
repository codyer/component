/*
 * ************************************************************
 * 文件：HtmlConfig.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:44:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xu.yi. on 2019/4/11.
 * 打开 html 需要的配置参数保存类
 */
public class HtmlConfig implements Parcelable {
    private String mTitle = "";
    private String mDescription;
    private String mUrl;
    private boolean mIsRoot;
    private boolean mCanShare;

    public String getTitle() {
        return mTitle;
    }

    public HtmlConfig setTitle(final String title) {
        mTitle = title;
        return this;
    }

    public String getDescription() {
        return mDescription;
    }

    public HtmlConfig setDescription(final String description) {
        mDescription = description;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public HtmlConfig setUrl(final String url) {
        mUrl = url;
        return this;
    }

    public boolean isRoot() {
        return mIsRoot;
    }

    public HtmlConfig setRoot(final boolean root) {
        mIsRoot = root;
        return this;
    }

    public boolean isCanShare() {
        return mCanShare;
    }

    public HtmlConfig setCanShare(final boolean canShare) {
        mCanShare = canShare;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mDescription);
        dest.writeString(this.mUrl);
        dest.writeByte(this.mIsRoot ? (byte) 1 : (byte) 0);
        dest.writeByte(this.mCanShare ? (byte) 1 : (byte) 0);
    }

    public HtmlConfig() {
    }

    protected HtmlConfig(Parcel in) {
        this.mTitle = in.readString();
        this.mDescription = in.readString();
        this.mUrl = in.readString();
        this.mIsRoot = in.readByte() != 0;
        this.mCanShare = in.readByte() != 0;
    }

    public static final Creator<HtmlConfig> CREATOR = new Creator<HtmlConfig>() {
        @Override
        public HtmlConfig createFromParcel(Parcel source) {
            return new HtmlConfig(source);
        }

        @Override
        public HtmlConfig[] newArray(int size) {
            return new HtmlConfig[size];
        }
    };
}
