package com.lanou.mirror.tools;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class VerticalPager extends ViewPager {
    private float startX, startY, moveX, moveY;

    public VerticalPager(Context context) {
        super(context);
        init();
    }

    public VerticalPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private class VerticalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            if (position < -1) {
                page.setAlpha(0);
            } else if (position <= 1) {
                page.setAlpha(1);

                page.setTranslationX(page.getWidth() * -position);

                float yPosition = position * page.getHeight();
                page.setTranslationY(yPosition);

            } else {
                page.setAlpha(0);
            }
        }
    }

    private MotionEvent swapXY(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();

        float newX = (event.getY() / height) * width;
        float newY = (event.getX() / width) * height;

        event.setLocation(newX, newY);

        return event;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(event));
        swapXY(event);
        //防止冲突
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();
                moveY = event.getY();
                if (Math.abs(moveY - startY) - Math.abs(moveX - startX) > 0) {
                    return true;
                }
                break;
        }
        return intercepted;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(swapXY(event));

    }


}

