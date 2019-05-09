/*
 * ************************************************************
 * 文件：Result.java  模块：lib-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：lib-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.lib.bean;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by xu.yi. on 2019/4/6.
 */
public class Result<T> {

    @SerializedName("code")
    private int code;

    @SerializedName(value = "message", alternate = {"msg"})
    private String message;

    @SerializedName(value = "data", alternate = {"dataMap"})
    private T data;

    public Result() {
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data == null ? getEmptyData() : data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getEmptyData() {
        try {
            return getDataClass().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getDataClass() {
        System.out.println(this.getClass());
        //返回表示此 Class 所表示的实体类的 直接父类 的 Type。注意，是直接父类
        Type type = getClass().getGenericSuperclass();
        // 判断 是否泛型
        if (type instanceof ParameterizedType) {
            // 返回表示此类型实际类型参数的Type对象的数组.
            // 当有多个泛型类时，数组的长度就不是1了
            Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
            return (Class<T>) typeArguments[0];  //将第一个泛型T对应的类返回（这里只有一个）
        } else {
            return (Class<T>) Object.class;//若没有给定泛型，则返回Object类
        }
    }
}
