/*
 * ************************************************************
 * 文件：IAccountDataSource.java  模块：app  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.repository;


import com.cody.http.core.callback.RequestCallback;

/**
 * 自动生成，不要修改
 */
public interface IAccountDataSource {

    void queryWeather(String cityName, RequestCallback<Weather> callback);

    void createQrCode(String text, int width, RequestCallback<QrCode> callback);

    void getNews(RequestCallback<NewsPack> callback);

    void test1(RequestCallback<String> callback);

    void test2(RequestCallback<String> callback);
}
