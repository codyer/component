/*
 * ************************************************************
 * 文件：BaseApplication.java  模块：app  项目：component
 * 当前修改时间：2019年04月07日 13:37:06
 * 上次修改时间：2019年04月07日 13:37:06
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component;

import com.cody.http.cat.HttpCat;
import com.cody.http.core.HttpCore;

import androidx.multidex.MultiDexApplication;

/**
 * Created by xu.yi. on 2019/4/7.
 * component
 */
public class BaseApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpCore.init(this)
                .withLog(true)
                .withHttpCat(HttpCat.create(this))
                .done();
    }
}
