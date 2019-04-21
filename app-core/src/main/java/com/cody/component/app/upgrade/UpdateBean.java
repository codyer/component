/*
 * ************************************************************
 * 文件：UpdateBean.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月21日 22:03:47
 * 上次修改时间：2018年07月19日 17:42:06
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
     * downloadUrl : https://imadmin.mmall.com/ftpupload/longguo-release.apk
     * latestVersion : 1.0.0
     * lowestVersion : 1.0.0
     * isForcedUpdate : true
     * isNeedUpdate : true
     * userSide : B
     * updateInfo : ww
     * deleteStatus : 0
     * markType : null
     * createDate : 1493800434000
     * updateDate : 1493800322000
     */
    public String version;
    public String lastVersion;
    public boolean deleteStatus;
    public String updateInfo;
    public String source;
    public String fromObject;
    public String platform;
    public String url;
    public String marketNumber;
    public String market;
    public long createDate;
    public String createName;
    public long updateDate;
    public String updateName;
    public Object mainIOSVersion;
    public boolean needUpdate;

}
