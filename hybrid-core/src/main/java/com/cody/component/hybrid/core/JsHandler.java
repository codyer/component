/*
 * ************************************************************
 * 文件：JsHandler.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:44:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.core;


import com.cody.component.util.NotProguard;

/**
 * Created by cody.yi on 2017/4/13.
 * 所有Js处理类实现这个接口
 * Js调用的方法必须按照一定的格式定义，否则不生效
 * 格式：
 * public static void ***(WebView webView, JsonObject data, JsCallback callback){}
 */
@NotProguard
public interface JsHandler {
}
