/*
 * ************************************************************
 * 文件：CatHttpBin.java  模块：app  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.data;

import com.cody.component.bean.TestDataBean;
import com.cody.http.lib.annotation.Domain;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by xu.yi. on 2019/4/7.
 * component
 */
@Domain("https://httpbin.org")
public interface CatHttpBin {

    @GET("/get")
    Observable<Void> get();

    @POST("/post")
    Observable<Void> post(@Body TestDataBean body);

    @PATCH("/patch")
    Observable<Void> patch(@Body TestDataBean body);

    @PUT("/put")
    Observable<Void> put(@Body TestDataBean body);

    @DELETE("/delete")
    Observable<Void> delete();

    @GET("/status/{tv_code}")
    Observable<Void> status(@Path("tv_code") int code);

    @GET("/stream/{lines}")
    Observable<Void> stream(@Path("lines") int lines);

    @GET("/stream-bytes/{bytes}")
    Observable<Void> streamBytes(@Path("bytes") int bytes);

    @GET("/delay/{seconds}")
    Observable<Void> delay(@Path("seconds") int seconds);

    @GET("/redirect-to")
    Observable<Void> redirectTo(@Query("url") String url);

    @GET("/redirect/{times}")
    Observable<Void> redirect(@Path("times") int times);

    @GET("/relative-redirect/{times}")
    Observable<Void> redirectRelative(@Path("times") int times);

    @GET("/absolute-redirect/{times}")
    Observable<Void> redirectAbsolute(@Path("times") int times);

    @GET("/image")
    Observable<Void> image(@Header("Accept") String accept);

    @GET("/gzip")
    Observable<Void> gzip();

    @GET("/xml")
    Observable<Void> xml();

    @GET("/encoding/utf8")
    Observable<Void> utf8();

    @GET("/deflate")
    Observable<Void> deflate();

    @GET("/cookies/set")
    Observable<Void> cookieSet(@Query("k1") String value);

    @GET("/basic-auth/{user}/{passwd}")
    Observable<Void> basicAuth(@Path("user") String user, @Path("passwd") String passwd);

    @GET("/drip")
    Observable<Void> drip(@Query("numbytes") int bytes, @Query("duration") int seconds, @Query("delay") int delay, @Query("tv_code") int code);

    @GET("/deny")
    Observable<Void> deny();

    @GET("/cache")
    Observable<Void> cache(@Header("If-Modified-Since") String ifModifiedSince);

    @GET("/cache/{seconds}")
    Observable<Void> cache(@Path("seconds") int seconds);

}
