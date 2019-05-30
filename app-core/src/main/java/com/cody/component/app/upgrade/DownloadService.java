/*
 * ************************************************************
 * 文件：DownloadService.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月21日 22:09:04
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.upgrade;

import android.Manifest;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.cody.component.app.R;
import com.cody.component.util.LogUtil;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cody.yi on 2016/10/20.
 * 下载apk并安装的service
 */
public class DownloadService extends Service {
    public static final int QUERY = 0x003;
    public static final int NOTIFICATION_ID = 0x004;
    public static final String DOWNLOAD = "DownloadService_download";
    public static final String APK_NAME = "DownloadService_apkName";
    public static final String IS_FORCE = "DownloadService_isForce";
    private static final String TAG = "DownloadService";
    private boolean mIsDownloaded = false;
    private long mDownLoadId;
    private String mApkName;
    private DownloadBinder mDownloadBinder;
    private DownloadManager mDownloadManager;
    private BroadcastReceiver mBroadcastReceiver;
    private ScheduledExecutorService mScheduledExecutorService;
    private Future<?> mFuture;
    private OnDownloadListener mOnDownloadListener;
    private OnDownloadListener mOnNotificationListener;
    private boolean mIsForce;
    private Handler mHandler = new Handler(msg -> {
        switch (msg.what) {
            case QUERY:
                updateProgress();
                break;
        }
        return true;
    });

    public boolean isDownloaded() {
        return mIsDownloaded;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Uri uri = Uri.parse(intent.getStringExtra(DOWNLOAD));
            mApkName = intent.getStringExtra(APK_NAME);
            mIsForce = intent.getBooleanExtra(IS_FORCE, false);
            PackageManager packageManager = getPackageManager();
            String path = Environment.getExternalStorageDirectory() + "/download/" + mApkName;
            if (new File(path).exists()) {
                mIsDownloaded = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES) != null;
                if (mIsDownloaded) {
                    installApk(getApplicationContext());
                    return mDownloadBinder;
                }
            }
            startDownload(uri);
        } else {
            LogUtil.d(getBaseContext().getString(R.string.permission_un_granted));
            stopSelf();
        }
        return mDownloadBinder;
    }

    private void installApk(final Context applicationContext) {
        if (mOnDownloadListener != null) {
            mOnDownloadListener.onFinish();
        }
        if (mOnNotificationListener != null) {
            mOnNotificationListener.onFinish();
        }
    }

    /**
     * @param onProgressListener 设置进度监听
     */
    public void setOnProgressListener(OnDownloadListener onProgressListener) {
        this.mOnDownloadListener = onProgressListener;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDownloadBinder = new DownloadBinder();
        mScheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    @Override
    public void onDestroy() {
        unregisterBroadcast();
        if (mFuture != null && !mFuture.isCancelled()) {
            mFuture.cancel(true);
        }

        if (mScheduledExecutorService != null && !mScheduledExecutorService.isShutdown()) {
            mScheduledExecutorService.shutdown();
        }
        LogUtil.d(TAG, "下载任务服务销毁");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_REDELIVER_INTENT;
    }

    /**
     * 下载文件方法
     *
     * @param uri 下载地址
     */
    private void startDownload(Uri uri) {
        if (mOnNotificationListener == null) {
            mOnNotificationListener = new DefaultNotificationDownloadListener(getApplicationContext(), NOTIFICATION_ID);
        }
        if (mOnDownloadListener != null) {
            mOnDownloadListener.onStart();
        }
        if (mOnNotificationListener != null) {
            mOnNotificationListener.onStart();
        }

        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setMimeType("application/vnd.android.package-archive");
        request.setTitle(getBaseContext().getString(R.string.version_update));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, mApkName);
        mDownLoadId = mDownloadManager.enqueue(request);
        registerBroadcast();

        //每过100ms通知handler去查询下载状态
        mFuture = mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Message msg = mHandler.obtainMessage();
                msg.what = QUERY;
                mHandler.sendMessage(msg);
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    /**
     * 注册广播
     */
    private void registerBroadcast() {
        /**注册service 广播 1.任务完成时 2.进行中的任务被点击*/
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        registerReceiver(mBroadcastReceiver = new DownLoadBroadcast(), intentFilter);
    }

    /**
     * 注销广播
     */
    private void unregisterBroadcast() {
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
            mBroadcastReceiver = null;
        }
    }

    /**
     * 通过query查询下载状态，包括已下载数据大小，总大小，下载状态
     */
    private void updateProgress() {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(mDownLoadId);
        Cursor cursor = null;
        try {
            cursor = mDownloadManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                //已经下载文件大小
                int size = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                //下载文件的总大小
                int total = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                if (mOnDownloadListener != null && total > 0) {
                    mOnDownloadListener.onProgress(size, total);
                }
                if (mOnNotificationListener != null && total > 0) {
                    mOnNotificationListener.onProgress(size, total);
                }
                //终止轮询task
                if (total == size || total == 0) {
                    mFuture.cancel(true);
                    if (mScheduledExecutorService != null && !mScheduledExecutorService.isShutdown()) {
                        mScheduledExecutorService.shutdown();
                    }
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * 检查权限
     *
     * @param permission 权限名称
     * @return 是否有改权限
     */
    private boolean checkPermission(@NonNull String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public interface OnDownloadListener {

        void onStart();

        void onProgress(int progress, int max);

        void onFinish();

        void onFailure();
    }

    private static class DefaultNotificationDownloadListener implements OnDownloadListener {
        private Context mContext;
        private int mNotifyId;
        private NotificationCompat.Builder mBuilder;

        DefaultNotificationDownloadListener(Context context, int notifyId) {
            mContext = context;
            mNotifyId = notifyId;
        }

        @Override
        public void onStart() {
            if (mBuilder == null) {
                String title = mContext.getString(R.string.downLoading) + mContext.getString(R.string.app_name);
                mBuilder = new NotificationCompat.Builder(mContext);
                mBuilder.setOngoing(true)
                        .setAutoCancel(false)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setSmallIcon(mContext.getApplicationInfo().icon)
                        .setTicker(title)
                        .setContentTitle(title);
            }
        }

        @Override
        public void onProgress(int progress, int max) {
            if (mBuilder != null) {
                if (progress > 0) {
                    mBuilder.setPriority(Notification.PRIORITY_DEFAULT);
                    mBuilder.setDefaults(0);
                }
                mBuilder.setProgress(max, progress, false);

                NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                nm.notify(mNotifyId, mBuilder.build());
            }
        }

        @Override
        public void onFinish() {
            NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(mNotifyId);
        }

        @Override
        public void onFailure() {
            NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(mNotifyId);
        }
    }

    public class DownloadBinder extends Binder {
        /**
         * 返回当前服务的实例
         */
        public DownloadService getService() {
            return DownloadService.this;
        }
    }

    private class DownLoadBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) return;
            long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (intent.getAction() == null) return;
            switch (intent.getAction()) {
                case DownloadManager.ACTION_DOWNLOAD_COMPLETE:
                    if (mDownLoadId == downId && downId != -1 && mDownloadManager != null) {
                        Uri downIdUri = mDownloadManager.getUriForDownloadedFile(mDownLoadId);
                        if (downIdUri != null) {
                            //成功下载Apk后安装
                            installApk(context);
                        } else {
                            if (mOnDownloadListener != null) {
                                mOnDownloadListener.onFailure();
                            }
                            if (mOnNotificationListener != null) {
                                mOnNotificationListener.onFailure();
                            }
                        }
                    }
                    break;
                default:
                    LogUtil.d(intent.toString());
                    break;
            }

        }
    }
}
