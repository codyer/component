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

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.cody.component.app.R;
import com.cody.component.app.widget.LoadingDialog;
import com.cody.component.app.widget.swipebacklayout.BGASwipeBackHelper;
import com.cody.component.handler.define.ViewAction;
import com.cody.component.handler.view.IBaseView;
import com.cody.component.handler.viewmodel.BaseViewModel;
import com.cody.component.handler.viewmodel.IViewModel;
import com.cody.component.util.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu.yi. on 2019/3/25.
 * 所有activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView,
        DialogInterface.OnCancelListener,
        BGASwipeBackHelper.Delegate {
    private final static float DISTANCE = dp2px(10);
    private LoadingDialog mLoading;
    private List<String> mViewModelNames;
    private Toast mToast;
    protected String TAG = null;
    private boolean mIsMoving = false;
    private float mDownX;
    private float mDownY;
    private BGASwipeBackHelper mSwipeBackHelper;

    protected abstract void onBaseReady(Bundle savedInstanceState);

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        if (mSwipeBackHelper != null) {
            mSwipeBackHelper.swipeBackward();
        }
    }

    @Override
    public void onBackPressed() {
        if (mSwipeBackHelper != null) {
            // 正在滑动返回的时候取消返回按钮事件
            if (mSwipeBackHelper.isSliding()) {
                return;
            }
            mSwipeBackHelper.backward();
        }
    }

    /**
     * 设置能否左滑关闭页面
     *
     * @param enable
     */
    public void setEnableBackLayout(boolean enable) {
        if (mSwipeBackHelper != null) {
            mSwipeBackHelper.setSwipeBackEnable(enable);
        }
    }
    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.swipeback_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        initSwipeBackFinish();
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
            mLoading = new LoadingDialog(this);
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

    @SuppressLint("ShowToast")
    @Override
    public void showToast(int message) {
        if (message > 0) {
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
                if (action.getData() instanceof Integer) {
                    showToast((Integer) action.getData());
                } else {
                    showToast(action.getMessage());
                }
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
