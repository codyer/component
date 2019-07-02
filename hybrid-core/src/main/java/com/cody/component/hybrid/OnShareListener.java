/*
 * ************************************************************
 * 文件：OnShareListener.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年07月02日 10:15:13
 * 上次修改时间：2019年05月06日 19:33:24
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid;

import android.app.Activity;

import com.cody.component.hybrid.data.HtmlConfig;

/**
 * Created by xu.yi. on 2019/4/11.
 * 分享监听
 */
public interface OnShareListener {
    void share(Activity activity, HtmlConfig config);
}
