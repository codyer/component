/*
 * ************************************************************
 * 文件：ToastHolder.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http.holder;

import android.content.Context;
import android.widget.Toast;

import com.cody.component.http.HttpCore;

/**
 * Created by xu.yi. on 2019/4/6.
 */
public class ToastHolder {

    private static void showToast(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String message) {
        if (HttpCore.getInstance().getContext() != null) {
            showToast(HttpCore.getInstance().getContext(), message);
        }
    }

    public static void showToast(int message) {
        if (HttpCore.getInstance().getContext() != null) {
            showToast(HttpCore.getInstance().getContext(), message);
        }
    }
}
