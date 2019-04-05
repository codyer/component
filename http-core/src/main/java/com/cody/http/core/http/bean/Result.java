/*
 * ************************************************************
 * 文件：Result.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月05日 23:46:36
 * 上次修改时间：2019年04月05日 23:29:14
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.http.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cody.yi on 2016/7/12.
 * 网络请求包裹类result
 */
public class Result<T> {
    /**
     * String	操作状态码
     */
    private String code;

    /**
     * String	操作提示信息
     */
    private String message;

    /**
     * data	返回数据
     * 相当于data model
     */
    @SerializedName(value = "data", alternate = {"dataMap", "dataReturn","value"})
    private T data;

    public Result() {
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        if (code == null) {
            return "-1";
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        if (message == null) {
            return "no message!";
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
