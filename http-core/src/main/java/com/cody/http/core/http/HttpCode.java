/*
 * ************************************************************
 * 文件：HttpCode.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月05日 23:11:25
 * 上次修改时间：2018年01月29日 18:11:15
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.http;


import com.cody.http.core.BuildConfig;

/**
 * Created by cody.yi on 2017/4/1.
 * <p>
 * http请求返回码
 */
public interface HttpCode {
    boolean DEBUG = BuildConfig.DEBUG;
    String REQUEST_ERROR = "41001";//请求出错
    String NETWORK_DISCONNECTED = "41002";//网络无连接
    String PARAMETER_ERROR = "41003";//参数错误
    String UN_LOGIN = "-401";           //未登录
    String SUCCESS = "200";           //请求成功  (后面统一规定)
    String EMPTY = "40003";           //请求成功  (数据为空)
    String SERVER_ERROR = "500";       //服务端运行异常
    String NOT_FOUND = "404";     //未找到数据/查询无信息/某某数据不存在
    String OTHER_ERROR = "4000";//其它错误类型
    String PASSWORD_ERROR = "60022";//密码错误
}
