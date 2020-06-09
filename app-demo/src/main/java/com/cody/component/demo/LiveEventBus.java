/*
 * ************************************************************
 * 文件：LivaDataBus_1.java  模块：component-app-demo  项目：component
 * 当前修改时间：2020年06月09日 13:51:43
 * 上次修改时间：2020年06月09日 13:51:42
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：component-app-demo
 * Copyright (c) 2020
 * ************************************************************
 */

package com.cody.component.demo;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xu.yi. on 2020/6/9.
 * LiveEventBus 简单实现
 */
public class LiveEventBus {

    public static <T> MutableLiveData<T> getDefault(String key, Class<T> clz) {
        return ready().with(key, clz);
    }

    private final Map<String, MutableLiveData<Object>> bus;

    private LiveEventBus() {
        bus = new HashMap<>();
    }

    private static class InstanceHolder {
        static final LiveEventBus INSTANCE = new LiveEventBus();
    }

    private static LiveEventBus ready() {
        return LiveEventBus.InstanceHolder.INSTANCE;
    }

    @SuppressWarnings("unchecked")
    private <T> MutableLiveData<T> with(String key, Class<T> clz) {
        if (!bus.containsKey(key)) {
            MutableLiveData<Object> liveData = new MutableLiveData<>();
            bus.put(key, liveData);
        }
        return (MutableLiveData<T>) bus.get(key);
    }
}
