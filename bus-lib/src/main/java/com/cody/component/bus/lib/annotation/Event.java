/*
 * ************************************************************
 * 文件：Event.java  模块：bus-lib  项目：component
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
 * Created by xu.yi. on 2019/3/31.
 * 事件类型,定义在枚举值上，定义事件名称和事件携带的数据类型
 * eg:
 * //@EventScope(name = "demo",active = true)
 * public enum AppDemo {
 * //@Event(description = "定义一个测试事件",data = String.class)testString,
 * //@Event(description = "定义一个测试事件测试对象",data = TestBean.class,process = true)testBean,
 * }
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Event {
    /**
     * @return 事件描述
     */
    String value() default "";

    /**
     * @return 同value作用一样，为了匹配实际意义添加
     */
    String description() default "";

    /**
     * @return 事件携带数据类型
     */
    Class data() default Object.class;

    /**
     * @return 此事件是否支持跨进程
     */
    boolean process() default false;
}
