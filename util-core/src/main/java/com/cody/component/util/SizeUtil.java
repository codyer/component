/*
 * ************************************************************
 * 文件：SizeUtil.java  模块：util-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月21日 22:20:27
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：util-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.util;

import java.text.DecimalFormat;

/**
 * Created by xu.yi. on 2019/4/21.
 * component
 */
public class SizeUtil {
    private static final double BASE = 1024, KB = BASE, MB = KB * BASE, GB = MB * BASE, TB = GB * BASE;
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public static String formatSize(double size) {
        if (size >= TB) {
            return df.format(size / TB) + " TB";
        }
        if (size >= GB) {
            return df.format(size / GB) + " GB";
        }
        if (size >= MB) {
            return df.format(size / MB) + " MB";
        }
        if (size >= KB) {
            return df.format(size / KB) + " KB";
        }
        return "" + (int) size + " bytes";
    }

}
