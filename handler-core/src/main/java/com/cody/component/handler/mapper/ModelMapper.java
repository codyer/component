/*
 * ************************************************************
 * 文件：ModelMapper.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月25日 08:38:07
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

/**
 * Created by cody.yi on 2016/8/24.
 * 将 beanModel 映射到 viewData
 * 当获取的数据和viewData有差距时需要使用mapper
 */
interface ModelMapper<BeanModel> {
/*
    *//**
     * 将beanModel装饰成viewData
     *
     * @param beanModel 数据模型，对应网络请求获取的bean或entity
     * @return 视图模型，对应data binding中的viewData
     *//*
    <VD extends ViewData> VD mapper(BeanModel beanModel);

    *//**
     * 将beanModel装饰成viewData
     *
     * @param beanModel 数据模型，对应网络请求获取的bean或entity
     * @return 视图模型，对应data binding中的viewData
     *//*
    <VD extends ViewData> VD mapper(BeanModel beanModel, int position);

    *//**
     * 将beanModel装饰成viewData
     *
     * @param beanModel 数据模型，对应网络请求获取的bean或entity
     * @return 视图模型，对应data binding中的viewData
     *//*
    <VD extends ViewData> VD mapper(VD uiModel, BeanModel beanModel);

    *//**
     * 将beanModel装饰成viewData
     * 没有分页的时候使用此函数
     *
     * @param beanModels 数据模型，对应网络请求获取的bean或entity, beanModel最好是已经排好序的
     * @return 视图模型，对应data binding中的viewData
     *//*
    @Deprecated
    <XVM extends XItemviewData> ListviewData<XVM> mapperList(List<BeanModel> beanModels) {
        return mapperList(beanModels, 0);
    }

    *//**
     * 将beanModel装饰成viewData
     *
     * @param beanModels 数据模型，对应网络请求获取的bean或entity, beanModel最好是已经排好序的
     * @param start      viewDataList 中需要mapper的开始的位置，默认从0开始
     * @return 视图模型，对应data binding中的viewData
     *//*
    <XVM extends XItemviewData> ListviewData<XVM> mapperList(List<BeanModel> beanModels, int start) {
        ListviewData<XVM> items = new ListviewData<>();
        return mapperList(items, beanModels, start, true);
    }

    *//**
     * 将beanModel装饰成viewData
     * 没有分页的时候使用此函数
     *
     * @param viewDataList 页面模型
     * @param beanModels   数据模型，对应网络请求获取的bean或entity, beanModel最好是已经排好序的
     * @return 视图模型，对应data binding中的viewData
     *//*
    @Deprecated
    <XVM extends XItemviewData> ListviewData<XVM> mapperList(ListviewData<XVM> viewDataList, List<BeanModel> beanModels) {
        return mapperList(viewDataList, beanModels, 0, true);
    }

    *//**
     * 将beanModel装饰成viewData
     *
     * @param viewDataList 页面模型
     * @param beanModels   数据模型，对应网络请求获取的bean或entity, beanModel最好是已经排好序的
     * @param start        viewDataList 中需要mapper的开始的位置，默认从0开始，getviewData().getPosition()分页时调用
     * @return 视图模型，对应data binding中的viewData
     *//*
    @Deprecated
    <XVM extends XItemviewData> ListviewData<XVM> mapperList(ListviewData<XVM> viewDataList, List<BeanModel> beanModels, int start) {
        return mapperList(viewDataList, beanModels, start, true);
    }

    *//**
     * 将beanModel装饰成viewData
     *
     * @param viewDataList 页面模型
     * @param beanModels   数据模型，对应网络请求获取的bean或entity, beanModel最好是已经排好序的
     * @param start        viewDataList 中需要mapper的开始的位置，默认从0开始，getviewData().getPosition()分页时调用
     * @return 视图模型，对应data binding中的viewData
     *//*
    List<ItemViewDataHolder<VD>> mapperList(List<ItemViewDataHolder<VD>> viewDataList, List<BeanModel> beanModels, int start, boolean hasMore) {

        if (viewDataList == null) {
            return mapperList(beanModels, start);
        }

        if (beanModels == null) {
            beanModels = new ArrayList<>();
        }

        ItemViewDataHolder<VD> xvm;
        int vmSize = viewDataList.size();
        int dmSize = beanModels.size() + start;//所有的beanModel

        //移除多余的项
        while (vmSize > dmSize) {
            viewDataList.remove(--vmSize);
        }

        for (int i = start; i < dmSize; i++) {
            xvm = (ItemViewDataHolder<VD>) mapper(beanModels.get(i - start), i);
            xvm.setId(i);

            xvm.setFirstItem(i == 0);
            xvm.setLastItem(!hasMore && i == dmSize - 1);

            if (i < vmSize) {
                if (xvm.equals(viewDataList.get(i))) {
                    continue;
                }
                viewDataList.set(i, xvm);
            } else {
                viewDataList.add(xvm);
            }
        }

        viewDataList.setHasMore(hasMore);
        return viewDataList;
    }*/
}
