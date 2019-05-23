/*
 * ************************************************************
 * 文件：TokenInvalidException.java  模块：http-lib  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-lib
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.lib.exception;


import com.cody.http.lib.config.HttpCode;
import com.cody.http.lib.exception.base.BaseException;

/**
 * Created by xu.yi. on 2019/4/6.
 *
 */
public class TokenInvalidException extends BaseException {

    private static final long serialVersionUID = 275593545034454755L;

    public TokenInvalidException() {
        super(HttpCode.CODE_TOKEN_INVALID, "登录状态已过期，请重新登录");
    }
}
