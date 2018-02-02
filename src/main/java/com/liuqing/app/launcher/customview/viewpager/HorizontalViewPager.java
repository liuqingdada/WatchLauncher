package com.liuqing.app.launcher.customview.viewpager;

/**
 * Created by wangxinzhi on 17-2-12.
 */


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Extend the original ViewPager to support scrolling effect.
 */
public class HorizontalViewPager extends ViewPager {
    private static final String TAG = HorizontalViewPager.class.getSimpleName();
    private boolean mSwipeEnabled;
    private static int[] DRAWOLDER = {2, 0, 1};
    private int mCurrentY;

    private int mCurrentX;

    /**
     * Create a new instance.
     *
     * @param context The application environment.
     * @param attrs   a collection of attributes.
     */
    public HorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSwipeEnabled = true;
    }
//
//    @Override
//    protected int getChildDrawingOrder(int childCount, int i) {
//        Log.d(TAG, "childCount " + childCount +" i: "+i+ " DRAWOLDER: "+DRAWOLDER[i]);
//
////        if (childCount<= DRAWOLDER.length){
//            return DRAWOLDER[i];
////        }
////        else return i;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(TAG, "onTouchEvent " + event);
        if (this.mSwipeEnabled) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
//        return super.onInterceptTouchEvent(event);
        if (this.mSwipeEnabled) {
//            int y = (int) event.getY();
//            int x = (int) event.getX();
//            if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                if (this.getCurrentItem() == 0 && mCurrentX < x) {
//                    return false;
//                }
//            }
//            mCurrentY = y;
//            mCurrentX = x;
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    /**
     * Custom method to enable or disable swipe.
     *
     * @param mSwipeEnabled true to enable swipe, false otherwise
     */
    public void setSwipeEnabled(boolean mSwipeEnabled) {
        Log.d(TAG, "setSwipeEnabled:" + mSwipeEnabled);
        this.mSwipeEnabled = mSwipeEnabled;
    }
}
