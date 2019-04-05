/*
 * ************************************************************
 * 文件：MonitorInterceptor.java  模块：http-monitor  项目：component
 * 当前修改时间：2019年04月05日 18:42:55
 * 上次修改时间：2019年04月05日 17:27:09
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.monitor;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.cody.http.monitor.db.MonitorHttpInformationDatabase;
import com.cody.http.monitor.db.data.ItemMonitorData;
import com.cody.http.monitor.holder.ContextHolder;
import com.cody.http.monitor.holder.NotificationHolder;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;

/**
 * Created by xu.yi. on 2019/4/5.
 * MonitorInterceptor
 */
class MonitorInterceptor implements Interceptor {

    private static final String TAG = "MonitorInterceptor";

    private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    private Context context;

    private long maxContentLength = 250000L;

    public MonitorInterceptor(Context context) {
        this.context = context.getApplicationContext();
        ContextHolder.setContext(context);
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        ItemMonitorData itemMonitorData = new ItemMonitorData();
        itemMonitorData.setRequestDate(new Date());
        itemMonitorData.setRequestHttpHeaders(request.headers());
        itemMonitorData.setMethod(request.method());
        String url = request.url().toString();
        itemMonitorData.setUrl(url);
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            itemMonitorData.setHost(uri.getHost());
            itemMonitorData.setPath(uri.getPath() + ((uri.getQuery() != null) ? "?" + uri.getQuery() : ""));
            itemMonitorData.setScheme(uri.getScheme());
        }
        if (requestBody != null) {
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                itemMonitorData.setRequestContentType(contentType.toString());
            }
            if (requestBody.contentLength() != -1) {
                itemMonitorData.setRequestContentLength(requestBody.contentLength());
            }
        }
        itemMonitorData.setRequestBodyIsPlainText(!bodyHasUnsupportedEncoding(request.headers()));
        if (requestBody != null && itemMonitorData.isRequestBodyIsPlainText()) {
            BufferedSource source = getNativeSource(new Buffer(), bodyGzipped(request.headers()));
            Buffer buffer = source.buffer();
            requestBody.writeTo(buffer);
            Charset charset;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(CHARSET_UTF8);
            } else {
                charset = CHARSET_UTF8;
            }
            if (isPlaintext(buffer)) {
                itemMonitorData.setRequestBody(readFromBuffer(buffer, charset));
            } else {
                itemMonitorData.setResponseBodyIsPlainText(false);
            }
        }
        long id = insert(itemMonitorData);
        itemMonitorData.setId(id);
        long startTime = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            itemMonitorData.setError(e.toString());
            update(itemMonitorData);
            throw e;
        }
        itemMonitorData.setResponseDate(new Date());
        itemMonitorData.setDuration(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));
        itemMonitorData.setRequestHttpHeaders(response.request().headers());
        itemMonitorData.setProtocol(response.protocol().toString());
        itemMonitorData.setResponseCode(response.code());
        itemMonitorData.setResponseMessage(response.message());
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            itemMonitorData.setResponseContentLength(responseBody.contentLength());
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                itemMonitorData.setResponseContentType(contentType.toString());
            }
        }
        itemMonitorData.setResponseHttpHeaders(response.headers());
        itemMonitorData.setResponseBodyIsPlainText(!bodyHasUnsupportedEncoding(response.headers()));
        if (HttpHeaders.hasBody(response) && itemMonitorData.isResponseBodyIsPlainText()) {
            BufferedSource source = getNativeSource(response);
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            Charset charset = CHARSET_UTF8;
            if (responseBody != null) {
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    try {
                        charset = contentType.charset(CHARSET_UTF8);
                    } catch (UnsupportedCharsetException e) {
                        update(itemMonitorData);
                        return response;
                    }
                }
            }
            if (isPlaintext(buffer)) {
                itemMonitorData.setResponseBody(readFromBuffer(buffer.clone(), charset));
            } else {
                itemMonitorData.setResponseBodyIsPlainText(false);
            }
            itemMonitorData.setResponseContentLength(buffer.size());
        }
        update(itemMonitorData);
        return response;
    }

    private long insert(ItemMonitorData itemMonitorData) {
        showNotification(itemMonitorData);
        return MonitorHttpInformationDatabase.getInstance(context).getHttpInformationDao().insert(itemMonitorData);
    }

    private void update(ItemMonitorData itemMonitorData) {
        showNotification(itemMonitorData);
        MonitorHttpInformationDatabase.getInstance(context).getHttpInformationDao().update(itemMonitorData);
    }

    private void showNotification(ItemMonitorData itemMonitorData) {
        NotificationHolder.getInstance(context).show(itemMonitorData);
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

    private boolean bodyHasUnsupportedEncoding(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null &&
                !contentEncoding.equalsIgnoreCase("identity") &&
                !contentEncoding.equalsIgnoreCase("gzip");
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
        return response.body().source();
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

    public MonitorInterceptor maxContentLength(long max) {
        this.maxContentLength = max;
        return this;
    }

}