/*
 * ************************************************************
 * 文件：ActivityUtil.java  模块：util-core  项目：component
 * 当前修改时间：2019年04月13日 08:43:55
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：util-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Created by cody.yi on 2016.8.15
 * Activity Navigator
 */
public class ActivityUtil {

    private static ActivityUtil sInstance;
    private Reference<Activity> mCurrentActivity;

    public static void install() {
        sInstance = new ActivityUtil();
    }

    public static void uninstall() {
        getInstance().mCurrentActivity.clear();
        getInstance().mCurrentActivity = null;
        sInstance = null;
    }

    private static ActivityUtil getInstance() {
        if (sInstance == null) {
            install();
//            throw new NullPointerException("You should call ActivityUtil.install() in you application first!");
        }
        return sInstance;
    }

    /**
     * 用getCurrentActivity 前判断是否为空
     */
    public static boolean isActivityDestroyed() {
        return getCurrentActivity() == null ||
                getCurrentActivity().isDestroyed() ||
                getCurrentActivity().isFinishing();
    }

    public static Activity getCurrentActivity() {
        if (getInstance().mCurrentActivity == null) {
            LogUtil.e("You should setCurrentActivity first!");
            return null;
        }
        return getInstance().mCurrentActivity.get();
    }

    public static void setCurrentActivity(@NonNull Activity currentActivity) {
        getInstance().mCurrentActivity = new SoftReference<>(currentActivity);
    }

    /**
     * startActivity
     */
    public static void navigateTo(Intent intent) {
        Activity activity = getCurrentActivity();
        if (activity == null) return;
        activity.startActivity(intent);
    }

    /**
     * startActivity
     */
    public static void navigateTo(Class<? extends Activity> clazz) {
        Activity activity = getCurrentActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }

    /**
     * startActivity with bundle
     */
    public static void navigateTo(Class<? extends Activity> clazz, Bundle bundle) {
        Activity activity = getCurrentActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    /**
     * startActivity then finish
     */
    public static void navigateToThenKill(Intent intent) {
        Activity activity = getCurrentActivity();
        if (activity == null) return;
        activity.startActivity(intent);
        finish();
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    public static void navigateToThenKill(Class<? extends Activity> clazz) {
        Activity activity = getCurrentActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     */
    public static void navigateToThenKill(Class<? extends Activity> clazz, Bundle bundle) {
        Activity activity = getCurrentActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     */
    public static void navigateToForResult(Class<? extends Activity> clazz, int requestCode) {
        navigateToForResult(null, clazz, requestCode);
    }

    /**
     * startActivityForResult with bundle
     */
    public static void navigateToForResult(Class<? extends Activity> clazz, int requestCode, Bundle bundle) {
        navigateToForResult(null, clazz, requestCode, bundle);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param context     上下文
     * @param clazz       跳转的activity
     * @param requestCode requestCode
     */
    public static void navigateToForResult(Object context, Class<? extends Activity> clazz, int requestCode) {
        navigateToForResult(context, clazz, requestCode, null);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param context     上下文
     * @param clazz       跳转的activity
     * @param requestCode requestCode
     * @param bundle      bundle
     */
    public static void navigateToForResult(Object context, Class<? extends Activity> clazz, int requestCode, Bundle bundle) {
        Activity activity = getCurrentActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        if (context == null) {
            context = activity;
        }
        startForResult(context, intent, requestCode);
    }

    public static void openDialPage(String tel) {
        if (TextUtils.isEmpty(tel)) return;
        if (isActivityDestroyed()) return;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(getCurrentActivity().getPackageManager()) != null) {
            getCurrentActivity().startActivity(intent);
        }
    }

    public static void finish() {
        Activity activity = getCurrentActivity();
        if (activity == null) return;
        activity.finish();
    }


    private static void startForResult(Object context, Intent intent, int request) {
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, request);
        } else if (context instanceof Fragment) {
            ((Fragment) context).startActivityForResult(intent, request);
        } else {
            throw new IllegalArgumentException("only use activity or fragment as a context:" + context);
        }
    }
}
