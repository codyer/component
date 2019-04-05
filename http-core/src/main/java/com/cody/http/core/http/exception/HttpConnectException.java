/*
 * ************************************************************
 * 文件：HttpConnectException.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月05日 23:37:22
 * 上次修改时间：2019年04月05日 23:11:29
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.http.exception;

/**
 * Created by cody.yi on 2016/7/21.
 * http请求异常
 */
public class HttpConnectException extends RuntimeException {
    private String mCode;  //异常对应的返回码
    private String mMessage;  //异常对应的描述信息

    public HttpConnectException() {
        super();
    }

    public HttpConnectException(String message) {
        super(message);
        mMessage = message;
    }

    public HttpConnectException(String code, String message) {
        super(message);
        this.mCode = code;
        this.mMessage = message;
    }

    public String getCode() {
        return mCode;
    }

    public String getMessage() {
        return mMessage;
    }
}
