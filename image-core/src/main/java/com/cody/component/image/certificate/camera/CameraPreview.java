/*
 * ************************************************************
 * 文件：CameraPreview.java  模块：image-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月22日 12:00:37
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：image-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.image.certificate.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.cody.component.image.utils.CameraUtil;
import com.cody.component.image.utils.DisplayUtil;

import java.io.IOException;
import java.util.List;

/**
 * Desc	${相机预览}
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private static String TAG = CameraPreview.class.getName();

    private Camera mCamera;
    private AutoFocusManager mAutoFocusManager;
    private SensorController mSensorController;
    private Context mContext;
    private SurfaceHolder mSurfaceHolder;

    public CameraPreview(Context context) {
        super(context);
        init(context);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setKeepScreenOn(true);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSensorController = SensorController.getInstance(context);
        mSensorController.setCameraFocusListener(this::focus);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = CameraUtils.openCamera();
        float previewRate = DisplayUtil.getScreenRate(mContext); //默认全屏的比例预览
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPictureFormat(android.graphics.ImageFormat.JPEG);//设置拍照后存储的图片格式
            CameraUtil.getInstance().printSupportPictureSize(parameters);
            CameraUtil.getInstance().printSupportPreviewSize(parameters);
            //设置PreviewSize和PictureSize
            Camera.Size pictureSize = CameraUtil.getInstance()
                    .getBestSize(parameters.getSupportedPictureSizes(), previewRate, 800);
            parameters.setPictureSize(pictureSize.width, pictureSize.height);
            Camera.Size previewSize = CameraUtil.getInstance()
                    .getBestSize(parameters.getSupportedPreviewSizes(), previewRate, 800);
            parameters.setPreviewSize(previewSize.width, previewSize.height);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                //竖屏拍照时，需要设置旋转90度，否者看到的相机预览方向和界面方向不相同
                mCamera.setDisplayOrientation(90);
                parameters.setRotation(90);
            } else {
                mCamera.setDisplayOrientation(0);
                parameters.setRotation(0);
            }

            CameraUtil.getInstance().printSupportFocusMode(parameters);
            List<String> focusModes = parameters.getSupportedFocusModes();
            if (focusModes.contains("continuous-video")) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            }
            mCamera.setParameters(parameters);

            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();//开启预览
                focus();//首次对焦
                parameters = mCamera.getParameters(); //重新get一次
                Log.i(TAG, "最终设置:PreviewSize--With = " + parameters.getPreviewSize().width
                        + "Height = " + parameters.getPreviewSize().height);
                Log.i(TAG, "最终设置:PictureSize--With = " + parameters.getPictureSize().width
                        + "Height = " + parameters.getPictureSize().height);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Error setting mCamera preview: " + e.getMessage());
                mCamera = null;
            }
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        //因为设置了固定屏幕方向，所以在实际使用中不会触发这个方法
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        holder.removeCallback(this);
        //回收释放资源
        release();
    }

    /**
     * 释放资源
     */
    private void release() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;

            if (mAutoFocusManager != null) {
                mAutoFocusManager.stop();
                mAutoFocusManager = null;
            }
        }
    }

    /**
     * 对焦，在CameraActivity中触摸对焦或者自动对焦
     */
    public void focus() {
        if (mCamera != null) {
            try {
                mCamera.autoFocus(null);
            } catch (Exception e) {
                Log.d(TAG, "takePhoto " + e);
            }
        }
    }

    /**
     * 开关闪光灯
     *
     * @return 闪光灯是否开启
     */
    public boolean switchFlashLight() {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            if (parameters.getFlashMode().equals(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
                return true;
            } else {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
                return false;
            }
        }
        return false;
    }

    /**
     * 拍摄照片
     *
     * @param pictureCallback 在pictureCallback处理拍照回调
     */
    public void takePhoto(Camera.PictureCallback pictureCallback) {
        if (mCamera != null) {
            try {
                mCamera.takePicture(null, null, pictureCallback);
            } catch (Exception e) {
                Log.d(TAG, "takePhoto " + e);
            }
        }
    }

    public void startPreview() {
        if (mCamera != null) {
            mCamera.startPreview();
        }
    }

    public void onStart() {
        if (mSensorController != null) {
            mSensorController.onStart();
        }
    }

    public void onStop() {
        if (mSensorController != null) {
            mSensorController.onStop();
        }
    }

    public void addCallback() {
        if (mSurfaceHolder != null) {
            mSurfaceHolder.addCallback(this);
        }
    }
}
