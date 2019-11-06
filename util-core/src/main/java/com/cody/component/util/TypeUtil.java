/*
 * ************************************************************
 * 文件：TypeUtil.java  模块：util-core  项目：component
 * 当前修改时间：2019年11月05日 10:44:26
 * 上次修改时间：2019年11月05日 10:44:26
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：util-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.util;

import androidx.annotation.NonNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by xu.yi. on 2019-11-05.
 * component
 */
public class TypeUtil {

    /**
     * 获取组合类类型
     */
    public static ParameterizedType getType(final Class raw, final Type... args) {
        return new ParameterizedType() {
            @NonNull
            public Type getRawType() {
                return raw;
            }

            @NonNull
            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}
