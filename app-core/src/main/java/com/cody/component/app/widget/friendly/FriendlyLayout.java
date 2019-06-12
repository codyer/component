/*
 * ************************************************************
 * 文件：FriendlyLayout.java  模块：app-core  项目：component
 * 当前修改时间：2019年06月11日 14:10:14
 * 上次修改时间：2019年06月11日 14:10:09
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.widget.friendly;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cody.component.app.R;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.define.Status;

/**
 * Created by xu.yi. on 2019-06-11.
 * component 包含
 * 初始化页面 RUNNING init
 * 出错页面 FAILED
 * 空页面 EMPTY
 * 正常页面xml中嵌入
 *
 * @see com.cody.component.handler.define.Status
 * RUNNING,
 * SUCCESS,
 * FAILED,
 * EMPTY,
 * CANCEL,
 * END
 */
public class FriendlyLayout extends FrameLayout {
    private Context mContext;
    private IFriendly mIFriendly;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RequestStatus mRequestStatus = new RequestStatus();
    private Status mStatus;

    private View mInitView;
    private View mFailedView;
    private View mEmptyView;

    public IFriendly getIFriendly() {
        if (mIFriendly == null){
            mIFriendly = new IFriendly() {
                @Override
                public int initLayoutId() {
                    return R.layout.mask_view;
                }

                @Override
                public int emptyLayoutId() {
                    return R.layout.mask_view;
                }

                @Override
                public int failedLayoutId() {
                    return R.layout.mask_view;
                }
            };
        }
        return mIFriendly;
    }

    public void setIFriendly(final IFriendly IFriendly) {
        mIFriendly = IFriendly;
    }

    public void setRequestStatus(final RequestStatus requestStatus) {
        mRequestStatus = requestStatus;
    }

    public void setStatus(final Status status) {
        mStatus = status;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public FriendlyLayout(@NonNull final Context context) {
        super(context);
        initView(context);
    }

    public FriendlyLayout(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FriendlyLayout(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FriendlyLayout(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        /*View view = LayoutInflater.from(context).inflate(R.layout.xf_pull_loadmore_layout, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayoutOnRefresh(this));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setVerticalScrollBarEnabled(true);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerViewOnScroll(this));

        mRecyclerView.setOnTouchListener(new onTouchRecyclerView());

        mFooterView = view.findViewById(R.id.footerView);
        mEmptyViewContainer = (FrameLayout) view.findViewById(R.id.emptyView);
        mDefaultViewContainer = (FrameLayout) view.findViewById(R.id.defaultView);

        loadMoreLayout = (LinearLayout) view.findViewById(R.id.loadMoreLayout);
        loadMoreText = (TextView) view.findViewById(R.id.loadMoreText);

        mFooterView.setVisibility(View.GONE);
        mEmptyViewContainer.setVisibility(View.GONE);
        mDefaultViewContainer.setVisibility(View.GONE);

        this.addView(view);*/
    }
}
