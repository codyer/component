/*
 * ************************************************************
 * 文件：HtmlActivity.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月11日 12:53:08
 * 上次修改时间：2019年04月11日 12:52:52
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.activity;

import android.view.View;

import com.cody.component.app.activity.FragmentContainerActivity;
import com.cody.component.hybrid.fragment.HtmlFragment;

import androidx.fragment.app.Fragment;

/**
 * Html 页面
 */
public class HtmlActivity extends FragmentContainerActivity {

    @Override
    public Fragment getFragment() {
        return HtmlFragment.getInstance(null);
    }

    @Override
    public void onClick(final View v) {

    }
}
