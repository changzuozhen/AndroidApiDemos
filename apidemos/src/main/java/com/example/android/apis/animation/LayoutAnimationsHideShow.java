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
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.android.apis.R;

/**
 * This application demonstrates how to use LayoutTransition to automate transition animations
 * as items are hidden or shown in a container.
 */
public class LayoutAnimationsHideShow extends Activity {

    ViewGroup container = null;
    private int numButtons = 1;
    private LayoutTransition mTransitioner;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_animations_hideshow);

        final CheckBox hideGoneCB = (CheckBox) findViewById(R.id.hideGoneCB);

        container = new LinearLayout(this);
        container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        // Add a slew of buttons to the container. We won't add any more buttons at runtime, but
        // will just show/hide the buttons we've already created
        for (int i = 0; i < 4; ++i) {
            Button newButton = new Button(this);
            newButton.setText(String.valueOf(i));
            container.addView(newButton);
            newButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    v.setVisibility(hideGoneCB.isChecked() ? View.GONE : View.INVISIBLE);
                }
            });
        }

        resetTransition();

        ViewGroup parent = (ViewGroup) findViewById(R.id.parent);
        parent.addView(container);

        Button addButton = (Button) findViewById(R.id.addNewButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < container.getChildCount(); ++i) {
                    View view = (View) container.getChildAt(i);
                    view.setVisibility(View.VISIBLE);
                }
            }
        });

        CheckBox customAnimCB = (CheckBox) findViewById(R.id.customAnimCB);
        customAnimCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                long duration;
                if (isChecked) {
                    mTransitioner.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
                    mTransitioner.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
                    setupCustomAnimations();
                    duration = 500;
                } else {
                    resetTransition();
                    duration = 300;
                }
                mTransitioner.setDuration(duration);
            }
        });
    }

    private void resetTransition() {
        mTransitioner = new LayoutTransition();
        container.setLayoutTransition(mTransitioner);
    }

    private void setupCustomAnimations() {
        // Changing while Adding
/**
 *
 * http://blog.csdn.net/harvic880925/article/details/50985596
 *
 * 这里有几点注意事项：
 1、LayoutTransition.CHANGE_APPEARING和LayoutTransition.CHANGE_DISAPPEARING必须使用PropertyValuesHolder所构造的动画才会有效果，不然无效！也就是说使用ObjectAnimator构造的动画，在这里是不会有效果的！
 2、在构造PropertyValuesHolder动画时，”left”、”top”属性的变动是必写的。如果不需要变动，则直接写为：
 [java] view plain copy 在CODE上查看代码片派生到我的代码片
 PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left",0,0);
 PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt("top",0,0);
 3、在构造PropertyValuesHolder时，所使用的ofInt,ofFloat中的参数值，第一个值和最后一个值必须相同，不然此属性所对应的的动画将被放弃，在此属性值上将不会有效果；
 [java] view plain copy 在CODE上查看代码片派生到我的代码片
 PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left",0,100,0);
 比如，这里ofInt(“left”,0,100,0)第一个值和最后一个值都是0，所以这里会有效果的，如果我们改为ofInt(“left”,0,100);那么由于首尾值不一致，则将被视为无效参数，将不会有效果！
 4、在构造PropertyValuesHolder时，所使用的ofInt,ofFloat中，如果所有参数值都相同，也将不会有动画效果。
 比如：
 [java] view plain copy 在CODE上查看代码片派生到我的代码片
 PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left",100,100);
 在这条语句中，虽然首尾一致，但由于全程参数值相同，所以left属性上的这个动画会被放弃，在left属性上也不会应用上任何动画。
 */


        PropertyValuesHolder pvhLeft =
                PropertyValuesHolder.ofInt("left", 0, 1);
//                PropertyValuesHolder.ofInt("left", 0, 1000, 0);
        PropertyValuesHolder pvhTop =
                PropertyValuesHolder.ofInt("top", 0, 1);
//                PropertyValuesHolder.ofInt("top", 0, 1000, 0);
        PropertyValuesHolder pvhRight =
                PropertyValuesHolder.ofInt("right", 0, 1);
//                PropertyValuesHolder.ofInt("right", 0, 1000, 0);
        PropertyValuesHolder pvhBottom =
                PropertyValuesHolder.ofInt("bottom", 0, 1);
//                PropertyValuesHolder.ofInt("bottom", 0, 1000, 0);
        PropertyValuesHolder pvhScaleX =
                PropertyValuesHolder.ofFloat("scaleX", 1f, 0f, 1f);
        PropertyValuesHolder pvhScaleY =
                PropertyValuesHolder.ofFloat("scaleY", 1f, 0f, 1f);
        final ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhScaleX, pvhScaleY).
                setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_APPEARING));
        mTransitioner.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);
        changeIn.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setScaleX(1f);
                view.setScaleY(1f);
            }
        });

        // Changing while Removing
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(.9999f, 360f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
        PropertyValuesHolder pvhRotation =
                PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
        final ObjectAnimator changeOut = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhRotation).
                setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        mTransitioner.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeOut);
        changeOut.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setRotation(0f);
            }
        });

        // Adding
        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "rotationY", 90f, 0f).
                setDuration(mTransitioner.getDuration(LayoutTransition.APPEARING));
        mTransitioner.setAnimator(LayoutTransition.APPEARING, animIn);
        animIn.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setRotationY(0f);
            }
        });

        // Removing
        ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "rotationX", 0f, 90f).
                setDuration(mTransitioner.getDuration(LayoutTransition.DISAPPEARING));
        mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);
        animOut.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setRotationX(0f);
            }
        });

    }
}