/*
 * ************************************************************
 * 文件：NotificationManagement.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import android.util.LongSparseArray;

import com.cody.http.cat.R;
import com.cody.http.cat.db.data.ItemHttpData;
import com.cody.http.cat.service.CatClearService;
import com.cody.http.cat.ui.CatMainActivity;

/**
 * Created by xu.yi. on 2019/4/5.
 * NotificationManagement
 */
public class NotificationManagement {

    private static final String CHANNEL_ID = "CatChannelId";

    private static final String CHANNEL_NAME = "Http Notifications";

    private static final String NOTIFICATION_TITLE = "Recording Http Activity";

    private static final int NOTIFICATION_ID = 19950724;

    private static final int BUFFER_SIZE = 10;

    private final LongSparseArray<ItemHttpData> transactionBuffer = new LongSparseArray<>();

    private final Context context;

    private final NotificationManager notificationManager;

    private int transactionCount;

    private volatile boolean showNotification = true;

    private static volatile NotificationManagement instance;

    private NotificationManagement(Context context) {
        this.context = context.getApplicationContext();
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT));
        }
    }

    public static NotificationManagement getInstance(Context context) {
        if (instance == null) {
            synchronized (NotificationManagement.class) {
                if (instance == null) {
                    instance = new NotificationManagement(context);
                }
            }
        }
        return instance;
    }

    public synchronized void show(ItemHttpData transaction) {
        if (showNotification) {
            addToBuffer(transaction);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentIntent(getContentIntent(context))
                    .setLocalOnly(true)
                    .setSmallIcon(R.mipmap.cat_ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.cat_ic_launcher))
                    .setContentTitle(NOTIFICATION_TITLE);
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            int size = transactionBuffer.size();
            if (size > 0) {
                builder.setContentText(transactionBuffer.valueAt(size - 1).getNotificationText());
                for (int i = size - 1; i >= 0; i--) {
                    inboxStyle.addLine(transactionBuffer.valueAt(i).getNotificationText());
                }
            }
            builder.setAutoCancel(true);
            builder.setStyle(inboxStyle);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setSubText(String.valueOf(transactionCount));
            } else {
                builder.setNumber(transactionCount);
            }
            builder.addAction(getClearAction());
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private synchronized void addToBuffer(ItemHttpData itemHttpData) {
        transactionCount++;
        transactionBuffer.put(itemHttpData.getId(), itemHttpData);
        if (transactionBuffer.size() > BUFFER_SIZE) {
            transactionBuffer.removeAt(0);
        }
    }

    public synchronized void showNotification(boolean showNotification) {
        this.showNotification = showNotification;
    }

    public synchronized void clearBuffer() {
        transactionBuffer.clear();
        transactionCount = 0;
    }

    public synchronized void dismiss() {
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private PendingIntent getContentIntent(Context context) {
        return PendingIntent.getActivity(context, 100, getLaunchIntent(context), 0);
    }

    private NotificationCompat.Action getClearAction() {
        PendingIntent intent = PendingIntent.getService(context, 200,
                new Intent(context, CatClearService.class), PendingIntent.FLAG_ONE_SHOT);
        return new NotificationCompat.Action(R.mipmap.cat_ic_launcher, "Clear", intent);
    }

    private static Intent getLaunchIntent(Context context) {
        Intent intent = new Intent(context, CatMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}