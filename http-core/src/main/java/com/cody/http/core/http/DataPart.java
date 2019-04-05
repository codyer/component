/*
 * ************************************************************
 * 文件：DataPart.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月05日 23:11:25
 * 上次修改时间：2018年09月26日 13:42:14
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.http;

import android.text.TextUtils;

import com.cody.http.core.http.util.ImageUtil;

/**
 * Created by cody.yi on 2016/7/25.
 * <p>
 * Simple data container use for passing byte file
 */
public class DataPart {
    private String name;// files
    private String fileName;
    private byte[] content;
    private String path;
    private String type;

    /**
     * Default data part
     */
    public DataPart() {
    }

    /**
     * Constructor with data.
     *
     * @param name label of data
     * @param data byte data
     */
    public DataPart(String name, byte[] data) {
        fileName = name;
        content = data;
    }

    /**
     * Constructor with mime data type.
     *
     * @param name     label of data
     * @param data     byte data
     * @param mimeType mime data like "image/jpeg"
     */
    public DataPart(String name, byte[] data, String mimeType) {
        fileName = name;
        content = data;
        type = mimeType;
    }

    /**
     * Constructor with mime data type.
     *
     * @param name     label of data
     * @param data     byte data
     * @param mimeType mime data like "image/jpeg"
     */
    public DataPart(String name, String file, byte[] data, String mimeType) {
        this.name = name;
        fileName = file;
        content = data;
        type = mimeType;
    }

    public DataPart(String name, String file, String path, String mimeType) {
        this.name = name;
        fileName = file;
        this.path = path;
        type = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter file name.
     *
     * @return file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Setter file name.
     *
     * @param fileName string file name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Getter content.
     *
     * @return byte file data
     */
    public byte[] getContent() {
        if (!TextUtils.isEmpty(path)) {
            return ImageUtil.getFileDataFromPath(path);
        }
        return content;
    }

    /**
     * Setter content.
     *
     * @param content byte file data
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * Getter mime type.
     *
     * @return mime type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter mime type.
     *
     * @param type mime type
     */
    public void setType(String type) {
        this.type = type;
    }
}
