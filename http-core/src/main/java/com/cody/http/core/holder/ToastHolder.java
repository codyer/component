/*
 * ************************************************************
 * 文件：ToastHolder.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月06日 02:00:02
 * 上次修改时间：2019年03月25日 09:23:00
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.holder;

import android.content.Context;
import android.widget.Toast;

import com.cody.http.core.HttpCore;

/**
 * Created by xu.yi. on 2019/4/6.
 *
 */
public class ToastHolder {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String message) {
        showToast(HttpCore.getContext(), message);
    }

}
