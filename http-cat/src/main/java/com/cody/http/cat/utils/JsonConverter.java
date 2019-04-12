/*
 * ************************************************************
 * 文件：JsonConverter.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月07日 18:02:21
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

/**
 * Created by xu.yi. on 2019/4/5.
 * FormatUtils
 */
public class JsonConverter {

    private final Gson gson;

    private JsonConverter() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
    }

    public static Gson getInstance() {
        return JsonConverterHolder.instance.gson;
    }

    private static class JsonConverterHolder {
        private static final JsonConverter instance = new JsonConverter();
    }

}
