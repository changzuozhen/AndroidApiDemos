package com.tencent.testtouchevents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import commontools.LogUtils;

/**
 * Created by AndyChang on 2017/5/23.
 */

@SuppressLint("AppCompatCustomView")
public class MyButton extends Button {
    private static final String TAG = "MyButton";

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
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
}
