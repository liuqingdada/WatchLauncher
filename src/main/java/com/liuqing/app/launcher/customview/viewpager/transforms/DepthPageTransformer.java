package com.liuqing.app.launcher.customview.viewpager.transforms;

import android.support.v4.view.ViewPager;
import android.view.View;

public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final String TAG = "DepthPageTransformer";
    private static final float MIN_SCALE = 0.9f;
    public static final int ITEM_LEFT_OR_TOP = 0;
    public static final int ITEM_CENTER = 1;
    public static final int ITEM_RIGHT_OR_BOTTOM = 2;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private int mOrientation = HORIZONTAL;

    public DepthPageTransformer(int orientation) {
        mOrientation = orientation;
    }

    @Override
    public void transformPage(View view, float position) {
        if(mOrientation == HORIZONTAL) {
            int pageWidth = view.getWidth();
            if (view == null) return;
            int pageIndex = (int) view.getTag();

            if (position < -1) { // [-Infinity,-1)
                view.setVisibility(View.INVISIBLE);

            } else if (position <= 0) { // [-1,0]
                view.setVisibility(View.VISIBLE);
                if (pageIndex == ITEM_CENTER) {
                    view.setTranslationX(pageWidth * -position);

                } else if (pageIndex == ITEM_LEFT_OR_TOP) {
                    view.setTranslationX(0);
                }


            } else if (position <= 1) { // (0,1]
                view.setVisibility(View.VISIBLE);
                if (pageIndex == ITEM_CENTER) {
                    view.setTranslationX(pageWidth * -position);
                } else {
                    view.setTranslationX(0);
                }

            } else { // (1,+Infinity]
                view.setVisibility(View.INVISIBLE);
            }
        }else{
            int pageHeight = view.getHeight();
            if (view == null) return;
            int pageIndex = (int) view.getTag();

            if (position < -1) { // [-Infinity,-1)
                view.setVisibility(View.INVISIBLE);

            } else if (position <= 0) { // [-1,0]
                view.setVisibility(View.VISIBLE);
                if (pageIndex == ITEM_CENTER) {
                    view.setTranslationY(pageHeight * -position);

                } else if (pageIndex == ITEM_LEFT_OR_TOP) {
                    view.setTranslationY(0);
                }


            } else if (position <= 1) { // (0,1]
                view.setVisibility(View.VISIBLE);
                if (pageIndex == ITEM_CENTER) {
                    view.setTranslationY(pageHeight * -position);
                } else {
                    view.setTranslationY(0);
                }

            } else { // (1,+Infinity]
                view.setVisibility(View.INVISIBLE);
            }
        }
    }
}
