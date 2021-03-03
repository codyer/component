/*
 * ************************************************************
 * 文件：LongLiveData.java  模块：component.handler-core  项目：component
 * 当前修改时间：2021年03月03日 23:46:06
 * 上次修改时间：2021年02月27日 15:56:36
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：component.handler-core
 * Copyright (c) 2021
 * ************************************************************
 */

package com.cody.component.handler.livedata;

/**
 * Created by xu.yi. on 2019/4/15.
 * component
 */
public class LongLiveData extends SafeMutableLiveData<Long> {

    public LongLiveData(final Long value) {
        super(value);
    }

    public long get() {
        Long old = getValue();
        if (old != null) {
            return old;
        }
        return 0L;
    }
}