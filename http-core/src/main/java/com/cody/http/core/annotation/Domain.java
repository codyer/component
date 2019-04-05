/*
 * ************************************************************
 * 文件：Domain.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月06日 00:31:55
 * 上次修改时间：2018年07月19日 17:42:06
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */
package com.cody.http.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by cody.yi on 2017/3/28.
 * 定义baseUrl 一般用于定义服务器域名 domain
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Domain {
    String value() default "";
}
