/*
 * ************************************************************
 * 文件：UrlUtil.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:44:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.core;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.URLUtil;

import com.cody.component.hybrid.JsBridge;
import com.cody.component.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chen.huarong on 2018/3/13.
 * url工具类
 */
public class UrlUtil {

    /**
     * @param url 地址
     * @return 是否是内部链接
     */
    public static boolean isInnerLink(String url) {
        if (TextUtils.isEmpty(url)) return false;
        if (URLUtil.isFileUrl(url)) return true;
        try {
            if (URLUtil.isNetworkUrl(url)) {
                String host = Uri.parse(url).getHost();
                // TODO 内网地址判断条件
                if (host !=null && host.endsWith(JsBridge.getInstance().getHostSuffix())) {
                    return true;
                }
                // debug 条件下，ip地址的都算内网地址，测试用
                return JsBridge.getInstance().isDebugMode();
            }
            return false;
        } catch (Exception e) {
            LogUtil.e(Log.getStackTraceString(e));
            return false;
        }
    }

    /**
     * 解析出url参数中的键值对
     *
     * @param url url地址
     * @return url请求参数部分
     */
    public static Map<String, String> getUrlParams(String url) {
        Map<String, String> params = new HashMap<>();
        if (TextUtils.isEmpty(url)) return params;
        int beginIndex = url.indexOf("?");
        if (beginIndex >= 0 && url.length() > 1) {
            url = url.substring(++beginIndex);
        }
        //每个键值为一组
        String[] keyValueParts = url.split("&");
        for (String keyValuePart : keyValueParts) {
            String[] keyValue;
            keyValue = keyValuePart.split("=");
            //解析出键值
            if (keyValue.length > 1) {
                //正确解析
                params.put(keyValue[0], keyValue[1]);
            } else if (TextUtils.isEmpty(keyValue[0])) {
                //只有参数没有值，不加入
                params.put(keyValue[0], "");
            }
        }
        return params;
    }
}
