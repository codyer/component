/*
 * ************************************************************
 * 文件：OnUrlListener.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月30日 13:36:27
 * 上次修改时间：2019年04月26日 22:46:37
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid;

/**
 * Created by xu.yi. on 2019/4/11.
 * url 变化监听
 */
public interface OnUrlListener {
    void onUrlChange(boolean canGoBack);
}
