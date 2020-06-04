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


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.cody.component.bus.wrapper.LiveEventWrapper;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xu.yi. on 2019/3/31.
 * 和生命周期绑定的事件总线,创建基于事件的总线，对不同scope进行隔离
 */
public class BusFactory {
    private final Object mLock = new Object();
    private volatile Handler mMainHandler;
    private final ExecutorService mExecutorService;
    private final HashMap<String, ScopeHolder> mScopeBus;//不同scope的bus集

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
    public <T> LiveEventWrapper<T> create(String scope, String event) {
        ScopeHolder scopeHolder = null;
        if (mScopeBus.containsKey(scope)) {
            scopeHolder = mScopeBus.get(scope);
        }
        if (scopeHolder == null) {
            scopeHolder = new ScopeHolder(scope, event);
            mScopeBus.put(scope, scopeHolder);
        }
        return scopeHolder.getBus(event);
    }

    public ExecutorService getExecutorService() {
        return mExecutorService;
    }

    public Handler getMainHandler() {
        if (mMainHandler == null) {
            synchronized (mLock) {
                if (mMainHandler == null) {
                    mMainHandler = createAsync(Looper.getMainLooper());
                }
            }
        }
        return mMainHandler;
    }

    /**
     * 每个scope一个总线集
     * 每个scope是独立的，不同scope之间事件不互通
     */
    final static class ScopeHolder {
        final String scope;
        final HashMap<String, LiveEventWrapper<?>> eventBus = new HashMap<>();

        ScopeHolder(String scopeName, String event) {
            if (!eventBus.containsKey(event)) {
                eventBus.put(event, new LiveEventWrapper<>());
            }
            scope = scopeName;
        }

        @SuppressWarnings("unchecked")
        <T> LiveEventWrapper<T> getBus(String event) {
            LiveEventWrapper<T> bus;
            if (eventBus.containsKey(event)) {
                bus = (LiveEventWrapper<T>) eventBus.get(event);
            } else {
                bus = new LiveEventWrapper<>();
                eventBus.put(event, bus);
            }
            return bus;
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private static Handler createAsync(@NonNull Looper looper) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Handler.createAsync(looper);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            try {
                return Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, boolean.class)
                        .newInstance(looper, null, true);
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException ignored) {
            } catch (InvocationTargetException e) {
                return new Handler(looper);
            }
        }
        return new Handler(looper);
    }
}
