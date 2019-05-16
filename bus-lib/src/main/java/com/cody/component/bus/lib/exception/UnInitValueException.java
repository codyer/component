/*
 * ************************************************************
 * 文件：UnInitValueException.java  模块：bus-lib  项目：component
 * 当前修改时间：2019年05月16日 14:56:48
 * 上次修改时间：2019年04月26日 22:46:37
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bus-lib
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bus.lib.exception;

/**
 * Created by xu.yi. on 2019/3/31.
 * 未初始化异常
 */
public class UnInitValueException extends RuntimeException {
    private static final long serialVersionUID = -3309643656351709235L;

    public UnInitValueException() {
        super("使用事件前，未初始化事件默认值，请在主线程调用 initValue(T value)");
    }
}