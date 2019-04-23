/*
 * ************************************************************
 * 文件：CatClearService.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.service;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

import com.cody.http.cat.notification.NotificationManagement;

/**
 * Created by xu.yi. on 2019/4/5.
 * CatClearService
 */
public class CatClearService extends IntentService {

    public CatClearService() {
        super(CatClearService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        NotificationManagement holder = NotificationManagement.getInstance(this);
        holder.clearBuffer();
        holder.dismiss();
    }

}