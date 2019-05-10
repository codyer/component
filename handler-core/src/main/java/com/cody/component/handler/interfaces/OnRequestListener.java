/*
 * ************************************************************
 * 文件：OnRequestListener.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 19:07:41
 * 上次修改时间：2019年04月23日 18:26:51
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.interfaces;

import com.cody.component.handler.define.Operation;

/**
 * Created by xu.yi. on 2019/4/8.
 * 请求数据,可以通过数据库或者网络加载方式实现
 */
public interface OnRequestListener {
    void onRequestData(Operation operation);
}