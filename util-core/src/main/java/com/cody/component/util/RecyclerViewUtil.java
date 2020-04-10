/*
 * ************************************************************
 * 文件：RecyclerViewUtil.java  模块：util-core  项目：component
 * 当前修改时间：2019年10月26日 10:58:18
 * 上次修改时间：2019年10月26日 10:58:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：util-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.util;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xu.yi. on 2019/3/29.
 * 滑动到指定位置
 */
public class RecyclerViewUtil {
    private final static int MAGIC_POSITION = 2;//正常情况下，10个item会超过两屏效果比较正常，因此魔术数字定为10

    public static boolean canScrollVertically(RecyclerView recyclerView, int direction) {
        int topRowVerticalPosition = recyclerView.getChildCount() == 0 ? 0 : recyclerView.getChildAt(0).getTop();
        return topRowVerticalPosition < 0 || recyclerView.canScrollVertically(direction);
    }

    public static void smoothReverse(RecyclerView recyclerView) {
        if (recyclerView == null || recyclerView.getChildCount() == 0) return;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            int position = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            if (position == 0) {
                smoothScrollToBottom(recyclerView);
            } else {
                smoothScrollToTop(recyclerView);
            }
        }
    }

    public static void smoothScrollToTop(RecyclerView recyclerView) {
        if (recyclerView == null || recyclerView.getChildCount() == 0) return;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            int position = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            int magicPosition = getMagicPosition((LinearLayoutManager) layoutManager);
            if (position > magicPosition) {
                recyclerView.scrollToPosition(magicPosition);
            }
        }
        recyclerView.smoothScrollToPosition(0);
    }

    public static void smoothScrollToBottom(RecyclerView recyclerView) {
        if (recyclerView == null || recyclerView.getChildCount() == 0) return;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            int position = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            int magicPosition = getMagicPosition((LinearLayoutManager) layoutManager);
            if (recyclerView.getChildCount() - magicPosition > position) {
                recyclerView.scrollToPosition(recyclerView.getChildCount() - magicPosition);
            }
        }
        recyclerView.smoothScrollToPosition(recyclerView.getChildCount() - 1);
    }

    /**
     * 结合使用如下布局管理器实现真实滚动某个位置到顶部，而不是仅仅可见
     *
     * com.gongbangbang.www.business.widget.ScrollSpeedLinearLayoutManger
     */
    public static void smoothScrollToTop(RecyclerView recyclerView, int newPosition) {
        if (recyclerView == null || recyclerView.getChildCount() == 0 || newPosition == RecyclerView.NO_POSITION) return;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            int position = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            int magicPosition = getMagicPosition((LinearLayoutManager) layoutManager);
            if (Math.abs(position - newPosition) > magicPosition) {
                if (newPosition > position) {
                    recyclerView.scrollToPosition(newPosition - magicPosition);
                } else {
                    recyclerView.scrollToPosition(newPosition + magicPosition);
                }
            }
        }
        recyclerView.smoothScrollToPosition(newPosition);
    }

    private static int getMagicPosition(LinearLayoutManager layoutManager) {
        int first = layoutManager.findFirstVisibleItemPosition();
        int last = layoutManager.findLastVisibleItemPosition();
        int onePageSize = last - first;
        return onePageSize + MAGIC_POSITION;
    }
}
