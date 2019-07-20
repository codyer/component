/*
 * ************************************************************
 * 文件：HtmlConfig.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:44:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xu.yi. on 2019/4/11.
 * 打开 html 需要的配置参数保存类
 */
public class HtmlConfig implements Parcelable {
    private String title = "";
    private String description = "";
    private String url = "";
    private String image;//缩略图
    private String picture;//大图片
    private boolean isRoot;
    private boolean canShare = true;
    private String data;//存储分享数据

    public String getTitle() {
        return title;
    }

    public HtmlConfig setTitle(final String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public HtmlConfig setDescription(final String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public HtmlConfig setUrl(final String url) {
        this.url = url;
        return this;
    }

    public String getImage() {
        return image;
    }

    public HtmlConfig setImage(final String image) {
        this.image = image;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public HtmlConfig setPicture(final String picture) {
        this.picture = picture;
        return this;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public HtmlConfig setRoot(final boolean root) {
        isRoot = root;
        return this;
    }

    public boolean isCanShare() {
        return canShare;
    }

    public HtmlConfig setCanShare(final boolean canShare) {
        this.canShare = canShare;
        return this;
    }

    public String getData() {
        return data;
    }

    public HtmlConfig setData(final String data) {
        this.data = data;
        return this;
    }

    public JSONObject getJsonData() throws JSONException {
        return new JSONObject(data);
    }

    public HtmlConfig setJsonData(@NonNull final JSONObject data) {
        this.data = data.toString();
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return title + '\n' +
                description + '\n' +
                url + '\n';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeString(this.image);
        dest.writeString(this.picture);
        dest.writeByte(this.isRoot ? (byte) 1 : (byte) 0);
        dest.writeByte(this.canShare ? (byte) 1 : (byte) 0);
        dest.writeString(this.data);
    }

    public HtmlConfig() {
    }

    protected HtmlConfig(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.image = in.readString();
        this.picture = in.readString();
        this.isRoot = in.readByte() != 0;
        this.canShare = in.readByte() != 0;
        this.data = in.readString();
    }

    public static final Creator<HtmlConfig> CREATOR = new Creator<HtmlConfig>() {
        @Override
        public HtmlConfig createFromParcel(Parcel source) {
            return new HtmlConfig(source);
        }

        @Override
        public HtmlConfig[] newArray(int size) {
            return new HtmlConfig[size];
        }
    };
}
