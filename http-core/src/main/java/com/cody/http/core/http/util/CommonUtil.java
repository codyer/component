/*
 * ************************************************************
 * 文件：CommonUtil.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月05日 23:47:33
 * 上次修改时间：2019年04月05日 23:35:23
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.http.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by xu.yi. on 2019/4/5.
 * component
 */
public class CommonUtil {

    /**
     * 重构List成String
     *
     * @return "a,b,c"
     */
    public static String reBuildString(List<String> list) {
        String str = "";
        if (list == null || list.size() == 0) return str;
        for (int i = 0; i < list.size() - 1; i++) {
            str += list.get(i) + ",";
        }
        str += list.get(list.size() - 1);
        return str;
    }

    /**
     * 获取组合类类型
     */
    public static ParameterizedType getType(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }


    /**
     * 拼接get请求的url地址
     *
     * @param url    原地址
     * @param params 参数
     * @return 拼接完成的地址
     */
    public static String buildPathUrl(String url, Map<String, String> params) {
        if (params.size() > 0 && !TextUtils.isEmpty(url)) {
            //restful
            if (url.contains("{")) {
                url = buildRestFulPathUrl(url, params);
            }
            url += url.contains("?") ? "&" : "?";
            StringBuilder encodedParams = new StringBuilder();
            try {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    encodedParams.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                    encodedParams.append('&');
                }
                url += encodedParams.toString();
                url = url.substring(0, url.length() - 1);
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException("Encoding not supported: " + "UTF-8", uee);
            }
        }
        return url;
    }

    /**
     * restful接口地址字段替换
     *
     * @param url    包含大括号的地址
     * @param params 需要替换的参数
     * @return 真实的请求地址
     */
    public static String buildRestFulPathUrl(String url, Map<String, String> params) {
        for (Iterator<Map.Entry<String, String>> it = params.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> item = it.next();
            if (url.contains(item.getKey())) {
                url = url.replace("{" + item.getKey() + "}", item.getValue());
                it.remove();
            }
        }
        return url;
    }
}
