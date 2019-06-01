/*
 * ************************************************************
 * 文件：BluesHandler.java  模块：cody-component  项目：component
 * 当前修改时间：2019年05月31日 18:46:22
 * 上次修改时间：2019年03月01日 19:38:58
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：cody-component
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.blues;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;


/**
 * Created by cody.yi on 2018/6/6.
 * blues 默认处理类
 */
final public class BluesHandler implements Blues.ExceptionHandler {
    final private static String BLUES_KEY = "BLUES_KEY";
    final private static String BLUES_TOAST = "出现异常\n建议返回重试或重启应用。";
    private Context mContext;
    private BluesCallBack mBluesCallBack;

    BluesHandler(Context context, BluesCallBack callBack) {
        mContext = context;
        mBluesCallBack = callBack;
        // 确保blues初始化在 buglly 之后
        CrashUtil.init(context, callBack);
    }

    @Override
    public void handlerException(final Thread thread, final Throwable throwable) {
        if (mContext == null) {
            CrashUtil.postException(throwable);
            return;
        }

        SharedPreferences settings = mContext.getSharedPreferences(BLUES_KEY, Context.MODE_PRIVATE);
        if (throwable.getStackTrace() != null && settings != null) {
            String blues = settings.getString(BLUES_KEY, "Blues");
            String stackTrace = (throwable.getStackTrace())[0].toString();
            showToast("出现异常：\n" + thread + "\n" + throwable.toString());
            if (TextUtils.equals(blues, stackTrace)) {
                if (mBluesCallBack != null) {
                    mBluesCallBack.sameException(thread, throwable);
                } else {
                    //建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log
                    Log.e("Blues", "--->BluesException:" + thread + "<---", throwable);
                }
            } else {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(BLUES_KEY, stackTrace);
                editor.apply();
                CrashUtil.postException(mContext, mBluesCallBack, throwable);
            }
        }
    }

    private void showToast(final String msg) {
        new Handler(Looper.getMainLooper()).post(() -> {
            try {
                if (mBluesCallBack != null) {
                    mBluesCallBack.showException(BluesConfig.isDebug() ? msg : BLUES_TOAST);
                }
            } catch (Throwable e) {
                CrashUtil.postException(mContext, mBluesCallBack, e);
            }
        });
    }
}
