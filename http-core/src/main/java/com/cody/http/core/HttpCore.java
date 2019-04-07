/*
 * ************************************************************
 * 文件：HttpCore.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月07日 12:28:20
 * 上次修改时间：2019年04月06日 02:01:01
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core;

import android.content.Context;

/**
 * Created by xu.yi. on 2019/4/6.
 *
 */
public class HttpCore {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        HttpCore.context = context;
    }

}
