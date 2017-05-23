package com.tencent.testtouchevents;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import butterknife.ButterKnife;
import commontools.LogUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
