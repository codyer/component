/*
 * ************************************************************
 * 文件：AccountDataSource.java  模块：app  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.repository;


import com.cody.component.handler.BaseViewModel;
import com.cody.http.core.BaseRemoteDataSource;
import com.cody.http.core.callback.RequestCallback;
import com.cody.component.lib.bean.Result;

import io.reactivex.Observable;

/**
 * 作者：leavesC
 * 时间：2019/1/30 13:02
 * 描述：
 */
public class AccountDataSource extends BaseRemoteDataSource implements IAccountDataSource {

    public AccountDataSource(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void queryWeather(String cityName, RequestCallback<Weather> callback) {
        Account account = getService(Account.class);
        Observable<Result<Weather>> observable = account.queryWeather(cityName);
        execute(observable, callback);
    }

    @Override
    public void createQrCode(String text, int width, RequestCallback<QrCode> callback) {
        execute(getService(Account.class).createQrCode(text, width), callback);
    }

    @Override
    public void getNews(RequestCallback<NewsPack> callback) {
        execute(getService(Account.class).getNews(), callback);
    }

    @Override
    public void test1(RequestCallback<String> callback) {
        execute(getService(Account.class).test1(), callback);
    }

    @Override
    public void test2(RequestCallback<String> callback) {
        execute(getService(Account.class).test2(), callback);
    }

}