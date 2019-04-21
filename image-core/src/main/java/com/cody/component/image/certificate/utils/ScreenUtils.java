/*
 * ************************************************************
 * 文件：ScreenUtils.java  模块：image-pick  项目：component
 * 当前修改时间：2019年04月21日 10:45:25
 * 上次修改时间：2019年04月21日 10:45:25
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：image-pick
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.image.certificate.utils;

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
