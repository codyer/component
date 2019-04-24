/*
 * ************************************************************
 * 文件：BaseActivity.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月22日 00:03:14
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.cody.component.app.R;
import com.cody.component.handler.viewmodel.BaseViewModel;
import com.cody.component.handler.viewmodel.IViewModel;
import com.cody.component.handler.action.ViewAction;
import com.cody.component.handler.view.IBaseView;
import com.cody.component.util.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by xu.yi. on 2019/3/25.
 * 所有activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView, DialogInterface.OnCancelListener {
    private final static float DISTANCE = dp2px(10);
    private ProgressDialog mLoading;
    private List<String> mViewModelNames;
    private Toast mToast;
    protected String TAG = null;
    private boolean mIsMoving = false;
    private float mDownX;
    private float mDownY;

    protected abstract void onBaseReady(Bundle savedInstanceState);

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getName();
        ActivityUtil.setCurrentActivity(this);
        onBaseReady(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityUtil.setCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mIsMoving = false;
                mDownX = ev.getX();
                mDownY = ev.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float moveX = Math.abs(ev.getX() - mDownX);//X轴距离
                float moveY = Math.abs(ev.getY() - mDownY);//y轴距离
                mIsMoving = (moveX > DISTANCE || moveY > DISTANCE);
                break;
            }
            case MotionEvent.ACTION_UP: {
                View v = getCurrentFocus();
                if (!mIsMoving && isShouldHideKeyboard(v, ev)) {
                    hideKeyboard(v.getWindowToken());
                }
                break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
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
        viewModel.getActionLiveData().observe(this, this::onExecuteAction);
    }
}
