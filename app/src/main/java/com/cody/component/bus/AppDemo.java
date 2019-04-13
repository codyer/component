/*
 * ************************************************************
 * 文件：AppDemo.java  模块：app  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bus;

import com.cody.component.bean.TestBean;
import com.cody.component.bus.lib.annotation.Event;
import com.cody.component.bus.lib.annotation.EventScope;

/**
 * Created by xu.yi. on 2019/4/3.
 * LiveEventBus
 */
@EventScope(name = "demo",active = true)
public enum AppDemo {
    @Event(description = "定义一个测试事件",data = String.class)testString,
    @Event(description = "定义一个测试事件测试对象",data = TestBean.class)testBean,
}
