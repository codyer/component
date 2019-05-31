/*
 * ************************************************************
 * 文件：CrashUtil.java  模块：cody-component  项目：component
 * 当前修改时间：2019年05月31日 18:46:54
 * 上次修改时间：2019年03月01日 19:38:58
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：cody-component
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.blues;

import android.content.Context;
import android.text.TextUtils;

import com.mmall.jz.app.BuildConfig;
import com.mmall.jz.repository.business.local.LocalKey;
import com.mmall.jz.repository.framework.Repository;
import com.mmall.jz.repository.framework.statistics.HxStat;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cody.yi on 2018/6/8.
 * bugly 配置
 */
public class CrashUtil {

    public static void init(Context context) {
        // 设置是否为上报进程
        //初始化bugly
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setAppChannel(BuildConfig.BUILD_TYPE);
        strategy.setAppVersion(BuildConfig.VERSION_NAME);
        strategy.setAppPackageName(BuildConfig.APPLICATION_ID);
        strategy.setCrashHandleCallback(new CrashHandleCallback());
        if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("release") ||
                BuildConfig.BUILD_TYPE.equalsIgnoreCase("stg")) {
            CrashReport.initCrashReport(context.getApplicationContext(), "13501369e4", BuildConfig.DEBUG, strategy);
        } else {
            CrashReport.initCrashReport(context.getApplicationContext(), "e9750f4265", BuildConfig.DEBUG, strategy);
        }
        String user = Repository.getLocalValue(LocalKey.REAL_NAME) + Repository.getLocalValue(LocalKey.MOBILE_PHONE);
        if (!TextUtils.isEmpty(user)) {
            CrashReport.setUserId(user);
        }
    }

    static class CrashHandleCallback extends CrashReport.CrashHandleCallback {
        @Override
        public synchronized Map<String, String> onCrashHandleStart(int crashType, String errorType,
                                                                   String errorMessage, String errorStack) {
            HashMap<String, String> map = new HashMap<>();
            HxStat.setStatParams(map);
            return map;
        }

        @Override
        public synchronized byte[] onCrashHandleStart2GetExtraDatas(int crashType, String errorType,
                                                                    String errorMessage, String errorStack) {
            return super.onCrashHandleStart2GetExtraDatas(crashType, errorType, errorMessage, errorStack);
        }
    }

    public static void postException(Throwable throwable) {
        CrashReport.postCatchedException(throwable);
    }

    public static void postException(Context context, Throwable throwable) {
        HashMap<String, String> map = new HashMap<>();
        HxStat.setStatParams(map);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            CrashReport.putUserData(context, entry.getKey(), entry.getValue());
        }
        CrashReport.postCatchedException(throwable);
    }
}
