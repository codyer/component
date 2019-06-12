/*
 * ************************************************************
 * 文件：UpdateViewData.java  模块：bind-update  项目：component
 * 当前修改时间：2019年05月31日 15:56:34
 * 上次修改时间：2019年05月13日 09:54:51
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-update
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.update;

import com.cody.component.handler.data.ViewData;
import com.cody.component.handler.livedata.StringLiveData;

/**
 * Created by cody.yi on 2017/5/8.
 * 更新
 */
public class UpdateViewData extends ViewData {
    final private StringLiveData mCountTime = new StringLiveData("3s");
    private boolean mVersionChecked = false;
    private boolean mForceUpdate = false;
    private boolean mOptionalUpdate = false;
    private boolean mIsDownloaded = false;//是否已下载
    private String mApkUrl = "";
    private String mNewVersion = "";
    private String mApkSize = "";
    private String mUpdateTime = "";
    private String mUpdateInfo = "";
    private String mApkName = "";

    public boolean isVersionChecked() {
        return mVersionChecked;
    }

    public void setVersionChecked(boolean versionChecked) {
        mVersionChecked = versionChecked;
    }

    public boolean isForceUpdate() {
        return mForceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        mForceUpdate = forceUpdate;
    }

    public boolean isOptionalUpdate() {
        return mOptionalUpdate;
    }

    public void setOptionalUpdate(boolean optionalUpdate) {
        mOptionalUpdate = optionalUpdate;
    }

    public String getApkUrl() {
        return mApkUrl;
    }

    public void setApkUrl(String apkUrl) {
        mApkUrl = apkUrl;
    }

    public String getApkName() {
        return mApkName;
    }

    public void setApkName(String apkName) {
        mApkName = apkName;
    }

    public String getNewVersion() {
        return mNewVersion;
    }

    public void setNewVersion(final String newVersion) {
        mNewVersion = newVersion;
    }

    public String getApkSize() {
        return mApkSize;
    }

    public void setApkSize(final String apkSize) {
        mApkSize = apkSize;
    }

    public String getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(final String updateTime) {
        mUpdateTime = updateTime;
    }

    public String getUpdateInfo() {
        return mUpdateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        mUpdateInfo = updateInfo;
    }

    public StringLiveData getCountTime() {
        return mCountTime;
    }

    public boolean isDownloaded() {
        return mIsDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        mIsDownloaded = downloaded;
    }
}
