/*
 * Copyright (C) 2007 The Android Open Source Project
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

package com.example.android.apis.AndyTest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.android.apis.DensityUtil;
import com.example.android.apis.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import commontools.LogUtils;

public class AndyTestXfermodes extends Activity {

    @BindView(R.id.modeBtn)
    Button modeBtn;
    SampleView sampleView;
    @BindView(R.id.layout_container)
    LinearLayout layoutContainer;

    // create a bitmap with a circle, used for the "dst" image
    static Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFFFFCC44);
        c.drawOval(new RectF(0, 0, w, h), p);
        return bm;
    }

    // create a bitmap with a rect, used for the "src" image
    static Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFF66AAFF);
//        c.drawRect(w / 3, h / 3, w * 19 / 20, h * 19 / 20, p);
        c.drawRect(0, 0, w, h, p);
        return bm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.andy_test_xfermodes_activity);
        ButterKnife.bind(this);
        sampleView = new SampleView(this);
        layoutContainer.addView(sampleView);

//        setContentView(new SampleView(this));

    }

    @OnClick({R.id.modeBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modeBtn:
                sampleView.switchMode();
                break;
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class SampleView extends View {
        private static final int ROW_MAX = 4;   // number of samples per row
        private static final Xfermode[] sModes = {
                new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
                new PorterDuffXfermode(PorterDuff.Mode.OVERLAY),
                new PorterDuffXfermode(PorterDuff.Mode.SRC),
                new PorterDuffXfermode(PorterDuff.Mode.DST),
                new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
                new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
                new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
                new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
                new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
                new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
                new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
                new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
                new PorterDuffXfermode(PorterDuff.Mode.XOR),
                new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
                new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
                new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
                new PorterDuffXfermode(PorterDuff.Mode.SCREEN),
                new PorterDuffXfermode(PorterDuff.Mode.ADD)
        };
        private static final String[] sLabels = {
                "Clear", "Overlay", "Src", "Dst", "SrcOver",
                "DstOver", "SrcIn", "DstIn", "SrcOut",
                "DstOut", "SrcATop", "DstATop", "Xor",
                "Darken", "Lighten", "Multiply", "Screen",
                "Add"
        };
        private static int W = 64;
        private static int H = 64;
        private final int dip2px;
        int mode = 0;
        // rect
        private Bitmap mSrcB;
        // oval
        private Bitmap mDstB;
        private Shader mBG;     // background checker-board pattern

        public SampleView(Context context) {
            super(context);

            dip2px = DensityUtil.dip2px(context, 1);
            DisplayMetrics dm = getResources().getDisplayMetrics();
            int w_screen = dm.widthPixels;
            int h_screen = dm.heightPixels;
            LogUtils.d("屏幕尺寸2：宽度 = " + w_screen + "高度 = " + h_screen + "密度 = " + dm.densityDpi);
            W = w_screen / 5;
            H = W;
//            W *= dip2px;
//            H *= dip2px;

            // oval 左上
            mDstB = makeDst(W * 2 / 3, H * 2 / 3);

            // rect 右下
            mSrcB = makeSrc(W * 2 / 3, H * 2 / 3);

            // make a ckeckerboard pattern
            Bitmap bm = Bitmap.createBitmap(new int[]{0xFFFFFFFF, 0xFFCCCCCC,
                            0xFFCCCCCC, 0xFFFFFFFF}, 2, 2,
                    Bitmap.Config.RGB_565);
            mBG = new BitmapShader(bm,
                    Shader.TileMode.REPEAT,
                    Shader.TileMode.REPEAT);
            Matrix m = new Matrix();
            m.setScale(6 * dip2px, 6 * dip2px);

            mBG.setLocalMatrix(m);
        }

        public void switchMode() {
            mode++;
            if (mode > 3) {
                mode = 0;
            }
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);

            Paint labelP = new Paint(Paint.ANTI_ALIAS_FLAG);
            labelP.setTextAlign(Paint.Align.CENTER);
            labelP.setTextSize(10 * dip2px);

            Paint paint = new Paint();
            paint.setFilterBitmap(false);

            canvas.translate(W / 5, W / 5);

            int x = 0;
            int y = 0;
            for (int i = 0; i < sModes.length; i++) {
                // draw the border
                paint.setStyle(Paint.Style.STROKE);
                paint.setShader(null);
                canvas.drawRect(x - 0.5f, y - 0.5f,
                        x + W + 0.5f, y + H + 0.5f, paint);

                // draw the checker-board pattern
                paint.setStyle(Paint.Style.FILL);

                // 画背景
                paint.setShader(mBG);
                canvas.drawRect(x, y, x + W, y + H, paint);

                // draw the src/dst example into our offscreen bitmap
                int sc = canvas.saveLayer(x, y, x + W, y + H, null,
                        Canvas.MATRIX_SAVE_FLAG |
                                Canvas.CLIP_SAVE_FLAG |
                                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                                Canvas.CLIP_TO_LAYER_SAVE_FLAG);
                canvas.translate(x, y);

                if (mode % 2 == 0) {
                    // oval 画圆
                    canvas.drawBitmap(mDstB, 0, 0, paint);
                } else {
                    //rect 画方
                    canvas.drawBitmap(mSrcB, 0, 0, paint);
                }
                float textSize = labelP.getTextSize();
                LogUtils.d("onDraw: " +
                        " W" + W +
                        "H" + H +
                        " getTextSize" + textSize);
                canvas.drawText("Dst", W / 3.f, textSize, labelP);

                paint.setXfermode(sModes[i]);

                if (mode < 2) {
                    //rect 画方
                    canvas.drawBitmap(mSrcB, W / 3, H / 3, paint);
                } else {
                    // oval 画圆
                    canvas.drawBitmap(mDstB, W / 3, H / 3, paint);
                }


                canvas.drawText("Src", (float) (W / 3 * 2), H - textSize / 2, labelP);

                paint.setXfermode(null);
                canvas.restoreToCount(sc);

                // draw the label
                canvas.drawText(sLabels[i],
                        x + W / 2, y - textSize / 2, labelP);

                x += W + W / 5;

                // wrap around when we've drawn enough for one row
                if ((i % ROW_MAX) == ROW_MAX - 1) {
                    x = 0;
                    y += H + 30 * dip2px;
                }
            }
        }
    }
}

