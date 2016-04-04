/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.apis.animation;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.example.android.apis.R;
import com.tencent.commontools.LogUtils;

import java.util.ArrayList;

/**
 * This application demonstrates the seeking capability of ValueAnimator. The SeekBar in the
 * UI allows you to set the position of the animation. Pressing the Run button will play from
 * the current position of the animation.
 */
public class AnimationSeeking extends Activity {

    private static final String TAG = "AnimationSeeking";
    private static final int DURATION = 3000;
    private SeekBar mSeekBar;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        LogUtils.d(TAG, "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_seeking);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        final MyAnimationView animView = new MyAnimationView(this);
        container.addView(animView);

        Button starter = (Button) findViewById(R.id.startButton);
        starter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                animView.startAnimation();
            }
        });

        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setMax(DURATION);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // prevent seeking on app creation
                if (animView.getHeight() != 0 && fromUser) {
                    animView.seek(progress);
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public class MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

        private static final int RED = 0xffFF8080;
        private static final int BLUE = 0xff8080FF;
        private static final int CYAN = 0xff80ffff;
        private static final int GREEN = 0xff80ff80;
        private static final float BALL_SIZE = 100f;
        private static final String TAG = "MyAnimationView";
        public final ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();
        //        AnimatorSet animation = null;
        ValueAnimator bounceValueAnimator = null;
        ShapeHolder ball = null;

        public MyAnimationView(Context context) {
            super(context);
            LogUtils.d(TAG, "MyAnimationView() called with: " + "context = [" + context + "]");
            ball = addBall(200, 0);
        }

        private void createAnimation() {
            if (bounceValueAnimator == null) {
                LogUtils.d(TAG, "createAnimation() called with: " + "");
                bounceValueAnimator = ObjectAnimator.ofFloat(ball, "y",
                        ball.getY(), getHeight() - BALL_SIZE).setDuration(DURATION);
                bounceValueAnimator.setInterpolator(new BounceInterpolator());
                bounceValueAnimator.addUpdateListener(this);
            }
        }

        public void startAnimation() {
            LogUtils.d(TAG, "startAnimation() called with: " + "");
            createAnimation();
            bounceValueAnimator.start();
        }

        public void seek(long seekTime) {
            LogUtils.d(TAG, "seek() called with: " + "seekTime = [" + seekTime + "]");
            createAnimation();
            bounceValueAnimator.setCurrentPlayTime(seekTime);
        }

        private ShapeHolder addBall(float x, float y) {
            LogUtils.d(TAG, "addBall() called with: " + "x = [" + x + "], y = [" + y + "]");
            OvalShape circle = new OvalShape();
            circle.resize(BALL_SIZE, BALL_SIZE);
            ShapeDrawable drawable = new ShapeDrawable(circle);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            shapeHolder.setX(x);
            shapeHolder.setY(y);
            int red = (int) (100 + Math.random() * 155);
            int green = (int) (100 + Math.random() * 155);
            int blue = (int) (100 + Math.random() * 155);
            int color = 0xff000000 | red << 16 | green << 8 | blue;
            Paint paint = drawable.getPaint();
            int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                    50f, color, darkColor, Shader.TileMode.CLAMP);
            paint.setShader(gradient);
            shapeHolder.setPaint(paint);
            balls.add(shapeHolder);
            return shapeHolder;
        }

        @Override
        protected void onDraw(Canvas canvas) {
//            LogUtils.d(TAG, "onDraw() called with: " + "ball.getX() = [" + ball.getX() + "]" + "ball.getY() = [" + ball.getY() + "]");
            canvas.translate(ball.getX(), ball.getY());
            ball.getShape().draw(canvas);
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public void onAnimationUpdate(ValueAnimator animation) {
//            LogUtils.d(TAG, "onAnimationUpdate() called with: " + "animation getCurrentPlayTime = [" + animation.getCurrentPlayTime() + "]" + "ball.getX() = [" + ball.getX() + "]" + "ball.getY() = [" + ball.getY() + "]");
            long playtime = bounceValueAnimator.getCurrentPlayTime();
            mSeekBar.setProgress((int) playtime);
            invalidate();
        }

        public void onAnimationCancel(Animator animation) {
            LogUtils.d(TAG, "onAnimationCancel() called with: " + "animation = [" + animation + "]");
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public void onAnimationEnd(Animator animation) {
            LogUtils.d(TAG, "onAnimationEnd() called with: " + "animation = [" + animation + "]");
            balls.remove(((ObjectAnimator) animation).getTarget());

        }

        public void onAnimationRepeat(Animator animation) {
            LogUtils.d(TAG, "onAnimationRepeat() called with: " + "animation = [" + animation + "]");
        }

        public void onAnimationStart(Animator animation) {
            LogUtils.d(TAG, "onAnimationStart() called with: " + "animation = [" + animation + "]");
        }
    }
}