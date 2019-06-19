/*
 * ************************************************************
 * 文件：WrongTypeException.java  模块：bus-lib  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:54
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bus-lib
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bus.lib.exception;

import com.cody.component.lib.exception.BaseException;

/**
 * Created by xu.yi. on 2019/3/31.
 * 类型错误异常
 */
public class WrongTypeException extends BaseException {
    private static final long serialVersionUID = -3309643656351709235L;

    public WrongTypeException() {
        super("请使用自动生成的接口文件");
    }
}