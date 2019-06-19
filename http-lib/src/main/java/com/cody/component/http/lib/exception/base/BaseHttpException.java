/*
 * ************************************************************
 * 文件：BaseHttpException.java  模块：http-lib  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-lib
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http.lib.exception.base;


import com.cody.component.lib.exception.BaseException;

/**
 * Created by xu.yi. on 2019/4/6.
 *
 */
public class BaseHttpException extends BaseException {

    private static final long serialVersionUID = -7839621263913749123L;

    public BaseHttpException() {
    }

    public BaseHttpException(final int code, final String message) {
        super(code, message);
    }
}