/*
 * ************************************************************
 * 文件：ItemHttpData.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月23日 11:48:20
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.db.data;

import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.http.cat.utils.FormatUtils;
import com.cody.http.cat.utils.JsonConverter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import okhttp3.Headers;

/**
 * Created by xu.yi. on 2019/3/31.
 * cat data
 */
@Entity(tableName = "http_cat_table")
public class ItemHttpData extends ItemViewDataHolder {
    private static final long serialVersionUID = -5435259574495140581L;
    private static final int DEFAULT_RESPONSE_CODE = -100;

    public enum Status {Requested, Complete, Failed}

    @PrimaryKey(autoGenerate = true)
    private long id;

    private Date mRequestDate;
    private Date mResponseDate;
    private long mDuration;
    private String mMethod;
    private String mUrl;
    private String mHost;
    private String mPath;
    private String mScheme;
    private String mProtocol;

    private String mRequestHeaders;
    private String mRequestBody;
    private String mRequestContentType;
    private long mRequestContentLength;
    private boolean mRequestBodyIsPlainText = true;

    private int mResponseCode = DEFAULT_RESPONSE_CODE;
    private String mResponseHeaders;
    private String mResponseBody;
    private String mResponseMessage;
    private String mResponseContentType;
    private long mResponseContentLength;
    private boolean mResponseBodyIsPlainText = true;

    private String mError;

    public void setRequestHttpHeaders(Headers headers) {
        if (headers != null) {
            List<HttpHeader> httpHeaders = new ArrayList<>();
            for (int i = 0, count = headers.size(); i < count; i++) {
                httpHeaders.add(new HttpHeader(headers.name(i), headers.value(i)));
            }
            setRequestHeaders(JsonConverter.getInstance().toJson(httpHeaders));
        } else {
            setRequestHeaders(null);
        }
    }

    public void setResponseHttpHeaders(Headers headers) {
        if (headers != null) {
            List<HttpHeader> httpHeaders = new ArrayList<>();
            for (int i = 0, count = headers.size(); i < count; i++) {
                httpHeaders.add(new HttpHeader(headers.name(i), headers.value(i)));
            }
            setResponseHeaders(JsonConverter.getInstance().toJson(httpHeaders));
        } else {
            setResponseHeaders(null);
        }
    }

    public ItemHttpData.Status getStatus() {
        if (mError != null) {
            return ItemHttpData.Status.Failed;
        } else if (mResponseCode == ItemHttpData.DEFAULT_RESPONSE_CODE) {
            return ItemHttpData.Status.Requested;
        } else {
            return ItemHttpData.Status.Complete;
        }
    }

    public String getNotificationText() {
        switch (getStatus()) {
            case Failed:
                return " ! ! !  " + mPath;
            case Requested:
                return " . . .  " + mPath;
            default:
                return String.valueOf(mResponseCode) + " " + mPath;
        }
    }

    public String getResponseSummaryText() {
        switch (getStatus()) {
            case Failed:
                return mError;
            case Requested:
                return null;
            default:
                return String.valueOf(mResponseCode) + " " + mResponseMessage;
        }
    }

    private List<HttpHeader> getRequestHeaderList() {
        return JsonConverter.getInstance().fromJson(mRequestHeaders,
                new TypeToken<List<HttpHeader>>() {
                }.getType());
    }

    public String getRequestHeadersString(boolean withMarkup) {
        return FormatUtils.formatHeaders(getRequestHeaderList(), withMarkup);
    }

    public String getFormattedRequestBody() {
        return FormatUtils.formatBody(mRequestBody, mRequestContentType);
    }

    public String getFormattedResponseBody() {
        return FormatUtils.formatBody(mResponseBody, mResponseContentType);
    }

    private List<HttpHeader> getResponseHeaderList() {
        return JsonConverter.getInstance().fromJson(mResponseHeaders,
                new TypeToken<List<HttpHeader>>() {
                }.getType());
    }

    public String getResponseHeadersString(boolean withMarkup) {
        return FormatUtils.formatHeaders(getResponseHeaderList(), withMarkup);
    }

    public String getDurationFormat() {
        return mDuration + " ms";
    }

    public boolean isSsl() {
        return "https".equalsIgnoreCase(mScheme);
    }

    public String getTotalSizeString() {
        return FormatUtils.formatBytes(mRequestContentLength + mResponseContentLength);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getRequestDate() {
        return mRequestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.mRequestDate = requestDate;
    }

    public Date getResponseDate() {
        return mResponseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.mResponseDate = responseDate;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public String getMethod() {
        return mMethod;
    }

    public void setMethod(String method) {
        this.mMethod = method;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getHost() {
        return mHost;
    }

    public void setHost(String name) {
        this.mHost = name;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        this.mPath = path;
    }

    public String getScheme() {
        return mScheme;
    }

    public void setScheme(String scheme) {
        this.mScheme = scheme;
    }

    public String getProtocol() {
        return mProtocol;
    }

    public void setProtocol(String protocol) {
        this.mProtocol = protocol;
    }

    public String getRequestHeaders() {
        return mRequestHeaders;
    }

    public void setRequestHeaders(String requestHeaders) {
        this.mRequestHeaders = requestHeaders;
    }

    public String getRequestBody() {
        return mRequestBody;
    }

    public void setRequestBody(String requestBody) {
        this.mRequestBody = requestBody;
    }

    public String getRequestContentType() {
        return mRequestContentType;
    }

    public void setRequestContentType(String requestContentType) {
        this.mRequestContentType = requestContentType;
    }

    public long getRequestContentLength() {
        return mRequestContentLength;
    }

    public void setRequestContentLength(long requestContentLength) {
        this.mRequestContentLength = requestContentLength;
    }

    public boolean isRequestBodyIsPlainText() {
        return mRequestBodyIsPlainText;
    }

    public void setRequestBodyIsPlainText(boolean requestBodyIsPlainText) {
        this.mRequestBodyIsPlainText = requestBodyIsPlainText;
    }

    public int getResponseCode() {
        return mResponseCode;
    }

    public void setResponseCode(int responseCode) {
        this.mResponseCode = responseCode;
    }

    public String getResponseHeaders() {
        return mResponseHeaders;
    }

    public void setResponseHeaders(String responseHeaders) {
        this.mResponseHeaders = responseHeaders;
    }

    public String getResponseBody() {
        return mResponseBody;
    }

    public void setResponseBody(String responseBody) {
        this.mResponseBody = responseBody;
    }

    public String getResponseMessage() {
        return mResponseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.mResponseMessage = responseMessage;
    }

    public String getResponseContentType() {
        return mResponseContentType;
    }

    public void setResponseContentType(String responseContentType) {
        this.mResponseContentType = responseContentType;
    }

    public long getResponseContentLength() {
        return mResponseContentLength;
    }

    public void setResponseContentLength(long responseContentLength) {
        this.mResponseContentLength = responseContentLength;
    }

    public boolean isResponseBodyIsPlainText() {
        return mResponseBodyIsPlainText;
    }

    public void setResponseBodyIsPlainText(boolean responseBodyIsPlainText) {
        this.mResponseBodyIsPlainText = responseBodyIsPlainText;
    }

    public String getError() {
        return mError;
    }

    public void setError(String error) {
        this.mError = error;
    }

    @NonNull
    @Override
    public String toString() {
        return "ItemHttpData{" +
                "id=" + id +
                ", mRequestDate=" + mRequestDate +
                ", mResponseDate=" + mResponseDate +
                ", mDuration=" + mDuration +
                ", mMethod='" + mMethod + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", mHost='" + mHost + '\'' +
                ", mPath='" + mPath + '\'' +
                ", mScheme='" + mScheme + '\'' +
                ", mProtocol='" + mProtocol + '\'' +
                ", mRequestHeaders='" + mRequestHeaders + '\'' +
                ", mRequestBody='" + mRequestBody + '\'' +
                ", mRequestContentType='" + mRequestContentType + '\'' +
                ", mRequestContentLength=" + mRequestContentLength +
                ", mRequestBodyIsPlainText=" + mRequestBodyIsPlainText +
                ", mResponseCode=" + mResponseCode +
                ", mResponseHeaders='" + mResponseHeaders + '\'' +
                ", mResponseBody='" + mResponseBody + '\'' +
                ", mResponseMessage='" + mResponseMessage + '\'' +
                ", mResponseContentType='" + mResponseContentType + '\'' +
                ", mResponseContentLength=" + mResponseContentLength +
                ", mResponseBodyIsPlainText=" + mResponseBodyIsPlainText +
                ", mError='" + mError + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemHttpData that = (ItemHttpData) o;
        if (id != that.id) return false;
        if (mDuration != that.mDuration) return false;
        if (mRequestContentLength != that.mRequestContentLength) return false;
        if (mRequestBodyIsPlainText != that.mRequestBodyIsPlainText) return false;
        if (mResponseCode != that.mResponseCode) return false;
        if (mResponseContentLength != that.mResponseContentLength) return false;
        if (mResponseBodyIsPlainText != that.mResponseBodyIsPlainText) return false;
        if (mRequestDate != null ? !mRequestDate.equals(that.mRequestDate) : that.mRequestDate != null)
            return false;
        if (mResponseDate != null ? !mResponseDate.equals(that.mResponseDate) : that.mResponseDate != null)
            return false;
        if (mMethod != null ? !mMethod.equals(that.mMethod) : that.mMethod != null) return false;
        if (mUrl != null ? !mUrl.equals(that.mUrl) : that.mUrl != null) return false;
        if (mHost != null ? !mHost.equals(that.mHost) : that.mHost != null) return false;
        if (mPath != null ? !mPath.equals(that.mPath) : that.mPath != null) return false;
        if (mScheme != null ? !mScheme.equals(that.mScheme) : that.mScheme != null) return false;
        if (mProtocol != null ? !mProtocol.equals(that.mProtocol) : that.mProtocol != null)
            return false;
        if (mRequestHeaders != null ? !mRequestHeaders.equals(that.mRequestHeaders) : that.mRequestHeaders != null)
            return false;
        if (mRequestBody != null ? !mRequestBody.equals(that.mRequestBody) : that.mRequestBody != null)
            return false;
        if (mRequestContentType != null ? !mRequestContentType.equals(that.mRequestContentType) : that.mRequestContentType != null)
            return false;
        if (mResponseHeaders != null ? !mResponseHeaders.equals(that.mResponseHeaders) : that.mResponseHeaders != null)
            return false;
        if (mResponseBody != null ? !mResponseBody.equals(that.mResponseBody) : that.mResponseBody != null)
            return false;
        if (mResponseMessage != null ? !mResponseMessage.equals(that.mResponseMessage) : that.mResponseMessage != null)
            return false;
        if (mResponseContentType != null ? !mResponseContentType.equals(that.mResponseContentType) : that.mResponseContentType != null)
            return false;
        return mError != null ? mError.equals(that.mError) : that.mError == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (mRequestDate != null ? mRequestDate.hashCode() : 0);
        result = 31 * result + (mResponseDate != null ? mResponseDate.hashCode() : 0);
        result = 31 * result + (int) (mDuration ^ (mDuration >>> 32));
        result = 31 * result + (mMethod != null ? mMethod.hashCode() : 0);
        result = 31 * result + (mUrl != null ? mUrl.hashCode() : 0);
        result = 31 * result + (mHost != null ? mHost.hashCode() : 0);
        result = 31 * result + (mPath != null ? mPath.hashCode() : 0);
        result = 31 * result + (mScheme != null ? mScheme.hashCode() : 0);
        result = 31 * result + (mProtocol != null ? mProtocol.hashCode() : 0);
        result = 31 * result + (mRequestHeaders != null ? mRequestHeaders.hashCode() : 0);
        result = 31 * result + (mRequestBody != null ? mRequestBody.hashCode() : 0);
        result = 31 * result + (mRequestContentType != null ? mRequestContentType.hashCode() : 0);
        result = 31 * result + (int) (mRequestContentLength ^ (mRequestContentLength >>> 32));
        result = 31 * result + (mRequestBodyIsPlainText ? 1 : 0);
        result = 31 * result + mResponseCode;
        result = 31 * result + (mResponseHeaders != null ? mResponseHeaders.hashCode() : 0);
        result = 31 * result + (mResponseBody != null ? mResponseBody.hashCode() : 0);
        result = 31 * result + (mResponseMessage != null ? mResponseMessage.hashCode() : 0);
        result = 31 * result + (mResponseContentType != null ? mResponseContentType.hashCode() : 0);
        result = 31 * result + (int) (mResponseContentLength ^ (mResponseContentLength >>> 32));
        result = 31 * result + (mResponseBodyIsPlainText ? 1 : 0);
        result = 31 * result + (mError != null ? mError.hashCode() : 0);
        return result;
    }

}