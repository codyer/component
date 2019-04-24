/*
 * ************************************************************
 * 文件：ListTestActivity.java  模块：app  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月14日 16:08:30
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list;

import android.view.View;

import com.cody.component.app.activity.FragmentContainerActivity;

import androidx.fragment.app.Fragment;

public class ListTestActivity extends FragmentContainerActivity {

    @Override
    public Fragment getFragment() {
        return new PageListTestBindFragment();
    }

    @Override
    public void onClick(final View v) {

    }

    @Override
    public void scrollToTop() {

    }
}
