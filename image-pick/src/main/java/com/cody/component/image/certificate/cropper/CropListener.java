/*
 * ************************************************************
 * 文件：CropListener.java  模块：image-pick  项目：component
 * 当前修改时间：2019年04月21日 10:45:25
 * 上次修改时间：2019年04月21日 10:45:25
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：image-pick
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.image.certificate.cropper;

import android.graphics.Bitmap;

/**
 * Desc	 ${裁剪监听接口}
 */
public interface CropListener {

    void onFinish(Bitmap bitmap);

}
