/*
 * ************************************************************
 * 文件：HttpCode.java  模块：http-lib  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-lib
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http.lib.config;

import com.cody.component.lib.exception.BaseCode;

/**
 * Created by xu.yi. on 2019/4/6.
 */
public interface HttpCode extends BaseCode {

    int CODE_SUCCESS = 200;

    int CODE_TOKEN_INVALID = 401;

    int CODE_ACCOUNT_INVALID = -3;

    int CODE_PARAMETER_INVALID = -4;

    int CODE_CONNECTION_FAILED = -5;

    int CODE_FORBIDDEN = -6;

    int CODE_RESULT_INVALID = -7;
    int CODE_DOMAIN_INVALID = -8;
    int CODE_DOMAIN_DEFINE_INVALID = -9;
    int CODE_GENERATE_FAILED = -10;
}
