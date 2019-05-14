/*
 * ************************************************************
 * 文件：PageDataMapper.java  模块：handler-core  项目：component
 * 当前修改时间：2019年05月13日 12:45:40
 * 上次修改时间：2019年04月29日 08:26:25
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.mapper;

import androidx.arch.core.util.Function;

import com.cody.component.handler.data.ItemViewDataHolder;

/**
 * Created by cody.yi on 2016/8/24.
 * 将 DataModel 映射到 ViewData
 * 当获取的数据和ViewData有差距时需要使用mapper
 */
public abstract class PageDataMapper<Item extends ItemViewDataHolder, Bean> extends DataMapper<Item, Bean> implements Function<Bean, Item> {

    @Override
    public Item apply(Bean bean) {
        Item item = mapperItem(bean);
        increase();
        return item;
    }
}
