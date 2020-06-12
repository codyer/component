/*
 * ************************************************************
 * 文件：LiveEventBus.java  模块：bus-core  项目：component
 * 当前修改时间：2019年06月05日 13:57:25
 * 上次修改时间：2019年06月05日 11:50:32
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bus-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bus;

import android.content.Context;

import androidx.annotation.NonNull;

import com.cody.component.bus.factory.BusFactory;
import com.cody.component.bus.lib.IEvent;
import com.cody.component.bus.lib.annotation.AutoGenerate;
import com.cody.component.bus.lib.exception.WrongTypeException;
import com.cody.component.bus.process.MultiProcessSupport;
import com.cody.component.bus.wrapper.LiveEventWrapper;
import com.cody.component.bus.wrapper.StubLiveEventWrapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by xu.yi. on 2019/3/31.
 * 使用 LiveData 实现类似event bus功能，支持生命周期管理
 * 使用方式：
 * LiveEventBus.begin()
 * .inScope(ToBeCompilerOut.class)
 * .withEvent$userDefinedEvent()
 * .observe(null, new ObserverWrapper&lt;String&gt;() {
 * Override
 * public void onChanged(@Nullable String s) {
 * }
 * });
 * <p>
 * LiveEventBus.begin()
 * .inScope(ToBeCompilerOut.class)
 * .withEvent$userDefinedEvent().post("");
 */
public class LiveEventBus {
    private static class InstanceHolder {
        private static final LiveEventBus INSTANCE = new LiveEventBus();
    }

    /**
     * 建议使用，统一事件管理，且可以定义分组Scope
     *
     * @return 单例
     * @see com.cody.component.bus.lib.annotation.EventScope
     */
    public static LiveEventBus begin() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * 进程创建时调用，一般在 Application 的 onCreate 中调用
     * @param context 上下文
     */
    public static void supportMultiProcess(Context context) {
        MultiProcessSupport.start(context);
    }

    /**
     * 进程结束时调用，一般在 Application 的 onTerminate 中调用
     */
    public static void stopSupportMultiProcess() {
        MultiProcessSupport.stop();
    }

    /**
     * 获取默认域的事件包装类
     *
     * @param event 事件名
     * @return 默认域的事件包装类
     * <p>
     * 所有事件以事件名为key进行观察
     * 使用此方法需要自己管理事件，重名等问题，不建议使用，建议使用begin函数
     * @see #begin()
     */
    public static LiveEventWrapper getDefault(String event) {
        return getDefault(event, Object.class);
    }

    /**
     * 获取默认域的事件包装类
     *
     * @param event 事件名
     * @param type  事件类型
     * @param <T>   事件类型
     * @return 默认域的事件包装类
     * <p>
     * 使用此方法需要自己管理事件，重名等问题，不建议使用，建议使用begin函数
     * @see #begin()
     */
    public static <T> LiveEventWrapper<T> getDefault(String event, @NonNull Class<T> type) {
        return getDefault(event, type, false);
    }

    /**
     * 获取默认域的事件包装类
     *
     * @param event 事件名
     * @param type  事件类型
     * @param <T>   事件类型
     * @return 默认域的事件包装类
     * <p>
     * 使用此方法需要自己管理事件，重名等问题，不建议使用，建议使用begin函数
     * @see #begin()
     */
    public static <T> LiveEventWrapper<T> getDefault(String event, @NonNull Class<T> type, boolean process) {
        return getDefault(LiveEventBus.class.getName(), event, type, process);
    }

    /**
     * 获取默认域的事件包装类
     *
     * @param scope   分组管理
     * @param event   事件名
     * @param type    事件类型
     * @param <T>     事件类型
     * @param process 是否支持跨进程
     * @return 默认域的事件包装类
     * <p>
     * 使用此方法需要自己管理事件，重名等问题，不建议使用，建议使用begin函数
     * @see #begin()
     */
    public static <T> LiveEventWrapper<T> getDefault(String scope, String event, @NonNull Class<T> type, boolean process) {
        return BusFactory.ready().create(scope, event, type.getName(), process);
    }

    private LiveEventBus() {
    }

    /**
     * 在什么范围
     *
     * @param scopeClass 范围，通过注解自动生成的类，如果没有生成，请使用注解
     *                   com.cody.component.bus.lib.annotation.EventScope 注解枚举类，并使用
     *                   com.cody.component.bus.lib.annotation.Event 注解事件
     * @return 返回代理类实例
     */
    @SuppressWarnings("unchecked")
    public synchronized <T extends IEvent> T inScope(Class<T> scopeClass) {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class<?>[]{scopeClass},
                new InterfaceInvokeHandler<>(scopeClass));
    }

    private static class InterfaceInvokeHandler<T> implements InvocationHandler {
        private final String scopeName;
        private boolean active;

        InterfaceInvokeHandler(Class<T> scopeClass) {
            AutoGenerate generate = scopeClass.getAnnotation(AutoGenerate.class);
            if (generate == null) {
                throw new WrongTypeException();
            }
            scopeName = generate.scope();
            active = generate.active();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object... args) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {

            if (method.getDeclaringClass() == Object.class) {
                return method.invoke(this, args);
            }
            if (active) {
                AutoGenerate generate = method.getAnnotation(AutoGenerate.class);
                if (generate == null) {
                    throw new WrongTypeException();
                }
                return BusFactory.ready().create(scopeName, method.getName(), generate.type(), generate.process());
            }
            return new StubLiveEventWrapper<>();
        }
    }
}
