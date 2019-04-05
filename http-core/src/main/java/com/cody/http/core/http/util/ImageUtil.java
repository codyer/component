/*
 * ************************************************************
 * 文件：ImageUtil.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月05日 23:47:33
 * 上次修改时间：2019年04月05日 23:29:14
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.http.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by xu.yi. on 2019/4/5.
 * component
 */
public class ImageUtil {
    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getBitmap(String filePath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
//        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        if (reqWidth > 0 && reqHeight > 0) {
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        }

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;


        return BitmapFactory.decodeFile(filePath, options);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static byte[] getFileDataFromPath(String filePath) {
        return getFileDataFromPath(filePath, 800, 800);
    }

    public static byte[] getFileDataFromPath(String filePath, int reqWidth, int reqHeight) {
        Bitmap bitmap = getBitmap(filePath, reqWidth, reqHeight);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        /*if (!bitmap.isRecycled()) {
            bitmap.recycle();
            System.gc();
        }*/
        return byteArrayOutputStream.toByteArray();
    }

}
