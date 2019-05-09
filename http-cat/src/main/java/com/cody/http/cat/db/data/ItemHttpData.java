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

import android.os.Parcel;

import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.http.cat.utils.FormatUtils;
import com.cody.http.cat.utils.JsonConverter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
                return mResponseCode + " " + mPath;
        }
    }

    public String getResponseSummaryText() {
        switch (getStatus()) {
            case Failed:
                return mError;
            case Requested:
                return null;
            default:
                return mResponseCode + " " + mResponseMessage;
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
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ItemHttpData that = (ItemHttpData) o;
        return id == that.id &&
                mDuration == that.mDuration &&
                mRequestContentLength == that.mRequestContentLength &&
                mRequestBodyIsPlainText == that.mRequestBodyIsPlainText &&
                mResponseCode == that.mResponseCode &&
                mResponseContentLength == that.mResponseContentLength &&
                mResponseBodyIsPlainText == that.mResponseBodyIsPlainText &&
                Objects.equals(mRequestDate, that.mRequestDate) &&
                Objects.equals(mResponseDate, that.mResponseDate) &&
                Objects.equals(mMethod, that.mMethod) &&
                Objects.equals(mUrl, that.mUrl) &&
                Objects.equals(mHost, that.mHost) &&
                Objects.equals(mPath, that.mPath) &&
                Objects.equals(mScheme, that.mScheme) &&
                Objects.equals(mProtocol, that.mProtocol) &&
                Objects.equals(mRequestHeaders, that.mRequestHeaders) &&
                Objects.equals(mRequestBody, that.mRequestBody) &&
                Objects.equals(mRequestContentType, that.mRequestContentType) &&
                Objects.equals(mResponseHeaders, that.mResponseHeaders) &&
                Objects.equals(mResponseBody, that.mResponseBody) &&
                Objects.equals(mResponseMessage, that.mResponseMessage) &&
                Objects.equals(mResponseContentType, that.mResponseContentType) &&
                Objects.equals(mError, that.mError);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, mRequestDate, mResponseDate, mDuration, mMethod, mUrl, mHost, mPath, mScheme, mProtocol, mRequestHeaders, mRequestBody, mRequestContentType, mRequestContentLength, mRequestBodyIsPlainText, mResponseCode, mResponseHeaders, mResponseBody, mResponseMessage, mResponseContentType, mResponseContentLength, mResponseBodyIsPlainText, mError);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.id);
        dest.writeLong(this.mRequestDate != null ? this.mRequestDate.getTime() : -1);
        dest.writeLong(this.mResponseDate != null ? this.mResponseDate.getTime() : -1);
        dest.writeLong(this.mDuration);
        dest.writeString(this.mMethod);
        dest.writeString(this.mUrl);
        dest.writeString(this.mHost);
        dest.writeString(this.mPath);
        dest.writeString(this.mScheme);
        dest.writeString(this.mProtocol);
        dest.writeString(this.mRequestHeaders);
        dest.writeString(this.mRequestBody);
        dest.writeString(this.mRequestContentType);
        dest.writeLong(this.mRequestContentLength);
        dest.writeByte(this.mRequestBodyIsPlainText ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mResponseCode);
        dest.writeString(this.mResponseHeaders);
        dest.writeString(this.mResponseBody);
        dest.writeString(this.mResponseMessage);
        dest.writeString(this.mResponseContentType);
        dest.writeLong(this.mResponseContentLength);
        dest.writeByte(this.mResponseBodyIsPlainText ? (byte) 1 : (byte) 0);
        dest.writeString(this.mError);
        dest.writeInt(this.mItemId);
        dest.writeInt(this.mItemType);
    }

    public ItemHttpData() {
    }

    protected ItemHttpData(Parcel in) {
        super(in);
        this.id = in.readLong();
        long tmpMRequestDate = in.readLong();
        this.mRequestDate = tmpMRequestDate == -1 ? null : new Date(tmpMRequestDate);
        long tmpMResponseDate = in.readLong();
        this.mResponseDate = tmpMResponseDate == -1 ? null : new Date(tmpMResponseDate);
        this.mDuration = in.readLong();
        this.mMethod = in.readString();
        this.mUrl = in.readString();
        this.mHost = in.readString();
        this.mPath = in.readString();
        this.mScheme = in.readString();
        this.mProtocol = in.readString();
        this.mRequestHeaders = in.readString();
        this.mRequestBody = in.readString();
        this.mRequestContentType = in.readString();
        this.mRequestContentLength = in.readLong();
        this.mRequestBodyIsPlainText = in.readByte() != 0;
        this.mResponseCode = in.readInt();
        this.mResponseHeaders = in.readString();
        this.mResponseBody = in.readString();
        this.mResponseMessage = in.readString();
        this.mResponseContentType = in.readString();
        this.mResponseContentLength = in.readLong();
        this.mResponseBodyIsPlainText = in.readByte() != 0;
        this.mError = in.readString();
        this.mItemId = in.readInt();
        this.mItemType = in.readInt();
    }

    public static final Creator<ItemHttpData> CREATOR = new Creator<ItemHttpData>() {
        @Override
        public ItemHttpData createFromParcel(Parcel source) {
            return new ItemHttpData(source);
        }

        @Override
        public ItemHttpData[] newArray(int size) {
            return new ItemHttpData[size];
        }
    };
}