/*
 * ************************************************************
 * 文件：ApplicationUtil.java  模块：util-core  项目：component
 * 当前修改时间：2019年06月18日 09:26:58
 * 上次修改时间：2019年06月18日 09:26:58
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：util-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.util;

import android.app.Application;

/**
 * Created by xu.yi. on 2019-06-18.
 * component holder application
 */
public class ApplicationUtil {
    private static Application sApplication;

    public static void install(Application application) {
        sApplication = application;
    }

    public static Application getApplication() {
        if (sApplication == null){
            throw new NullPointerException("You should call ApplicationUtil.install() in you application first!");
        }
        return sApplication;
    }
}
