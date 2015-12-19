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
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
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
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.example.android.apis.R;

import java.util.ArrayList;


public class BouncingBalls extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bouncing_balls);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        container.addView(new MyAnimationView(this));
    }

    public class MyAnimationView extends View {

        private static final int RED = 0xffFF8080;
        private static final int BLUE = 0xff8080FF;
        private static final int CYAN = 0xff80ffff;
        private static final int GREEN = 0xff80ff80;

        public final ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();
        AnimatorSet animation = null;
        private float mDensity = 1.f;

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public MyAnimationView(Context context) {
            super(context);
            mDensity = getContext().getResources().getDisplayMetrics().density;
            // Animate background color
            // Note that setting the background color will automatically invalidate the
            // view, so that the animated color, and the bouncing balls, get redisplayed on
            // every frame of the animation.
            ValueAnimator colorAnim = ObjectAnimator.ofInt(this, "backgroundColor", RED, BLUE);
            colorAnim.setDuration(3000);
            colorAnim.setEvaluator(new ArgbEvaluator());
            colorAnim.setRepeatCount(ValueAnimator.INFINITE);
            colorAnim.setRepeatMode(ValueAnimator.REVERSE);
            colorAnim.start();
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() != MotionEvent.ACTION_DOWN &&
                    event.getAction() != MotionEvent.ACTION_MOVE) {
                return false;
            }
            ShapeHolder newBall = addRect(event.getX(), event.getY());

            // Bouncing animation with squash and stretch
            float startY = newBall.getY();
            float endY = getHeight() - 50f * mDensity;
            float h = (float) getHeight();
            float eventY = event.getY();
            int duration = (int) (500 * ((h - eventY) / h));

            ValueAnimator bounceAnim = ObjectAnimator.ofFloat(newBall, "y", startY, endY);
            bounceAnim.setDuration(duration);
            bounceAnim.setInterpolator(new AccelerateInterpolator());

            ValueAnimator squashAnim1x = ObjectAnimator.ofFloat(newBall, "x", newBall.getX(),
                    newBall.getX() - 25f * mDensity);
            squashAnim1x.setDuration(duration / 4);
            squashAnim1x.setRepeatCount(1);
            squashAnim1x.setRepeatMode(ValueAnimator.REVERSE);
            squashAnim1x.setInterpolator(new DecelerateInterpolator());

            ValueAnimator squashAnim2width = ObjectAnimator.ofFloat(newBall, "width", newBall.getWidth(),
                    newBall.getWidth() + 50 * mDensity);
            squashAnim2width.setDuration(duration / 4);
            squashAnim2width.setRepeatCount(1);
            squashAnim2width.setRepeatMode(ValueAnimator.REVERSE);
            squashAnim2width.setInterpolator(new DecelerateInterpolator());

            ValueAnimator stretchAnim1y = ObjectAnimator.ofFloat(newBall, "y", endY,
                    endY + 25f * mDensity);
            stretchAnim1y.setDuration(duration / 4);
            stretchAnim1y.setRepeatCount(1);
            stretchAnim1y.setInterpolator(new DecelerateInterpolator());
            stretchAnim1y.setRepeatMode(ValueAnimator.REVERSE);

            ValueAnimator stretchAnim2height = ObjectAnimator.ofFloat(newBall, "height",
                    newBall.getHeight(), newBall.getHeight() - 25 * mDensity);
            stretchAnim2height.setDuration(duration / 4);
            stretchAnim2height.setRepeatCount(1);
            stretchAnim2height.setInterpolator(new DecelerateInterpolator());
            stretchAnim2height.setRepeatMode(ValueAnimator.REVERSE);


            ValueAnimator bounceBackAnim = ObjectAnimator.ofFloat(newBall, "y", endY,
                    startY);
            bounceBackAnim.setDuration(duration);
            bounceBackAnim.setInterpolator(new DecelerateInterpolator());


            // Sequence the down/squash&stretch/up animations
            AnimatorSet bouncer = new AnimatorSet();
            bouncer.play(bounceAnim).before(squashAnim1x);
            bouncer.play(squashAnim1x).with(squashAnim2width).with(stretchAnim1y).with(stretchAnim2height);
//            bouncer.play(squashAnim1x).with(stretchAnim1y);
//            bouncer.play(squashAnim1x).with(stretchAnim2height);
            bouncer.play(bounceBackAnim).after(stretchAnim2height);

            // Fading animation - remove the ball when the animation is done
            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(newBall, "alpha", 1f, 0f);
            fadeAnim.setDuration(250);
            fadeAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    balls.remove(((ObjectAnimator) animation).getTarget());

                }
            });

            // Sequence the two animations to play one after the other
            AnimatorSet animatorSet = new AnimatorSet();
//            animatorSet.play(bouncer).before(fadeAnim);
            animatorSet.play(fadeAnim).after(bouncer);

            // Start the animation
            animatorSet.start();

            return true;
        }

        private ShapeHolder addBall(float x, float y) {
            OvalShape circle = new OvalShape();
            circle.resize(50f, 50f);
            ShapeDrawable drawable = new ShapeDrawable(circle);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            shapeHolder.setX(x - 25f);
            shapeHolder.setY(y - 25f);
            int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);
            int color = 0xff000000 | red << 16 | green << 8 | blue;
            Paint paint = drawable.getPaint(); //new Paint(Paint.ANTI_ALIAS_FLAG);
            int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                    50f, color, darkColor, Shader.TileMode.CLAMP);
            paint.setShader(gradient);
            shapeHolder.setPaint(paint);
            balls.add(shapeHolder);
            return shapeHolder;
        }

        private ShapeHolder addRect(float x, float y) {
            RectShape rectShape = new RectShape();
            rectShape.resize(50f * mDensity, 50f * mDensity);
            ShapeDrawable drawable = new ShapeDrawable(rectShape);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            shapeHolder.setX(x - 25f * mDensity);
            shapeHolder.setY(y - 25f * mDensity);
            int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);
            int color = 0xff000000 | red << 16 | green << 8 | blue;
            int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
            Paint paint = drawable.getPaint();
            RadialGradient gradient = new RadialGradient(37.5f * mDensity, 12.5f * mDensity, 50f * mDensity, color, darkColor, Shader.TileMode.CLAMP);
            paint.setShader(gradient);
            shapeHolder.setPaint(paint);
            balls.add(shapeHolder);
            return shapeHolder;

        }

        @Override
        protected void onDraw(Canvas canvas) {
            for (int i = 0; i < balls.size(); ++i) {
                ShapeHolder shapeHolder = balls.get(i);
                canvas.save();
                canvas.translate(shapeHolder.getX(), shapeHolder.getY());
                shapeHolder.getShape().draw(canvas);
                canvas.restore();
            }
        }
    }
}