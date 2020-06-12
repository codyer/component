/*
 * ************************************************************
 * 文件：BusProcess.java  模块：bus-core  项目：component
 * 当前修改时间：2020年06月11日 23:14:53
 * 上次修改时间：2020年06月11日 23:14:53
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bus-core
 * Copyright (c) 2020
 * ************************************************************
 */

package com.cody.component.bus.process;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cody.component.bus.factory.BusFactory;


/**
 * 支持进程间事件总线的扩展
 */
public class MultiProcessSupport {
    private static String TAG = "MultiProcessSupport";
    private String mProcessName;
    private IBusProcess mBusProcess;
    private IBusListener mBusListener;
    private Context mContext;

    private MultiProcessSupport() {
        mProcessName = Application.getProcessName();
    }

    private static class InstanceHolder {
        @SuppressLint("StaticFieldLeak")
        static final MultiProcessSupport INSTANCE = new MultiProcessSupport();
    }

    private static MultiProcessSupport ready() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * 进程创建的时候调用
     *
     * @param context           上下文
     * @param mainApplicationId 共享服务且常驻的包名
     *                          如果是单应用，即为应用的包名
     *                          如果是多个应用，即为常驻的主应用的包名
     *                          主应用必须安装，否则不能正常运行
     */
    public static void start(Context context, String mainApplicationId) {
        ready().mContext = context;
        ready().bindService(mainApplicationId);
    }

    /**
     * 进程销毁的时候调用
     */
    public static void stop() {
        ready().unbindService();
    }

    public static <T> void post(String scope, String event, String type, T value) {
        try {
            ready().mBusProcess.post(ready().mProcessName, scope, event, type, JSON.toJSONString(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBusProcess = IBusProcess.Stub.asInterface(service);
            try {
                mBusProcess.register(mBusListener = new IBusListener.Stub() {
                    @Override
                    public String process() {
                        return mProcessName;
                    }

                    @Override
                    public void onPost(String scope, String event, String type, String value) {
                        postToCurrentProcess(scope, event, type, value, false);
                        Log.d(TAG, "onPost(" + value + ")to process=" + Application.getProcessName());
                    }

                    @Override
                    public void onPostInit(String scope, String event, String type, String value) {
                        postToCurrentProcess(scope, event, type, value, true);
                        Log.d(TAG, "onPostInit(" + value + ")to process=" + Application.getProcessName());
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected id=" + Thread.currentThread().getId());
        }
    };

    private void postToCurrentProcess(final String scope, final String event, final String type, final String value, final boolean init) {
        try {
            Object obj = JSON.parseObject(value, Class.forName(type));
            if (init) {
                BusFactory.ready().create(scope, event, type, false).postInitValue(obj);
            } else {
                BusFactory.ready().create(scope, event, type, false).postToCurrentProcess(obj);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void bindService(final String mainApplicationId) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(new ComponentName(mainApplicationId, BusProcessService.CLASS_NAME));
        mContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindService() {
        mContext.unbindService(mServiceConnection);
        if (mBusProcess != null && mBusProcess.asBinder().isBinderAlive()) {
            try {
                // 取消注册
                mBusProcess.unregister(mBusListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mContext = null;
    }
}
