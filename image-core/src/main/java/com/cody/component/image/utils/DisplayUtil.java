/*
 * ************************************************************
 * 文件：DisplayUtil.java  模块：image-core  项目：component
 * 当前修改时间：2019年04月22日 11:02:38
 * 上次修改时间：2019年04月22日 11:02:36
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：image-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.image.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by xu.yi. on 2019/4/22.
 * component
 */
public class DisplayUtil {
    private static final String TAG = "DisplayUtil";

    /**
     * dip转px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度和高度，单位为px
     */
    public static Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        Log.i(TAG, "Screen---Width = " + w_screen + " Height = " + h_screen + " densityDpi = " + dm.densityDpi);
        return new Point(w_screen, h_screen);

    }

    /**
     * 获取屏幕长宽比,控制比例不小于1
     */
    public static float getScreenRate(Context context) {
        Point P = getScreenMetrics(context);
        float H = P.y;
        float W = P.x;
        if (H < W) return W / H;
        return (H / W);
    }
}
