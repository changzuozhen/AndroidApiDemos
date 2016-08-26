package com.example.android.apis.AndyTest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.apis.R;

public class AndyTestColorMatrixActivity extends Activity {
    RadioGroup radioGroup;
    int matrixType = 0;
    private SeekBar mSeekBar;
    private ImageView mImageView;
    private Bitmap mOriginBmp, mTempBmp;
    private TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.andytest_color_matrix_activity);

        mImageView = (ImageView) findViewById(R.id.img);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:
                        matrixType = 0;
                        break;
                    case R.id.radioButton2:
                        matrixType = 1;
                        break;
                    case R.id.radioButton3:
                        matrixType = 2;
                        break;
                    case R.id.radioButton4:
                        matrixType = 3;
                        break;
                    case R.id.radioButton5:
                        matrixType = 4;
                        break;
                }
                Bitmap bitmap = handleColorRotateOrSaturationBmp(mSeekBar.getProgress());
                mImageView.setImageBitmap(bitmap);
            }
        });
        mOriginBmp = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        mTempBmp = Bitmap.createBitmap(mOriginBmp.getWidth() * 2, mOriginBmp.getHeight(),
                Bitmap.Config.ARGB_8888);
        tv = (TextView) findViewById(R.id.tv);
        tv.setText("色彩旋转(-180——180):");


        mSeekBar.setMax(360);
        mSeekBar.setProgress(180);

        radioGroup.check(R.id.radioButton5);
        matrixType = 4;

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Bitmap bitmap = handleColorRotateOrSaturationBmp(progress);
                mImageView.setImageBitmap(bitmap);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private Bitmap handleColorRotateOrSaturationBmp(int progress) {

        // 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
        Canvas canvas = new Canvas(mTempBmp); // 得到画笔对象
        Paint paintOrigion = new Paint(); // 新建paint
        Paint paint = new Paint(); // 新建paint
        paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理
        ColorMatrix colorMatrix = new ColorMatrix();
        switch (matrixType) {
            case 0:
            case 1:
            case 2:
                colorMatrix.setRotate(matrixType, progress - 180);
                paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));// 设置颜色变换效果
                tv.setText("色彩旋转(-180——180):" + (progress - 180));
                break;
            case 3:
                colorMatrix.setSaturation(((progress - 180) / 9));
                paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));// 设置颜色变换效果
                tv.setText("色相(-20——20):" + ((progress - 180) / 9));
                break;
            case 4:
                float fprogress = progress / 360.f;
                int mult = ((int) (0xff * fprogress)) * 0x100;
                paint.setColorFilter(new LightingColorFilter(mult, 0x008000));
                tv.setText("LightingColorFilter(-20——20):" + ((int) (0xff * fprogress)) + " " + 0x008000);
                break;
            case 5:
            case 6:
        }

        canvas.drawBitmap(mOriginBmp, 0, 0, paintOrigion); // 将颜色变化后的图片输出到新创建的位图区
        canvas.translate(mOriginBmp.getWidth(), 0);
        canvas.drawBitmap(mOriginBmp, 0, 0, paint); // 将颜色变化后的图片输出到新创建的位图区
        // 返回新的位图，也即调色处理后的图片
        return mTempBmp;
    }
}
