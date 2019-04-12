/*
 * ************************************************************
 * 文件：BaseApplication.java  模块：app  项目：component
 * 当前修改时间：2019年04月12日 09:21:11
 * 上次修改时间：2019年04月11日 21:32:24
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component;

import com.cody.component.image.ImagePicker;
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
        ImagePicker.init();
        HttpCore.init(this)
                .withLog(true)
                .withHttpCat(HttpCat.create(this))
                .done();
    }
}
