/*
 * ************************************************************
 * 文件：BluesCallBack.java  模块：blues-core  项目：component
 * 当前修改时间：2019年06月01日 14:07:39
 * 上次修改时间：2019年06月01日 14:07:39
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：blues-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.blues;

import java.util.Map;

public interface BluesCallBack {
    //showToast
    void showException(final String msg);
    //多次发生同样的异常
    void sameException(final Thread thread, final Throwable throwable);
    //上报异常数据
    void fillCrashData(Map<String, String> map);
}
