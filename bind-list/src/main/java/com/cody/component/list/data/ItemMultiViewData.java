/*
 * ************************************************************
 * 文件：ItemMultiViewData.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月09日 15:13:51
 * 上次修改时间：2019年04月08日 17:38:08
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.data;

import com.cody.component.lib.data.ItemViewData;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by xu.yi. on 2019/4/8.
 * 列表底部数据类
 */
public class ItemMultiViewData extends ItemViewData {
    private static final long serialVersionUID = 6101620960031035326L;
    final private MutableLiveData<Boolean> mNoMoreItem = new MutableLiveData<>();
    final private MutableLiveData<Boolean> mError = new MutableLiveData<>();
    final private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    final private MutableLiveData<String> mErrorMessage = new MutableLiveData<>();

    public MutableLiveData<Boolean> getNoMoreItem() {
        return mNoMoreItem;
    }

    public MutableLiveData<Boolean> getError() {
        return mError;
    }

    public MutableLiveData<Boolean> getLoading() {
        return mLoading;
    }

    public MutableLiveData<String> getErrorMessage() {
        return mErrorMessage;
    }
}