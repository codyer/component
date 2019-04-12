/*
 * ************************************************************
 * 文件：SafeMutableLiveData.java  模块：bind-data-lib  项目：component
 * 当前修改时间：2019年04月12日 09:21:20
 * 上次修改时间：2019年04月11日 15:06:52
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-data-lib
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.lib.safe;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by xu.yi. on 2019/4/11.
 * 防止空指针
 */
public class SafeMutableLiveData<T> extends MutableLiveData<T> {
    public SafeMutableLiveData(final T value) {
        super();
        if (value != null) {
            setValue(value);
        }
    }

    public SafeMutableLiveData() {
    }

    @Override
    public void postValue(final T value) {
        if (value != null) {
            super.postValue(value);
        }
    }

    @Override
    public void setValue(final T value) {
        if (value != null) {
            super.setValue(value);
        }
    }
}
