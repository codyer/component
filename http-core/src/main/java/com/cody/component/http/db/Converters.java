/*
 * ************************************************************
 * 文件：Converters.java  模块：http-core  项目：component
 * 当前修改时间：2019年05月22日 11:03:04
 * 上次修改时间：2019年04月26日 22:46:37
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http.db;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Created by xu.yi. on 2019/4/5.
 * Converters
 */
public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}