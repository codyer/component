/*
 * ************************************************************
 * 文件：ResultInvalidException.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月06日 01:06:24
 * 上次修改时间：2019年03月25日 09:23:00
 * 作者：Cody.yi   https://github.com/codyer
 *
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
public class ResultInvalidException extends BaseException {

    private static final long serialVersionUID = -5601882119845336021L;

    public ResultInvalidException() {
        super(HttpCode.CODE_RESULT_INVALID, "无效请求");
    }

}
