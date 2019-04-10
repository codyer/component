/*
 * ************************************************************
 * 文件：StubViewData.java  模块：bind-app  项目：component
 * 当前修改时间：2019年04月09日 17:20:13
 * 上次修改时间：2019年04月09日 17:20:13
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.data;

import com.cody.component.app.R;
import com.cody.component.view.data.ViewData;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by xu.yi. on 2019/4/9.
 * 页面出错、无网络、无数据等布局需要的数据
 */
public class StubViewData extends ViewData {
    private static final long serialVersionUID = -381194114860141421L;
    private final MutableLiveData<Boolean> mVisibility = new MutableLiveData<>(false);
    private final MutableLiveData<Integer> mInfoId = new MutableLiveData<>(R.string.no_content);
    private final MutableLiveData<Integer> mImageId = new MutableLiveData<>(R.drawable.ic_no_content);

    public void noStubView() {
        mVisibility.postValue(false);
    }

    public void noContentView() {
        mVisibility.postValue(true);
        mInfoId.postValue(R.string.no_content);
        mImageId.postValue(R.drawable.ic_no_content);
    }

    public void badNetWorkView() {
        mVisibility.postValue(true);
        mInfoId.postValue(R.string.bad_network_view_tip);
        mImageId.postValue(R.drawable.ic_bad_network);
    }

    public void failedView() {
        mVisibility.postValue(true);
        mInfoId.postValue(R.string.load_failed_click_to_reload);
        mImageId.postValue(R.drawable.ic_load_failed);
    }

    public StubViewData() {
    }

    public MutableLiveData<Boolean> getVisibility() {
        return mVisibility;
    }

    public MutableLiveData<Integer> getInfoId() {
        return mInfoId;
    }

    public MutableLiveData<Integer> getImageId() {
        return mImageId;
    }
}
