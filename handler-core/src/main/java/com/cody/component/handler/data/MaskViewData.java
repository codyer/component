/*
 * ************************************************************
 * 文件：MaskViewData.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月24日 09:39:11
 * 上次修改时间：2019年04月24日 09:38:27
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.data;

import com.cody.component.handler.R;
import com.cody.component.handler.livedata.BooleanLiveData;
import com.cody.component.handler.livedata.IntegerLiveData;
import com.cody.component.handler.livedata.StringLiveData;

import androidx.annotation.NonNull;

/**
 * Created by xu.yi. on 2019/4/9.
 * 页面出错、无网络、无数据等布局需要的数据
 */
public class MaskViewData extends ViewData {
    private static final long serialVersionUID = -381194114860141421L;
    private final BooleanLiveData mVisibility = new BooleanLiveData(false);
    private final IntegerLiveData mInfoId = new IntegerLiveData(R.string.ui_str_no_content);
    private final StringLiveData mMessage = new StringLiveData("");
    final private BooleanLiveData mLoading = new BooleanLiveData(false);
    private final IntegerLiveData mImageId = new IntegerLiveData(R.drawable.ic_no_content);

    public void hideMaskView() {
        mVisibility.postValue(false);
    }

    public void noContentView() {
        mVisibility.postValue(true);
        mLoading.postValue(false);
        mInfoId.postValue(R.string.ui_str_no_content);
        mImageId.postValue(R.drawable.ic_no_content);
    }

    public void badNetWorkView() {
        mVisibility.postValue(true);
        mLoading.postValue(false);
        mInfoId.postValue(R.string.ui_str_bad_network_view_tip);
        mImageId.postValue(R.drawable.ic_bad_network);
    }

    public void startLoading() {
        mVisibility.postValue(true);
        mLoading.postValue(true);
        mInfoId.postValue(R.string.ui_str_loading);
    }

    public void failedView(String message) {
        mMessage.postValue(message);
        failedView();
    }

    public void failedView() {
        mVisibility.postValue(true);
        mLoading.postValue(false);
        mInfoId.postValue(R.string.ui_str_load_failed_click_to_reload);
        mImageId.postValue(R.drawable.ic_load_failed);
    }

    public MaskViewData() {
    }

    public BooleanLiveData getLoading() {
        return mLoading;
    }

    @NonNull
    public BooleanLiveData getVisibility() {
        return mVisibility;
    }

    public IntegerLiveData getInfoId() {
        return mInfoId;
    }

    public StringLiveData getMessage() {
        return mMessage;
    }

    public IntegerLiveData getImageId() {
        return mImageId;
    }
}
