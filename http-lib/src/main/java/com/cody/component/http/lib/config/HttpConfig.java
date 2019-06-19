/*
 * ************************************************************
 * 文件：HttpConfig.java  模块：http-lib  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-lib
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http.lib.config;

/**
 * Created by xu.yi. on 2019/4/6.
 */
public class HttpConfig {
    public static final long CACHE_SIZE = 1024 * 1024 * 2;
    public static final long WINDOW_DURATION = 500;
    public static final long READ_TIMEOUT = 6000;
    public static final long WRITE_TIMEOUT = 6000;
    public static final long CONNECT_TIMEOUT = 6000;
}