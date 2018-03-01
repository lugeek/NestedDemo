package com.lugeek.nestedscroll2demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lugeek on 2018/2/28.
 */

public class NSWebViewContainer extends NestedScrollView2 {

    public NSWebViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (dy > 0 && this.canScrollVertically(1)) {
            //当前是上滑且还存在上滑空间，则消费掉这段滚动。
            scrollBy(0, dy);
            consumed[1] = dy;
            return;
        }
        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }

}
