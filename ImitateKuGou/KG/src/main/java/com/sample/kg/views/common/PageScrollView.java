package com.sample.kg.views.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by zhaoli on 2017/3/15.
 */
public class PageScrollView extends HorizontalScrollView {

    //滑动基线。也就是点击并滑动之前的x值，以此值计算相对滑动距离。
    private int baseScrollX;
    //最大滑动距离，就可以切换
    private int maxScrollX;
    private int currentPageIndex = 0;
    private IPageCallback pageCallback;

    private DisplayMetrics dm;

    private GestureDetector gestureDetector;

    public PageScrollView(Context context) {
        this(context, null);
    }

    public PageScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PageScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dm = context.getApplicationContext().getResources()
                .getDisplayMetrics();
        gestureDetector = new GestureDetector(context, new YScrollDetector());
    }

    public void setPageCallback(IPageCallback pageCallback) {
        this.pageCallback = pageCallback;
    }

    /**
     * 设置最大滑动多少开始切换
     * @param maxScrollX
     */
    public void setMaxScrollX(int maxScrollX) {
        this.maxScrollX = maxScrollX;
    }

    /**
     * 获取当前的页面index
     * @return
     */
    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    /**
     * 使相对于基线移动x距离。
     * @param x x为正值时右移；为负值时左移。
     */
    private void baseSmoothScrollTo(int x) {
        if (x > 0) {
            currentPageIndex ++;
        } else if (x < 0) {
            currentPageIndex --;
        }
        smoothScrollTo(x + baseScrollX, 0);
        if (null != pageCallback) {
            pageCallback.onPageChange(currentPageIndex);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean superIntercept = super.onInterceptTouchEvent(ev);
        boolean intercept = gestureDetector.onTouchEvent(ev);
        return superIntercept && intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX() - baseScrollX;
                if (scrollX > maxScrollX) {
                    //左滑，大于最大值，切换到下一页
                    baseSmoothScrollTo(dm.widthPixels);
                    baseScrollX += dm.widthPixels;
                } else if (scrollX > 0) {
                    //左滑，不到最大值，不切换，返回原位
                    baseSmoothScrollTo(0);
                } else if (scrollX > -maxScrollX) {
                    //右滑，不到最大值，不切换，返回原位
                    baseSmoothScrollTo(0);
                } else {
                    //右滑，大于最大值，切换到下一页
                    baseSmoothScrollTo(-dm.widthPixels);
                    baseScrollX -= dm.widthPixels;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    private class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1,
                                float distanceX, float distanceY) {
            if (Math.abs(distanceY) > Math.abs(distanceX)) {
                return false;
            }
            return true;
        }
    }

    public interface IPageCallback {
        void onPageChange(int currentIndex);
    }
}
