/*
 * ************************************************************
 * 文件：OnImageViewListener.java  模块：image-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月21日 10:52:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：image-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.image;


import com.lzy.imagepicker.bean.ImageItem;

import java.util.List;

/**
 * Created by cody.yi on 2017/5/19.
 * 获取图片成功
 */
public interface OnImageViewListener {
    /**
     * 预览成功
     *
     * @param id     delegate id
     * @param images 预览操作后剩余图片列表
     */
    void onPreview(int id, List<ImageItem> images); // 预览

    /**
     * 选择成功
     *
     * @param id     delegate id
     * @param images 操作后图片列表
     */
    void onPickImage(int id, List<ImageItem> images); // 获取图片
}
