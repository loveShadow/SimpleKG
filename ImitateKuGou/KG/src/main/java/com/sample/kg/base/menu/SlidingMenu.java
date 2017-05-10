package com.sample.kg.base.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sample.kg.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by zhaoli on 2017/3/8.
 * 实现滑行菜单
 */
public class SlidingMenu extends FrameLayout {

    //菜单布局
    private ViewGroup menu;
    //主内容布局
    private ViewGroup content;

    //简化View拖拽操作的帮助类
    private ViewDragHelper viewDragHelper;

    private int height;
    private int width;
    //菜单最大偏移量，移动的距离
    private int menuMaxMove;
    private Point lastMoveContentPoint = new Point();

    private boolean supportSliding = true;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (null != attrs) {
            TypedArray t = getContext().obtainStyledAttributes(attrs,
                    R.styleable.SlidingMenu);
            supportSliding = t.getBoolean(R.styleable.SlidingMenu_supportSliding, true);
        }

        //创建拖拽View的帮助类
        if (supportSliding) {
            viewDragHelper = ViewDragHelper.create(this, new SlidingMenuCallback());
        }
    }

    public void setSupportSliding(boolean supportSliding) {
        this.supportSliding = supportSliding;
        if (supportSliding && null == viewDragHelper) {
            viewDragHelper = ViewDragHelper.create(this, new SlidingMenuCallback());
        }
    }

    /**
     * 是否支持滑动
     * @return
     */
    public boolean isSupportSliding() {
        return supportSliding && (null != viewDragHelper);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() < 2) {
            throw new IllegalStateException("使用SlidingMenu必须有两个View");
        }
        if (!(getChildAt(0) instanceof ViewGroup) ||
                !(getChildAt(1) instanceof ViewGroup)) {
            throw new IllegalStateException("子View必须是ViewGroup的子类");
        }

        menu = (ViewGroup) getChildAt(0);
        content = (ViewGroup) getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //重新计算菜单滑出宽和高
        height = getMeasuredHeight();
        width = getMeasuredWidth();
        menuMaxMove = (int) (width * 0.9);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (! isSupportSliding()) {
            return super.onInterceptTouchEvent(event);
        }
        return viewDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        //需要将触摸事件交给ViewDragHelper
        if (! isSupportSliding()) {
            return super.onInterceptHoverEvent(event);
        }

        boolean intercept = viewDragHelper.shouldInterceptTouchEvent(event);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (! isSupportSliding()) {
            return super.onTouchEvent(event);
        }
        try {
            //需要让ViewDragHelper处理触摸事件
            viewDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void openMenu() {
        if (isSupportSliding() && viewDragHelper.smoothSlideViewTo(content, menuMaxMove, 0)) {
            //主页面是否滑动到指定位置
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void closeMenu() {
        if (isSupportSliding() && viewDragHelper.smoothSlideViewTo(content, 0, 0)) {
            //主页面是否滑动到起始位置
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (isSupportSliding() &&
                viewDragHelper.continueSettling(true)) {
            //继续执行动画
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //防止再布局上添加按钮或重新requestLayout，进行重新onLayout
        super.onLayout(changed, left, top, right, bottom);

        content.layout(lastMoveContentPoint.x, lastMoveContentPoint.y,
                lastMoveContentPoint.x + content.getMeasuredWidth(),
                lastMoveContentPoint.y + content.getMeasuredHeight());
    }

    //---------ViewDragHelper.Callback-------------

    private class SlidingMenuCallback extends ViewDragHelper.Callback {
        /**
         * 拖拽View帮助类的回调
         *
         * @param child
         * @param pointerId
         * @return
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //返回true 表示允许尝试捕获子View，允许被拖拽
            return true;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            //返回拖拽的距离，并不对拖拽进行限制，决定了动画的执行速度
            return menuMaxMove;
        }

        /**
         * 横向拖动，返回该child现在的位置
         *
         * @param child
         * @param left  即将移动到的位置
         * @param dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == content) {
                //移动的是内容部分
                left = fixContentSlidingRange(left);
            } else if (child == menu) {
                left = fixMenuSlidingRange(left);
            }
            return left;
        }

        /**
         * 当child的位置变动时
         *
         * @param changedView
         * @param left
         * @param top
         * @param dx
         * @param dy
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (! menu.isShown()) {
                menu.setVisibility(VISIBLE);
            }
            int moveContentLeft = left;
            if (changedView == menu) {
                moveContentLeft = content.getLeft() + left;
                menu.layout(0, 0, menu.getWidth(), menu.getHeight());
            }
            moveContentLeft = fixContentSlidingRange(moveContentLeft);
            content.layout(moveContentLeft, 0,
                    moveContentLeft + content.getWidth(), content.getHeight());
            animShow(moveContentLeft);
            lastMoveContentPoint.x = content.getLeft();
            lastMoveContentPoint.y = content.getTop();
            invalidate();
        }

        /**
         * 当View松手时触发，处理自动平滑动画
         *
         * @param releasedChild
         * @param xvel          离开屏幕时，X轴的速度
         * @param yvel          离开屏幕时，Y轴的速度
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (xvel > 0) {
                openMenu();
            } else if (xvel == 0) {
                //手指在滑到菜单一半时抬起
                if (content.getLeft() > menuMaxMove / 2.0f) {
                    openMenu();
                } else {
                    closeMenu();
                }
            } else {
                closeMenu();
            }
        }

        /**
         * 修改主页面的滑动距离
         *
         * @param left
         * @return
         */
        private int fixContentSlidingRange(int left) {
            if (left > menuMaxMove) {
                //最多滑动到界面宽度的0.8
                return menuMaxMove;
            } else if (left < 0) {
                //最小拖动到0
                return 0;
            }
            return left;
        }

        /**
         * 修改菜单的滑动距离
         *
         * @param left
         * @return
         */
        private int fixMenuSlidingRange(int left) {
            return left;
        }

        /**
         * 伴随动画
         */
        private void animShow(int moveContentLeft) {
            float percent = moveContentLeft * 1.0f / menuMaxMove;// 0~1

            /**
             * 分析：
             *  菜单区域：位移动画，缩放动画
             *  内容区域：缩放动画
             */
            ViewHelper.setTranslationX(menu, evaluate(percent, -menuMaxMove / 1.2f, 0));// 位移动画
            ViewHelper.setScaleX(menu, evaluate(percent, 0.7f, 1.0f));// 缩放动画
            ViewHelper.setScaleY(menu, evaluate(percent, 0.7f, 1.0f));
            ViewHelper.setAlpha(menu, evaluate(percent, 0.1f, 1.0f));// 渐变动画

            ViewHelper.setPivotX(content, 0);// 缩放中心
            ViewHelper.setPivotY(content, height / 2);
            ViewHelper.setScaleX(content, evaluate(percent, 1.0f, 0.7f));// 缩放动画
            ViewHelper.setScaleY(content, evaluate(percent, 1.0f, 0.7f));
        }

        /**
         * 估值器，根据开始数字和结束数字，随着百分比的变化得到一个数值，详情见FloatEvaluator
         */
        private Float evaluate(float fraction, Number startValue, Number endValue) {
            float startFloat = startValue.floatValue();
            return startFloat + fraction * (endValue.floatValue() - startFloat);
        }

    }

    //---------ViewDragHelper.Callback--end--------
}
