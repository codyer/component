/*
 * ************************************************************
 * 文件：BaseFragment.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 20:42:10
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.cody.component.app.R;
import com.cody.component.handler.viewmodel.BaseViewModel;
import com.cody.component.handler.view.IBaseView;
import com.cody.component.handler.viewmodel.IViewModel;
import com.cody.component.handler.define.ViewAction;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by xu.yi. on 2019/3/25.
 * 所有fragment的基类
 */
public abstract class BaseFragment extends Fragment implements IBaseView, DialogInterface.OnCancelListener {
    private ProgressDialog mLoading;
    private List<String> mViewModelNames;
    private Toast mToast;
    protected String TAG = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
    }

    @Override
    public void onDestroy() {
        hideLoading();
        super.onDestroy();
    }

    @Override
    public void onCancel(final DialogInterface dialog) {
        hideLoading();
    }

    @Override
    public <VM extends BaseViewModel> VM getViewModel(@NonNull Class<VM> viewModelClass, @Nullable ViewModelProvider.Factory factory) {
        String canonicalName = viewModelClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        VM viewModel = ViewModelProviders.of(this, factory).get(viewModelClass);
        viewModel.setLifecycleOwner(this);
        checkViewModel(canonicalName, viewModel);
        return viewModel;
    }

    @Override
    public void showLoading() {
        showLoading(null);
    }

    @Override
    public void showLoading(String message) {
        if (!isAdded()) return;
        if (mLoading == null && getActivity() != null) {
            mLoading = new ProgressDialog(getActivity());
            mLoading.setCanceledOnTouchOutside(false);
            mLoading.setCancelable(false);
        }
        mLoading.setTitle(TextUtils.isEmpty(message) ? getString(R.string.ui_str_loading) : message);
        mLoading.show();
    }

    @Override
    public void hideLoading() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
    }

    @SuppressLint("ShowToast")
    @Override
    public void showToast(String message) {
        if (!isAdded()) return;
        if (!TextUtils.isEmpty(message) && getActivity() != null) {
            if (mToast == null) {
                mToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                mToast.setText(message);
            }
            mToast.show();
        }
    }

    @Override
    public void finish() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public void finishWithResultOk() {
        if (getActivity() != null) {
            getActivity().setResult(Activity.RESULT_OK);
        }
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
        viewModel.getActionLiveData().observe(this, action -> onExecuteAction(action));
    }
}
