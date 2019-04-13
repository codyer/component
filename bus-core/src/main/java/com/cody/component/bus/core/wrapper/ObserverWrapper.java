/*
 * ************************************************************
 * 文件：ObserverWrapper.java  模块：bus-core  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bus-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bus.core.wrapper;

import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;

/**
 * Created by xu.yi. on 2019/3/31.
 * 不要主动改变属性值
 */
public abstract class ObserverWrapper<T> {
    int sequence;
    Observer<ValueWrapper<T>> observer;

    public abstract void onChanged(@Nullable T t);
}
