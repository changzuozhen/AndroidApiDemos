package com.example.android.apis.AndyTest;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.android.apis.R;
import com.tencent.commontools.LogUtils;

public class AndyTestTouchEventActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "AndyTestTouchEventActivity";
    String bt1Str =
            "prepare";
    String bt2Str =
            "start";
    String bt3Str =
            "stop";
    String bt4Str =
            "reset";
    String bt5Str =
            "seek";
    Button btn1, btn2, btn3, btn4, btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andy_test);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);

        btn1.setText(bt1Str);
        btn2.setText(bt2Str);
        btn3.setText(bt3Str);
        btn4.setText(bt4Str);
        btn5.setText(bt5Str);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void testKeyFrame() {

        /**
         * 左右震动效果
         */
        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.1f, -20f);
        Keyframe frame2 = Keyframe.ofFloat(0.2f, 20f);
        Keyframe frame3 = Keyframe.ofFloat(0.3f, -20f);
        Keyframe frame4 = Keyframe.ofFloat(0.4f, 20f);
        Keyframe frame5 = Keyframe.ofFloat(0.5f, -20f);
        Keyframe frame6 = Keyframe.ofFloat(0.6f, 20f);
        Keyframe frame7 = Keyframe.ofFloat(0.7f, -20f);
        Keyframe frame8 = Keyframe.ofFloat(0.8f, 20f);
        Keyframe frame9 = Keyframe.ofFloat(0.9f, -20f);
        Keyframe frame10 = Keyframe.ofFloat(1, 0);
        PropertyValuesHolder frameHolder1 = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8, frame9, frame10);


/**
 * scaleX放大1.1倍
 */
        Keyframe scaleXframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleXframe1 = Keyframe.ofFloat(0.1f, 1.1f);
        Keyframe scaleXframe2 = Keyframe.ofFloat(0.2f, 1.1f);
        Keyframe scaleXframe3 = Keyframe.ofFloat(0.3f, 1.1f);
        Keyframe scaleXframe4 = Keyframe.ofFloat(0.4f, 1.1f);
        Keyframe scaleXframe5 = Keyframe.ofFloat(0.5f, 1.1f);
        Keyframe scaleXframe6 = Keyframe.ofFloat(0.6f, 1.1f);
        Keyframe scaleXframe7 = Keyframe.ofFloat(0.7f, 1.1f);
        Keyframe scaleXframe8 = Keyframe.ofFloat(0.8f, 1.1f);
        Keyframe scaleXframe9 = Keyframe.ofFloat(0.9f, 1.1f);
        Keyframe scaleXframe10 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder frameHolder2 = PropertyValuesHolder.ofKeyframe("ScaleX", scaleXframe0, scaleXframe1, scaleXframe2, scaleXframe3, scaleXframe4, scaleXframe5, scaleXframe6, scaleXframe7, scaleXframe8, scaleXframe9, scaleXframe10);


/**
 * scaleY放大1.1倍
 */
        Keyframe scaleYframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleYframe1 = Keyframe.ofFloat(0.1f, 1.1f);
        Keyframe scaleYframe9 = Keyframe.ofFloat(0.9f, 1.1f);
        Keyframe scaleYframe10 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder frameHolder3 = PropertyValuesHolder.ofKeyframe("ScaleY", scaleYframe0, scaleYframe1, scaleYframe9, scaleYframe10);

/**
 * 构建动画
 */
        Animator animator = ObjectAnimator.ofPropertyValuesHolder(btn1, frameHolder1, frameHolder2, frameHolder3);
        animator.setDuration(1000);
        animator.start();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void prepare() {
//        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animator_set2);
//        animatorSet.setTarget(btn1);
//        animatorSet.start();

        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animator_set2);
        animatorSet.setTarget(btn1);
        animatorSet.start();


//        PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("Rotation", 60f, -60f, 40f, -40f, -20f, 20f, 10f, -10f, 0f);
//        PropertyValuesHolder colorHolder = PropertyValuesHolder.ofInt("BackgroundColor", 0xffffffff, 0xffff0000, 0xff00ff00, 0xff0000ff, 0xffffffff);
//        colorHolder.setEvaluator(new ArgbEvaluator());
//        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(btn1, rotationHolder, colorHolder);
//        animator.setDuration(3000);
//        animator.setInterpolator(new AccelerateInterpolator());
//        animator.start();


        int value = 0;
        if (btn1.getTag() != null) {
            value = (int) btn1.getTag();
        }
        switch (value) {
            case 0:
                btn1.setTag(1);
                break;
            case 1:
                btn1.setTag(0);
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void start() {
        int value = 0;
        if (btn2.getTag() != null) {
            value = (int) btn2.getTag();
        }
        switch (value) {
            case 0:
                btn2.setTag(1);
                break;
            case 1:
                btn2.setTag(0);
                break;
        }
    }

    private void stop() {
    }

    private void reset() {
    }

    private void seek() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn1: {
                LogUtils.d(TAG, "onClick() prepare");
                prepare();
            }
            break;
            case R.id.btn2: {
                LogUtils.d(TAG, "onClick() start();");
                start();
            }
            break;
            case R.id.btn3: {
                LogUtils.d(TAG, "onClick() stop");
                stop();
            }
            break;
            case R.id.btn4: {
                LogUtils.d(TAG, "onClick() reset();");
                reset();
            }
            break;
            case R.id.btn5: {
                LogUtils.d(TAG, "onClick() seek();");
                seek();
            }
            break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        printMotionEvent(event);
        return super.onTouchEvent(event);
    }

    void printMotionEvent(MotionEvent ev) {
        final int historySize = ev.getHistorySize();
        final int pointerCount = ev.getPointerCount();

        StringBuilder stringBuilder = new StringBuilder(ev.toString() + '\n');
        for (int h = 0; h < historySize; h++) {
            stringBuilder.append("At time " + ev.getHistoricalEventTime(h) + ":");
            for (int p = 0; p < pointerCount; p++) {
                stringBuilder.append("  pointer " + ev.getPointerId(p) + ": (" + ev.getHistoricalX(p, h) + "," + ev.getHistoricalY(p, h) + ")");
            }
        }
        stringBuilder.append("At time " + ev.getEventTime() + ":");
        for (int p = 0; p < pointerCount; p++) {
            stringBuilder.append("  pointer " + ev.getPointerId(p) + ": (" + ev.getX(p) + "," + ev.getY(p) + ")");
        }
        LogUtils.d(TAG, "printMotionEvent: " + stringBuilder.toString());
    }
}
