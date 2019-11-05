/*
 * ************************************************************
 * 文件：HttpCatInterceptor.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:54
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.cat.interceptor;

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

import com.cody.component.app.local.Repository;
import com.cody.component.cat.db.HttpCatDatabase;
import com.cody.component.cat.db.data.ItemHttpData;
import com.cody.component.cat.notification.NotificationManagement;
import com.cody.component.cat.utils.LauncherUtil;

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
 * HttpCatInterceptor
 */
public class HttpCatInterceptor implements Interceptor {

    private static final String TAG = "HttpCatInterceptor";

    private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    private final Context context;

    private long maxContentLength = 250000L;

    public HttpCatInterceptor(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (LauncherUtil.isCatVisible()) {
            Request request = chain.request();
            RequestBody requestBody = request.body();
            ItemHttpData itemHttpData = new ItemHttpData();
            itemHttpData.setRequestDate(new Date());
            itemHttpData.setRequestHttpHeaders(request.headers());
            itemHttpData.setMethod(request.method());
            String url = request.url().toString();
            itemHttpData.setUrl(url);
            if (!TextUtils.isEmpty(url)) {
                Uri uri = Uri.parse(url);
                itemHttpData.setHost(uri.getHost());
                itemHttpData.setPath(uri.getPath() + ((uri.getQuery() != null) ? "?" + uri.getQuery() : ""));
                itemHttpData.setScheme(uri.getScheme());
            }
            if (requestBody != null) {
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    itemHttpData.setRequestContentType(contentType.toString());
                }
                if (requestBody.contentLength() != -1) {
                    itemHttpData.setRequestContentLength(requestBody.contentLength());
                }
            }
            itemHttpData.setRequestBodyIsPlainText(bodyAllSupportedEncoding(request.headers()));
            if (requestBody != null && itemHttpData.isRequestBodyIsPlainText()) {
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
                    itemHttpData.setRequestBody(readFromBuffer(buffer, charset));
                } else {
                    itemHttpData.setResponseBodyIsPlainText(false);
                }
            }
            long id = insert(itemHttpData);
            itemHttpData.setId(id);
            long startTime = System.nanoTime();
            Response response;
            try {
                response = chain.proceed(request);
            } catch (Exception e) {
                itemHttpData.setError(e.toString());
                update(itemHttpData);
                throw e;
            }
            itemHttpData.setResponseDate(new Date());
            itemHttpData.setDuration(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));
            itemHttpData.setRequestHttpHeaders(response.request().headers());
            itemHttpData.setProtocol(response.protocol().toString());
            itemHttpData.setResponseCode(response.code());
            itemHttpData.setResponseMessage(response.message());
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                itemHttpData.setResponseContentLength(responseBody.contentLength());
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    itemHttpData.setResponseContentType(contentType.toString());
                }
            }
            itemHttpData.setResponseHttpHeaders(response.headers());
            itemHttpData.setResponseBodyIsPlainText(bodyAllSupportedEncoding(response.headers()));
            if (HttpHeaders.hasBody(response) && itemHttpData.isResponseBodyIsPlainText()) {
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
                            update(itemHttpData);
                            return response;
                        }
                    }
                }
                if (isPlaintext(buffer)) {
                    itemHttpData.setResponseBody(readFromBuffer(buffer.clone(), charset));
                } else {
                    itemHttpData.setResponseBodyIsPlainText(false);
                }
                itemHttpData.setResponseContentLength(buffer.size());
            }
            update(itemHttpData);
            return response;
        } else {
            return chain.proceed(chain.request());
        }
    }

    private long insert(ItemHttpData itemHttpData) {
        showNotification(itemHttpData);
        return HttpCatDatabase.getInstance().getHttpInformationDao().insert(itemHttpData);
    }

    private void update(ItemHttpData itemHttpData) {
        showNotification(itemHttpData);
        HttpCatDatabase.getInstance().getHttpInformationDao().update(itemHttpData);
    }

    private void showNotification(ItemHttpData itemHttpData) {
        NotificationManagement.getInstance(context).show(itemHttpData);
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

    public HttpCatInterceptor maxContentLength(long max) {
        this.maxContentLength = max;
        return this;
    }

}