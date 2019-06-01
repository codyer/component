/*
 * ************************************************************
 * 文件：BluesConfig.java  模块：blues-core  项目：component
 * 当前修改时间：2019年06月01日 13:55:30
 * 上次修改时间：2019年06月01日 13:55:30
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：blues-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.blues;

public class BluesConfig {
    private static boolean sDebug;
    private static boolean sTestMode;
    private static String sUserId;
    private static String sAppChannel;
    private static String sAppVersion;
    private static String sAppPackageName;
    private static String sCrashDebugKey;
    private static String sCrashReleaseKey;

    public static boolean isDebug() {
        return sDebug;
    }

    public static void setDebug(final boolean debug) {
        sDebug = debug;
    }

    public static boolean isTestMode() {
        return sTestMode;
    }

    public static void setTestMode(final boolean testMode) {
        sTestMode = testMode;
    }

    public static String getUserId() {
        return sUserId;
    }

    public static void setUserId(final String userId) {
        sUserId = userId;
    }

    public static String getAppChannel() {
        return sAppChannel;
    }

    public static void setAppChannel(final String appChannel) {
        sAppChannel = appChannel;
    }

    public static String getAppVersion() {
        return sAppVersion;
    }

    public static void setAppVersion(final String appVersion) {
        sAppVersion = appVersion;
    }

    public static String getAppPackageName() {
        return sAppPackageName;
    }

    public static void setAppPackageName(final String appPackageName) {
        sAppPackageName = appPackageName;
    }

    public static String getCrashDebugKey() {
        return sCrashDebugKey;
    }

    public static void setCrashDebugKey(final String crashDebugKey) {
        sCrashDebugKey = crashDebugKey;
    }

    public static String getCrashReleaseKey() {
        return sCrashReleaseKey;
    }

    public static void setCrashReleaseKey(final String crashReleaseKey) {
        sCrashReleaseKey = crashReleaseKey;
    }
}
