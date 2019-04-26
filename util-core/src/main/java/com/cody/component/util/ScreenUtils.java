/*
 * ************************************************************
 * 文件：ScreenUtils.java  模块：util-core  项目：component
 * 当前修改时间：2019年04月26日 13:33:35
 * 上次修改时间：2019年04月23日 18:23:20
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：util-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.util;

import android.content.Context;

/**
 * Desc	 ${屏幕相关工具类}
 */
public class ScreenUtils {

    /**
     * 获取屏幕宽度（px）
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度（px）
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
