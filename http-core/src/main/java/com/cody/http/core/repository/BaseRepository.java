/*
 * ************************************************************
 * 文件：BaseRepository.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月07日 18:02:21
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-core
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