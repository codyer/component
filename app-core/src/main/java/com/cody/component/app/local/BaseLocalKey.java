/*
 * ************************************************************
 * 文件：BaseLocalKey.java  模块：app-core  项目：component
 * 当前修改时间：2019年05月06日 14:20:35
 * 上次修改时间：2019年05月06日 13:13:31
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.local;

/**
 * Created by cody.yi on 2019/3/30.
 * 本地数据存储
 * 目前只提供sharePreference方式
 */
public interface BaseLocalKey {
    String BASE = "BS_";
    String VERSION_CODE = BASE + "version_code";
    String COOKIE = BASE + "cookie";
    String TOKEN = BASE + "token";
    String DEVICE_ID = "device_id";//设备唯一号
    String CITY_NAME = "cityName";// 城市名称
    String CITY_CODE = "cityCode";// 城市code
}