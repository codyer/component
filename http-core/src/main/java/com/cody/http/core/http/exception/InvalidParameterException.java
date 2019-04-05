/*
 * ************************************************************
 * 文件：InvalidParameterException.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月05日 23:38:39
 * 上次修改时间：2019年04月05日 23:37:36
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.http.exception;

/**
 * Created by cody.yi on 2016/7/21.
 * http请求异常 参数错误
 */
public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException() {
        super("参数错误");
    }
}
