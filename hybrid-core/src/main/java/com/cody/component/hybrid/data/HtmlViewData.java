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

import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.livedata.BooleanLiveData;
import com.cody.component.handler.livedata.IntegerLiveData;
import com.cody.component.handler.livedata.StringLiveData;

/**
 * Created by xu.yi. on 2019/4/11.
 * html页面数据
 */
public class HtmlViewData extends MaskViewData {
    private static final long serialVersionUID = -5654022087355170345L;
    public final static int MAX_PROGRESS = 100;
    private boolean mIgnoreError = false;
    final private BooleanLiveData mShowHeader = new BooleanLiveData(true);
    final private IntegerLiveData mProgress = new IntegerLiveData(0);
    final private StringLiveData mHeader = new StringLiveData("");
    final private StringLiveData mUrl = new StringLiveData("");

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
        getLoading().setValue(progress < MAX_PROGRESS);
    }
}
