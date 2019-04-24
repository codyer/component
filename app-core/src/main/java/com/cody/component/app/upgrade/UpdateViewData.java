/*
 * ************************************************************
 * 文件：UpdateViewData.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月21日 22:53:02
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.upgrade;

import com.cody.component.handler.data.ViewData;
import com.cody.component.handler.livedata.StringLiveData;

/**
 * Created by cody.yi on 2017/5/8.
 * 更新
 */
public class UpdateViewData extends ViewData {
    private static final long serialVersionUID = -2157233147924647191L;
    private final StringLiveData mCountTime = new StringLiveData("3s");
    private boolean mVersionChecked = false;
    private boolean mForceUpdate = false;
    private boolean mOptionalUpdate = false;
    private boolean mIsDownloaded = false;//是否已下载
    private String mApkUrl = "";
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
