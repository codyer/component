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

import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.data.ViewData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by cody.yi on 2016/8/24.
 * 将 DataModel 映射到 ViewData
 * 当获取的数据和ViewData有差距时需要使用mapper
 */
interface IDataMapper<BD> {
    @NonNull
    List<ItemViewDataHolder<?>> viewDataHolderList();

    ViewData newViewData();

    /**
     * 将beanData装饰成viewData
     *
     * @param beanData 数据模型，对应网络请求获取的bean或entity
     * @return 视图模型，对应data binding中的viewData
     */
    ViewData mapper(ViewData viewData, BD beanData);

    /**
     * 将beanData装饰成viewData
     *
     * @param beanData 数据模型，对应网络请求获取的bean或entity
     * @return 视图模型，对应data binding中的viewData
     */
    default ViewData mapper(BD beanData) {
        return mapper(newViewData(), beanData);
    }

    /**
     * 将beanData装饰成viewData
     *
     * @param beanData 数据模型，对应网络请求获取的bean或entity
     * @return 视图模型，对应data binding中的viewData
     */
    default ViewData mapper(BD beanData, int position) {
        if (position >= 0 && position < viewDataHolderList().size()) {
            return mapper(viewDataHolderList().get(position), beanData);
        } else {
            return mapper(beanData);
        }
    }

    /**
     * 将beanData装饰成viewData
     * 没有分页的时候使用此函数
     *
     * @param beanDataList 数据模型，对应网络请求获取的bean或entity, beanData最好是已经排好序的
     * @return 视图模型，对应data binding中的viewData
     */
    default List<ItemViewDataHolder<?>> mapperList(List<BD> beanDataList) {
        return mapperList(beanDataList, 0);
    }

    /**
     * 将beanData装饰成viewData
     *
     * @param beanDataList 数据模型，对应网络请求获取的bean或entity, beanData最好是已经排好序的
     * @param start        viewDataHolderList 中需要mapper的开始的位置，默认从0开始
     * @return 视图模型，对应data binding中的viewData
     */
    default List<ItemViewDataHolder<?>> mapperList(List<BD> beanDataList, int start) {

        if (beanDataList == null) {
            beanDataList = new ArrayList<>();
        }

        int vdSize = viewDataHolderList().size();
        int bdSize = beanDataList.size();// 新增的beanData

        //移除多余的项
        while (vdSize - start > bdSize) {
            viewDataHolderList().remove(--vdSize + start);
        }

        for (int i = 0; i < bdSize; i++) {
            ItemViewDataHolder<?> itemViewDataHolder;
            itemViewDataHolder = new ItemViewDataHolder<>(viewDataHolderList().get(i + start).getItemType(), mapper(beanDataList.get(i), i + start));
            itemViewDataHolder.setItemId(i + start);

            if (i + start < vdSize) {
                if (itemViewDataHolder.areContentsTheSame(viewDataHolderList().get(i + start))) {
                    continue;
                }
                viewDataHolderList().set(i + start, itemViewDataHolder);
            } else {
                viewDataHolderList().add(itemViewDataHolder);
            }
        }

        return viewDataHolderList();
    }
}
