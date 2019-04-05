/*
 * ************************************************************
 * 文件：LauncherUtil.java  模块：http-monitor  项目：component
 * 当前修改时间：2019年04月05日 21:43:47
 * 上次修改时间：2019年04月05日 21:43:47
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.monitor.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by xu.yi. on 2019/4/5.
 * component
 */
public class LauncherUtil {
    /**
     * 显示隐藏App图标
     */
    static public void launcherVisible(Context context, Class launcher, boolean visible) {
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, launcher);
        int res = getLauncherStats(packageManager, componentName);
        if (visible == (res == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT || res == PackageManager.COMPONENT_ENABLED_STATE_ENABLED)) return;
        if (visible) {
            showLauncher(packageManager, componentName);
        } else {
            hideLauncher(packageManager, componentName);
        }
    }

    public static void hideLauncher(PackageManager packageManager, ComponentName componentName) {
        try {
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLauncher(PackageManager packageManager, ComponentName componentName) {
        try {
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getLauncherStats(PackageManager packageManager, ComponentName componentName) {
        try {
            return packageManager.getComponentEnabledSetting(componentName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PackageManager.COMPONENT_ENABLED_STATE_DEFAULT;
    }
}
