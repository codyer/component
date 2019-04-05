/*
 * ************************************************************
 * 文件：ILoginStatusListener.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月05日 23:43:36
 * 上次修改时间：2019年04月05日 23:11:29
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.http.listener;

import java.util.Map;

/**
 * Created by cody.yi on 2016/9/21.
 * 登录状态监听接口
 */
public interface ILoginStatusListener {
    void onLogin(Map<String, String> header);

    void onLogOutByTime();// 超时需要重登

    void onLogOutByUser();// 用户主动退出

    void clearHeader();// 清空登录信息
}