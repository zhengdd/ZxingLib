package com.dongdong.zxingscan;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;


/**
 * Created by dongdongzheng on 2021/2/19.
 */

public class ZoomListenerView extends View implements ScaleGestureDetector.OnScaleGestureListener {

    private ScaleGestureDetector mScaleGestureDetector = null;
    private ScaleListener scaleListener;


    public ZoomListenerView(Context context) {
        super(context);
        initView();
    }

    public ZoomListenerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ZoomListenerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public ZoomListenerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        mScaleGestureDetector = new ScaleGestureDetector(this.getContext(), this);
    }

    public void setScaleListener(ScaleListener scaleListener) {
        this.scaleListener = scaleListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mScaleGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();
        if (scaleListener != null) {
            scaleListener.onScaleFactor(scaleFactor);
        }
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    public static interface ScaleListener {

        void onScaleFactor(float factor);

    }


}
