package com.tencent.testapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import commontools.LogUtils;

/**
 * TODO: document your custom view class.
 */
public class MyView extends View {
    private static final String TAG = "MyView";
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;
    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private float touchX;
    private float touchY;
    private Paint mMaskPaint;
    private Bitmap mMaskBitmap;

    public MyView(Context context) {
        super(context);
        init(null, 0);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    protected static Bitmap createBitmapAndGcIfNecessary(int width, int height) {
        try {
            return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError e) {
            System.gc();
            return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.MyView, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.MyView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.MyView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.MyView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.MyView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.MyView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();

        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LogUtils.d(TAG, "onTouch " + event.toString());
//                centerView.layout((int) event.getX() + v.getLeft(), (int) event.getY() + v.getTop(), (int) event.getX() + 20 + v.getLeft(), (int) event.getY() + 20 + v.getTop());
                touchX = event.getX();
                touchY = event.getY();
                v.invalidate();
                return true;
            }
        });
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRect(50,50,150,150,clearPaint);
//        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        canvas.drawText(mExampleString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }
        canvas.drawText("test", touchX + mTextPaint.getTextSize(), touchY, mTextPaint);

        Paint paint = new Paint();
//            paint.setColor(0x88FF4081);
        paint.setStrokeWidth(5);
//        paint.setColor(0xff000000);
        paint.setTextSize(100);
        paint.setShader(new LinearGradient(0, 0, 10, 10, 0xff000000, 0x00000000, Shader.TileMode.REPEAT));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, 0, 100, 100, paint);

        paint.setColor(0x80000000);
        paint.setShader(new LinearGradient(0, 0, 10, 10, 0xff000000, 0x00000000, Shader.TileMode.REPEAT));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(100, 0, 200, 100, paint);

        if (mMaskPaint == null) {
            mMaskPaint = new Paint();
            mMaskPaint.setAntiAlias(true);
            mMaskPaint.setDither(true);
            mMaskPaint.setFilterBitmap(true);
            mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        }
        Bitmap maskBitmap = getMaskBitmap();
        canvas.drawBitmap(maskBitmap, 0, 100, mMaskPaint);
    }

    private Bitmap getMaskBitmap() {
        if (mMaskBitmap != null) {
            return mMaskBitmap;
        }

        int width = 100;
        int height = 100;

        mMaskBitmap = createBitmapAndGcIfNecessary(width, height);
        Canvas canvas = new Canvas(mMaskBitmap);
        Shader gradient;

        gradient =
                new LinearGradient(
                        0, 0, 20, 20,
                        0xff000000,
                        0x00000000,
                        Shader.TileMode.REPEAT);

        canvas.rotate(0, width / 2, height / 2);
        Paint paint = new Paint();
        paint.setShader(gradient);
        // We need to increase the rect size to account for the tilt
        int padding = (int) (Math.sqrt(2) * Math.max(width, height)) / 2;
        canvas.drawRect(-padding, -padding, width + padding, height + padding, paint);

        return mMaskBitmap;
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}
