/*
 * ************************************************************
 * 文件：IPageDataMapper.java  模块：handler-core  项目：component
 * 当前修改时间：2019年05月13日 12:45:40
 * 上次修改时间：2019年04月29日 08:26:25
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.mapper;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;

import com.cody.component.handler.data.ItemViewDataHolder;

/**
 * Created by cody.yi on 2016/8/24.
 * 将 DataModel 映射到 ViewData
 * 当获取的数据和ViewData有差距时需要使用mapper
 */
public interface IPageDataMapper<Item extends ItemViewDataHolder, Bean> extends Function<Bean, Item> {

    @NonNull
    Item createItem();

    /**
     * 将 bean 装饰成 Item viewData
     *
     * @param bean 数据模型，对应网络请求获取的bean或entity
     * @return 视图模型，对应 data binding 中的 viewData
     */
    Item mapperItem(@NonNull Item item, Bean bean);

    @Override
    default Item apply(Bean bean) {
        return mapperItem(createItem(), bean);
    }
}
