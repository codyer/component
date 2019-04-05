package com.cody.http.monitor.service;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

import com.cody.http.monitor.holder.NotificationHolder;

/**
 * Created by xu.yi. on 2019/4/5.
 * ClearMonitorService
 */
public class ClearMonitorService extends IntentService {

    public ClearMonitorService() {
        super(ClearMonitorService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        NotificationHolder holder = NotificationHolder.getInstance(this);
        holder.clearBuffer();
        holder.dismiss();
    }

}