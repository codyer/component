/*
 * ************************************************************
 * 文件：BaseRepository.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月07日 12:40:29
 * 上次修改时间：2019年04月07日 12:40:21
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.repository;

/**
 * Created by xu.yi. on 2019/4/6.
 *
 */
public class BaseRepository<T> {

    protected final T mRemoteDataSource;

    public BaseRepository(T remoteDataSource) {
        this.mRemoteDataSource = remoteDataSource;
    }
}