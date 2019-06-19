/*
 * ************************************************************
 * 文件：ResultInvalidHttpException.java  模块：http-lib  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:54
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-lib
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http.lib.exception;


import com.cody.component.http.lib.config.HttpCode;
import com.cody.component.http.lib.exception.base.BaseHttpException;

/**
 * Created by xu.yi. on 2019/4/6.
 *
 */
public class ResultInvalidHttpException extends BaseHttpException {

    private static final long serialVersionUID = -5601882119845336021L;

    public ResultInvalidHttpException() {
        super(HttpCode.CODE_RESULT_INVALID, "无效请求");
    }

}
