/*
 * ************************************************************
 * 文件：CameraActivity.java  模块：image-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月21日 21:51:15
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：image-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.image.certificate.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cody.component.app.activity.BaseActivity;
import com.cody.component.image.ImageViewDelegate;
import com.cody.component.image.OnImageViewListener;
import com.cody.component.image.R;
import com.cody.component.image.certificate.cropper.CropImageView;
import com.cody.component.image.certificate.global.Constant;
import com.cody.component.util.FileUtils;
import com.cody.component.util.ImageUtils;
import com.cody.component.util.PermissionUtils;
import com.cody.component.util.ScreenUtils;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;


/**
 * Desc ${拍照界面}
 */
public class CameraActivity extends BaseActivity implements View.OnClickListener, OnImageViewListener {

    /**
     * 拍摄类型-身份证正面
     */
    public final static int TYPE_ID_CARD_FRONT = 1;
    /**
     * 拍摄类型-身份证反面
     */
    public final static int TYPE_ID_CARD_BACK = 2;
    /**
     * 拍摄类型-竖版营业执照
     */
    public final static int TYPE_BUSINESS_LICENSE_PORTRAIT = 3;
    /**
     * 拍摄类型-横版营业执照
     */
    public final static int TYPE_BUSINESS_LICENSE_LANDSCAPE = 4;
    public final static float BUSINESS_LICENSE_RATIO = 43.0f / 30.0f;
    public final static float ID_CARD_RATIO = 85.6f / 54.0f;

    public final static int REQUEST_CODE = 0X11;//请求码
    public final static int RESULT_CODE = 0X12;//结果码
    private final static String TAKE_TYPE = "take_type";//拍摄类型标记
    private final static String IMAGE_PATH = "image_path";//图片路径标记
    private int mType;//拍摄类型
    private boolean isToast = true;//是否弹吐司，为了保证for循环只弹一次

    private ImageViewDelegate mImageViewDelegate;
    private CropImageView mCropImageView;
    private Bitmap mCropBitmap;
    private CameraPreview mCameraPreview;
    private View mContainerView;
    private ImageView mCropView;
    private ImageView mFlashView;
    private View mOptionView;
    private View mResultView;
    private TextView mTouchHint;

    /**
     * 跳转到拍照界面
     *
     * @param type 拍摄类型
     *             {@link #TYPE_ID_CARD_FRONT}
     *             {@link #TYPE_ID_CARD_BACK}
     *             {@link #TYPE_BUSINESS_LICENSE_PORTRAIT}
     *             {@link #TYPE_BUSINESS_LICENSE_LANDSCAPE}
     */
    public static void openCameraActivity(Activity activity, int type) {
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra(TAKE_TYPE, type);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * 获取图片路径
     */
    public static String getImagePath(Intent data) {
        if (data != null) {
            return data.getStringExtra(IMAGE_PATH);
        }
        return "";
    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        /*动态请求需要的权限*/
        //权限请求码
        final int PERMISSION_CODE_FIRST = 0x13;
        boolean checkPermissionFirst = PermissionUtils.checkPermissionFirst(this, PERMISSION_CODE_FIRST,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA});
        if (checkPermissionFirst) {
            init();
        }

        mImageViewDelegate = new ImageViewDelegate(this);
        mImageViewDelegate.setCanDelete(false);
    }

    /**
     * 处理请求权限的响应
     *
     * @param requestCode  请求码
     * @param permissions  权限数组
     * @param grantResults 请求权限结果数组
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isPermissions = true;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                isPermissions = false;
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) { //用户选择了"不再询问"
                    if (isToast) {
                        Toast.makeText(this, "请手动打开该应用需要的权限", Toast.LENGTH_SHORT).show();
                        isToast = false;
                    }
                }
            }
        }
        isToast = true;
        if (isPermissions) {
            Log.d("onRequestPermission", "onRequestPermissionsResult: " + "允许所有权限");
            init();
        } else {
            Log.d("onRequestPermission", "onRequestPermissionsResult: " + "有权限不允许");
            finish();
        }
    }

    private void init() {
        mType = getIntent().getIntExtra(TAKE_TYPE, 0);
        if (mType == TYPE_BUSINESS_LICENSE_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        setContentView(R.layout.activity_camera);
        initView();
        initListener();
    }

    private void initView() {
        mCameraPreview = findViewById(R.id.camera_preview);
        mCropImageView = findViewById(R.id.crop_image_view);
        mFlashView = findViewById(R.id.camera_flash);
        mOptionView = findViewById(R.id.camera_option);
        mResultView = findViewById(R.id.camera_result);
        mContainerView = findViewById(R.id.camera_crop_container);
        mCropView = findViewById(R.id.camera_crop);
        mTouchHint = findViewById(R.id.touch_hint);
        float screenMinSize = Math.min(ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenHeight(this));
        float screenMaxSize = Math.max(ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenHeight(this));
        RelativeLayout.LayoutParams layoutParams;
        if (mType == TYPE_BUSINESS_LICENSE_PORTRAIT) {
            layoutParams = new RelativeLayout.LayoutParams((int) screenMinSize, (int) screenMaxSize);
        } else {
            layoutParams = new RelativeLayout.LayoutParams((int) screenMaxSize, (int) screenMinSize);
        }
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mCameraPreview.setLayoutParams(layoutParams);

        if (mType == TYPE_BUSINESS_LICENSE_PORTRAIT) {
            float width = (int) (screenMinSize * 0.8);
            float height = (int) (width * BUSINESS_LICENSE_RATIO);
            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) height);
            LinearLayout.LayoutParams cropParams = new LinearLayout.LayoutParams((int) width, (int) height);
            mContainerView.setLayoutParams(containerParams);
            mCropView.setLayoutParams(cropParams);
        } else if (mType == TYPE_BUSINESS_LICENSE_LANDSCAPE) {
            float height = (int) (screenMinSize * 0.8);
            float width = (int) (height * BUSINESS_LICENSE_RATIO);
            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams((int) width, ViewGroup.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams cropParams = new LinearLayout.LayoutParams((int) width, (int) height);
            mContainerView.setLayoutParams(containerParams);
            mCropView.setLayoutParams(cropParams);
        } else {
            float height = (int) (screenMinSize * 0.75);
            float width = (int) (height * ID_CARD_RATIO);
            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams((int) width, ViewGroup.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams cropParams = new LinearLayout.LayoutParams((int) width, (int) height);
            mContainerView.setLayoutParams(containerParams);
            mCropView.setLayoutParams(cropParams);
        }
        switch (mType) {
            case TYPE_ID_CARD_FRONT:
                mCropView.setImageResource(R.mipmap.camera_idcard_front);
                break;
            case TYPE_ID_CARD_BACK:
                mCropView.setImageResource(R.mipmap.camera_idcard_back);
                break;
            case TYPE_BUSINESS_LICENSE_PORTRAIT:
                mCropView.setImageResource(R.mipmap.camera_company);
                break;
            case TYPE_BUSINESS_LICENSE_LANDSCAPE:
                mCropView.setImageResource(R.mipmap.camera_company_landscape);
                break;
        }

        /*增加0.5秒过渡界面，解决个别手机首次申请权限导致预览界面启动慢的问题*/
        new Handler().postDelayed(() -> runOnUiThread(() -> mCameraPreview.setVisibility(View.VISIBLE)), 500);
    }

    private void initListener() {
        mCameraPreview.setOnClickListener(this);
        findViewById(R.id.camera_close).setOnClickListener(this);
        findViewById(R.id.camera_gallery).setOnClickListener(this);
        findViewById(R.id.camera_take).setOnClickListener(this);
        mFlashView.setOnClickListener(this);
        findViewById(R.id.camera_result_ok).setOnClickListener(this);
        findViewById(R.id.camera_rotate).setOnClickListener(this);
        findViewById(R.id.camera_result_cancel).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != mImageViewDelegate) {
            mImageViewDelegate.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onPreview(int id, List<ImageItem> images) {
    }

    @Override
    public void onPickImage(int id, List<ImageItem> images) {
        if (images == null || images.size() == 0) return;
        if (id == R.id.action_gallery) {
            mCropBitmap = ImageUtils.getBitmap(images.get(0).path,800,800);
            if (mType == TYPE_BUSINESS_LICENSE_PORTRAIT) {
                mCropBitmap = ImageUtils.rotateBitmapByDegree(mCropBitmap, 90);
            }
            /*设置成手动裁剪模式*/
            runOnUiThread(() -> {
                //将手动裁剪区域设置成与扫描框一样大
                mCropImageView.setLayoutParams(new LinearLayout.LayoutParams(mCropView.getWidth(), mCropView.getHeight()));
                setCropLayout();
                mCropImageView.setImageBitmap(mCropBitmap);
            });
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.camera_preview) {
            mCameraPreview.focus();
        } else if (id == R.id.camera_close) {
            finish();
        } else if (id == R.id.camera_take) {
            takePhoto();
        } else if (id == R.id.camera_gallery) {
            if (null != mImageViewDelegate) {
                mImageViewDelegate.withId(R.id.action_gallery).selectImage(1, false);
            }
        } else if (id == R.id.camera_flash) {
            boolean isFlashOn = mCameraPreview.switchFlashLight();
            mFlashView.setImageResource(isFlashOn ? R.drawable.ic_flashlight_on : R.drawable.ic_flashlight_off);
        } else if (id == R.id.camera_result_ok) {
            confirm();
        } else if (id == R.id.camera_rotate) {
            mCropBitmap = ImageUtils.rotateBitmapByDegree(mCropBitmap, 90);
            /*设置成手动裁剪模式*/
            runOnUiThread(() -> mCropImageView.setImageBitmap(mCropBitmap));
        } else if (id == R.id.camera_result_cancel) {
            mCameraPreview.setEnabled(true);
            mCameraPreview.addCallback();
            mCameraPreview.startPreview();
            mFlashView.setImageResource(R.drawable.ic_flashlight_off);
            setTakePhotoLayout();
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        mCameraPreview.setEnabled(false);
        CameraUtils.getCamera().setOneShotPreviewCallback((bytes, camera) -> {
            final Camera.Size size = camera.getParameters().getPreviewSize(); //获取预览大小
            camera.stopPreview();
            new Thread(() -> {
                final int w = size.width;
                final int h = size.height;
                Bitmap bitmap = ImageUtils.getBitmapFromByte(bytes, w, h);
                if (mType == TYPE_BUSINESS_LICENSE_PORTRAIT) {
                    bitmap = ImageUtils.rotateBitmapByDegree(bitmap, 90);
                }
                cropImage(bitmap);
            }).start();
        });
    }

    /**
     * 裁剪图片
     */
    private void cropImage(Bitmap bitmap) {
        //计算裁剪位置
        float left, top, right, bottom;
        if (mType == TYPE_BUSINESS_LICENSE_PORTRAIT) {
            left = (float) mCropView.getLeft() / (float) mCameraPreview.getWidth();
            top = ((float) mContainerView.getTop() - (float) mCameraPreview.getTop()) / (float) mCameraPreview.getHeight();
            right = (float) mCropView.getRight() / (float) mCameraPreview.getWidth();
            bottom = (float) mContainerView.getBottom() / (float) mCameraPreview.getHeight();
        } else {
            left = ((float) mContainerView.getLeft() - (float) mCameraPreview.getLeft()) / (float) mCameraPreview.getWidth();
            top = (float) mCropView.getTop() / (float) mCameraPreview.getHeight();
            right = (float) mContainerView.getRight() / (float) mCameraPreview.getWidth();
            bottom = (float) mCropView.getBottom() / (float) mCameraPreview.getHeight();
        }

        //裁剪及保存到文件
        mCropBitmap = Bitmap.createBitmap(bitmap,
                (int) (left * (float) bitmap.getWidth()),
                (int) (top * (float) bitmap.getHeight()),
                (int) ((right - left) * (float) bitmap.getWidth()),
                (int) ((bottom - top) * (float) bitmap.getHeight()));

        /*设置成手动裁剪模式*/
        runOnUiThread(() -> {
            //将手动裁剪区域设置成与扫描框一样大
            mCropImageView.setLayoutParams(new LinearLayout.LayoutParams(mCropView.getWidth(), mCropView.getHeight()));
            setCropLayout();
            mCropImageView.setImageBitmap(mCropBitmap);
        });
    }

    /**
     * 设置裁剪布局
     */
    private void setCropLayout() {
        mCropView.setVisibility(View.GONE);
        mCameraPreview.setVisibility(View.GONE);
        mOptionView.setVisibility(View.INVISIBLE);
        mCropImageView.setVisibility(View.VISIBLE);
        mResultView.setVisibility(View.VISIBLE);
        mTouchHint.setText("");
    }

    /**
     * 设置拍照布局
     */
    private void setTakePhotoLayout() {
        mCropView.setVisibility(View.VISIBLE);
        mCameraPreview.setVisibility(View.VISIBLE);
        mOptionView.setVisibility(View.VISIBLE);
        mCropImageView.setVisibility(View.GONE);
        mResultView.setVisibility(View.GONE);
        mTouchHint.setText(getString(R.string.touch_to_focus));
        mCameraPreview.focus();
    }

    /**
     * 点击确认，返回图片路径
     */
    private void confirm() {
        /*手动裁剪图片*/
        mCropImageView.crop(bitmap -> {
            if (bitmap == null) {
                Toast.makeText(getApplicationContext(), getString(R.string.crop_fail), Toast.LENGTH_SHORT).show();
                finish();
            }

            /*保存图片到sdcard并返回图片路径*/
            if (FileUtils.createOrExistsDir(Constant.DIR_ROOT)) {
                String imagePath = getFileName();
                if (ImageUtils.save(bitmap, imagePath, Bitmap.CompressFormat.JPEG)) {
                    Intent intent = new Intent();
                    intent.putExtra(CameraActivity.IMAGE_PATH, imagePath);
                    setResult(RESULT_CODE, intent);
                    finish();
                }
            }
        }, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCameraPreview != null) {
            mCameraPreview.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCameraPreview != null) {
            mCameraPreview.onStop();
        }
    }

    /**
     * @return 拍摄图片裁剪文件
     */
    private String getFileName() {
        StringBuilder buffer = new StringBuilder();
        String fileName = new Date(System.currentTimeMillis()).toString();
        switch (mType) {
            case TYPE_ID_CARD_FRONT:
                fileName += "_identity_card_front.jpg";
                break;
            case TYPE_ID_CARD_BACK:
                fileName += "_identity_card_back.jpg";
                break;
            case TYPE_BUSINESS_LICENSE_PORTRAIT:
            case TYPE_BUSINESS_LICENSE_LANDSCAPE:
                fileName += "_business_license.jpg";
                break;
        }
        return buffer.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append(".").append(fileName).toString();
    }
}