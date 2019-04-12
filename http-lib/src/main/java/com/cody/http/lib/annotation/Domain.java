/*
 * ************************************************************
 * 文件：Domain.java  模块：http-lib  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月06日 16:20:04
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-lib
 * Copyright (c) 2019
 * ************************************************************
 */
package com.cody.http.lib.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by cody.yi on 2019/4/5.
 * 定义baseUrl 一般用于定义服务器域名 domain
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Domain {
    String value() default "";
}
