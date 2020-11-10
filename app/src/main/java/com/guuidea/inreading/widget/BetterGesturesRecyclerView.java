package com.guuidea.inreading.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author 江 杰
 * @file BetterGesturesRecyclerView
 * @description TODO
 * @createDate 2020/11/10 10:42
 */
public class BetterGesturesRecyclerView extends RecyclerView {

    private int mScrollPointerId;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mTouchSlop;

    public BetterGesturesRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public BetterGesturesRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BetterGesturesRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        ViewConfiguration vc = ViewConfiguration.get(getContext());
        this.mTouchSlop = vc.getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean canScrollHorizontally = getLayoutManager().canScrollHorizontally();
        boolean canScrollVertically = getLayoutManager().canScrollVertically();
        int action = e.getActionMasked();
        int actionIndex = e.getActionIndex();
        switch (action) {
            case 0:
                mScrollPointerId = e.getPointerId(0);
                this.mInitialTouchX = (int) (e.getX() + 0.5F);
                this.mInitialTouchY = (int) (e.getY() + 0.5F);
                return super.onInterceptTouchEvent(e);
            case 2:
                int index = e.findPointerIndex(this.mScrollPointerId);
                if (index < 0) {
                    return false;
                }
                int x = (int) (e.getX(index) + 0.5F);
                int y = (int) (e.getY(index) + 0.5F);
                if (getScrollState() != 1) {
                    int dx = x - this.mInitialTouchX;
                    int dy = y - this.mInitialTouchY;
                    boolean startScroll = false;
                    if (canScrollHorizontally && Math.abs(dx) > this.mTouchSlop && Math.abs(dx) > Math.abs(dy)) {
                        startScroll = true;
                    }

                    if (canScrollVertically && Math.abs(dy) > this.mTouchSlop && Math.abs(dy) > Math.abs(dx)) {
                        startScroll = true;
                    }
                    return startScroll && super.onInterceptTouchEvent(e);
                }
                return super.onInterceptTouchEvent(e);
            case 5:
                this.mScrollPointerId = e.getPointerId(actionIndex);
                this.mInitialTouchX = (int) (e.getX(actionIndex) + 0.5F);
                this.mInitialTouchY = (int) (e.getY(actionIndex) + 0.5F);
                return super.onInterceptTouchEvent(e);
            default:
                break;
        }
        return super.onInterceptTouchEvent(e);

    }
}
