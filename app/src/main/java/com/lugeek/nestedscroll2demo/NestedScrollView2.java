package com.lugeek.nestedscroll2demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

import static android.support.v4.view.ViewCompat.TYPE_NON_TOUCH;

/**
 * {@link NestedScrollView} 没有实现 {@link NestedScrollingParent2} 这个接口.
 * NestedScrollView2 实现 NestedScrollingParent2，并且设置type为ViewCompat.TYPE_TOUCH。
 * 解决fling阻断的问题。
 */
public class NestedScrollView2 extends NestedScrollView implements NestedScrollingParent2 {
    private final NestedScrollingParentHelper parentHelper;

    public NestedScrollView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        parentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    public boolean onStartNestedScroll(
            @NonNull View child, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(
            @NonNull View child, @NonNull View target, int axes, int type) {
        parentHelper.onNestedScrollAccepted(child, target, axes);
        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, type);
    }

    @Override
    public void onNestedPreScroll(
            @NonNull View target, int dx, int dy, @Nullable int[] consumed, int type) {
        dispatchNestedPreScroll(dx, dy, consumed, null, type);
    }

    @Override
    public void onNestedScroll(
            @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

        //如果已经滑到顶或底，则停止子view继续滚动。
        if (type == TYPE_NON_TOUCH && ((dyUnconsumed > 0 && !canScrollVertically(1)) || (dyUnconsumed < 0 && !canScrollVertically(-1)))) {
            if (target instanceof NestedScrollingChild2) {
                ((NestedScrollingChild2) target).stopNestedScroll(type);
                return;
            }
        }

        final int oldScrollY = getScrollY();
        scrollBy(0, dyUnconsumed);
        final int myConsumed = getScrollY() - oldScrollY;
        final int myUnconsumed = dyUnconsumed - myConsumed;
        dispatchNestedScroll(0, myConsumed, 0, myUnconsumed, null, type);

    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        parentHelper.onStopNestedScroll(target, type);
        stopNestedScroll(type);
    }

    @Override
    public boolean onStartNestedScroll(
            @NonNull View child, @NonNull View target, int axes) {
        return onStartNestedScroll(child, target, axes, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedScrollAccepted(
            @NonNull View child, @NonNull View target, int axes) {
        onNestedScrollAccepted(child, target, axes, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedPreScroll(
            @NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        onNestedPreScroll(target, dx, dy, consumed, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedScroll(
            @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {
        onStopNestedScroll(target, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public int getNestedScrollAxes() {
        return parentHelper.getNestedScrollAxes();
    }
}
