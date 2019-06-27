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
import com.cody.component.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cody.yi on 2016/8/24.
 * 将 DataModel 映射到 ViewData
 * 当获取的数据和ViewData有差距时需要使用mapper
 */
public abstract class DataMapper<Item extends ItemViewDataHolder, Bean> {
    private int mPosition = 0;
    private List<Item> mOldItems;

    public List<Item> getOldItems() {
        return mOldItems;
    }

    /**
     * 保留oldItem用于clone已经绑定到view的变量，否则绑定会失效
     * ListViewModel使用 mapperList 方法，所有不用注入oldItems，PageListViewModel需要主动注入
     */
    public void setOldItems(final List<Item> oldItems) {
        mOldItems = oldItems;
    }

    @SuppressWarnings("unchecked")
    public Item findItem() {
        if (mOldItems != null && getPosition() < mOldItems.size()) {
            Item item = mOldItems.get(getPosition());
            if (item != null) {
                try {
                    return (Item) item.clone();
                } catch (Exception e) {
                    LogUtil.e("DataMapper", e);
                }
            }
        }
        return null;
    }

    public int getPosition() {
        return mPosition;
    }

    public void init() {
        mPosition = 0;
    }

    //增加一个item
    void increase() {
        mPosition++;
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
        Item item = findItem();
        if (item == null) {
            item = createItem();
        }
        item.setItemId(mPosition);
        if (bean == null) return item;
        return mapperItem(item, bean);
    }

    /**
     * 将bean装饰成viewData
     * 一次性加载的list使用，每次都重新计算位置
     *
     * @param beanList 数据模型，对应网络请求获取的bean或entity, bean最好是已经排好序的
     * @return 视图模型，对应data binding中的viewData
     */
    public List<Item> mapperListInit(List<Bean> beanList) {
        init();
        return mapperList(mOldItems, beanList);
    }

    /**
     * 将bean装饰成viewData
     * 子list使用，每次都重新计算位置，且不同Item可以共用同一个data mapper
     *
     * @param beanList 数据模型，对应网络请求获取的bean或entity, bean最好是已经排好序的
     * @return 视图模型，对应data binding中的viewData
     */
    public List<Item> mapperListInit(List<Item> oldItems, List<Bean> beanList) {
        init();
        return mapperList(oldItems, beanList);
    }

    /**
     * 将bean装饰成viewData
     * 加载新分页的时候使用此函数
     *
     * @param beanList 数据模型，对应网络请求获取的bean或entity, bean最好是已经排好序的
     * @return 视图模型，对应data binding中的viewData
     */
    public List<Item> mapperListAppend(List<Bean> beanList) {
        return mapperList(mOldItems, beanList);
    }


    /**
     * 将bean装饰成viewData
     * 加载新分页的时候使用此函数
     *
     * @param beanList 数据模型，对应网络请求获取的bean或entity, bean最好是已经排好序的
     * @return 视图模型，对应data binding中的viewData
     */
    public List<Item> mapperListAppend(List<Item> oldItems, List<Bean> beanList) {
        return mapperList(oldItems, beanList);
    }

    /**
     * 将bean装饰成viewData
     *
     * @param beanList 数据模型，对应网络请求获取的bean或entity, bean最好是已经排好序的
     *                 start        viewDataHolderList 中需要mapper的开始的位置，默认从最后开始
     * @return 视图模型，对应data binding中的viewData
     */
    private List<Item> mapperList(List<Item> oldItems, List<Bean> beanList) {
        mOldItems = oldItems;
        if (mOldItems == null) {
            mOldItems = new ArrayList<>();
            init();
        }

        if (beanList == null) {
            mOldItems = new ArrayList<>();
            init();
            return mOldItems;
        }
        int oldSize = mOldItems.size();
        int beanSize = beanList.size();// 新增的bean
        int start = mPosition;
        //浅拷贝一份，不要动原来的数据
        List<Item> newItems = new ArrayList<>(mOldItems);
        //移除多余的项
        while (oldSize > beanSize + start) {
            newItems.remove(--oldSize);
        }

        for (int i = 0; i < beanSize; i++) {
            Item item = mapperItem(beanList.get(i));
            increase();
            if (i + start < oldSize) {
                newItems.set(i + start, item);
            } else {
                newItems.add(item);
            }
        }

        return mOldItems = newItems;
    }
}
