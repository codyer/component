/*
 * ************************************************************
 * 文件：DataSourceInfoBean.java  模块：http-compiler  项目：component
 * 当前修改时间：2019年04月13日 08:43:55
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-compiler
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http.compiler.bean;

import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu.yi. on 2019/4/2.
 * 解析出来的生成类信息
 */
public class DataSourceInfoBean {
    private String mPackageName = "";
    private String mClassName = "";
    private TypeName mInterfaceTypeName;
    private DomainBean mDomainBean;
    private final List<MethodBean> mMethodBeans = new ArrayList<>();

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String className) {
        mClassName = className;
    }

    public DomainBean getDomainBean() {
        return mDomainBean;
    }

    public void setDomainBean(DomainBean domainBean) {
        mDomainBean = domainBean;
    }

    public List<MethodBean> getMethodBeans() {
        return mMethodBeans;
    }

    public void addMethodBean(MethodBean methodBean) {
        mMethodBeans.add(methodBean);
    }

    public TypeName getInterfaceTypeName() {
        return mInterfaceTypeName;
    }

    public void setInterfaceTypeName(TypeName interfaceTypeName) {
        mInterfaceTypeName = interfaceTypeName;
    }
}
