/*
 * ************************************************************
 * 文件：UploadBase64ImageRequest.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月05日 23:43:04
 * 上次修改时间：2019年04月05日 23:11:29
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.http.request;

import android.graphics.Bitmap;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.cody.http.core.http.request.BaseRequest;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cody.yi on 2016/7/22.
 * 上传base64格式的图片
 */
public class UploadBase64ImageRequest<T> extends BaseRequest<T> {

    private final static String KEY_IMAGE = "image";
    private final static String KEY_NAME = "name";

    private String mImageName;
    private Bitmap mBitmap;

    public UploadBase64ImageRequest(String url,
                                    String imageName, Bitmap bitmap,
                                    Map<String, String> headers,
                                    Map<String, String> params, Type type,
                                    Response.Listener<T> listener,
                                    Response.ErrorListener errorListener) {
        super(Method.POST, url, headers, params, null, type, listener, errorListener, null);
        mImageName = imageName;
        mBitmap = bitmap;
    }

    /**
     * convert Bitmap to base64 String.
     *
     * @param bmp bitmap
     * @return base64
     */
    public static String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        //Converting Bitmap to String
        String image = getStringImage(mBitmap);

        //getting parameters
        Map<String, String> params = super.getParams();

        if (params == null) params = new HashMap<>();
        //Adding parameters
        params.put(KEY_IMAGE, image);
        params.put(KEY_NAME, mImageName);

        //returning parameters
        return params;
    }
}
