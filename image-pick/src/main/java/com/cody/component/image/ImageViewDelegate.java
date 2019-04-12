/*
 * ************************************************************
 * 文件：ImageViewDelegate.java  模块：image-pick  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月11日 21:44:04
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：image-pick
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.image;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.TextUtils;

import com.cody.component.util.ActivityUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;

import java.io.IOException;
import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.exifinterface.media.ExifInterface;

/**
 * Created by cody.yi on 2017/5/19.
 * imageView代理
 * 上传图片，预览图片
 */
public class ImageViewDelegate implements IImageViewListener {
    private static final int REQUEST_CODE_SELECT = 100;
    private static final int REQUEST_CODE_PREVIEW = 101;

    private int mCurrentId;
    private boolean mCanDelete = true;
    private OnImageViewListener mOnImageViewListener;
    private Object mContext;

    public ImageViewDelegate(OnImageViewListener onImageViewListener, Object context) {
        mOnImageViewListener = onImageViewListener;
        mContext = context;
    }

    public ImageViewDelegate(OnImageViewListener onImageViewListener) {
        mOnImageViewListener = onImageViewListener;
        mContext = onImageViewListener;
    }

    public boolean isCanDelete() {
        return mCanDelete;
    }

    public void setCanDelete(boolean canDelete) {
        mCanDelete = canDelete;
    }

    public ImageViewDelegate withId(int delegateId) {
        mCurrentId = delegateId;
        return this;
    }

    @Override
    public void preview(String path) {
        if (TextUtils.isEmpty(path)) return;
        ArrayList<ImageItem> items = new ArrayList<>();
        ImageItem item = new ImageItem();
        item.path = path;
        items.add(item);
        preview(items, 0);
    }

    @Override
    public void preview(final ArrayList<ImageItem> items, final int position) {
        if (null == items || items.size() == 0) return;
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImagePicker.EXTRA_IMAGE_ITEMS, items);
        bundle.putInt(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
        bundle.putBoolean(ImagePicker.EXTRA_FROM_ITEMS, true);
        ActivityUtil.navigateToForResult(mContext, mCanDelete ? ImagePreviewDelActivity.class : ImagePreviewActivity
                .class, REQUEST_CODE_PREVIEW, bundle);
    }

    @Override
    public void selectImage(int limit, boolean isCrop) {
        // 设置是否裁剪照片
        ImagePicker.getInstance().setCrop(isCrop);
        if (limit == 1) {
            ImagePicker.getInstance().setMultiMode(false);//图片选着模式，单选
        } else {
            ImagePicker.getInstance().setMultiMode(true);//图片选着模式，多选
            ImagePicker.getInstance().setSelectLimit(limit);
        }
        ActivityUtil.navigateToForResult(mContext, ImageGridActivity.class, REQUEST_CODE_SELECT);
    }

    @Override
    public void pickImage(int limit, boolean isCrop) {
        // 设置是否裁剪照片
        ImagePicker.getInstance().setCrop(isCrop);
        if (limit == 1) {
            ImagePicker.getInstance().setMultiMode(false);//图片选着模式，单选
        } else {
            ImagePicker.getInstance().setMultiMode(true);//图片选着模式，多选
            ImagePicker.getInstance().setSelectLimit(limit);
        }
        if (ActivityUtil.isActivityDestroyed()) return;
        Activity activity = ActivityUtil.getCurrentActivity();
        if (activity == null) return;
        String[] choice = {activity.getString(R.string.ui_str_from_photos), activity.getString(R.string.ui_str_from_camera)};
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setCancelable(true)
                .setSingleChoiceItems(choice, 0, (dialog, which) -> {
                    if (which == 0) {
                        ActivityUtil.navigateToForResult(mContext, ImageGridActivity.class, REQUEST_CODE_SELECT);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        ActivityUtil.navigateToForResult(mContext, ImageGridActivity.class,
                                REQUEST_CODE_SELECT, bundle);
                    }
                    dialog.dismiss();
                }).create();
        if (activity instanceof DialogInterface.OnCancelListener) {
            alertDialog.setOnCancelListener((DialogInterface.OnCancelListener) activity);
        }
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<ImageItem> images;
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mOnImageViewListener.onPickImage(mCurrentId, images);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                mOnImageViewListener.onPreview(mCurrentId, images);
            }
        }
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError ex) {
            ex.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bm;
        }

        if (bm != null && bm != returnBm) {
            bm.recycle();
        }

        return returnBm;
    }
}
