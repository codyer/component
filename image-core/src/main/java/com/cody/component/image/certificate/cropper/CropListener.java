/*
 * ************************************************************
 * 文件：CropListener.java  模块：image-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月21日 11:14:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：image-core
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
