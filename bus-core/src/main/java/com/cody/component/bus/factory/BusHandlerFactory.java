/*
 * ************************************************************
 * 文件：BusHandlerFactory.java  模块：component-bus-core  项目：component
 * 当前修改时间：2020年06月03日 15:55:12
 * 上次修改时间：2020年05月29日 17:49:27
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：component-bus-core
 * Copyright (c) 2020
 * ************************************************************
 */

package com.cody.component.bus.factory;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;

/**
 * 为了减少 Handler的创建，全局使用一个
 */
public class BusHandlerFactory {

    private final Object mLock = new Object();
    private volatile Handler mMainHandler;

    private static class InstanceHolder {
        private static final BusHandlerFactory INSTANCE = new BusHandlerFactory();
    }

    public static BusHandlerFactory ready() {
        return BusHandlerFactory.InstanceHolder.INSTANCE;
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
