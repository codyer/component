/*
 * ************************************************************
 * 文件：BaseException.java  模块：lib-core  项目：component
 * 当前修改时间：2019年06月19日 10:32:21
 * 上次修改时间：2019年06月19日 10:32:21
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：lib-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.lib.exception;

/**
 * Created by xu.yi. on 2019-06-19.
 * component
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -4760213625687945508L;
    private int mCode = BaseCode.CODE_UNKNOWN;

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(int code, String message) {
        super(message);
        this.mCode = code;
    }

    public int getCode() {
        return mCode;
    }
}
