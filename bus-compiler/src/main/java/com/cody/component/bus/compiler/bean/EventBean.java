/*
 * ************************************************************
 * 文件：EventBean.java  模块：bus-compiler  项目：component
 * 当前修改时间：2019年04月12日 09:21:20
 * 上次修改时间：2019年04月11日 09:54:17
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bus-compiler
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bus.compiler.bean;

/**
 * Created by xu.yi. on 2019/4/2.
 * 根据注解获取的信息
 */
public class EventBean {
    private String name;
    private String type;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
