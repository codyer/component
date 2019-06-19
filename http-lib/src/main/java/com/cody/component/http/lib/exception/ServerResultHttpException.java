/*
 * ************************************************************
 * 文件：ServerResultHttpException.java  模块：http-lib  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-lib
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http.lib.exception;


import com.cody.component.http.lib.exception.base.BaseHttpException;

/**
 * Created by xu.yi. on 2019/4/6.
 *
 */
public class ServerResultHttpException extends BaseHttpException {

    private static final long serialVersionUID = -3348529211360188415L;

    public ServerResultHttpException(int code, String message) {
        super(code, message);
    }

}
