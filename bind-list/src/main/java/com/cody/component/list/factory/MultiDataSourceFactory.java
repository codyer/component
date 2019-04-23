/*
 * ************************************************************
 * 文件：MultiDataSourceFactory.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-list
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.factory;

import com.cody.component.lib.data.ItemViewDataHolder;
import com.cody.component.lib.safe.SafeMutableLiveData;
import com.cody.component.list.define.PageInfo;
import com.cody.component.list.listener.OnRequestPageListener;
import com.cody.component.list.source.MultiPageKeyedDataSource;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.paging.DataSource;

/**
 * Created by xu.yi. on 2019/4/8.
 * 根据接口返回的信息进行分页加载数据工厂基类
 * 泛型为分页Item的类类型
 */
public class MultiDataSourceFactory<ItemBean> extends DataSource.Factory<PageInfo, ItemBean> {
    private SafeMutableLiveData<MultiPageKeyedDataSource<ItemBean>> mDataSource = new SafeMutableLiveData<>();
    private OnRequestPageListener<ItemBean> mOnRequestPageListener;
    private Function<ItemBean, ItemViewDataHolder> mModelMapper;

    public MultiDataSourceFactory(OnRequestPageListener<ItemBean> onRequestPageListener, Function<ItemBean, ItemViewDataHolder> modelMapper) {
        mOnRequestPageListener = onRequestPageListener;
        mModelMapper = modelMapper;
    }

    @NonNull
    public DataSource.Factory<PageInfo, ItemViewDataHolder> map() {
        return super.map(mModelMapper);
    }

    @NonNull
    @Override
    public DataSource<PageInfo, ItemBean> create() {
        MultiPageKeyedDataSource<ItemBean> dataSource = new MultiPageKeyedDataSource<>(mOnRequestPageListener);
        mDataSource.postValue(dataSource);
        return dataSource;
    }

    public SafeMutableLiveData<MultiPageKeyedDataSource<ItemBean>> getDataSource() {
        return mDataSource;
    }
}
