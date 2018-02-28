package com.lugeek.nestedscroll2demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
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
            scrollBy(0, dy);
            consumed[1] = dy;
            return;
        }
        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }


    @Override
    public void fling(int velocityY) {
        Log.i("ljm", "vel " + velocityY);
        super.fling(velocityY);
    }

}
