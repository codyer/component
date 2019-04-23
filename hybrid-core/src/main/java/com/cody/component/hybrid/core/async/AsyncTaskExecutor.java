/*
 * ************************************************************
 * 文件：AsyncTaskExecutor.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:44:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.core.async;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cody.yi on 17/4/12.
 * AsyncTaskExecutor
 */
public class AsyncTaskExecutor {
    private static final int JS_BRIDGE_TASK_THREAD_NUM = 3;
    private static final ThreadPoolExecutor ASYNC_THREAD_POOL;

    static {
        ASYNC_THREAD_POOL = new ThreadPoolExecutor(
                JS_BRIDGE_TASK_THREAD_NUM,
                JS_BRIDGE_TASK_THREAD_NUM,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new AsyncTaskThreadFactory());
    }

    public static void runOnAsyncThread(Runnable runnable) {
        if (runnable == null)
            return;
        ASYNC_THREAD_POOL.execute(runnable);
    }

    public static void runOnMainThread(Runnable runnable) {
        if (runnable == null)
            return;
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void shutDown() {
        if (ASYNC_THREAD_POOL != null && !ASYNC_THREAD_POOL.isShutdown()
                && !ASYNC_THREAD_POOL.isTerminating()) {
            ASYNC_THREAD_POOL.shutdown();
        }
    }
}
