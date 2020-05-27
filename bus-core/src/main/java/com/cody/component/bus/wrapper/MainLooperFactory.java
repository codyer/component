/*
 * ************************************************************
 * 文件：MainLooperFactory.java  模块：bus-core  项目：component
 * 当前修改时间：2020年05月28日 00:11:00
 * 上次修改时间：2020年05月28日 00:10:59
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bus-core
 * Copyright (c) 2020
 * ************************************************************
 */

package com.cody.component.bus.wrapper;

import android.os.Handler;
import android.os.Looper;

/**
 * 为了减少 Handler的创建，全局使用一个
 */
class MainLooperFactory {

    private final Object mLock = new Object();
    private volatile Handler mMainHandler;

    private static class InstanceHolder {
        private static final MainLooperFactory INSTANCE = new MainLooperFactory();
    }

    static MainLooperFactory ready() {
        return MainLooperFactory.InstanceHolder.INSTANCE;
    }

    Handler getMainHandler() {
        if (mMainHandler == null) {
            synchronized (mLock) {
                if (mMainHandler == null) {
                    mMainHandler = Handler.createAsync(Looper.getMainLooper());
                }
            }
        }
        return mMainHandler;
    }

}
