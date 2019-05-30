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
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.cody.component.app.R;
import com.cody.component.app.activity.BaseActivity;
import com.cody.component.util.LogUtil;
import com.cody.component.util.SizeUtil;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * 版本更新
 */
public class UpdateDelegate {
    private static final String TAG = UpdateDelegate.class.getSimpleName();
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
                installApk();
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
            } else {
                //接口回调，下载进度
                downloadService.setOnProgressListener(new DownloadService.OnDownloadListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onProgress(int size, int total) {
                        String progress = String.format(mActivity.getString(R.string.download_progress_format), SizeUtil.formatSize(size), SizeUtil.formatSize(total));
                        LogUtil.d("download", progress);
                        if (mProgressDialog != null) {
                            mProgressDialog.setMax(total);
                            mProgressDialog.setProgress(size);
                            mProgressDialog.setMessage(progress);
                        }
                    }

                    @Override
                    public void onFinish() {
                        if (mActivity != null) {
                            mActivity.showToast(mActivity.getString(R.string.download_finished));
                        }
                        stopDownLoadService();
                        installApk();

                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure() {
                        if (mActivity != null) {
                            mActivity.showToast("更新失败！");
                        }
                        stopDownLoadService();
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                        if (mOnUpdateListener != null) {
                            mOnUpdateListener.onUpdateFinish();
                        }
                    }
                });
            }

            isBindService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBindService = false;
        }
    };

    private UpdateDelegate(BaseActivity activity, UpdateViewData viewData, OnUpdateListener onUpdateListener) {
        mActivity = activity;
        mUpdateViewData = viewData;
        mOnUpdateListener = onUpdateListener;
        if (viewData == null || mOnUpdateListener == null) {
            if (activity != null) {
                activity.finish();
            }
            return;
        }

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

    public static UpdateDelegate delegate(BaseActivity activity, UpdateViewData viewModel, OnUpdateListener onUpdateListener) {
        return new UpdateDelegate(activity, viewModel, onUpdateListener);
    }


    private void installApk() {
        if (mActivity == null || mUpdateViewData == null) return;
        installApk(mActivity, new File(Environment.getExternalStorageDirectory() + "/download/" + mUpdateViewData.getApkName()));
    }

    private void installApk(BaseActivity context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        } else {
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".file_provider", file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //兼容8.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean hasInstallPermission = context.getPackageManager().canRequestPackageInstalls();
                if (!hasInstallPermission) {
                    startInstallPermissionSettingActivity();
                    return;
                }
            }
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            //如果APK安装界面存在，携带请求码跳转。使用forResult是为了处理用户 取消 安装的事件。外面这层判断理论上来说可以不要，但是由于国内的定制，这个加上还是比较保险的
            context.startActivityForResult(intent, 2);
        }
    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        if (mActivity == null)return;
        //后面跟上包名，可以直接跳转到对应APP的未知来源权限设置界面。使用startActivityForResult 是为了在关闭设置界面之后，获取用户的操作结果，然后根据结果做其他处理
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + mActivity.getPackageName()));
        mActivity.startActivityForResult(intent, 1);
    }

    // Activity 需要调用
    public void onActivityResult(int requestCode, int resultCode) {
        if (mActivity == null)return;
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                installApk();
            }
        } else {
            if (requestCode == 1) {
                // 8.0手机位置来源安装权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean hasInstallPermission = mActivity.getPackageManager().canRequestPackageInstalls();
                    if (!hasInstallPermission) {
                        LogUtil.e(TAG, "没有赋予 未知来源安装权限");
                        showUnKnowResourceDialog();
                    }
                }
            } else if (requestCode == 2) {
                // 下午4:31 在安装页面中退出安装了
                LogUtil.e(TAG, "从安装页面回到欢迎页面--拒绝安装");
                showApkInstallDialog();
            }
        }
    }

    /**
     * 功用：弹窗请安装APP的弹窗
     * 说明：8.0手机升级APK时获取了未知来源权限，并跳转到APK界面后，用户可能会选择取消安装，所以，再给一个弹窗
     */
    private void showApkInstallDialog() {
        if (mActivity == null)return;
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                .setTitle(mActivity.getString(R.string.upgrade_title))
                .setMessage(mUpdateViewData.getUpdateInfo())
                .setCancelable(false)
                .setPositiveButton(R.string.update_now, (dialog, which) -> installApk());
        if (!mUpdateViewData.isForceUpdate()) {
            builder.setNegativeButton(R.string.not_now, (dialog, which) -> mOnUpdateListener.onUpdateFinish());
        }
        builder.show();
    }

    /**
     * 功用：未知来源权限弹窗
     * 说明：8.0系统中升级APK时，如果跳转到了 未知来源权限设置界面，并且用户没用允许该权限，会弹出此窗口
     */
    private void showUnKnowResourceDialog() {
        if (mActivity == null)return;
        new AlertDialog.Builder(mActivity)
                .setTitle(mActivity.getString(R.string.get_install_permission))
                .setCancelable(false)
                .setPositiveButton(R.string.ui_str_confirm, (dialog, which) -> {
                    //兼容8.0
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        boolean hasInstallPermission = mActivity.getPackageManager().canRequestPackageInstalls();
                        if (!hasInstallPermission) {
                            startInstallPermissionSettingActivity();
                        }
                    }
                })
                .setNegativeButton(R.string.ui_str_cancel, (dialog, which) -> {
                    mOnUpdateListener.onUpdateFinish();
                    if (mUpdateViewData.isForceUpdate()) {
                        System.exit(0);
                    }
                })
                .show();
    }

    /**
     * 选择更新
     */
    private void optionalUpdate() {
        if (mActivity == null)return;
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
        if (mActivity == null)return;
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
        if (mActivity == null)return;
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