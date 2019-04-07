/*
 * ************************************************************
 * 文件：BaseRepository.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月06日 02:03:31
 * 上次修改时间：2019年03月25日 09:23:00
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core;

/**
 * Created by xu.yi. on 2019/4/6.
 *
 */
public class BaseRepository<T> {

    protected T mRemoteDataSource;

    public BaseRepository(T remoteDataSource) {
        this.mRemoteDataSource = remoteDataSource;
    }
}