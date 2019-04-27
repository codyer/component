/*
 * ************************************************************
 * 文件：UnImplementException.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月27日 21:30:39
 * 上次修改时间：2019年04月27日 21:30:39
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler;

/**
 * Created by xu.yi. on 2019/4/27.
 * component 提供了默认实现，但是没有实现任何一个方法
 */
public class UnImplementException extends RuntimeException {
    private static final long serialVersionUID = 3655252154583356087L;

    public UnImplementException(final String message) {
        super(message);
    }
}
