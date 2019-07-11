/*
 * ************************************************************
 * 文件：ToastUtil.java  模块：util-core  项目：component
 * 当前修改时间：2019年07月11日 11:26:06
 * 上次修改时间：2019年03月01日 19:38:59
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：util-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by cody.yi on 2016/8/4.
 * TODO 统一样式
 */
public class ToastUtil {
    private static Toast mToast;

    public static void showToast(String text) {
        showToast(ApplicationUtil.getApplication().getApplicationContext(), text);
    }

    public static void showToast(int resId) {
        showToast(ApplicationUtil.getApplication().getApplicationContext(), resId);
    }

    @SuppressLint("ShowToast")
    public static void showToast(Context context, String text) {
        if (isNotMainThread()) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    @SuppressLint("ShowToast")
    private static void showToast(Context context, int resId) {
        if (isNotMainThread()) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(context.getString(resId));
        }
        mToast.show();
    }

    /**
     * 是否在主线程
     */
    private static boolean isNotMainThread() {
        return Looper.myLooper() != Looper.getMainLooper();
    }
}
