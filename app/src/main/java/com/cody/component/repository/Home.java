/*
 * ************************************************************
 * 文件：Home.java  模块：app  项目：component
 * 当前修改时间：2019年04月28日 17:03:00
 * 上次修改时间：2019年04月28日 17:02:51
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.repository;

import com.cody.component.bean.HomeBean;
import com.cody.component.bean.RecommendGoodsBean;
import com.cody.component.lib.bean.Result;
import com.cody.http.lib.annotation.Domain;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 *
 */
@Domain("https://zkh-gbb-app-ms-fat.gongbangbang.com/")
public interface Home {

    @GET("v2/app/home")
    Observable<Result<HomeBean>> getHomeData();

    @GET("v2/app/home/bbjx")
    Observable<Result<RecommendGoodsBean>> getRecommendGoods();
}
