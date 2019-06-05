/*
 * ************************************************************
 * 文件：ItemCacheData.java  模块：http-core  项目：component
 * 当前修改时间：2019年05月22日 10:50:12
 * 上次修改时间：2019年05月13日 09:54:51
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http.db.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Created by xu.yi. on 2019/5/22.
 * result data
 */
@Entity(tableName = "http_cache_table")
public class ItemCacheData implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private Date mRequestDate;
    private String mKey;
    private String mDataJson;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Date getRequestDate() {
        return mRequestDate;
    }

    public void setRequestDate(final Date requestDate) {
        mRequestDate = requestDate;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(final String key) {
        mKey = key;
    }

    public String getDataJson() {
        return mDataJson;
    }

    public void setDataJson(final String dataJson) {
        mDataJson = dataJson;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.mRequestDate != null ? this.mRequestDate.getTime() : -1);
        dest.writeString(this.mKey);
        dest.writeString(this.mDataJson);
    }

    public ItemCacheData() {
    }

    protected ItemCacheData(Parcel in) {
        this.id = in.readLong();
        long tmpMRequestDate = in.readLong();
        this.mRequestDate = tmpMRequestDate == -1 ? null : new Date(tmpMRequestDate);
        this.mKey = in.readString();
        this.mDataJson = in.readString();
    }

    public static final Creator<ItemCacheData> CREATOR = new Creator<ItemCacheData>() {
        @Override
        public ItemCacheData createFromParcel(Parcel source) {
            return new ItemCacheData(source);
        }

        @Override
        public ItemCacheData[] newArray(int size) {
            return new ItemCacheData[size];
        }
    };
}