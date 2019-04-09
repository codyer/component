/*
 * ************************************************************
 * 文件：ItemHttpDataKeydDataSource.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月07日 22:18:31
 * 上次修改时间：2019年04月07日 22:18:31
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.repository;

import com.cody.http.cat.db.data.ItemHttpData;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

/**
 * Created by xu.yi. on 2019/4/7.
 * 根据上一个item的id找下一个id
 */
public class ItemHttpDataKeydDataSource extends ItemKeyedDataSource<Long, ItemHttpData> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<ItemHttpData> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<ItemHttpData> callback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<ItemHttpData> callback) {

    }

    @NonNull
    @Override
    public Long getKey(@NonNull ItemHttpData item) {
        return item.getId();
    }
}
