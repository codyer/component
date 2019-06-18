/*
 * ************************************************************
 * 文件：FriendlyViewData.java  模块：handler-core  项目：component
 * 当前修改时间：2019年06月13日 15:09:08
 * 上次修改时间：2019年05月27日 17:13:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.data;

import com.cody.component.handler.R;
import com.cody.component.handler.livedata.StringLiveData;
import com.cody.component.util.ApplicationUtil;
import com.cody.component.util.ScreenUtil;

import java.util.Objects;

/**
 * Created by xu.yi. on 2019/4/9.
 * 页面出错、无网络、无数据等布局需要的数据
 */
public class FriendlyViewData extends ViewData {
    final private StringLiveData mMessage = new StringLiveData("数据为空");

    public int imageWidth() {
        return ScreenUtil.getScreenWidth(ApplicationUtil.getApplication()) * 129 / 375;
    }

    public int getLoadingResId() {
        return R.drawable.ic_loading_gif;
    }

    public StringLiveData getMessage() {
        return mMessage;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FriendlyViewData that = (FriendlyViewData) o;
        return Objects.equals(mMessage, that.mMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mMessage);
    }
}
