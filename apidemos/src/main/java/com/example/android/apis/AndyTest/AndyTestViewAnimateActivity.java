package com.example.android.apis.AndyTest;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.apis.R;
import com.tencent.commontools.LogUtils;

public class AndyTestViewAnimateActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "AndyTestViewAnimateActivity";
    String bt1Str =
            "prepare";
    String bt2Str =
            "start";
    String bt3Str =
            "propertyValuesHolder";
    String bt4Str =
            "animatorSet";
    String bt5Str =
            "keyframe";
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

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void prepare() {

        int value = 0;
        if (btn1.getTag() != null) {
            value = (int) btn1.getTag();
        }
        switch (value) {
            case 0:
                btn1.setTag(1);
                btn1.animate().rotation(5).start();
                break;
            case 1:
                btn1.setTag(0);
                btn1.animate().rotation(0).start();
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
                break;
            case 1:
                btn2.setTag(1);
                btn2.animate().x(5).y(300).start();
                break;
            case 2:
                btn2.setTag(0);
                btn2.animate().x(0).y(btn2.getHeight()).start();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void propertyValuesHolder() {
        // 一个 ObjectAnimator 结合多个 propertyValuesHolder 实现
        int value = 0;
        if (btn3.getTag() != null) {
            value = (int) btn3.getTag();
        }
        PropertyValuesHolder pvhX;
        PropertyValuesHolder pvhY;
        switch (value) {
            case 0:
                btn3.setTag(1);
                pvhX = PropertyValuesHolder.ofFloat("x", 50f);
                pvhY = PropertyValuesHolder.ofFloat("y", 300f);
                ObjectAnimator.ofPropertyValuesHolder(btn3, pvhX, pvhY).start();

                break;
            case 1:
                btn3.setTag(0);
                pvhX = PropertyValuesHolder.ofFloat("x", 0f);
                pvhY = PropertyValuesHolder.ofFloat("y", btn4.getHeight() * 2);
                ObjectAnimator.ofPropertyValuesHolder(btn3, pvhX, pvhY).start();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void animatorSet() {
        // 多个 ObjectAnimator 结合 animatorSet 实现
        int value = 0;
        if (btn4.getTag() != null) {
            value = (int) btn4.getTag();
        }
        ObjectAnimator animX;
        ObjectAnimator animY;
        AnimatorSet animSetXY = new AnimatorSet();
        switch (value) {
            case 0:
                btn4.setTag(1);
                animX = ObjectAnimator.ofFloat(btn4, "x", 50f);
                animY = ObjectAnimator.ofFloat(btn4, "y", 300f);
                animSetXY.playTogether(animX, animY);
                animSetXY.start();
                break;
            case 1:
                btn4.setTag(0);
                animX = ObjectAnimator.ofFloat(btn4, "x", 0f);
                animY = ObjectAnimator.ofFloat(btn4, "y", btn4.getHeight() * 3);
                animSetXY = new AnimatorSet();
                animSetXY.playTogether(animX, animY);
                animSetXY.start();
                break;
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void keyframe() {

        // 多个 ObjectAnimator 结合 animatorSet 实现
        int value = 0;
        if (btn5.getTag() != null) {
            value = (int) btn5.getTag();
        }
        Keyframe kf0;
        Keyframe kf1;
        Keyframe kf2;
        PropertyValuesHolder pvhRotation;//动画属性名，可变参数
        ObjectAnimator rotationAnim;
        switch (value) {
            case 0:
                btn5.setTag(1);
                kf0 = Keyframe.ofFloat(0f, 0f);
                kf1 = Keyframe.ofFloat(.5f, 360f);
                kf2 = Keyframe.ofFloat(1f, 0f);
                pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
                rotationAnim = ObjectAnimator.ofPropertyValuesHolder(btn5, pvhRotation);
                rotationAnim.setDuration(2000);
                rotationAnim.start();
                break;
            case 1:
                btn5.setTag(0);
                kf0 = Keyframe.ofFloat(0f, 0f);
                kf1 = Keyframe.ofFloat(.5f, 180f);
                kf2 = Keyframe.ofFloat(1f, 0f);
                pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
                rotationAnim = ObjectAnimator.ofPropertyValuesHolder(btn5, pvhRotation);
                rotationAnim.setDuration(2000);
//                rotationAnim.start();

                ObjectAnimator transY = ObjectAnimator.ofFloat(btn5, "y", btn5.getHeight() * 4, 50, 300, 200, btn5.getHeight() * 4);
                transY.setDuration(2000);

                ObjectAnimator transX = ObjectAnimator.ofFloat(btn5, "x", 0, 100, 200, 0);
                transX.setDuration(2000);
//                transX.start();

                AnimatorSet animSetXRotate = new AnimatorSet();
                animSetXRotate.playTogether(rotationAnim, transY, transX);
                animSetXRotate.start();
                break;
        }


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
                LogUtils.d(TAG, "onClick() propertyValuesHolder");
                propertyValuesHolder();
            }
            break;
            case R.id.btn4: {
                LogUtils.d(TAG, "onClick() animatorSet();");
                animatorSet();
            }
            break;
            case R.id.btn5: {
                LogUtils.d(TAG, "onClick() keyframe();");
                keyframe();
            }
            break;
        }
    }
}
