/*
 * ************************************************************
 * 文件：BaseApplication.java  模块：app-core  项目：component
 * 当前修改时间：2019年05月27日 19:57:57
 * 上次修改时间：2019年05月24日 15:21:32
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app;


import androidx.multidex.MultiDexApplication;

import com.cody.component.app.local.Repository;
import com.cody.component.app.widget.swipebacklayout.BGASwipeBackHelper;

/**
 * Created by xu.yi. on 2019/4/7.
 * component
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Repository.install(this);
        /*
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this, null);
    }
}
