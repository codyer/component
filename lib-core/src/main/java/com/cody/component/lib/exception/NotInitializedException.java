/*
 * ************************************************************
 * 文件：NotInitializedException.java  模块：lib-core  项目：component
 * 当前修改时间：2019年11月05日 15:43:45
 * 上次修改时间：2019年11月05日 15:42:57
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：lib-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.lib.exception;

/**
 * Created by xu.yi. on 2019/4/7.
 * component
 */
public class NotInitializedException extends BaseException {
    private static final long serialVersionUID = -2553683554516417069L;

    public NotInitializedException(final String s) {
        super(s +": 使用实例必须先调用 init");
    }
}