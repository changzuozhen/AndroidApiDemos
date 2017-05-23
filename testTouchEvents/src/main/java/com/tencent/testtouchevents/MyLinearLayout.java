package com.tencent.testtouchevents;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

import commontools.LogUtils;

/**
 * Created by AndyChang on 2017/5/23.
 */

public class MyLinearLayout extends LinearLayoutCompat {
    private static final String TAG = "MyLinearLayout";

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.d(TAG, "onTouchEvent() called with: " + "event = [" + MotionEvent.actionToString(event.getAction()) + "]");
        boolean b = super.onTouchEvent(event);
        LogUtils.v(TAG, "onTouchEvent() called returned: " + b + " with: " + "event = [" + MotionEvent.actionToString(event.getAction()) + "]");
        return b;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.d(TAG, "dispatchTouchEvent() called with: " + "ev = [" + MotionEvent.actionToString(ev.getAction()) + "]");
        boolean b = super.dispatchTouchEvent(ev);
        LogUtils.v(TAG, "dispatchTouchEvent() called returned: " + b + " with: " + "event = [" + MotionEvent.actionToString(ev.getAction()) + "]");
        return b;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        LogUtils.d(TAG, "onInterceptTouchEvent() called with: " + "event = [" + MotionEvent.actionToString(event.getAction()) + "]");
        boolean b = super.onInterceptTouchEvent(event);
        LogUtils.v(TAG, "onInterceptTouchEvent() called returned: " + b + " with: " + "event = [" + event + "]");
        return b;
//        LogUtils.d(TAG, "onInterceptTouchEvent() returned: " + true);
//        return true;
    }
}
