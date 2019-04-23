/*
 * ************************************************************
 * 文件：AutoGenerate.java  模块：bus-lib  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bus-lib
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bus.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Cody.yi on 2019/3/31.
 * 自动生成的文件注解，不要人为加上
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoGenerate {
    /**
     * 范围
     */
    String value() default "DefaultScope";

    /**
     * 是否激活,可以根据需要配置是否激活事件分发，eg：debug开启，release关闭
     */
    boolean active() default true;
}
