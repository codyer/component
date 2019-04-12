/*
 * ************************************************************
 * 文件：CatApiOpen.java  模块：app  项目：component
 * 当前修改时间：2019年04月12日 09:21:11
 * 上次修改时间：2019年04月11日 20:06:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.data;

import com.cody.http.lib.annotation.Domain;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by xu.yi. on 2019/4/7.
 * component
 */
@Domain("https://api.apiopen.top")
public interface CatApiOpen {

    @GET("/singlePoetry")
    @Headers({"testHeader" + ":" + "singlePoetry"})
    Observable<String> singlePoetry();

    @GET("/recommendPoetry")
    @Headers({"testHeader" + ":" + "recommendPoetry"})
    Observable<String> recommendPoetry();

    @GET("/musicBroadcasting")
    @Headers({"testHeader" + ":" + "musicBroadcasting"})
    Observable<String> musicBroadcasting();

    @GET("/novelApi")
    @Headers({"testHeader" + ":" + "novelApi"})
    Observable<String> novelApi();
}
