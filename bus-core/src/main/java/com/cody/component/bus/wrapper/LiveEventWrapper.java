/*
 * ************************************************************
 * 文件：LiveEventWrapper.java  模块：bus-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bus-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bus.wrapper;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.cody.component.bus.lib.exception.UnInitValueException;

/**
 * Created by xu.yi. on 2019/3/31.
 * 和lifecycle绑定的事件总线
 * 每添加一个observer，LiveEventWrapper 的序列号增加1，并赋值给新加的observer，
 * 每次消息更新使用目前的序列号进行请求，持有更小的序列号才需要获取变更通知。
 * <p>
 * 解决会收到注册前发送的消息更新问题
 */
@SuppressWarnings("unused")
final public class LiveEventWrapper<T> {
    private int mSequence = 0;
    private final MutableLiveData<ValueWrapper<T>> mMutableLiveData;

    public LiveEventWrapper() {
        mMutableLiveData = new MutableLiveData<>();
    }

    @MainThread
    public void initValue(@NonNull T value) {
        mMutableLiveData.setValue(new ValueWrapper<>(value, mSequence));
    }

    public void observeForever(@NonNull final ObserverWrapper<T> observer) {
        observer.sequence = mSequence++;
        mMutableLiveData.observeForever(filterObserver(observer));
    }

    public void removeObserver(@NonNull ObserverWrapper<T> observer) {
        mMutableLiveData.removeObserver(filterObserver(observer));
    }

    public void removeObservers(@NonNull LifecycleOwner owner) {
        mMutableLiveData.removeObservers(owner);
    }

    public T getValue() {
        if (mMutableLiveData.getValue() == null) {
            throw new UnInitValueException();
        }
        return mMutableLiveData.getValue().value;
    }

    /**
     * LiveData 不为空，但是value有可能为空，因此要调用 getValue 前一定要先初始化，先设置默认值，后调用
     */
    @NonNull
    @Deprecated
    public LiveData<T> getLiveData() {
        if (mMutableLiveData.getValue() == null) {
            throw new UnInitValueException();
        }
        return Transformations.switchMap(mMutableLiveData, input -> {
            if (input == null) {
                throw new UnInitValueException();
            }
            return new MutableLiveData<>(input.value);
        });
    }

    public boolean hasObservers() {
        return mMutableLiveData.hasObservers();
    }

    public boolean hasActiveObservers() {
        return mMutableLiveData.hasActiveObservers();
    }

    /**
     * 设置监听之前发送的消息也可以接受到
     */
    public void observeAny(@NonNull LifecycleOwner owner, @NonNull ObserverWrapper<T> observer) {
        observer.sequence = -1;
        mMutableLiveData.observe(owner, filterObserver(observer));
    }

    /**
     * 设置监听之前发送的消息不可以接受到
     */
    public void observe(@NonNull LifecycleOwner owner, @NonNull ObserverWrapper<T> observer) {
        observer.sequence = mSequence++;
        mMutableLiveData.observe(owner, filterObserver(observer));
    }

    public void postValue(@NonNull T value) {
        mMutableLiveData.postValue(new ValueWrapper<>(value, mSequence));
    }

    public void setValue(@NonNull T value) {
        mMutableLiveData.setValue(new ValueWrapper<>(value, mSequence));
    }


    @NonNull
    private Observer<ValueWrapper<T>> filterObserver(@NonNull final ObserverWrapper<T> observerWrapper) {
        if (observerWrapper.observer != null) {
            return observerWrapper.observer;
        }
        return observerWrapper.observer = valueWrapper -> {
            if (valueWrapper != null && valueWrapper.sequence > observerWrapper.sequence) {
                observerWrapper.onChanged(valueWrapper.value);
            }
        };
    }
}
