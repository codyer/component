/*
 * ************************************************************
 * 文件：BaseRepository.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http.repository;

/**
 * Created by xu.yi. on 2019/4/6.
 *
 */
public class BaseRepository<T> {

    protected final T mDataSource;

    public BaseRepository(T dataSource) {
        this.mDataSource = dataSource;
    }
}