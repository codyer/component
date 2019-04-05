/*
 * ************************************************************
 * 文件：OnUploadListener.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月05日 23:43:50
 * 上次修改时间：2019年04月05日 23:11:29
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.http.listener;

/**
 * Created by cody.yi on 2017/6/1.
 * 上传进度
 */
public interface OnUploadListener {
    void onProgress(int progress, int max);
}
