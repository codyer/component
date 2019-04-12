/*
 * ************************************************************
 * 文件：BaseRemoteDataSource.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月10日 15:58:25
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core;

import com.cody.component.lib.view.IView;
import com.cody.component.lib.bean.Result;
import com.cody.http.core.callback.RequestCallback;
import com.cody.http.lib.config.TimeConfig;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xu.yi. on 2019/4/6.
 */
public abstract class BaseRemoteDataSource {

    private final CompositeDisposable mCompositeDisposable;
    private final IView mBaseViewModel;

    public BaseRemoteDataSource(IView baseViewModel) {
        this.mCompositeDisposable = new CompositeDisposable();
        this.mBaseViewModel = baseViewModel;
    }

    protected <T> T getService(Class<T> clz) {
        return RetrofitManagement.getInstance().getService(clz);
    }

    public void clear() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    public void dispose() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    /**
     * 原始数据对象不做解析
     */
    protected <T> void executeOriginal(Observable<T> observable, RequestCallback<T> callback) {
        Disposable disposable = observable
                .throttleFirst(TimeConfig.WINDOW_DURATION, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(loadingTransformer(callback))
                .subscribeWith(new BaseSubscriber<>(callback));
        addDisposable(disposable);
    }

    /**
     * 直接解析出result中的data
     */
    protected <T> void execute(Observable<Result<T>> observable, RequestCallback<T> callback) {
        Disposable disposable = observable
                .throttleFirst(TimeConfig.WINDOW_DURATION, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(applySchedulers())
                .compose(loadingTransformer(callback))
                .subscribeWith(new BaseSubscriber<>(callback));
        addDisposable(disposable);
    }

    private void addDisposable(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    private <T> ObservableTransformer<Result<T>, T> applySchedulers() {
        return RetrofitManagement.getInstance().applySchedulers();
    }

    private void showLoading() {
        if (mBaseViewModel != null) {
            mBaseViewModel.showLoading();
        }
    }

    private void hideLoading() {
        if (mBaseViewModel != null) {
            mBaseViewModel.hideLoading();
        }
    }

    private <T> ObservableTransformer<T, T> loadingTransformer(RequestCallback<T> callback) {
        return observable -> {
            observable = observable.subscribeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());
            if (callback.startWithLoading()) {
                observable = observable.doOnSubscribe(disposable -> showLoading());
            }
            return (callback.endDismissLoading() ? observable.doFinally(this::hideLoading) : observable);
        };
    }
}