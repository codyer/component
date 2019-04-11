/*
 * ************************************************************
 * 文件：OnCreateOptionsListener.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月11日 16:53:11
 * 上次修改时间：2019年04月11日 16:53:11
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid;

import android.view.Menu;

/**
 * Created by xu.yi. on 2019/4/11.
 * 动态创建菜单
 * TODO 实现动态创建
 */
public interface OnCreateOptionsListener {
    void onCreateOptionsMenu(Menu menu);
}
