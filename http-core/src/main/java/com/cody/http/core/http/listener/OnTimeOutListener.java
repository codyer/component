/*
 * ************************************************************
 * 文件：OnTimeOutListener.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月05日 23:43:45
 * 上次修改时间：2019年04月05日 23:11:29
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.http.listener;

/**
 * Created by cody.yi on 2017/5/25.
 * token TimeOut
 */
public interface OnTimeOutListener {
    void onSuccess();

    void onCancel();
}
