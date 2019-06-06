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

import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cody.yi on 2018/6/8.
 * bugly 配置
 */
public class CrashUtil {

    public static void init(Context context, BluesCallBack callBack) {
        // 设置是否为上报进程
        //初始化bugly
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setAppChannel(BluesConfig.getAppChannel());
        strategy.setAppVersion(BluesConfig.getAppVersion());
        strategy.setAppPackageName(BluesConfig.getAppPackageName());
        strategy.setCrashHandleCallback(new CrashHandleCallback(callBack));
        if (BluesConfig.isTestMode()) {
            CrashReport.initCrashReport(context.getApplicationContext(), BluesConfig.getCrashDebugKey(), BluesConfig.isDebug(), strategy);
        } else {
            CrashReport.initCrashReport(context.getApplicationContext(), BluesConfig.getCrashReleaseKey(), BluesConfig.isDebug(), strategy);
        }
        String user = BluesConfig.getUserId();
        if (!TextUtils.isEmpty(user)) {
            CrashReport.setUserId(user);
        }
    }

    static class CrashHandleCallback extends CrashReport.CrashHandleCallback {
        BluesCallBack mBluesCallBack;

        CrashHandleCallback(BluesCallBack callBack) {
            mBluesCallBack = callBack;
        }

        @Override
        public synchronized Map<String, String> onCrashHandleStart(int crashType, String errorType,
                                                                   String errorMessage, String errorStack) {
            Map<String, String> map = new HashMap<>();
            if (mBluesCallBack != null) {
                mBluesCallBack.fillCrashData(map);
            }
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

    static void postException(Context context, BluesCallBack callBack, Throwable throwable) {
        HashMap<String, String> map = new HashMap<>();
        if (callBack != null) {
            callBack.fillCrashData(map);
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            CrashReport.putUserData(context, entry.getKey(), entry.getValue());
        }
        CrashReport.postCatchedException(throwable);
    }
}
