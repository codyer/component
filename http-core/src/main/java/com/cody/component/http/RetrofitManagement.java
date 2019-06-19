/*
 * ************************************************************
 * 文件：RetrofitManagement.java  模块：http-core  项目：component
 * 当前修改时间：2019年06月05日 13:59:04
 * 上次修改时间：2019年05月30日 21:29:36
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http;

import android.webkit.URLUtil;

import com.cody.component.http.lib.config.HttpConfig;
import com.cody.component.http.lib.exception.ServerResultHttpException;
import com.cody.component.http.lib.exception.TokenInvalidHttpException;
import com.cody.component.lib.bean.Result;
import com.cody.component.http.interceptor.HeaderInterceptor;
import com.cody.component.http.lib.annotation.Domain;
import com.cody.component.http.lib.config.HttpCode;
import com.cody.component.http.lib.exception.AccountInvalidHttpException;
import com.cody.component.http.lib.exception.DomainInvalidHttpException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
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
                            throw new TokenInvalidHttpException();
                        }
                        case HttpCode.CODE_ACCOUNT_INVALID: {
                            throw new AccountInvalidHttpException();
                        }
                        default: {
                            throw new ServerResultHttpException(result.getCode(), result.getMessage());
                        }
                    }
                });
    }

    private <T> Observable<T> createData(T t) {
        return Observable.create(emitter -> {
            try {
                if (t != null) {
                    emitter.onNext(t);
                }
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    <T> T getService(Class<T> clz) {
        Domain domain = clz.getAnnotation(Domain.class);
        if (domain == null || !URLUtil.isNetworkUrl(domain.value())) {
            throw new DomainInvalidHttpException();
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
                .readTimeout(HttpConfig.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(HttpConfig.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(HttpConfig.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
//                .addInterceptor(new HttpInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .retryOnConnectionFailure(true);
        if (HttpCore.getInstance().getContext() != null) {
            builder.cache(new Cache(HttpCore.getInstance().getContext().getCacheDir(), HttpConfig.CACHE_SIZE));
        }
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
        OkHttpClient client = null;

        // Add legacy cipher suite for Android 4
        // Necessary because our servers don't have the right cipher suites.
        // https://github.com/square/okhttp/issues/4053
        List<CipherSuite> oldCipherSuites = ConnectionSpec.MODERN_TLS.cipherSuites();
        if (oldCipherSuites != null) {
            List<CipherSuite> cipherSuites = new ArrayList<>(oldCipherSuites);
            cipherSuites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA);
            cipherSuites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA);

            ConnectionSpec legacyTls = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .cipherSuites(cipherSuites.toArray(new CipherSuite[0]))
                    .build();

            client = builder.connectionSpecs(Arrays.asList(legacyTls, ConnectionSpec.CLEARTEXT))
                    .build();
        }
        if (client == null) {
            client = builder.build();
        }
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
