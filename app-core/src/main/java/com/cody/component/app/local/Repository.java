/*
 * ************************************************************
 * 文件：Repository.java  模块：app-core  项目：component
 * 当前修改时间：2019年05月06日 14:20:35
 * 上次修改时间：2019年05月05日 16:47:52
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.local;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cody.yi on 2017/3/30.
 * 数据仓库，所有数据通过此类进行管理
 */
public class Repository {
    private static Repository sRepository;
    private final Map<String, String> mLocalStringCache = new ConcurrentHashMap<>();
    private final Map<String, Integer> mLocalIntegerCache = new ConcurrentHashMap<>();
    private final Map<String, Boolean> mLocalBooleanCache = new ConcurrentHashMap<>();
    private final Map<String, Float> mLocalFloatCache = new ConcurrentHashMap<>();
    private final Map<String, Map<String, Object>> mLocalMapCache = new ConcurrentHashMap<>();
    private final Map<String, List<String>> mLocalListCache = new ConcurrentHashMap<>();
    private LocalProfile mProfile;

    private Repository(LocalProfile profile) {
        mProfile = profile;
    }

    private static Repository getRepository() {
        if (sRepository == null) {
            throw new NullPointerException("you should call Repository.install(context) in you Application first.");
        } else {
            return sRepository;
        }
    }

    public static void install(Context context) {
        if (sRepository != null) {
            throw new NullPointerException("please only call install Repository one time.");
        } else if (context instanceof Application) {
            sRepository = new Repository(new LocalProfile(context.getApplicationContext()));
        } else {
            throw new NullPointerException("context is invalid when call Repository.install.");
        }
    }

    public static String getLocalValue(String localKey) {
        String value;
        checkKey(localKey);
        if (!getRepository().mLocalStringCache.containsKey(localKey)) {
            value = getRepository().mProfile.getValue(localKey, "");
            getRepository().mLocalStringCache.put(localKey, value);
        } else {
            value = getRepository().mLocalStringCache.get(localKey);
        }
        return value;
    }

    public static void setLocalValue(String localKey, String localValue) {
        checkKey(localKey);
        if (TextUtils.isEmpty(localValue)) {
            getRepository().mProfile.remove(localKey);
            getRepository().mLocalStringCache.remove(localKey);
        } else {
            getRepository().mProfile.setValue(localKey, localValue);
            getRepository().mLocalStringCache.put(localKey, localValue);
        }
    }

    public static Integer getLocalInt(String localKey) {
        Integer value;
        checkKey(localKey);
        if (!getRepository().mLocalIntegerCache.containsKey(localKey)) {
            value = getRepository().mProfile.getValue(localKey, 0);
            getRepository().mLocalIntegerCache.put(localKey, value);
        } else {
            value = getRepository().mLocalIntegerCache.get(localKey);
        }
        return value;
    }

    public static void setLocalInt(String localKey, int localValue) {
        checkKey(localKey);
        if (localValue == 0) {
            getRepository().mProfile.remove(localKey);
            getRepository().mLocalIntegerCache.remove(localKey);
        } else {
            getRepository().mProfile.setValue(localKey, localValue);
            getRepository().mLocalIntegerCache.put(localKey, localValue);
        }
    }

    public static Float getLocalFloat(String localKey) {
        Float value;
        checkKey(localKey);
        if (!getRepository().mLocalFloatCache.containsKey(localKey)) {
            value = getRepository().mProfile.getValue(localKey, 0f);
            getRepository().mLocalFloatCache.put(localKey, value);
        } else {
            value = getRepository().mLocalFloatCache.get(localKey);
        }
        return value;
    }

    public static void setLocalFloat(String localKey, float localValue) {
        checkKey(localKey);
        if (localValue == 0f) {
            getRepository().mProfile.remove(localKey);
            getRepository().mLocalFloatCache.remove(localKey);
        } else {
            getRepository().mProfile.setValue(localKey, localValue);
            getRepository().mLocalFloatCache.put(localKey, localValue);
        }
    }

    public static Boolean getLocalBoolean(String localKey) {
        Boolean value;
        checkKey(localKey);
        if (!getRepository().mLocalBooleanCache.containsKey(localKey)) {
            value = getRepository().mProfile.getValue(localKey, false);
            getRepository().mLocalBooleanCache.put(localKey, value);
        } else {
            value = getRepository().mLocalBooleanCache.get(localKey);
        }
        return value;
    }

    public static void setLocalBoolean(String localKey, boolean localValue) {
        checkKey(localKey);
        if (!localValue) {
            getRepository().mProfile.remove(localKey);
            getRepository().mLocalBooleanCache.remove(localKey);
        } else {
            getRepository().mProfile.setValue(localKey, true);
            getRepository().mLocalBooleanCache.put(localKey, true);
        }
    }

    public static Map<String, Object> getLocalMap(String localKey) {
        Map<String, Object> value;
        checkKey(localKey);
        if (!getRepository().mLocalMapCache.containsKey(localKey)) {
            value = getRepository().mProfile.getMap(localKey);
            if (value != null && !value.isEmpty()) {
                getRepository().mLocalMapCache.put(localKey, value);
            }
        } else {
            value = getRepository().mLocalMapCache.get(localKey);
        }
        return value;
    }

    public static void setLocalMap(String localKey, Map<String, Object> localMap) {
        checkKey(localKey);
        if (localMap == null) {
            getRepository().mLocalMapCache.remove(localKey);
            getRepository().mProfile.remove(localKey);
        } else {
            getRepository().mLocalMapCache.put(localKey, localMap);
            getRepository().mProfile.setMap(localKey, localMap);
        }
    }

    public static List<String> getLocalList(String localKey) {
        List<String> value;
        checkKey(localKey);
        if (!getRepository().mLocalListCache.containsKey(localKey)) {
            value = getRepository().mProfile.getList(localKey);
            if (value != null && !value.isEmpty()) {
                getRepository().mLocalListCache.put(localKey, value);
            }
        } else {
            value = getRepository().mLocalListCache.get(localKey);
        }
        return value;
    }

    public static void setLocalList(String localKey, List<String> localList) {
        checkKey(localKey);
        if (localList == null) {
            getRepository().mLocalListCache.remove(localKey);
            getRepository().mProfile.remove(localKey);
        } else {
            getRepository().mLocalListCache.put(localKey, localList);
            getRepository().mProfile.setList(localKey, localList);
        }
    }

    public static void clear() {
        getRepository().mLocalStringCache.clear();
        getRepository().mLocalIntegerCache.clear();
        getRepository().mLocalBooleanCache.clear();
        getRepository().mLocalFloatCache.clear();
        getRepository().mLocalMapCache.clear();
        getRepository().mLocalListCache.clear();
        getRepository().mProfile.clear();
    }

    private static void checkKey(String localKey) {
        if (localKey == null) {
            throw new NullPointerException("localKey should not be null.");
        }
    }
}
