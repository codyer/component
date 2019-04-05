/*
 * ************************************************************
 * 文件：MultipartRequest.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月05日 23:44:05
 * 上次修改时间：2019年04月05日 23:11:29
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.http.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.cody.http.core.http.DataPart;
import com.cody.http.core.http.listener.OnUploadListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cody.yi on 2016/7/22.
 * 表单文件上传
 */
public class MultipartRequest<T> extends BaseRequest<T> {

    private final String twoHyphens = "--";
    private final String lineEnd = "\r\n";
    private final String boundary = "apiclient-" + System.currentTimeMillis();
    private final OnUploadListener mOnUploadListener;
    private Map<String, DataPart> mByteData = new HashMap<>();
    private int mProgress;
    private int mMax;

    /**
     * Default constructor with predefined header and post method.
     *
     * @param url           request destination
     * @param params        predefined custom header
     * @param listener      on success achieved 200 code from request
     * @param errorListener on error http or library timeout
     */
    public MultipartRequest(String url, Map<String, String> headers, Map<String, String> params, Map<String, DataPart>
            byteData, Type type, Response.Listener<T> listener, Response.ErrorListener errorListener, OnUploadListener onUploadListener) {
        super(Method.POST, url, headers, params, null, type, listener, errorListener, null);
        this.mByteData = byteData;
        this.mOnUploadListener = onUploadListener;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data; boundary=" + boundary;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try {
            // populate text payload
            Map<String, String> params = getParams();
            if (params != null && params.size() > 0) {
                textParse(dos, params, getParamsEncoding());
            }

            // populate data byte payload
            Map<String, DataPart> data = getByteData();
            if (data != null && data.size() > 0) {
                dataParse(dos, data);
            }

            // close multipart form data after text and file data
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Custom method handle data payload.
     *
     * @return Map data part label with data byte
     * @throws AuthFailureError
     */
    protected Map<String, DataPart> getByteData() throws AuthFailureError {
        return mByteData;
    }

    /**
     * Parse string map into data output stream by key and value.
     *
     * @param dataOutputStream data output stream handle string parsing
     * @param params           string inputs collection
     * @param encoding         encode the inputs, default UTF-8
     * @throws IOException
     */
    private void textParse(DataOutputStream dataOutputStream, Map<String, String> params, String encoding) throws
            IOException {
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                buildTextPart(dataOutputStream, entry.getKey(), entry.getValue());
            }
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + encoding, uee);
        }
    }

    /**
     * Parse data into data output stream.
     *
     * @param dataOutputStream data output stream handle file attachment
     * @param data             loop through data
     * @throws IOException
     */
    private void dataParse(DataOutputStream dataOutputStream, Map<String, DataPart> data) throws IOException {
        mProgress = 0;
        mMax = data.size() * 10;
        for (Map.Entry<String, DataPart> entry : data.entrySet()) {
            if (mOnUploadListener != null) {
                mOnUploadListener.onProgress(mProgress, mMax);
            }
            buildDataPart(dataOutputStream, entry.getValue(), entry.getKey());
        }
        if (mOnUploadListener != null) {
            mOnUploadListener.onProgress(mMax, mMax);
        }
    }

    /**
     * Write string data into header and data output stream.
     *
     * @param dataOutputStream data output stream handle string parsing
     * @param parameterName    name of input
     * @param parameterValue   value of input
     * @throws IOException
     */
    private void buildTextPart(DataOutputStream dataOutputStream, String parameterName, String parameterValue) throws
            IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + parameterName + "\"");
        dataOutputStream.writeBytes(lineEnd);
        dataOutputStream.writeBytes(lineEnd);
        dataOutputStream.write(parameterValue.getBytes());
        dataOutputStream.writeBytes(lineEnd);
    }

    /**
     * Write data file into header and data output stream.
     *
     * @param dataOutputStream data output stream handle data parsing
     * @param dataFile         data byte as DataPart from collection
     * @param fileName         name of data input
     * @throws IOException
     */
    private void buildDataPart(DataOutputStream dataOutputStream, DataPart dataFile, String fileName) throws
            IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" +
                dataFile.getName() + "\"; filename=\"" + fileName + "\"" + lineEnd);
        if (dataFile.getType() != null && !dataFile.getType().trim().isEmpty()) {
            dataOutputStream.writeBytes("Content-Type: " + dataFile.getType() + lineEnd);
        }
        dataOutputStream.writeBytes(lineEnd);

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(dataFile.getContent());
        int bytesAvailable = fileInputStream.available();
        int max = bytesAvailable;
        int progress = 0;

        int maxBufferSize = 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            progress = progress + bytesRead;
            mProgress = mProgress + progress * 10 / max;
            if (mOnUploadListener != null) {
                mOnUploadListener.onProgress(mProgress, mMax);
            }

            dataOutputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }
        dataOutputStream.writeBytes(lineEnd);
    }
}