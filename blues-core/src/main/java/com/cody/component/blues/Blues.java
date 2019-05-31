/*
 * ************************************************************
 * 文件：Blues.java  模块：cody-component  项目：component
 * 当前修改时间：2019年05月31日 18:46:38
 * 上次修改时间：2018年12月07日 16:22:12
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：cody-component
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.blues;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by cody.yi on 2018/6/6.
 * blues
 */
public final class Blues {

    public interface ExceptionHandler {
        /**
         * handlerException内部建议手动
         * try{ 你的异常处理逻辑 }catch(Throwable e){}
         * 以防handlerException内部再次抛出异常，导致循环调用handlerException
         */
        void handlerException(Thread thread, Throwable throwable);
    }

    private Blues() {
    }

    private static ExceptionHandler sExceptionHandler;
    private static Thread.UncaughtExceptionHandler sUncaughtExceptionHandler;
    private static boolean sInstalled = false;//标记位，避免重复安装卸载

    /**
     * 安装 Blues
     */
    public static synchronized void install(final Context context) {
        install(new BluesHandler(context));
    }

    /**
     * 当主线程或子线程抛出异常时会调用exceptionHandler.handlerException(Thread thread, Throwable throwable)
     * <p>
     * exceptionHandler.handlerException可能运行在非UI线程中。
     * <p>
     * 若设置了Thread.setDefaultUncaughtExceptionHandler则可能无法捕获子线程异常。
     *
     * @param exceptionHandler 异常处理
     */
    public static synchronized void install(ExceptionHandler exceptionHandler) {
        if (sInstalled) {
            return;
        }
        sInstalled = true;
        sExceptionHandler = exceptionHandler;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
                        if (e instanceof BluesQuitException) {
                            return;
                        }
                        if (sExceptionHandler != null) {
                            sExceptionHandler.handlerException(Looper.getMainLooper().getThread(), e);
                        }
                    }
                }
            }
        });

        sUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (sExceptionHandler != null) {
                    sExceptionHandler.handlerException(t, e);
                }
            }
        });
    }

    /**
     * 卸载 Blues
     */
    public static synchronized void uninstall() {
        if (!sInstalled) {
            return;
        }
        sInstalled = false;
        sExceptionHandler = null;
        //卸载后恢复默认的异常处理逻辑，否则主线程再次抛出异常后将导致ANR，并且无法捕获到异常位置
        Thread.setDefaultUncaughtExceptionHandler(sUncaughtExceptionHandler);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                throw new BluesQuitException("Quit Blues .....");//主线程抛出异常，迫使 while (true) {}结束
            }
        });
    }
}
