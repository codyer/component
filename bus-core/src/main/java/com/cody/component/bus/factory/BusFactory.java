/*
 * ************************************************************
 * 文件：BusFactory.java  模块：bus-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:54
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bus-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bus.factory;


import androidx.annotation.NonNull;

import com.cody.component.bus.wrapper.LiveEventWrapper;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xu.yi. on 2019/3/31.
 * 和生命周期绑定的事件总线,创建基于事件的总线，对不同scope进行隔离
 */
public class BusFactory {
    private final ExecutorService mExecutorService;
    private final HashMap<String, ScopeHolder<Object>> mScopeBus;//不同scope的bus集

    private static class InstanceHolder {
        private static final BusFactory INSTANCE = new BusFactory();
    }

    public static BusFactory ready() {
        return InstanceHolder.INSTANCE;
    }

    private BusFactory() {
        mScopeBus = new HashMap<>();
        mExecutorService = Executors.newCachedThreadPool();
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public <T> LiveEventWrapper<T> create(String scope, String event) {
        ScopeHolder<Object> scopeHolder = null;
        if (mScopeBus.containsKey(scope)) {
            scopeHolder = mScopeBus.get(scope);
        }
        if (scopeHolder == null) {
            scopeHolder = new ScopeHolder<>(scope, event);
            mScopeBus.put(scope, scopeHolder);
        }
        return (LiveEventWrapper<T>) scopeHolder.getBus(event);
    }

    public ExecutorService getExecutorService() {
        return mExecutorService;
    }

    /**
     * 每个scope一个总线集
     * 每个scope是独立的，不同scope之间事件不互通
     *
     * @param <T>
     */
    final static class ScopeHolder<T> {
        final String scope;
        final HashMap<String, LiveEventWrapper<T>> eventBus = new HashMap<>();

        ScopeHolder(String scopeName, String event) {
            if (!eventBus.containsKey(event)) {
                eventBus.put(event, new LiveEventWrapper<>());
            }
            scope = scopeName;
        }

        LiveEventWrapper<T> getBus(String event) {
            LiveEventWrapper<T> bus;
            if (eventBus.containsKey(event)) {
                bus = eventBus.get(event);
            } else {
                bus = new LiveEventWrapper<>();
                eventBus.put(event, bus);
            }
            return bus;
        }
    }
}
