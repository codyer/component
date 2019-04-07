/*
 * ************************************************************
 * 文件：ParameterBean.java  模块：http-compiler  项目：component
 * 当前修改时间：2019年04月06日 19:35:04
 * 上次修改时间：2019年04月06日 19:29:16
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.live.event.bus.compiler.bean;

import com.squareup.javapoet.TypeName;

/**
 * Created by xu.yi. on 2019/4/2.
 * 根据注解获取的方法参数信息
 */
public class ParameterBean {
    private String name = "";
    private TypeName type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeName getType() {
        return type;
    }

    public void setType(TypeName type) {
        this.type = type;
    }
}
