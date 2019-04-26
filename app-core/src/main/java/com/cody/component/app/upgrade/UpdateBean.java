/*
 * ************************************************************
 * 文件：UpdateBean.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月21日 22:03:58
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.upgrade;

/**
 * Created by cody.yi on 2017/5/5.
 * App 版本信息
 * 检查更新接口使用
 */
public class UpdateBean {
    /**
     * id : 77
     * type : Android
     * downloadUrl : https://www.app.com/ftpupload/app-release.apk
     * latestVersion : 2.0.0
     * lowestVersion : 1.2.0
     * isForcedUpdate : true
     * isNeedUpdate : true
     * userSide : app
     * apkName : cody-app
     * updateInfo : 更新了用户信息界操作逻辑
     * market : 应用宝
     * createDate : 1493800434000
     * updateDate : 1493800322000
     */
    public String type;
    public String downloadUrl;
    public String latestVersion;
    public String lowestVersion;
    public boolean isForcedUpdate;
    public boolean isNeedUpdate;
    public String userSide;
    public String apkName;
    public String updateInfo;
    public String market;
    public long createDate;
    public long updateDate;
}
