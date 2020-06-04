/*
 * ************************************************************
 * 文件：StubLiveEventWrapper.java  模块：component-bus-core  项目：component
 * 当前修改时间：2020年06月04日 10:33:53
 * 上次修改时间：2020年06月04日 10:33:53
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：component-bus-core
 * Copyright (c) 2020
 * ************************************************************
 */

package com.cody.component.bus.wrapper;


import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cody.component.bus.lib.exception.UnInitValueException;

/**
 * Created by xu.yi. on 2020/6/4.
 * 当未激活时使用stub包装类
 */
public class StubLiveEventWrapper<T> extends LiveEventWrapper<T> {
    private T value;

    public StubLiveEventWrapper() {
    }

    @Override
    public T getValue() throws UnInitValueException {
        return value;
    }

    @NonNull
    @Override
    @Deprecated
    public LiveData<T> getLiveData() throws UnInitValueException {
        return new MutableLiveData<>(value);
    }

    @Override
    public boolean hasObservers() {
        return false;
    }

    @Override
    public boolean hasActiveObservers() {
        return false;
    }

    @Override
    public void post(@NonNull final T value) {
        this.value = value;
    }

    @Override
    public void setValue(@NonNull final T value) {
        this.value = value;
    }

    @Override
    @Deprecated
    public void postValue(@NonNull final T value) {
        this.value = value;
    }

    @Override
    public void removeObserver(@NonNull final ObserverWrapper<T> observerWrapper) {
    }

    @Override
    public void removeObservers(@NonNull final LifecycleOwner owner) {
    }

    @Override
    public void observeForever(@NonNull final ObserverWrapper<T> observerWrapper) {
    }

    @Override
    @Deprecated
    public void observeSticky(@NonNull final LifecycleOwner owner, @NonNull final ObserverWrapper<T> observerWrapper) {
    }

    @Override
    public void observe(@NonNull final LifecycleOwner owner, @NonNull final ObserverWrapper<T> observerWrapper) {
    }
}
