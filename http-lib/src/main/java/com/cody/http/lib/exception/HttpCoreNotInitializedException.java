/*
 * ************************************************************
 * 文件：HttpCoreNotInitializedException.java  模块：http-lib  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:54
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-lib
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.lib.exception;

/**
 * Created by xu.yi. on 2019/4/7.
 * component
 */
public class HttpCoreNotInitializedException extends RuntimeException {
    private static final long serialVersionUID = -2553683554516417069L;

    public HttpCoreNotInitializedException() {
        super("使用实例必须先调用 init");
    }
}
