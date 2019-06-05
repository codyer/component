/*
 * ************************************************************
 * 文件：AccountRepository.java  模块：app  项目：component
 * 当前修改时间：2019年05月22日 10:38:58
 * 上次修改时间：2019年05月22日 10:38:58
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.demo.repository;

import com.cody.component.handler.viewmodel.BaseViewModel;
import com.cody.component.http.callback.RequestCallback;
import com.cody.component.http.repository.BaseRepository;

/**
 * Created by xu.yi. on 2019-05-22.
 * component
 */
public class AccountRepository extends BaseRepository<IAccountDataSource> implements IAccountDataSource {

    public static AccountRepository getRepository(BaseViewModel viewModel) {
        return new AccountRepository(viewModel);
    }

    public AccountRepository(BaseViewModel viewModel) {
        super(new AccountDataSource(viewModel));
    }

    @Override
    public void queryWeather(final String cityName, final RequestCallback<Weather> callback) {
        // 自动生成发现这个方法需要缓存

        mDataSource.queryWeather(cityName, callback);
    }

    @Override
    public void createQrCode(final String text, final int width, final RequestCallback<QrCode> callback) {
        mDataSource.createQrCode(text, width, callback);
    }

    @Override
    public void getNews(final RequestCallback<NewsPack> callback) {
        mDataSource.getNews(callback);
    }

    @Override
    public void test1(final RequestCallback<String> callback) {
        mDataSource.test1(callback);
    }

    @Override
    public void test2(final RequestCallback<String> callback) {
        mDataSource.test2(callback);
    }
}
