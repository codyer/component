/*
 * ************************************************************
 * 文件：RetrofitManagement.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:44:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core;

import android.webkit.URLUtil;

import com.cody.http.lib.annotation.Domain;
import com.cody.component.lib.bean.Result;
import com.cody.http.lib.config.HttpCode;
import com.cody.http.lib.config.TimeConfig;
import com.cody.http.lib.exception.AccountInvalidException;
import com.cody.http.lib.exception.DomainInvalidException;
import com.cody.http.lib.exception.ServerResultException;
import com.cody.http.lib.exception.TokenInvalidException;
import com.cody.http.core.interceptor.HeaderInterceptor;
import com.cody.http.core.interceptor.HttpInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by xu.yi. on 2019/4/6.
 * Retrofit 工厂 包级别可见，通过HttpCore进行设置
 */
class RetrofitManagement {

    private static class InstanceHolder {
        private static final RetrofitManagement INSTANCE = new RetrofitManagement();
    }

    static RetrofitManagement getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private final Map<String, Object> mRetrofitServices;
    private Interceptor mHttpCatInterceptor;
    private List<Interceptor> mInterceptors;
    private boolean mLog = false;

    private RetrofitManagement() {
        mRetrofitServices = new ConcurrentHashMap<>();
    }

    <T> ObservableTransformer<Result<T>, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(result -> {
                    switch (result.getCode()) {
                        case HttpCode.CODE_SUCCESS: {
                            return createData(result.getData());
                        }
                        case HttpCode.CODE_TOKEN_INVALID: {
                            throw new TokenInvalidException();
                        }
                        case HttpCode.CODE_ACCOUNT_INVALID: {
                            throw new AccountInvalidException();
                        }
                        default: {
                            throw new ServerResultException(result.getCode(), result.getMessage());
                        }
                    }
                });
    }

    private <T> Observable<T> createData(T t) {
        return Observable.create(emitter -> {
            try {
                emitter.onNext(t);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    <T> T getService(Class<T> clz) {
        Domain domain = clz.getAnnotation(Domain.class);
        if (domain == null || !URLUtil.isNetworkUrl(domain.value())) {
            throw new DomainInvalidException();
        }
        String baseUrl = domain.value();
        return getService(baseUrl, clz);
    }

    public void setLog(boolean log) {
        this.mLog = log;
    }

    public void setHttpCatInterceptor(Interceptor httpCatInterceptor) {
        mHttpCatInterceptor = httpCatInterceptor;
        mRetrofitServices.clear();
    }

    public void addInterceptor(Interceptor interceptor) {
        if (this.mInterceptors == null) {
            this.mInterceptors = new ArrayList<>();
        }
        if (interceptor != null) {
            this.mInterceptors.add(interceptor);
        }
        mRetrofitServices.clear();
    }

    public void addInterceptor(List<Interceptor> interceptorList) {
        if (this.mInterceptors == null) {
            this.mInterceptors = new ArrayList<>();
        }
        if (interceptorList != null && interceptorList.size() > 0) {
            this.mInterceptors.addAll(interceptorList);
        }
        mRetrofitServices.clear();
    }

    @SuppressWarnings("unchecked")
    <T> T getService(String baseUrl, Class<T> clz) {
        String key = baseUrl + clz.getCanonicalName();
        T value;
        if (mRetrofitServices.containsKey(key)) {
            Object obj = mRetrofitServices.get(key);
            if (obj == null) {
                value = createRetrofit(baseUrl).create(clz);
                mRetrofitServices.put(key, value);
            } else {
                value = (T) obj;
            }
        } else {
            value = createRetrofit(baseUrl).create(clz);
            mRetrofitServices.put(key, value);
        }
        return value;
    }

    private Retrofit createRetrofit(String baseUrl) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(TimeConfig.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TimeConfig.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TimeConfig.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
//                .addInterceptor(new HttpInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .retryOnConnectionFailure(true);
        if (mInterceptors != null) {
            for (Interceptor interceptor : mInterceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        if (mHttpCatInterceptor != null) {
            builder.addInterceptor(mHttpCatInterceptor);
        }
        if (mLog) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        OkHttpClient client = builder.build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
