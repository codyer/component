/*
 * ************************************************************
 * 文件：JsHandlerFactory.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:44:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.core;


import android.text.TextUtils;
import android.webkit.WebView;

import com.google.gson.JsonObject;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import androidx.collection.ArrayMap;

/**
 * Created by Cody.yi on 17/4/12.
 * 符合注入的方法的格式:
 * public static void ***(WebView webView, JsonObject data, JsCallback callback){}
 */
public class JsHandlerFactory {
    private ArrayMap<String, ArrayMap<String, Method>> mHandlerMethods;
    private ArrayMap<String, Class<? extends JsHandler>> mHandlerClasses;

    public JsHandlerFactory() {
        mHandlerMethods = new ArrayMap<>();
        mHandlerClasses = new ArrayMap<>();
    }

    /**
     * 注册可以处理Js请求的类
     */
    public JsHandlerFactory register(String handlerName, Class<? extends JsHandler> clazz) {
        if (clazz == null)
            throw new NullPointerException("NativeMethodInjectHelper:The addJsHandler can not be null!");
        mHandlerClasses.put(handlerName, clazz);
        return this;
    }

    /**
     * 构建所有注册的Js Handler
     */
    public void build() {
        for (Map.Entry<String, Class<? extends JsHandler>> entry : mHandlerClasses.entrySet()) {
            putMethod(entry.getKey(), entry.getValue());
        }
        mHandlerClasses.clear();
    }

    public Method findMethod(String handlerName, String methodName) {
        if (TextUtils.isEmpty(handlerName) || TextUtils.isEmpty(methodName))
            return null;
        if (mHandlerMethods.containsKey(handlerName)) {
            ArrayMap<String, Method> arrayMap = mHandlerMethods.get(handlerName);
            if (arrayMap == null)
                return null;
            if (arrayMap.containsKey(methodName)) {
                return arrayMap.get(methodName);
            }
        }
        return null;
    }

    /**
     * 添加类包含符合规则的Js处理方法
     */
    private void putMethod(String handlerName, Class<?> clazz) {
        if (clazz == null)
            return;
        ArrayMap<String, Method> arrayMap = new ArrayMap<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (checkMethod(method)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes != null && parameterTypes.length == 3) {
                    if (checkParameter(parameterTypes)) {
                        arrayMap.put(method.getName(), method);
                    }
                }
            }
        }
        mHandlerMethods.put(handlerName, arrayMap);
    }

    /**
     * 检查Js处理器的方法格式是否正确
     * 正确格式：public static void ***(WebView webView, JsonObject data, JsCallback callback){}
     */
    private boolean checkMethod(Method method) {
        return method.getModifiers() == (Modifier.PUBLIC | Modifier.STATIC) &&
                method.getReturnType() == void.class &&
                method.getName() != null;
    }

    /**
     * 检查参数格式是否正确
     * 正确格式：public static void ***(WebView webView, JsonObject data, JsCallback callback){}
     */
    private boolean checkParameter(Class<?>[] parameterTypes) {
        return WebView.class == parameterTypes[0] &&
                JsonObject.class == parameterTypes[1] &&
                JsCallback.class == parameterTypes[2];
    }

}
