/*
 * ************************************************************
 * 文件：HtmlViewData.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月11日 12:53:48
 * 上次修改时间：2019年04月11日 12:53:48
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.data;

import com.cody.component.hybrid.R;
import com.cody.component.lib.BuildConfig;
import com.cody.component.lib.data.ViewData;
import com.cody.component.lib.safe.SafeMutableLiveData;

/**
 * Created by xu.yi. on 2019/4/11.
 * html页面数据
 */
public class HtmlViewData extends ViewData {
    private static final long serialVersionUID = -5654022087355170345L;
    public final static int MAX_PROGRESS = 100;
    private boolean mIgnoreError = false;
    final private SafeMutableLiveData<Boolean> mLoading = new SafeMutableLiveData<>(true);
    final private SafeMutableLiveData<Boolean> mShowHeader = new SafeMutableLiveData<>(true);
    final private SafeMutableLiveData<Boolean> mError = new SafeMutableLiveData<>(false);
    final private SafeMutableLiveData<Boolean> mDebug = new SafeMutableLiveData<>(BuildConfig.DEBUG);
    final private SafeMutableLiveData<Integer> mProgress = new SafeMutableLiveData<>(0);
    final private SafeMutableLiveData<String> mHeader = new SafeMutableLiveData<>("");
    final private SafeMutableLiveData<String> mMessage = new SafeMutableLiveData<>("");
    final private SafeMutableLiveData<String> mUrl = new SafeMutableLiveData<>("");
    private int loadingResId = R.drawable.ic_html_loading_gif;

    public int getLoadingResId() {
        return loadingResId;
    }

    public void setLoadingResId(final int loadingResId) {
        this.loadingResId = loadingResId;
    }

    public SafeMutableLiveData<Boolean> getLoading() {
        return mLoading;
    }

    public SafeMutableLiveData<Boolean> getShowHeader() {
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

    public SafeMutableLiveData<Boolean> getError() {
        return mError;
    }

    public SafeMutableLiveData<Boolean> getDebug() {
        return mDebug;
    }

    public SafeMutableLiveData<Integer> getProgress() {
        return mProgress;
    }

    public SafeMutableLiveData<String> getHeader() {
        return mHeader;
    }

    public SafeMutableLiveData<String> getMessage() {
        return mMessage;
    }

    public void setUrl(String url) {
        setProgress(0);
        mUrl.setValue(url);
    }

    public SafeMutableLiveData<String> getUrl() {
        return mUrl;
    }

    public void setProgress(final int progress) {
        mProgress.setValue(progress);
        mLoading.setValue(progress < MAX_PROGRESS);
    }
}
