/*
 * ************************************************************
 * 文件：BooleanLiveData.java  模块：lib-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月15日 23:01:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：lib-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.livedata;

/**
 * Created by xu.yi. on 2019/4/15.
 * component
 */
public class BooleanLiveData extends SafeMutableLiveData<Boolean>{

    public BooleanLiveData(final Boolean value) {
        super(value);
    }

    public void negation() {
        Boolean old = getValue();
        if (old != null) {
            postValue(!old);
        }
    }

    public boolean get() {
        Boolean old = getValue();
        if (old != null) {
            return old;
        }
        return false;
    }
}
