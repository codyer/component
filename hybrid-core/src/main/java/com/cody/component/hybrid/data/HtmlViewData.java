/*
 * ************************************************************
 * 文件：HtmlViewData.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月13日 08:43:55
 * 上次修改时间：2019年04月12日 21:41:40
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.data;

import com.cody.component.hybrid.R;
import com.cody.component.lib.BuildConfig;
import com.cody.component.lib.data.ViewData;
import com.cody.component.lib.safe.BooleanLiveData;
import com.cody.component.lib.safe.IntegerLiveData;
import com.cody.component.lib.safe.StringLiveData;

/**
 * Created by xu.yi. on 2019/4/11.
 * html页面数据
 */
public class HtmlViewData extends ViewData {
    private static final long serialVersionUID = -5654022087355170345L;
    public final static int MAX_PROGRESS = 100;
    private boolean mIgnoreError = false;
    final private BooleanLiveData mLoading = new BooleanLiveData(true);
    final private BooleanLiveData mShowHeader = new BooleanLiveData(true);
    final private BooleanLiveData mError = new BooleanLiveData(false);
    final private BooleanLiveData mDebug = new BooleanLiveData(BuildConfig.DEBUG);
    final private IntegerLiveData mProgress = new IntegerLiveData(0);
    final private StringLiveData mHeader = new StringLiveData("");
    final private StringLiveData mMessage = new StringLiveData("");
    final private StringLiveData mUrl = new StringLiveData("");
    private int loadingResId = R.drawable.ic_loading_gif;

    public int getLoadingResId() {
        return loadingResId;
    }

    public void setLoadingResId(final int loadingResId) {
        this.loadingResId = loadingResId;
    }

    public BooleanLiveData getLoading() {
        return mLoading;
    }

    public BooleanLiveData getShowHeader() {
        return mShowHeader;
    }

    public void setError(boolean error) {
        if (!mIgnoreError || !error) {
            mError.setValue(error);
        }
    }

    public void setIgnoreError(final boolean ignoreError) {
        mIgnoreError = ignoreError;
        setError(false);
    }

    public BooleanLiveData getError() {
        return mError;
    }

    public BooleanLiveData getDebug() {
        return mDebug;
    }

    public IntegerLiveData getProgress() {
        return mProgress;
    }

    public StringLiveData getHeader() {
        return mHeader;
    }

    public StringLiveData getMessage() {
        return mMessage;
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
        mLoading.setValue(progress < MAX_PROGRESS);
    }
}
