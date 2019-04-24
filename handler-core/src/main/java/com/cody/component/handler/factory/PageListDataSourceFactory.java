/*
 * ************************************************************
 * 文件：PageListDataSourceFactory.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.factory;

import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.livedata.SafeMutableLiveData;
import com.cody.component.handler.define.PageInfo;
import com.cody.component.handler.interfaces.OnRequestPageListener;
import com.cody.component.handler.source.PageListKeyedDataSource;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.paging.DataSource;

/**
 * Created by xu.yi. on 2019/4/8.
 * 根据接口返回的信息进行分页加载数据工厂基类
 * 泛型为分页Item的类类型
 */
public class PageListDataSourceFactory<ItemBean> extends DataSource.Factory<PageInfo, ItemBean> {
    private SafeMutableLiveData<PageListKeyedDataSource<ItemBean>> mDataSource = new SafeMutableLiveData<>();
    private OnRequestPageListener<ItemBean> mOnRequestPageListener;
    private Function<ItemBean, ItemViewDataHolder<?>> mModelMapper;

    public PageListDataSourceFactory(OnRequestPageListener<ItemBean> onRequestPageListener, Function<ItemBean, ItemViewDataHolder<?>> modelMapper) {
        mOnRequestPageListener = onRequestPageListener;
        mModelMapper = modelMapper;
    }

    @NonNull
    public DataSource.Factory<PageInfo, ItemViewDataHolder<?>> map() {
        return super.map(mModelMapper);
    }

    @NonNull
    @Override
    public DataSource<PageInfo, ItemBean> create() {
        PageListKeyedDataSource<ItemBean> dataSource = new PageListKeyedDataSource<>(mOnRequestPageListener);
        mDataSource.postValue(dataSource);
        return dataSource;
    }

    public SafeMutableLiveData<PageListKeyedDataSource<ItemBean>> getDataSource() {
        return mDataSource;
    }
}
