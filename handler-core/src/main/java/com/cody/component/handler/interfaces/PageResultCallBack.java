/*
 * ************************************************************
 * 文件：PageResultCallBack.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月24日 09:53:13
 * 上次修改时间：2019年04月23日 18:23:20
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.interfaces;


import com.cody.component.lib.bean.ListBean;
import com.cody.component.handler.define.PageInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by xu.yi. on 2019/4/8.
 * 获取分页数据后进行回调的接口
 */
public interface PageResultCallBack<Bean> {
    /**
     * eg: callBack.onComplete(listResult.getData().getItems(), PageInfo.getPrePageInfo(listResult), PageInfo.getNextPageInfo(listResult));
     */
    void onResult(@NonNull List<Bean> data, @Nullable PageInfo prePageKey, @Nullable PageInfo nextPageKey);

    /**
     * eg: callBack.onComplete(result);
     */
    default void onResult(List<Bean> data, @Nullable PageInfo prePageKey, @NonNull ListBean<?> listBean) {
        onResult(data, PageInfo.getPrePageInfo(prePageKey, listBean), PageInfo.getNextPageInfo(prePageKey, listBean));
    }
}