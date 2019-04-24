/*
 * ************************************************************
 * 文件：ViewAction.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月24日 09:57:37
 * 上次修改时间：2019年04月23日 18:23:20
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.define;

/**
 * Created by xu.yi. on 2019/3/25.
 * 通知视图的动作
 */
public class ViewAction {
    public static final int DEFAULT = 0;
    public static final int SHOW_LOADING = 1;
    public static final int HIDE_LOADING = 2;
    public static final int SHOW_TOAST = 3;
    public static final int FINISH = 4;
    public static final int FINISH_WITH_RESULT_OK = 5;
    public static final int MAX_ACTION = 5;//定义枚举最大值
    /**
     * String	操作码
     */
    private int mAction;
    /**
     * String	操作提示信息
     */
    private String mMessage;
    /**
     * 携带数据，慎用
     */
    private Object mData;

    public ViewAction(int action) {
        mAction = action;
    }

    public ViewAction(int action, String message) {
        mAction = action;
        mMessage = message;
    }

    public int getAction() {
        return mAction;
    }

    public void setAction(int action) {
        mAction = action;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Object getData() {
        return mData;
    }

    public void setData(Object data) {
        mData = data;
    }
}
