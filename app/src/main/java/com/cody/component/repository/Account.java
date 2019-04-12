/*
 * ************************************************************
 * 文件：Account.java  模块：app  项目：component
 * 当前修改时间：2019年04月12日 09:21:11
 * 上次修改时间：2019年04月10日 15:58:25
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.repository;

import com.cody.http.lib.annotation.Domain;
import com.cody.component.lib.bean.Result;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 */
@Domain("https://www.jianshu.com/")
public interface Account {

    @GET("onebox/weather/query")
    Observable<Result<Weather>> queryWeather(@Query("cityname") String cityName);

    @GET("qrcode/api")
    Observable<Result<QrCode>> createQrCode(@Query("text") String text, @Query("w") int width);

    @GET("toutiao/index")
    Observable<Result<NewsPack>> getNews();

    @GET("leavesC/test1")
    Observable<Result<String>> test1();

    @GET("leavesC/test2")
    Observable<Result<String>> test2();

}
