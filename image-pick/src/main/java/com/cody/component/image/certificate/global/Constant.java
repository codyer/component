/*
 * ************************************************************
 * 文件：Constant.java  模块：image-pick  项目：component
 * 当前修改时间：2019年04月21日 10:45:25
 * 上次修改时间：2019年04月21日 10:45:25
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：image-pick
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.image.certificate.global;

import com.cody.component.image.certificate.utils.FileUtils;

import java.io.File;

/**
 * Desc	${常量}
 */
public class Constant {
    public static final String APP_NAME = "ComponentCamera";//app名称
    public static final String BASE_DIR = APP_NAME + File.separator;//ComponentCamera/
    public static final String DIR_ROOT = FileUtils.getRootPath() + File.separator + Constant.BASE_DIR;//文件夹根目录 /storage/emulated/0/ComponentCamera/
}