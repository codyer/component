/*
 * ************************************************************
 * 文件：DataBindingAdapterDefines.java  模块：bind-adapter  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月22日 20:07:37
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-adapter
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bind.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by xu.yi. on 2019/4/4.
 * component 定义项目的data binding全局adapter
 * 类似图片加载，动态设定View的宽度高度等对data binding的扩宽支持
 */
public class DataBindingAdapterDefines {
    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter({"gifSrc"})
    public static void setGifSrc(ImageView view, Drawable gif) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);
        if (view.getScaleType() == ImageView.ScaleType.FIT_CENTER) {
            options = options.fitCenter();
        } else {
            options = options.centerCrop();
        }
        Glide.with(view.getContext()).asGif().load(gif).apply(options).into(view);
    }

    @BindingAdapter({"gifSrc"})
    public static void setGifSrc(ImageView view, int gif) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);
        if (view.getScaleType() == ImageView.ScaleType.FIT_CENTER) {
            options = options.fitCenter();
        } else {
            options = options.centerCrop();
        }
        Glide.with(view.getContext()).asGif().load(gif).apply(options).into(view);
    }

    @BindingAdapter(value = {"roundImageUrl", "error", "placeholder"}, requireAll = false)
    public static void setRoundImageUrl(ImageView view, String roundImageUrl, Drawable error, Drawable placeholder) {
        Context context = view.getContext();
        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)
                .error(error)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        if (!TextUtils.isEmpty(roundImageUrl) && !roundImageUrl.startsWith("http")) {
            Glide.with(context).load(roundImageUrl).apply(options).into(view);
            return;
        }
        Glide.with(context)
                .load(roundImageUrl)
                .apply(options)
                .into(view);
    }

    @BindingAdapter(value = {"rectImageUrl", "error", "placeholder"}, requireAll = false)
    public static void setRectImageUrl(ImageView view, String rectImageUrl, Drawable error, Drawable placeholder) {
        Context context = view.getContext();
        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)
                .error(error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        if (view.getScaleType() == ImageView.ScaleType.FIT_CENTER) {
            options = options.fitCenter();
        } else {
            options = options.centerCrop();
        }
        if (!TextUtils.isEmpty(rectImageUrl) && !rectImageUrl.startsWith("http")) {
            Glide.with(context).load(rectImageUrl).apply(options).into(view);
            return;
        }
        Glide.with(context)
                .load(rectImageUrl)
                .apply(options)
                .into(view);
    }

    @BindingAdapter("android:layout_width")
    public static void setLayoutWidth(View view, float width) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = (int) width;
        view.setLayoutParams(params);
    }

    @BindingAdapter("android:layout_height")
    public static void setLayoutHeight(View view, float height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = (int) height;
        view.setLayoutParams(params);
    }

    @BindingAdapter({"strike"})
    public static void setStrike(TextView view, boolean strike) {
        if (strike) {
            view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            view.setPaintFlags(view.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }
}
