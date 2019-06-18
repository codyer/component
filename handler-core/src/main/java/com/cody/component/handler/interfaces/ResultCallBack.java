/*
 * ************************************************************
 * 文件：ResultCallBack.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月29日 10:43:36
 * 上次修改时间：2019年04月29日 08:26:25
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.interfaces;


/**
 * Created by xu.yi. on 2019/4/8.
 * 获取分页数据后进行回调的接口
 */
public interface ResultCallBack {
    /**
     * eg: callBack.onSuccess();
     */
    void onSuccess();

    /**
     * eg: callBack.onFailure(message);
     */
    void onFailure(String message);
}
