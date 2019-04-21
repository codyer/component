/*
 * ************************************************************
 * 文件：CropImageView.java  模块：image-pick  项目：component
 * 当前修改时间：2019年04月21日 10:45:25
 * 上次修改时间：2019年04月21日 10:45:25
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：image-pick
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.image.certificate.cropper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.cody.component.image.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Desc	        ${裁剪布局}
 */
public class CropImageView extends FrameLayout {

    private ImageView mImageView;
    private CropOverlayView mCropOverlayView;

    public CropImageView(@NonNull Context context) {
        super(context);
    }

    public CropImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.crop_image_view, this, true);
        mImageView = (ImageView) v.findViewById(R.id.img_crop);
        mCropOverlayView = (CropOverlayView) v.findViewById(R.id.overlay_crop);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setImageBitmap(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
        mCropOverlayView.setBitmap(bitmap);
    }

    public void crop(CropListener listener, boolean needStretch) {
        if (listener == null)
            return;
        mCropOverlayView.crop(listener, needStretch);
    }

}
