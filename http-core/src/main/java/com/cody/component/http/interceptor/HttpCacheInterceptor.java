/*
 * ************************************************************
 * 文件：HttpCacheInterceptor.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http.interceptor;


import android.util.Log;

import androidx.annotation.NonNull;

import com.cody.component.http.HttpCore;
import com.cody.component.http.db.HttpCacheDatabase;
import com.cody.component.http.db.data.ItemCacheData;
import com.cody.component.http.lib.config.HttpCode;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Date;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;

/**
 * Created by xu.yi. on 2019/4/6.
 */
public class HttpCacheInterceptor implements Interceptor {

    private static final String TAG = "HttpCacheInterceptor";

    private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    private long maxContentLength = 250000L;

    public HttpCacheInterceptor() {
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        // 需要缓存，且请求是纯文本
        String cache = request.header(HttpCore.CACHE_KEY);
        if (cache != null) {
            Request.Builder requestBuilder = request.newBuilder();
            requestBuilder.removeHeader(HttpCore.CACHE_KEY);
            Response response;
            try {
                response = chain.proceed(requestBuilder.build());
            } catch (Exception e) {
                throw e;
            }
            if (response.code() == HttpCode.CODE_SUCCESS) {
                ItemCacheData itemCacheData = new ItemCacheData();
                itemCacheData.setVersion(HttpCore.getInstance().getVersion());
                itemCacheData.setKey(cache);
                itemCacheData.setRequestDate(new Date());
                ResponseBody responseBody = response.body();
                if (responseBody != null && HttpHeaders.hasBody(response) && bodyAllSupportedEncoding(response.headers())) {
                    BufferedSource source = getNativeSource(response);
                    if (source != null) {
                        source.request(Long.MAX_VALUE);
                        Buffer buffer = source.buffer();
                        Charset charset = CHARSET_UTF8;
                        MediaType contentType = responseBody.contentType();
                        if (contentType != null) {
                            try {
                                charset = contentType.charset(CHARSET_UTF8);
                            } catch (UnsupportedCharsetException e) {
                                return response;
                            }
                        }
                        if (isPlaintext(buffer)) {
                            itemCacheData.setDataJson(readFromBuffer(buffer.clone(), charset));
                        }
                        insert(itemCacheData);
                    }
                }
            }
            return response;
        } else {
            return chain.proceed(chain.request());
        }
    }

    private void insert(ItemCacheData itemHttpData) {
        HttpCacheDatabase.getInstance().getCacheDao().insert(itemHttpData);
    }

    private boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false;
        }
    }

    private boolean bodyAllSupportedEncoding(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding == null ||
                contentEncoding.equalsIgnoreCase("identity") ||
                contentEncoding.equalsIgnoreCase("gzip");
    }

    private String readFromBuffer(Buffer buffer, Charset charset) {
        long bufferSize = buffer.size();
        long maxBytes = Math.min(bufferSize, maxContentLength);
        String body;
        try {
            body = buffer.readString(maxBytes, charset);
        } catch (EOFException e) {
            body = "\\n\\n--- Unexpected end of content ---";
        }
        if (bufferSize > maxContentLength) {
            body += "\\n\\n--- Content truncated ---";
        }
        return body;
    }

    private BufferedSource getNativeSource(Response response) throws IOException {
        if (bodyGzipped(response.headers())) {
            BufferedSource source = response.peekBody(maxContentLength).source();
            if (source.buffer().size() < maxContentLength) {
                return getNativeSource(source, true);
            } else {
                Log.e(TAG, "gzip encoded response was too long");
            }
        }
        return response.body() != null ? response.body().source() : null;
    }

    private BufferedSource getNativeSource(BufferedSource input, boolean isGzipped) {
        if (isGzipped) {
            GzipSource source = new GzipSource(input);
            return Okio.buffer(source);
        } else {
            return input;
        }
    }

    private boolean bodyGzipped(Headers headers) {
        return "gzip".equalsIgnoreCase(headers.get("Content-Encoding"));
    }
}