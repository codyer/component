/*
 * ************************************************************
 * 文件：BusProcessService.java  模块：bus-core  项目：component
 * 当前修改时间：2020年06月11日 23:14:53
 * 上次修改时间：2020年06月11日 23:14:53
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bus-core
 * Copyright (c) 2020
 * ************************************************************
 */

package com.cody.component.bus.process;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;


/**
 * 跨进程事件总线支持服务
 * 在 Application 的 onCreate 方法中调用 LiveEventBus.supportMultiProcess(this);
 * 在 Application 的 onTerminate 方法中调用 LiveEventBus.stopSupportMultiProcess();
 */
public class BusProcessService extends Service {
    private final static String TAG = "BusProcessService";
    private RemoteCallbackList<IBusListener> mBusListener = new RemoteCallbackList<>();
    private String mProcessName;
    private Map<String, BusEventItem> mProcessCacheMap = new HashMap<>();

    public BusProcessService() {
        mProcessName = Application.getProcessName();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private Binder mBinder = new IBusProcess.Stub() {

        @Override
        public void post(String process, String scope, String event, String type, String value) throws RemoteException {
            Log.d(TAG, "process=" + process + "\nvalue=" + value);
            postValueToOtherProcess(process, scope, event, type, value);
        }

        @Override
        public void register(IBusListener listener) throws RemoteException {
            mBusListener.register(listener);
            postInitValueToNewProcess(listener);
        }

        @Override
        public void unregister(IBusListener listener) {
            mBusListener.unregister(listener);
        }
    };

    /**
     * 转发 粘性事件到新的进程
     *
     * @param listener 监听
     * @throws RemoteException 异常
     */
    private void postInitValueToNewProcess(final IBusListener listener) throws RemoteException {
        for (BusEventItem item : mProcessCacheMap.values()) {
            listener.onPostInit(item.scope, item.event, item.type, item.value);
        }
    }

    /**
     * 转发事件总线
     *
     * @param process 进程名
     * @param value   发送值
     * @throws RemoteException 异常
     */
    private void postValueToOtherProcess(String process, String scope, String event, String type, String value) throws RemoteException {
        int count = mBusListener.beginBroadcast();
        for (int i = 0; i < count; i++) {
            IBusListener listener = mBusListener.getBroadcastItem(i);
            if (listener == null) continue;
            if (isBusProcess(listener)) {
                BusEventItem cache = new BusEventItem(scope, event, type, value);
                mProcessCacheMap.put(cache.getKey(), cache);
                Log.d(TAG, "filter process not to post, add to cache:" + listener.process());
            } else if (isSameProcess(listener, process)) {
                Log.d(TAG, "filter process not to post:" + listener.process());
            } else {
                listener.onPost(scope, event, type, value);
            }
        }
        mBusListener.finishBroadcast();
    }

    /**
     * 是否为当前管理进程
     *
     * @param listener 事件总线转发
     * @return 是否需要转发
     * @throws RemoteException 异常
     */
    private boolean isBusProcess(IBusListener listener) throws RemoteException {
        return TextUtils.equals(listener.process(), mProcessName);
    }

    /**
     * 是否为发送进程不需要转发事件总线（原进程中已经处理）
     *
     * @param listener 事件总线转发
     * @param process  进程名
     * @return 是否需要转发
     * @throws RemoteException 异常
     */
    private boolean isSameProcess(IBusListener listener, String process) throws RemoteException {
        return TextUtils.equals(listener.process(), process);
    }
}
