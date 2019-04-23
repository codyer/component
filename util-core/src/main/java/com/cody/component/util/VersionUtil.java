/*
 * ************************************************************
 * 文件：VersionUtil.java  模块：util-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月21日 22:06:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：util-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.util;

import android.text.TextUtils;

/**
 * Created by xu.yi. on 2019/4/21.
 * 版本
 */
public class VersionUtil {

    /***
     * 比较版本
     * @param newVersion 新版本
     * @param oldVersion 当前版本
     * @return
     */
    public static boolean compareVersion(String newVersion, String oldVersion){
        if (TextUtils.isEmpty(newVersion) || TextUtils.isEmpty(oldVersion)){
            return false;
        }

        if (TextUtils.equals(newVersion, oldVersion)){
            return false;
        }

        try {
            String[] newSplit = newVersion.trim().split("\\.");
            String[] oldSplit = oldVersion.trim().split("\\.");
            int minLen = Math.min(newSplit.length, oldSplit.length);
            int diff = 0;
            int index = 0;
            while (index < minLen && (diff = Integer.parseInt(newSplit[index]) - Integer.parseInt(oldSplit[index])) == 0) {
                index ++;
            }

            if (diff == 0) {
                for (int i = index; i < newSplit.length; i ++) {
                    if (Integer.parseInt(newSplit[i]) > 0) {
                        return true;
                    }
                }

                return false;
            } else {
                return diff > 0;
            }
        } catch (Exception e){

        }
        return false;
    }

}
