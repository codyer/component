/*
 * ************************************************************
 * 文件：OnRetryListener.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月09日 15:14:54
 * 上次修改时间：2019年04月09日 12:00:58
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.listener;

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