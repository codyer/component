/*
 * ************************************************************
 * 文件：ObserverWrapper.java  模块：bus-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bus-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bus.wrapper;

import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;

/**
 * Created by xu.yi. on 2019/3/31.
 * 不要主动改变属性值
 */
public abstract class ObserverWrapper<T> {
    // 每个观察者都记录自己序号，只有在进入观察状态之后产生的数据才通知到观察者
    int sequence;
    Observer<ValueWrapper<T>> observer;

    public abstract void onChanged(@Nullable T t);
}
