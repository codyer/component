/*
 * ************************************************************
 * 文件：IDataMapper.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月25日 09:37:39
 * 上次修改时间：2018年09月26日 13:42:14
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.mapper;

import com.cody.component.handler.UnImplementException;
import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.data.ViewData;
import com.cody.component.handler.define.Operation;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by cody.yi on 2016/8/24.
 * 将 DataModel 映射到 ViewData
 * 当获取的数据和ViewData有差距时需要使用mapper
 */
public interface IDataMapper {
    //下次数据开始的位置
    default int getPosition(Operation operation, List<ItemViewDataHolder> viewDataList) {
        if (viewDataList == null || isRefreshing(operation)) {
            return 0;
        }
        return viewDataList.size();
    }

    default boolean isRefreshing(final Operation operation) {
        return operation == Operation.INIT || operation == Operation.REFRESH;
    }

    @NonNull
    default ItemViewDataHolder newItemViewData(int position) {
        throw new UnImplementException("please implement at least one mapping method !");
    }

    /**
     * 将beanData装饰成viewData
     *
     * @param beanData 数据模型，对应网络请求获取的bean或entity
     * @return 视图模型，对应data binding中的viewData
     */
    default <ItemBean> ItemViewDataHolder mapperItem(@NonNull ItemViewDataHolder itemViewDataHolder, ItemBean beanData, int position) {
        throw new UnImplementException("please implement at least one mapping method !");
    }

    /**
     * 将beanData装饰成viewData
     *
     * @param beanData 数据模型，对应网络请求获取的bean或entity
     * @return 视图模型，对应data binding中的viewData
     */
    default <ItemBean> ItemViewDataHolder mapperItem(ItemBean beanData, int position) {
        return mapperItem(newItemViewData(position), beanData, position);
    }

    /**
     * 将beanData装饰成viewData
     * 加载新分页的时候使用此函数
     *
     * @param beanDataList 数据模型，对应网络请求获取的bean或entity, beanData最好是已经排好序的
     * @return 视图模型，对应data binding中的viewData
     */
    default <ItemBean> List<ItemViewDataHolder> mapperList(Operation operation, List<ItemBean> beanDataList) {
        return mapperList(operation, null, beanDataList, 0);
    }

    /**
     * 将beanData装饰成viewData
     * 没有分页的时候使用此函数
     *
     * @param beanDataList 数据模型，对应网络请求获取的bean或entity, beanData最好是已经排好序的
     * @return 视图模型，对应data binding中的viewData
     */
    default <ItemBean> List<ItemViewDataHolder> mapperList(Operation operation, List<ItemViewDataHolder> viewDataList, List<ItemBean> beanDataList) {
        return mapperList(operation, viewDataList, beanDataList, getPosition(operation, viewDataList));
    }

    /**
     * 将beanData装饰成viewData
     *
     * @param beanDataList 数据模型，对应网络请求获取的bean或entity, beanData最好是已经排好序的
     * @param start        viewDataHolderList 中需要mapper的开始的位置，默认从最后开始
     * @return 视图模型，对应data binding中的viewData
     */
    default <ItemBean> List<ItemViewDataHolder> mapperList(Operation operation, List<ItemViewDataHolder> viewDataList, List<ItemBean> beanDataList, int start) {

        if (viewDataList == null || !isRefreshing(operation)) {
            viewDataList = new ArrayList<>();
        }

        if (beanDataList == null) {
            beanDataList = new ArrayList<>();
        }
        int vdSize = viewDataList.size();
        int bdSize = beanDataList.size();// 新增的beanData

        //移除多余的项
        while (vdSize - start > bdSize) {
            viewDataList.remove(--vdSize + start);
        }

        if (Modifier.isAbstract(viewDataList.getClass().getModifiers())) {
            viewDataList = new ArrayList<>(viewDataList);
        }

        for (int i = 0; i < bdSize; i++) {
            ItemViewDataHolder itemViewDataHolder = null;
            if (i + start < viewDataList.size() && i + start >= 0) {
                itemViewDataHolder = viewDataList.get(i + start);
            }
            if (itemViewDataHolder == null) {
                itemViewDataHolder = mapperItem(beanDataList.get(i), i + start);
            } else {
                itemViewDataHolder = mapperItem(itemViewDataHolder, beanDataList.get(i), i + start);
            }
            itemViewDataHolder.setItemId(i + start);

            if (i + start < vdSize) {
                if (itemViewDataHolder.areItemsTheSame(viewDataList.get(i + start)) && itemViewDataHolder.areContentsTheSame(viewDataList.get(i + start))) {
                    continue;
                }
                viewDataList.set(i + start, itemViewDataHolder);
            } else {
                viewDataList.add(itemViewDataHolder);
            }
        }

        return viewDataList;
    }
}
