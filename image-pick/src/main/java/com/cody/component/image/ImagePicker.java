/*
 * ************************************************************
 * 文件：ImagePicker.java  模块：image-pick  项目：component
 * 当前修改时间：2019年04月13日 08:43:55
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：image-pick
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.image;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.view.CropImageView;

/**
 * Created by xu.yi. on 2019/4/11.
 * component
 */
public class ImagePicker {

    /**
     * 初始化imagePicker
     */
    public static void init() {
        com.lzy.imagepicker.ImagePicker imagePicker = com.lzy.imagepicker.ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(9);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(800);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(800);                         //保存文件的高度。单位像素
    }

    private final static class GlideImageLoader implements ImageLoader {

        private static final long serialVersionUID = 2085013190629145299L;

        @Override
        public void displayImage(Activity activity, String path, ImageView imageView, int width,
                                 int height) {
            if (TextUtils.isEmpty(path)) return;
            RequestOptions options = new RequestOptions()
//                    .centerCrop()
//                    .transform(new CircleCrop())
//                    .placeholder(placeholder)
//                    .error(error)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);
            if (!TextUtils.isEmpty(path) && !path.startsWith("http")) {
                Glide.with(activity).load(path).apply(options).into(imageView);
                return;
            }
            Glide.with(activity)
                    .load(path)
                    .apply(options)
                    .into(imageView);
        }

        @Override
        public void displayImagePreview(final Activity activity, final String path, final ImageView imageView, final int width, final int height) {
            if (TextUtils.isEmpty(path)) return;
            RequestOptions options = new RequestOptions()
//                    .centerCrop()
//                    .transform(new CircleCrop())
//                    .placeholder(placeholder)
//                    .error(error)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);
            if (!TextUtils.isEmpty(path) && !path.startsWith("http")) {
                Glide.with(activity).load(path).apply(options).into(imageView);
                return;
            }
            Glide.with(activity)
                    .load(path)
                    .apply(options)
                    .into(imageView);
        }

        @Override
        public void clearMemoryCache() {
        }
    }
}
