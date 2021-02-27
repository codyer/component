/*
 * ************************************************************
 * 文件：AppDemo.java  模块：app-demo  项目：component
 * 当前修改时间：2021年02月27日 15:30:04
 * 上次修改时间：2021年02月27日 15:05:41
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-demo
 * Copyright (c) 2021
 * ************************************************************
 */

package com.cody.component.demo.bus;

import com.cody.component.demo.bean.TestBean;

import cody.bus.annotation.Event;
import cody.bus.annotation.EventGroup;

/**
 * Created by xu.yi. on 2019/4/3.
 * LiveEventBus
 */
@EventGroup(name = "DemoGroup", active = true)
public class AppDemo {
    @Event(description = "定义一个测试事件")
    String testString;
    @Event(description = "定义一个测试事件测试对象", multiProcess = true)
    TestBean testBean;
}
