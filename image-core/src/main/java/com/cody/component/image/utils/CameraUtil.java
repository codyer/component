/*
 * ************************************************************
 * 文件：CameraUtil.java  模块：image-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月22日 12:00:37
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：image-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.image.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;

/**
 * Created by xu.yi. on 2019/4/22.
 * component
 */
public class CameraUtil {
    private static final String TAG = "CameraUtil";
    private static CameraUtil myCamPara = null;

    private CameraUtil() {

    }

    public static CameraUtil getInstance() {
        if (myCamPara == null) {
            myCamPara = new CameraUtil();
            return myCamPara;
        } else {
            return myCamPara;
        }
    }

    public Size getBestSize(List<Size> list, float rate, int minWidth) {
        int i = 0;
        int pos = 0;
        float abs = 1.0f;
        for (Size s : list) {
            if ((s.width >= minWidth)) {
                float r = (s.width > s.height) ? (float) (s.width) / (float) (s.height) : (float) (s.height) / (float) (s.width);
                float newAbs = Math.abs(r - rate);
                if (newAbs < abs) {
                    abs = newAbs;
                    pos = i;
                }
                Log.i(TAG, "PictureSize : w = " + s.width + "h = " + s.height);
            }
            i++;
        }
        return list.get(pos);
    }

    /**
     * 打印支持的previewSizes
     *
     * @param params
     */
    public void printSupportPreviewSize(Camera.Parameters params) {
        List<Size> previewSizes = params.getSupportedPreviewSizes();
        for (int i = 0; i < previewSizes.size(); i++) {
            Size size = previewSizes.get(i);
            Log.i(TAG, "previewSizes:width = " + size.width + " height = " + size.height);
        }
    }

    /**
     * 打印支持的pictureSizes
     *
     * @param params
     */
    public void printSupportPictureSize(Camera.Parameters params) {
        List<Size> pictureSizes = params.getSupportedPictureSizes();
        for (int i = 0; i < pictureSizes.size(); i++) {
            Size size = pictureSizes.get(i);
            Log.i(TAG, "pictureSizes:width = " + size.width
                    + " height = " + size.height);
        }
    }

    /**
     * 打印支持的聚焦模式
     *
     * @param params
     */
    public void printSupportFocusMode(Camera.Parameters params) {
        List<String> focusModes = params.getSupportedFocusModes();
        for (String mode : focusModes) {
            Log.i(TAG, "focusModes--" + mode);
        }
    }
}
