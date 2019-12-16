/*
 * ************************************************************
 * 文件：AlphaTransformation.java  模块：bind-core  项目：component
 * 当前修改时间：2019年12月10日 16:13:25
 * 上次修改时间：2019年12月10日 16:13:25
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bind.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;
import java.util.Objects;

/**
 * Created by xu.yi. on 2019-12-10.
 * component 将给定颜色替换成透明色 默认是白色替换为透明
 */
public class AlphaTransformation extends BitmapTransformation {
    private static final String ID = "com.cody.component.bind.adapter.AlphaTransformation";
    private int mColor = Color.WHITE;
    private ImageView.ScaleType mScaleType;

    public AlphaTransformation() {
    }

    public AlphaTransformation(final ColorDrawable color, final ImageView.ScaleType scaleType) {
        if (color != null) {
            mColor = color.getColor();
        }
        mScaleType = scaleType;
    }

    public AlphaTransformation(final ImageView.ScaleType scaleType) {
        mScaleType = scaleType;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int width,
                               int height) {
        Bitmap result;
        if (mScaleType == ImageView.ScaleType.FIT_CENTER) {
            result = TransformationUtils.fitCenter(pool, toTransform, width, height);
        } else {
            result = TransformationUtils.centerCrop(pool, toTransform, width, height);
        }
        result.setHasAlpha(true);
        int bitmapWidth = result.getWidth();
        int bitmapHeight = result.getHeight();

        for (int i = 0; i < bitmapHeight; i++) {
            for (int j = 0; j < bitmapWidth; j++) {
                int color = result.getPixel(j, i);
                if (color == mColor) {
                    result.setPixel(j, i, Color.TRANSPARENT);
                }
            }
        }

        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AlphaTransformation that = (AlphaTransformation) o;
        return mColor == that.mColor &&
                mScaleType == that.mScaleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, mColor, mScaleType);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID + mColor + mScaleType).getBytes(CHARSET));
    }
}
