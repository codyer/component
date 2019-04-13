/*
 * ************************************************************
 * 文件：LogUtil.java  模块：util-core  项目：component
 * 当前修改时间：2019年04月13日 08:43:55
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：util-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.util;

import android.util.Log;

/**
 * Created by Cody.yi on 2016/7/13.
 * 日志工具类
 */
public class LogUtil {
    /**
     * 运行模式 DEBUG = 1; INFO = 2; WARN = 3; ERROR = 4; 优先级DEBUG < INFO < WARN <
     * ERROR
     * RUNNING_MODE大于4时Log关闭
     */
    public static final int RUNNING_MODE = 1;
    public static final int RUNNING_MODE_DEBUG = 1;
    public static final int RUNNING_MODE_INFO = 2;
    public static final int RUNNING_MODE_WRING = 3;
    public static final int RUNNING_MODE_ERROR = 4;
    public static final String LOG_TAG = "Foundation";

    public static void d(String message) {
        if (message == null) {
            return;
        }
        if (RUNNING_MODE <= RUNNING_MODE_DEBUG) {
            Log.d(LOG_TAG, message);
        }
    }

    public static void d(String tag, String message) {
        if (null == message || null == tag) {
            return;
        }
        if (RUNNING_MODE <= RUNNING_MODE_DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void d(String message, Throwable t) {
        if (message == null || t == null) {
            return;
        }
        if (RUNNING_MODE <= RUNNING_MODE_DEBUG) {
            Log.d(LOG_TAG, message, t);
        }
    }

    public static void i(String message) {
        if (message == null) {
            return;
        }
        if (RUNNING_MODE <= RUNNING_MODE_INFO) {
            Log.i(LOG_TAG, message);
        }
    }

    public static void i(String message, Throwable t) {
        if (message == null || t == null) {
            return;
        }
        if (RUNNING_MODE <= RUNNING_MODE_INFO) {
            Log.i(LOG_TAG, message, t);
        }
    }

    public static void w(String message) {
        if (message == null) {
            return;
        }
        if (RUNNING_MODE <= RUNNING_MODE_WRING) {
            Log.w(LOG_TAG, message);
        }
    }

    public static void w(String message, Throwable t) {
        if (message == null || t == null) {
            return;
        }
        if (RUNNING_MODE <= RUNNING_MODE_WRING) {
            Log.w(LOG_TAG, message, t);
        }
    }

    public static void e(String message) {
        if (message == null) {
            return;
        }
        if (RUNNING_MODE <= RUNNING_MODE_ERROR) {
            Log.e(LOG_TAG, message);
        }
    }

    public static void e(String tag, String message) {
        if (null == message || null == tag) {
            return;
        }
        if (RUNNING_MODE <= RUNNING_MODE_ERROR) {
            Log.e(tag, message);
        }
    }

    public static void e(String message, Throwable t) {
        if (message == null || t == null) {
            return;
        }
        if (RUNNING_MODE <= RUNNING_MODE_ERROR) {
            Log.e(LOG_TAG, message, t);
        }
    }

    public static void e(String tag, String message, Throwable t) {
        if (null == message || null == tag || t == null) {
            return;
        }
        if (RUNNING_MODE <= RUNNING_MODE_ERROR) {
            Log.e(tag, message, t);
        }
    }
}
