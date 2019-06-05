/*
 * ************************************************************
 * 文件：CatApiOpen.java  模块：app  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.demo.data;

import com.cody.component.http.lib.annotation.Domain;

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
