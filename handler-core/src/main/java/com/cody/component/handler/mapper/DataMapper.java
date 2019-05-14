/*
 * ************************************************************
 * 文件：DataMapper.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月25日 09:37:39
 * 上次修改时间：2018年09月26日 13:42:14
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.mapper;

import androidx.annotation.NonNull;

import com.cody.component.handler.data.ItemViewDataHolder;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cody.yi on 2016/8/24.
 * 将 DataModel 映射到 ViewData
 * 当获取的数据和ViewData有差距时需要使用mapper
 */
public abstract class DataMapper<Item extends ItemViewDataHolder, Bean> {
    private int mPosition = 0;

    public int getPosition() {
        return mPosition;
    }

    public void init() {
        mPosition = 0;
    }

    //增加一个item
    int increase() {
        return mPosition++;
    }

    @NonNull
    public abstract Item createItem();

    /**
     * 将 bean 装饰成 Item viewData
     *
     * @param bean 数据模型，对应网络请求获取的bean或entity
     * @return 视图模型，对应 data binding 中的 viewData
     */
    public abstract Item mapperItem(@NonNull Item item, Bean bean);

    /**
     * 将bean装饰成viewData
     *
     * @param bean 数据模型，对应网络请求获取的bean或entity
     * @return 视图模型，对应data binding中的viewData
     */
    Item mapperItem(Bean bean) {
        Item item = createItem();
        item.setItemId(mPosition);
        return mapperItem(item, bean);
    }

    /**
     * 将bean装饰成viewData
     * 加载新分页的时候使用此函数
     *
     * @param beanList 数据模型，对应网络请求获取的bean或entity, bean最好是已经排好序的
     * @return 视图模型，对应data binding中的viewData
     */
    public List<Item> mapperList(List<Bean> beanList) {
        return mapperList(null, beanList);
    }

    /**
     * 将bean装饰成viewData
     *
     * @param beanList 数据模型，对应网络请求获取的bean或entity, bean最好是已经排好序的
     *                 start        viewDataHolderList 中需要mapper的开始的位置，默认从最后开始
     * @return 视图模型，对应data binding中的viewData
     */
    public List<Item> mapperList(List<Item> oldItems, List<Bean> beanList) {
        if (oldItems == null) {
            oldItems = new ArrayList<>();
        }

        if (beanList == null) {
            oldItems.clear();
            return oldItems;
        }
        int oldSize = oldItems.size();
        int beanSize = beanList.size();// 新增的bean
        int start = mPosition;
        //移除多余的项
        while (oldSize > beanSize + start) {
            oldItems.remove(--oldSize);
        }

        if (Modifier.isAbstract(oldItems.getClass().getModifiers())) {
            oldItems = new ArrayList<>(oldItems);
        }

        for (int i = 0; i < beanSize; i++) {
            Item item = mapperItem(beanList.get(i));
            increase();
            if (i + start < oldSize) {
                oldItems.set(i + start, item);
            } else {
                oldItems.add(item);
            }
        }

        return oldItems;
    }
}
