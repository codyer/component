/*
 * ************************************************************
 * 文件：LogUtil.java  模块：util-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:44:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：util-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.util;

import android.util.Log;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
    private static int RUNNING_MODE = 1;

    /**
     * 日志状态
     */
    @IntDef({RUNNING_MODE_DEBUG, RUNNING_MODE_INFO, RUNNING_MODE_WARRING, RUNNING_MODE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LogMode {
    }

    public static final int RUNNING_MODE_DEBUG = 1;
    public static final int RUNNING_MODE_INFO = 2;
    public static final int RUNNING_MODE_WARRING = 3;
    public static final int RUNNING_MODE_ERROR = 4;
    public static final String LOG_TAG = "Foundation";

    public static void setMode(@LogMode int mode) {
        RUNNING_MODE = mode;
    }

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
        i(LOG_TAG, message);
    }

    public static void i(String tag, String message) {
        if (tag == null || message == null) {
            return;
        }
        if (RUNNING_MODE <= RUNNING_MODE_INFO) {
            Log.i(tag, message);
        }
    }

    public static void i(String message, Throwable t) {
        i(LOG_TAG, message, t);
    }

    public static void i(String tag, String message, Throwable t) {
        if (tag == null || message == null || t == null) {
            return;
        }
        if (RUNNING_MODE <= RUNNING_MODE_INFO) {
            Log.i(tag, message, t);
        }
    }

    public static void w(String message) {
        if (message == null) {
            return;
        }
        if (RUNNING_MODE <= RUNNING_MODE_WARRING) {
            Log.w(LOG_TAG, message);
        }
    }

    public static void w(String message, Throwable t) {
        w(LOG_TAG, message, t);
    }

    public static void w(String tag, String message, Throwable t) {
        if (tag == null || message == null || t == null) {
            return;
        }
        if (RUNNING_MODE <= RUNNING_MODE_WARRING) {
            Log.w(tag, message, t);
        }
    }

    public static void e(String message) {
        e(LOG_TAG, message);
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
        e(LOG_TAG, message, t);
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
