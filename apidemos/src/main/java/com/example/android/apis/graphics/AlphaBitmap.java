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

package com.example.android.apis.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;

import com.example.android.apis.DensityUtil;
import com.example.android.apis.R;
import com.tencent.commontools.LogUtils;

import java.io.InputStream;

public class AlphaBitmap extends GraphicsActivity {
    private static final String TAG = "AlphaBitmap";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new SampleView(this));
        setContentView(new DrawView(this));
    }

    private static class SampleView extends View {
        private Bitmap mBitmap;
        private Bitmap mBitmap2;
        private Bitmap mBitmap3;
        private Shader mShader;
        private Shader mShader2;

        public SampleView(Context context) {
            super(context);
            setFocusable(true);

            InputStream is = context.getResources().openRawResource(R.drawable.app_sample_code);
            mBitmap = BitmapFactory.decodeStream(is);
            mBitmap2 = mBitmap.extractAlpha();
            mBitmap3 = Bitmap.createBitmap(200, 200, Bitmap.Config.ALPHA_8);
            drawIntoBitmap(mBitmap3);

            mShader = new LinearGradient(0, 0, 100, 70, new int[]{
                    Color.RED, Color.GREEN, Color.BLUE},
                    null, Shader.TileMode.REPEAT);
            mShader2 = new LinearGradient(0, 0, 100, 70, new int[]{
                    Color.RED, Color.GREEN, Color.BLUE},
                    null, Shader.TileMode.CLAMP);

        }

        private static void drawIntoBitmap(Bitmap bm) {
            float x = bm.getWidth();
            float y = bm.getHeight();
            Canvas c = new Canvas(bm);
            Paint p = new Paint();
            p.setAntiAlias(true);

            p.setAlpha(0x80);
            c.drawCircle(x / 2, y / 2, x / 2, p);

            p.setAlpha(0x30);
            p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            p.setTextSize(60);
            p.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetrics fm = p.getFontMetrics();
            c.drawText("Alpha", x / 2, (y - fm.ascent) / 2, p);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);

            Paint p = new Paint();
            float y = 10;

            p.setColor(Color.RED);
            canvas.drawBitmap(mBitmap, 10, y, p);
            y += mBitmap.getHeight() + 10;
            canvas.drawBitmap(mBitmap2, 10, y, p);
            y += mBitmap2.getHeight() + 10;
            p.setShader(mShader);
            canvas.drawBitmap(mBitmap3, 10, y, p);
            y += mBitmap3.getHeight() + 10;
            p.setShader(mShader2);
            canvas.drawBitmap(mBitmap2, 10, y, p);
        }
    }

    public class DrawView extends View {

        public DrawView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.BLACK);
        /*
         * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
		 * drawLine 绘制直线 drawPoin 绘制点
		 */
            // 创建画笔
            Paint p = new Paint();
            p.setColor(Color.RED);// 设置红色

            canvas.save();

            float px = DensityUtil.dip2px(getContext(), 1);
            LogUtils.i(TAG, "1 dip2px: " + px);
            canvas.scale(px, px);

            canvas.drawText("画圆：", 10, 20, p);// 画文本
            canvas.drawCircle(60, 20, 10, p);// 小圆
            p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
            canvas.drawCircle(120, 20, 20, p);// 大圆

            canvas.drawText("画线及弧线：", 10, 60, p);
            p.setColor(Color.GREEN);// 设置绿色
            canvas.drawLine(60, 40, 100, 40, p);// 画线
            canvas.drawLine(110, 40, 190, 80, p);// 斜线

            Paint p2 = new Paint();
            p2.setColor(Color.RED);// 设置红色
            p2.setAlpha(150);

            //画笑脸弧线
            p.setStyle(Paint.Style.STROKE);//设置空心

            RectF oval1 = new RectF();

            oval1.set(160, 30, 210, 60);
//            canvas.drawRect(oval1, p2);
            canvas.drawArc(oval1, 0, 180, false, p);//小弧形

            oval1 = new RectF(150, 20, 180, 40);
            canvas.drawRect(oval1, p2);
            canvas.drawArc(oval1, 180, 180, false, p);//小弧形

            oval1.set(190, 20, 220, 40);
            canvas.drawRect(oval1, p2);
            canvas.drawArc(oval1, 180, 180, false, p);//小弧形


            canvas.drawText("画矩形：", 10, 80, p);
            p.setColor(Color.GRAY);// 设置灰色
            p.setStyle(Paint.Style.FILL);//设置填满
            canvas.drawRect(60, 60, 80, 80, p);// 正方形
            canvas.drawRect(60, 90, 160, 100, p);// 长方形

            canvas.drawText("画扇形和椭圆:", 10, 120, p);
        /* 设置渐变色 这个正方形的颜色是改变的 */
            Shader mShader = new LinearGradient(0, 0, 100, 100,
                    new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                            Color.LTGRAY}, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
            p.setShader(mShader);
            // p.setColor(Color.BLUE);
            RectF oval2 = new RectF(60, 100, 200, 240);// 设置个新的长方形，扫描测量
            canvas.drawArc(oval2, 200, 130, true, p);
            // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
            //画椭圆，把oval改一下
            oval2.set(210, 100, 250, 130);
            canvas.drawOval(oval2, p);

            canvas.drawText("画三角形：", 10, 200, p);
            // 绘制这个三角形,你可以绘制任意多边形
            Path path = new Path();
            path.moveTo(80, 200);// 此点为多边形的起点
            path.lineTo(120, 250);
            path.lineTo(80, 250);
            path.close(); // 使这些点构成封闭的多边形
            canvas.drawPath(path, p);

            // 你可以绘制很多任意多边形，比如下面画六连形
            p.reset();//重置
            p.setColor(Color.LTGRAY);
            p.setStyle(Paint.Style.STROKE);//设置空心
            Path path1 = new Path();
            path1.moveTo(180, 200);
            path1.lineTo(200, 200);
            path1.lineTo(210, 210);
            path1.lineTo(200, 220);
            path1.lineTo(180, 220);
            path1.lineTo(170, 210);
            path1.close();//封闭
            canvas.drawPath(path1, p);
        /*
         * Path类封装复合(多轮廓几何图形的路径
		 * 由直线段*、二次曲线,和三次方曲线，也可画以油画。drawPath(路径、油漆),要么已填充的或抚摸
		 * (基于油漆的风格),或者可以用于剪断或画画的文本在路径。
		 */

            //画圆角矩形
            p.setStyle(Paint.Style.FILL);//充满
            p.setColor(Color.LTGRAY);
            p.setAntiAlias(true);// 设置画笔的锯齿效果
            canvas.drawText("画圆角矩形:", 10, 260, p);
            RectF oval3 = new RectF(80, 260, 200, 300);// 设置个新的长方形

            canvas.drawRoundRect(oval3, 20, 15, p);//第二个参数是x半径，第三个参数是y半径
            canvas.drawRect(80, 260, 100, 275, p2);

            //画贝塞尔曲线
            canvas.drawText("画贝塞尔曲线:", 10, 310, p);
            p.reset();
            p.setStyle(Paint.Style.STROKE);
            p.setColor(Color.GREEN);
            Path path2 = new Path();
            path2.moveTo(100, 320);//设置Path的起点
            path2.quadTo(150, 320, 170, 400); //设置贝塞尔曲线的控制点坐标和终点坐标
            canvas.drawPath(path2, p);//画出贝塞尔曲线

            p.setAntiAlias(true);
            p.setColor(Color.BLUE);
            path2 = new Path();
            path2.moveTo(100, 320);//设置Path的起点
            path2.quadTo(200, 320, 170, 400); //设置贝塞尔曲线的控制点坐标和终点坐标
            canvas.drawPath(path2, p);//画出贝塞尔曲线

            //画点
            p.setStyle(Paint.Style.FILL);
            canvas.drawText("画点：", 10, 390, p);
            canvas.drawPoint(60, 390, p);//画一个点
            canvas.drawPoints(new float[]{60, 400, 65, 400, 70, 400}, p);//画多个点

            //画图片，就是贴图
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_sample_code);
            canvas.drawBitmap(bitmap, 250, 360, p);

            canvas.restore();
        }
    }
}

