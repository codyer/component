/*
 * ************************************************************
 * 文件：MethodBean.java  模块：http-compiler  项目：component
 * 当前修改时间：2019年04月13日 08:43:55
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-compiler
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http.compiler.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu.yi. on 2019/4/2.
 * 根据注解获取的方法体信息
 */
public class MethodBean {
    private String name = "";
    private String type = "";//返回值类型
    private boolean original = false;//返回值是否无Result包裹，为原始类型
    private final List<ParameterBean> mParameters = new ArrayList<>();

    public List<ParameterBean> getParameters() {
        return mParameters;
    }

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

    public boolean isOriginal() {
        return original;
    }

    public void setOriginal(boolean original) {
        this.original = original;
    }

    public void addParameter(ParameterBean parameterBean) {
        mParameters.add(parameterBean);
    }

}
