/*
 * ************************************************************
 * 文件：BaseActivity.java  模块：bind-ui  项目：component
 * 当前修改时间：2019年04月05日 17:32:51
 * 上次修改时间：2019年03月30日 11:03:07
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.cody.component.app.R;
import com.cody.component.handler.IView;
import com.cody.component.handler.BaseViewModel;
import com.cody.component.handler.IViewModel;
import com.cody.component.handler.ViewAction;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by xu.yi. on 2019/3/25.
 * 所有activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity implements IView {
    private ProgressDialog mLoading;
    private List<String> mViewModelNames;
    private Toast mToast;
    protected String TAG = null;

    protected abstract void onBaseReady(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getName();
        onBaseReady(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        super.onDestroy();
    }

    @Override
    public <T extends BaseViewModel> T getViewModel(@NonNull Class<T> viewModelClass) {
        String canonicalName = viewModelClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        T viewModel = ViewModelProviders.of(this).get(viewModelClass);
        checkViewModel(canonicalName, viewModel);
        return viewModel;
    }

    @Override
    public void showLoading() {
        showLoading(null);
    }

    @Override
    public void showLoading(String message) {
        if (mLoading == null) {
            mLoading = new ProgressDialog(this);
            mLoading.setCanceledOnTouchOutside(false);
            mLoading.setCancelable(false);
        }
        mLoading.setTitle(TextUtils.isEmpty(message) ? getString(R.string.app_loading) : message);
        mLoading.show();
    }

    @Override
    public void hideLoading() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
    }

    @Override
    public void showToast(String message) {
        if (!TextUtils.isEmpty(message)) {
            if (mToast == null) {
                mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                mToast.setText(message);
            }
            mToast.show();
        }
    }

    @Override
    public void finishWithResultOk() {
        setResult(RESULT_OK);
        finish();
    }

    @CallSuper
    protected void onExecuteAction(ViewAction action) {
        if (action == null) return;
        switch (action.getAction()) {
            case ViewAction.SHOW_LOADING:
                showLoading(action.getMessage());
                break;
            case ViewAction.HIDE_LOADING:
                hideLoading();
                break;
            case ViewAction.SHOW_TOAST:
                showToast(action.getMessage());
                break;
            case ViewAction.FINISH:
                finish();
                break;
            case ViewAction.FINISH_WITH_RESULT_OK:
                finishWithResultOk();
                break;
            default://ViewAction.DEFAULT:
                break;
        }
    }

    /**
     * 检查是否添加监听
     */
    private void checkViewModel(String canonicalName, IViewModel viewModel) {
        if (mViewModelNames == null) {
            mViewModelNames = new ArrayList<>();
        }
        if (!mViewModelNames.contains(canonicalName)) {
            observeAction(viewModel);
            mViewModelNames.add(canonicalName);
        }
    }

    /**
     * 监听viewModel的变化
     */
    private void observeAction(IViewModel viewModel) {
        if (viewModel == null) return;
        viewModel.getActionLiveData().observe(this, new Observer<ViewAction>() {
            @Override
            public void onChanged(@Nullable ViewAction action) {
                onExecuteAction(action);
            }
        });
    }
}
