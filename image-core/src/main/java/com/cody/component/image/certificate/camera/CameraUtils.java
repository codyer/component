/*
 * ************************************************************
 * 文件：CameraUtils.java  模块：image-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月21日 11:14:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：image-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.image.certificate.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.view.Surface;
import android.view.WindowManager;

import com.isseiaoki.simplecropview.util.Logger;

/**
 * Desc	 ${相机工具类}
 */
public class CameraUtils {

    private static Camera camera;

    /**
     * @param context 上下文
     * @return 检查是否有相机
     */
    public static boolean hasCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * @return 打开相机
     */
    public static Camera openCamera() {
        camera = null;
        try {
            camera = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return camera; // returns null if camera is unavailable
    }

    public static Camera getCamera() {
        return camera;
    }

    /**
     * @return 适配相机旋转
     * @param context 上下文
     */
    public static int getCameraDisplayOrientation(Context context) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(getDefaultCameraId(), info);
        int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        //前置
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        }
        //后置
        else {
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }

    /**
     * 获取摄像头ID
     */
    private static int getDefaultCameraId() {
        int defaultId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                defaultId = i;
            }
        }
        if (defaultId == -1) {
            if (numberOfCameras > 0) {
                //没有后置摄像头
                defaultId = 0;
            } else {
                Logger.e("没有摄像头");
            }
        }
        return defaultId;
    }
}
