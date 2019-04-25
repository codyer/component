/*
 * ************************************************************
 * 文件：PageDataCallBack.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月24日 09:53:13
 * 上次修改时间：2019年04月23日 18:23:20
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.interfaces;


import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.lib.bean.ListBean;
import com.cody.component.lib.bean.Result;
import com.cody.component.handler.define.PageInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by xu.yi. on 2019/4/8.
 * 获取分页数据后进行回调的接口
 */
public interface PageDataCallBack {
    /**
     * eg: callBack.onSuccess(listResult.getData().getItems(), PageInfo.getPrePageInfo(listResult), PageInfo.getNextPageInfo(listResult));
     */
    void onSuccess(@NonNull List<ItemViewDataHolder<?>> data, @Nullable PageInfo prePageKey, @Nullable PageInfo nextPageKey);

    /**
     * eg: callBack.onSuccess(result);
     */
    default void onSuccess(List<ItemViewDataHolder<?>> data, @NonNull Result<ListBean<?>> result) {
        onSuccess(data, PageInfo.getPrePageInfo(result), PageInfo.getNextPageInfo(result));
    }

    /**
     * eg: callBack.onFailure(message);
     */
    void onFailure(String message);
}
