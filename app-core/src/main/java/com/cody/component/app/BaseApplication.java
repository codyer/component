/*
 * ************************************************************
 * 文件：BaseApplication.java  模块：app-core  项目：component
 * 当前修改时间：2019年05月27日 19:57:57
 * 上次修改时间：2019年05月24日 15:21:32
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app;


import android.text.TextUtils;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import com.cody.component.app.local.Repository;
import com.cody.component.app.widget.swipebacklayout.BGASwipeBackHelper;
import com.cody.component.util.ApplicationUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by xu.yi. on 2019/4/7.
 * component
 */
public class BaseApplication extends MultiDexApplication {
    // This flag should be set to true to enable VectorDrawable support for API < 21
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    /**
     * 只初始化一次
     */
    public void onInit() {
        ApplicationUtil.install(this);
        Repository.install(this);
//        Blues.install(this);
        /*
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this, null);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!isInMainProcess()) {
            return;
        }
        onInit();
    }

    public boolean isInMainProcess() {
        // 获取当前包名
        String packageName = getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        return processName == null || processName.equals(packageName);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
