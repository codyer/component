/*
 * ************************************************************
 * 文件：ImageActivity.java  模块：image-core  项目：component
 * 当前修改时间：2019年05月25日 15:39:42
 * 上次修改时间：2019年05月25日 15:39:42
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：image-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.image.preview;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.cody.component.app.activity.EmptyBindActivity;
import com.cody.component.image.R;
import com.cody.component.image.databinding.ActivityImageBinding;
import com.lzy.imagepicker.DataHolder;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.adapter.ImagePageAdapter;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;

public class ImageActivity extends EmptyBindActivity<ActivityImageBinding> {
    protected ImagePicker mImagePicker;
    protected ArrayList<ImageItem> mImageItems;
    protected int mCurrentPosition = 0;
    protected ArrayList<ImageItem> selectedImages;
    protected ImagePageAdapter mAdapter;
    protected boolean isFromItems = false;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_image;
    }

    @Override
    protected void onBaseReady(final Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);

        mCurrentPosition = getIntent().getIntExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
        isFromItems = getIntent().getBooleanExtra(ImagePicker.EXTRA_FROM_ITEMS, false);

        if (isFromItems) {
            mImageItems = (ArrayList<ImageItem>) getIntent().getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
        } else {
            mImageItems = (ArrayList<ImageItem>) DataHolder.getInstance().retrieve(DataHolder.DH_CURRENT_IMAGE_FOLDER_ITEMS);
        }

        mImagePicker = ImagePicker.getInstance();
        selectedImages = mImagePicker.getSelectedImages();
        mAdapter = new ImagePageAdapter(this, mImageItems);
        mAdapter.setPhotoViewClickListener((view, v, v1) -> onImageSingleTap());
        getBinding().viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                setCurrentPosition();
            }
        });
        getBinding().viewpager.setAdapter(mAdapter);
        getBinding().viewpager.setCurrentItem(mCurrentPosition, false);
        setCurrentPosition();
    }

    private void setCurrentPosition() {
        getBinding().position.setText(getString(R.string.ip_preview_image_count, mCurrentPosition + 1, mImageItems.size()));
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.back) {
            finish();
        }
    }

    private void onImageSingleTap() {
        if (getBinding().position.getVisibility() == View.VISIBLE) {
            getBinding().back.setVisibility(View.GONE);
            getBinding().position.setVisibility(View.GONE);
        } else {
            getBinding().back.setVisibility(View.VISIBLE);
            getBinding().position.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ImagePicker.getInstance().restoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        ImagePicker.getInstance().saveInstanceState(outState);
    }
}
