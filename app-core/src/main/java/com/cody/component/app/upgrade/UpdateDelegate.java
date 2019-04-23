/*
 * ************************************************************
 * 文件：UpdateDelegate.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月21日 22:23:41
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.upgrade;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.cody.component.app.R;
import com.cody.component.app.activity.BaseActivity;
import com.cody.component.util.LogUtil;
import com.cody.component.util.SizeUtil;

import androidx.appcompat.app.AlertDialog;

/**
 * 版本更新
 */
public class UpdateDelegate {
    private final BaseActivity mActivity;
    private final UpdateViewData mUpdateViewData;
    private ProgressDialog mProgressDialog;
    private OnUpdateListener mOnUpdateListener;
    private boolean isBindService;
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
            DownloadService downloadService = binder.getService();
            if (downloadService.isDownloaded()) {
                stopDownLoadService();
                mOnUpdateListener.onUpdateFinish();
                mProgressDialog.dismiss();
            }

            //接口回调，下载进度
            downloadService.setOnProgressListener(new DownloadService.OnDownloadListener() {
                @Override
                public void onStart() {
                }

                @Override
                public void onProgress(int size, int total) {
                    String progress = String.format(mActivity.getString(R.string.download_progress_format), SizeUtil.formatSize(size), SizeUtil.formatSize(total));
                    LogUtil.d("download", progress);
                    mProgressDialog.setMax(total);
                    mProgressDialog.setProgress(size);
                    mProgressDialog.setMessage(progress);
                }

                @Override
                public void onFinish() {
                    mActivity.showToast(mActivity.getString(R.string.download_finished));
                    stopDownLoadService();
                    mProgressDialog.dismiss();
                    mOnUpdateListener.onUpdateFinish();
                }

                @Override
                public void onFailure() {
                    mActivity.showToast("更新失败！");
                    stopDownLoadService();
                    mProgressDialog.dismiss();
                    mOnUpdateListener.onUpdateFinish();
                }
            });

            isBindService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBindService = false;
        }
    };

    private UpdateDelegate(BaseActivity activity, UpdateViewData viewModel, OnUpdateListener onUpdateListener) {
        mActivity = activity;
        mUpdateViewData = viewModel;
        mOnUpdateListener = onUpdateListener;

        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setTitle(R.string.version_update);
        mProgressDialog.setMessage(activity.getString(R.string.update_progress));
        mProgressDialog.setProgressNumberFormat(null);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        if (mUpdateViewData.isForceUpdate()) {
            forceUpdate();
        } else if (mUpdateViewData.isOptionalUpdate()) {
            optionalUpdate();
        } else {
            mOnUpdateListener.onUpdateFinish();
        }
    }

    public static void delegate(BaseActivity activity, UpdateViewData viewModel, OnUpdateListener onUpdateListener) {
        new UpdateDelegate(activity, viewModel, onUpdateListener);
    }

    /**
     * 选择更新
     */
    private void optionalUpdate() {
        new AlertDialog.Builder(mActivity)
                .setTitle(mActivity.getString(R.string.upgrade_title))
                .setMessage(mUpdateViewData.getUpdateInfo())
                .setCancelable(false)
                .setPositiveButton(R.string.update_now, (dialog, which) -> bindDownLoadService())
                .setNegativeButton(R.string.not_now, (dialog, which) -> mOnUpdateListener.onUpdateFinish()).show();
    }

    /**
     * 强制更新
     */
    private void forceUpdate() {
        final AlertDialog alertDialog = new AlertDialog.Builder(mActivity)
                .setTitle(mActivity.getString(R.string.upgrade_title))
                .setMessage(mUpdateViewData.getUpdateInfo())
                .setCancelable(false)
                .setPositiveButton(R.string.update_now, (dialog, which) -> bindDownLoadService()).create();
        alertDialog.show();
        alertDialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                ((Activity) alertDialog.getContext()).finish();
            }
            return false;
        });
    }

    /**
     * DownloadManager是否可用
     */
    private boolean isDownLoadMangerEnable(Context context) {
        int state = context.getApplicationContext().getPackageManager()
                .getApplicationEnabledSetting("com.android.providers.downloads");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return !(state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
                    state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED);
        } else {
            return !(state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
                    state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER);
        }
    }

    private void bindDownLoadService() {
        if (isDownLoadMangerEnable(mActivity)) {
            Intent intent = new Intent(mActivity, DownloadService.class);
            intent.putExtra(DownloadService.DOWNLOAD, mUpdateViewData.getApkUrl());
            intent.putExtra(DownloadService.APK_NAME, mUpdateViewData.getApkName());
            intent.putExtra(DownloadService.IS_FORCE, mUpdateViewData.isForceUpdate());
            mActivity.startService(intent);
            mProgressDialog.show();
            mActivity.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        } else {//下载管理器被禁用时跳转浏览器下载
            try {
                if (!TextUtils.isEmpty(mUpdateViewData.getApkUrl())) {
                    Intent intent = new Intent();
                    Uri uri = Uri.parse(mUpdateViewData.getApkUrl());
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    if (intent.resolveActivity(mActivity.getPackageManager()) != null) {
                        mActivity.startActivity(Intent.createChooser(intent, "请选择浏览器进行下载更新"));
                        System.exit(0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stopDownLoadService() {
        try {
            Intent intent = new Intent(mActivity, DownloadService.class);
            if (isBindService) {
                mActivity.unbindService(mServiceConnection);
                isBindService = false;
            }
            mActivity.stopService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnUpdateListener {
        void onUpdateFinish();
    }
}