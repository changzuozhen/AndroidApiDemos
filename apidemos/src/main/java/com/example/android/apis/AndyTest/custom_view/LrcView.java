package com.example.android.apis.AndyTest.custom_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.example.android.apis.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commontools.LogUtils;
import commontools.Utils;

/**
 * 显示lrc歌词控件
 *
 * @author 亓斌 (qibin@gmail.com)
 */
@SuppressLint("DrawAllocation")
public class LrcView extends View {
    private static final String TAG = "LrcView";
    private List<String> mLrcs = new ArrayList<String>(); // 存放歌词
    private List<Long> mTimes = new ArrayList<Long>(); // 存放时间
    private String hintStr = "";
    private long mNextTime = 0l; // 保存下一句开始的时间
    private int mViewWidth; // view的宽度
    private int mLrcHeight; // lrc界面的高度
    private int mRows;      // 多少行
    private int mCurrentLine = 0; // 当前行
    private float mTextSize; // 字体
    private float mTextDecent; // 字体
    private float mDividerHeight; // 行间距
    private Paint mNormalPaint; // 常规的字体
    private Paint mCurrentPaint; // 当前歌词的大小
    private Bitmap mBackground = null;
    private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private LinearGradient linearGradient1;
    private LinearGradient linearGradient2;

    public LrcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    public LrcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(attrs);
    }

    public void setHintStr(String hintStr) {
        LogUtils.d(TAG, "setHintStr() called with: " + "hintStr = [" + hintStr + "]", 2);
        this.hintStr = hintStr;
        postInvalidate();
    }

    // 初始化操作
    private void initViews(AttributeSet attrs) {
        // <begin>
        // 解析自定义属性
        TypedArray ta = getContext().obtainStyledAttributes(attrs,
                R.styleable.Lrc);
        mTextSize = ta.getDimension(R.styleable.Lrc_lrcTextSize, 50.0f);
        mRows = ta.getInteger(R.styleable.Lrc_rows, 5);
        if (mRows % 2 == 0) mRows += 1;
        mDividerHeight = ta.getDimension(R.styleable.Lrc_dividerHeight, 0.0f);

        int normalTextColor = ta.getColor(R.styleable.Lrc_normalTextColor,
                0xffffffff);
        int currentTextColor = ta.getColor(R.styleable.Lrc_currentTextColor,
                0xff00ffde);

        ta.recycle();
        // </end>

        // 计算lrc面板的高度
        mLrcHeight = (int) (mTextSize + mDividerHeight) * mRows;

        mNormalPaint = new Paint();
        mCurrentPaint = new Paint();

        // 初始化paint
        mNormalPaint.setTextSize(mTextSize);
        mNormalPaint.setColor(normalTextColor);
        mCurrentPaint.setTextSize(mTextSize);
        mCurrentPaint.setColor(currentTextColor);

        linearGradient1 = new LinearGradient(0, 0, 0, mTextSize + mDividerHeight, new int[]{0x00cc0000, 0x8000cc00, 0xff0000cc}, new float[]{0, 0.8f, 1f}, Shader.TileMode.REPEAT);
        linearGradient2 = new LinearGradient(0, 0, 0, mTextSize + mDividerHeight, new int[]{0xffcc0000, 0x8000cc00, 0x000000cc}, new float[]{0, 0.8f, 1f}, Shader.TileMode.REPEAT);

        mTextDecent = mNormalPaint.getFontMetrics().descent;

        mNormalPaint.setAntiAlias(true);
        mCurrentPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 获取view宽度
        mViewWidth = getMeasuredWidth();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 重新设置view的高度
        int measuredHeight = MeasureSpec.makeMeasureSpec(mLrcHeight, MeasureSpec.AT_MOST);
        setMeasuredDimension(widthMeasureSpec, measuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (Utils.notEmpty(hintStr)) {
            canvas.save();
            // 圈出可视区域
            canvas.clipRect(0, 0, mViewWidth, mLrcHeight);
            // 将画布上移
//            canvas.translate(0, -((mCurrentLine -(mRows / 2 + 1)) * (mTextSize + mDividerHeight)));
            float currentX = (mViewWidth - mCurrentPaint.measureText(hintStr)) / 2;
            canvas.drawText(hintStr, currentX, (mTextSize + mDividerHeight) * (mRows / 2 + 1) - mTextDecent, mNormalPaint);
            canvas.restore();
            return;
        }
        if (mLrcs.isEmpty() || mTimes.isEmpty()) {
            LogUtils.d(TAG, "onDraw() called with: lrc  Empty");
            return;
        }

        int startLine = mRows / 2;
        int endline = mRows / 2;
        canvas.save();
        // 圈出可视区域
        canvas.clipRect(0, 0, mViewWidth, mLrcHeight);

        if (null != mBackground) {
            canvas.drawBitmap(Bitmap.createScaledBitmap(mBackground, mViewWidth, mLrcHeight, true),
                    new android.graphics.Matrix(), null);
        }

        // 将画布上移
//        canvas.translate(0, -((mCurrentLine -(mRows / 2 + 1)) * (mTextSize + mDividerHeight)));

        String currentLrc = mLrcs.get(mCurrentLine);
        float lrcLength = mCurrentPaint.measureText(currentLrc);
        float currentX = (mViewWidth - lrcLength) / 2;
        // 画当前行
        if (lrcLength > mViewWidth) {
            int splitSlot = ((int) lrcLength) / mViewWidth + 1;
            int strLengthPerLine = currentLrc.length() / splitSlot;
            int currentStringStartIndex = 0;
            if (splitSlot > mRows) splitSlot = mRows;
            startLine = mRows / 2 - splitSlot / 2;
            endline = mRows / 2 - splitSlot / 2 + splitSlot - 1;
            for (int i = startLine; i <= endline; i++) {
                String thisLine = currentLrc.substring(currentStringStartIndex, currentStringStartIndex + strLengthPerLine);
                currentX = (mViewWidth - mCurrentPaint.measureText(thisLine)) / 2;
                canvas.drawText(thisLine, currentX, (mTextSize + mDividerHeight) * (i + 1) - mTextDecent, mCurrentPaint);
                currentStringStartIndex += strLengthPerLine;
            }
        } else {
            canvas.drawText(currentLrc, currentX, (mTextSize + mDividerHeight) * (mRows / 2 + 1) - mTextDecent, mCurrentPaint);
        }
        LogUtils.d(TAG, "onDraw() called with: " + "currentLrc = [" + currentLrc + "]");

        // 画当前行上面的
        int lineOffset = mCurrentLine - (mRows / 2 + 1) + (mRows / 2 - startLine);
        for (int i = mCurrentLine - 1; i >= 0 && Math.abs(mCurrentLine - i) <= startLine; i--) {
            String lrc = mLrcs.get(i);
            float lrcLength2 = mNormalPaint.measureText(lrc);
            float x = (mViewWidth - lrcLength2) / 2;

            if (i - lineOffset == 1) {
                int sc = canvas.saveLayer(0, (mTextSize + mDividerHeight) * (i - lineOffset - 1), mViewWidth, (mTextSize + mDividerHeight) * (i - lineOffset), mNormalPaint, Canvas.ALL_SAVE_FLAG);
                canvas.drawText(lrc, x, (mTextSize + mDividerHeight) * (i - lineOffset) - mTextDecent, mNormalPaint);
                mNormalPaint.setXfermode(xfermode);
                mNormalPaint.setShader(linearGradient1);
                canvas.drawRect(0, (mTextSize + mDividerHeight) * (i - lineOffset - 1), mViewWidth, (mTextSize + mDividerHeight) * (i - lineOffset), mNormalPaint);
                mNormalPaint.setXfermode(null);
                mNormalPaint.setShader(null);
                canvas.restoreToCount(sc);
            } else {
                canvas.drawText(lrc, x, (mTextSize + mDividerHeight) * (i - lineOffset) - mTextDecent, mNormalPaint);
            }
        }

        // 画当前行下面的
        lineOffset = mCurrentLine - (mRows / 2 + 1) - (endline - mRows / 2);
        for (int i = mCurrentLine + 1; i < mLrcs.size() && Math.abs(mCurrentLine - i) <= (mRows - endline - 1); i++) {
            String lrc = mLrcs.get(i);
            float x = (mViewWidth - mNormalPaint.measureText(lrc)) / 2;

            if (i - lineOffset == mRows) {
//                int sc = canvas.saveLayer(Canvas.ALL_SAVE_FLAG);
                int sc = canvas.saveLayer(0, (mTextSize + mDividerHeight) * (i - lineOffset - 1), mViewWidth, (mTextSize + mDividerHeight) * (i - lineOffset), mNormalPaint, Canvas.ALL_SAVE_FLAG);
                canvas.drawText(lrc, x, (mTextSize + mDividerHeight) * (i - lineOffset) - mTextDecent, mNormalPaint);
                mNormalPaint.setXfermode(xfermode);
                mNormalPaint.setShader(linearGradient2);
                canvas.drawRect(0, (mTextSize + mDividerHeight) * (i - lineOffset - 1), mViewWidth, (mTextSize + mDividerHeight) * (i - lineOffset), mNormalPaint);
                mNormalPaint.setXfermode(null);
                mNormalPaint.setShader(null);
                canvas.restoreToCount(sc);
            } else {
                canvas.drawText(lrc, x, (mTextSize + mDividerHeight) * (i - lineOffset) - mTextDecent, mNormalPaint);
            }
        }

        canvas.restore();
    }

    // 解析每行
    private String[] parseLine(String line) {
        /* >benny: [16-12-30 16:58] 格式, 时和毫秒可选 [00:?00:00.00?] */
        Matcher matcher = Pattern.compile("\\[((\\d{2}):)?(\\d{2}):(\\d{2})(\\.(\\d{2}))?\\](.+)").matcher(line);
        try {
            if (matcher.find() && matcher.groupCount() >= 7) {
                String hourStr = matcher.group(2);
                int hour = hourStr == null ? 0 : Integer.valueOf(hourStr);
                int min = Integer.valueOf(matcher.group(3));
                int sec = Integer.valueOf(matcher.group(4));
                String milSecStr = matcher.group(6);
                int milSec = milSecStr == null ? 0 : Integer.valueOf(milSecStr);
                String lrcLine = matcher.group(7);
                return new String[]{String.valueOf(hour * 3600 * 1000 + min * 60 * 1000 + sec * 1000 + milSec * 10), lrcLine};
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 外部提供方法
    // 传入当前播放时间
    public synchronized void changeCurrent(long time, boolean isSeeking) {

        // 手动 seek 需要重新计算 currentline
        if (mTimes.isEmpty()) return;
        if (isSeeking) {
            mNextTime = 0;
            int i = 0;
            for (; i < mTimes.size(); i++) {
                if (mTimes.get(i) > time) {
                    mCurrentLine = i <= 1 ? 0 : i - 1;
                    break;
                }
            }
            if (i == mTimes.size()) mCurrentLine = i <= 1 ? 0 : i - 1;
            LogUtils.d(TAG, "changeCurrent() called with: " + "time = [" + time + "], mCurrentLine = [" + mCurrentLine + "]");
        }

        // 如果当前时间小于下一句开始的时间
        // 直接return
        if (mNextTime > time) {
            LogUtils.i(TAG, "changeCurrent() called with: " + "time = [" + time + "], mNextTime = [" + mNextTime + "], isSeeking = [" + isSeeking + "]");
            return;
        }

        // 每次进来都遍历存放的时间
//        LogUtils.d(TAG, "changeCurrent() called with: " + "time = [" + time + "], isSeeking = [" + isSeeking + "]");
        int i = 0;
        for (i = 0; i < mTimes.size(); i++) {
            // 发现这个时间大于传进来的时间
            // 那么现在就应该显示这个时间前面的对应的那一行
            // 每次都重新显示，是不是要判断：现在正在显示就不刷新了
            if (mTimes.get(i) > time && i >= mCurrentLine + 1) {
                System.out.println(TAG + " 换");
                mNextTime = mTimes.get(i);
                mCurrentLine = i <= 1 ? 0 : i - 1;
                postInvalidate();
                break;
            }
        }
        /* >AndyChang: [16-12-30 11:03] 如果歌词中的每一条都是小于现有时间的，那么应该显示最后一行 */
        if (i == mTimes.size()) {
            mCurrentLine = i <= 1 ? 0 : i - 1;
            postInvalidate();
        }
    }

    // 外部提供方法
    // 设置lrc的路径
    public void setLrcPath(String path) throws Exception {
        File file = new File(path);
        setLrcFile(file);
    }

    public void setLrcFile(File file) throws Exception {
        LogUtils.d(TAG, "setLrcFile() called with: " + "file = [" + file + "]", 2);
        clearData();
        if (!file.exists()) {
            LogUtils.d(TAG, "setLrcFile() called with: lrc not found... " + "file = [" + file + "]");
            throw new Exception("lrc not found...");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file)));

        String line = "";
        String[] arr;
        while (null != (line = reader.readLine())) {
            arr = parseLine(line);
            if (null == arr) {
                continue;
            }

            // 如果解析出来只有一个
            if (1 == arr.length) {
                String last = mLrcs.remove(mLrcs.size() - 1);
                mLrcs.add(last + arr[0]);
                continue;
            }
            mTimes.add(Long.parseLong(arr[0]));
            mLrcs.add(arr[1]);
        }
        if (mLrcs.isEmpty()) {
            LogUtils.i(TAG, "setLrcFile() called with: isEmpty  delete" + "file = [" + file + "]");
            file.delete();
        } else {
            postInvalidate();
        }

        reader.close();
        changeCurrent(0, true);
    }

    public void clearData() {
        hintStr = "";
        mLrcs.clear();
        mTimes.clear();
        mCurrentLine = 0;
        mNextTime = 0;
    }

    // 外部提供方法
    // 设置lrc的路径
    public void setLrcStr(String lrcStr) {
        LogUtils.d(TAG, "setLrcStr() called with: " + "lrcStr = [" + lrcStr + "]");
        clearData();
        BufferedReader reader = new BufferedReader(new StringReader(lrcStr));
        String line = "";
        String[] arr;
        try {
            while (null != (line = reader.readLine())) {
                arr = parseLine(line);
                if (null == arr) {
                    continue;
                }

                // 如果解析出来只有一个
                if (1 == arr.length) {
                    String last = mLrcs.remove(mLrcs.size() - 1);
                    mLrcs.add(last + arr[0]);
                    continue;
                }
                mTimes.add(Long.parseLong(arr[0]));
                mLrcs.add(arr[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeCurrent(0, true);

    }

    // 外部提供方法
    // 设置背景图片
    public void setBackground(Bitmap bmp) {
        mBackground = bmp;
    }
}
