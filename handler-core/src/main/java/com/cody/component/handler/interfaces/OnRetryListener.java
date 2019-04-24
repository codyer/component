/*
 * ************************************************************
 * 文件：OnRetryListener.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.interfaces;

/**
 * Created by xu.yi. on 2019/4/8.
 * 重新请求数据
 */
public interface OnRetryListener {
    /**
     * 出错重试按钮被点击时调用
     */
    void retry();
}