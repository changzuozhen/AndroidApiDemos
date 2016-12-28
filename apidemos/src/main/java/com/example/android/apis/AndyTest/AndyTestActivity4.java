package com.example.android.apis.AndyTest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.android.apis.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import commontools.LogUtils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class AndyTestActivity4 extends Activity {

    private static final String TAG = "AndyTestActivity";
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
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    private Subscription subscription;
    private GestureDetector gestureDetector1;
    private GestureDetector gestureDetector2;
    private GestureDetector gestureDetector3;

    @Override
    protected void onPause() {
        super.onPause();
        subscription.unsubscribe();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andy_test);
        ButterKnife.bind(this);

        btn1.setText(bt1Str);
        btn2.setText(bt2Str);
        btn3.setText(bt3Str);
        btn4.setText(bt4Str);
        btn5.setText(bt5Str);
        gestureDetector1 = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            private static final String TAG = "GestureDetector1";

            @Override
            public boolean onDown(MotionEvent e) {
                LogUtils.d(TAG, "onDown() called with: ");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                LogUtils.d(TAG, "onShowPress() called with: ");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                LogUtils.d(TAG, "onSingleTapUp() called with: ");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                LogUtils.d(TAG, "onScroll() called with: distanceX = [" + distanceX + "], distanceY = [" + distanceY + "]");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                LogUtils.d(TAG, "onLongPress() called with: ");

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                LogUtils.d(TAG, "onFling() called with: velocityX = [" + velocityX + "], velocityY = [" + velocityY + "]");
                return false;
            }
        });
        gestureDetector2 = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            private static final String TAG = "GestureDetector2";

            @Override
            public boolean onDown(MotionEvent e) {
                LogUtils.d(TAG, "onDown() called with: ");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                LogUtils.d(TAG, "onShowPress() called with: ");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                LogUtils.d(TAG, "onSingleTapUp() called with: ");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                LogUtils.d(TAG, "onScroll() called with: distanceX = [" + distanceX + "], distanceY = [" + distanceY + "]");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                LogUtils.d(TAG, "onLongPress() called with: ");

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                LogUtils.d(TAG, "onFling() called with: velocityX = [" + velocityX + "], velocityY = [" + velocityY + "]");
                return false;
            }
        });
        gestureDetector3 = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            private static final String TAG = "GestureDetector3";

            @Override
            public boolean onDown(MotionEvent e) {
                LogUtils.d(TAG, "onDown() called with: ");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                LogUtils.d(TAG, "onShowPress() called with: ");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                LogUtils.d(TAG, "onSingleTapUp() called with: ");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                LogUtils.d(TAG, "onScroll() called with: distanceX = [" + distanceX + "], distanceY = [" + distanceY + "]");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                LogUtils.d(TAG, "onLongPress() called with: ");

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                LogUtils.d(TAG, "onFling() called with: velocityX = [" + velocityX + "], velocityY = [" + velocityY + "]");
                return false;
            }
        });
        gestureDetector3.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            private static final String TAG = "OnDoubleTapListener";

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                LogUtils.d(TAG, "onSingleTapConfirmed() called with: " + "e = [" + e + "]");
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                LogUtils.d(TAG, "onDoubleTap() called with: " + "e = [" + e + "]");
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                LogUtils.d(TAG, "onDoubleTapEvent() called with: " + "e = [" + e + "]");
                return false;
            }
        });
        btn4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector3.onTouchEvent(event);
                return false;
            }
        });
        btn5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector2.onTouchEvent(event);
                return false;
            }
        });
    }

    //下面实现的这些接口负责处理所有在该Activity上发生的触碰屏幕相关的事件
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return gestureDetector1.onTouchEvent(e);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void prepare() {

        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice = "" + tm.getSubscriberId();// 得到用户Id（IMSI）
        LogUtils.d(TAG, "getDeviceId() IMSI : " + tmDevice);


        int value = 0;
        if (btn1.getTag() != null) {
            value = (int) btn1.getTag();
        }
        switch (value) {
            case 0:
                Observable
                        .range(1, 10)
                        .subscribeOn(Schedulers.computation())
                        .filter(new Func1<Integer, Boolean>() {
                            @Override
                            public Boolean call(Integer integer) {
                                if (integer % 3 == 0) LogUtils.d(TAG, "call: " + integer);
                                return integer % 3 == 0;
                            }
                        })
                        .map(new Func1<Integer, Integer>() {
                            @Override
                            public Integer call(Integer integer) {
                                LogUtils.d(TAG, "call: " + integer);
                                return integer * 10;
                            }
                        })
                        .takeLastBuffer(2)
                        .map(new Func1<List<Integer>, Integer>() {
                            @Override
                            public Integer call(List<Integer> integers) {
                                for (int i = 0; i < integers.size(); i++) {
                                    LogUtils.d(TAG, "call: " +
                                            "i:" + i +
                                            ":" + integers.get(i));
                                }
                                return integers.size();
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                LogUtils.d(TAG, "call: " + integer);
                            }
                        });
                btn1.setTag(1);
                break;
            case 1:
                Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 50; i++) {
                            subscriber.onNext(i);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .sample(1, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                LogUtils.d(TAG, "call: i:" + integer);
                            }
                        });
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
                subscription = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 50; i++) {
                            subscriber.onNext(i);
                            try {
                                Thread.sleep(i * 100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                        .subscribeOn(Schedulers.io())
//                        .throttleWithTimeout(1, TimeUnit.SECONDS)
                        .debounce(1, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                LogUtils.d(TAG, "call: i:" + integer);
                            }
                        });
                btn2.setTag(1);
                break;
            case 1:
                btn2.setTag(0);
                break;
        }
    }

    private void stop() {
        testReflect();
    }

    private void reset() {
    }

    private void seek() {
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5})
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

    private void testReflect() {
        try {
            Class<?> clazz = Class.forName("com.example.android.apis.AndyTest.CustomView.LrcView");
            LogUtils.d(TAG, "===============本类属性===============");
            // 取得本类的全部属性
            Field[] field = clazz.getDeclaredFields();
            for (int i = 0; i < field.length; i++) {
                // 权限修饰符
                int mo = field[i].getModifiers();
                String priv = Modifier.toString(mo);
                // 属性类型
                Class<?> type = field[i].getType();
                LogUtils.d(TAG, priv + " " + type.getName() + " " + field[i].getName() + ";");
            }

            LogUtils.d(TAG, "==========实现的接口或者父类的属性==========");
            // 取得实现的接口或者父类的属性
            Field[] filed1 = clazz.getFields();
            for (int j = 0; j < filed1.length; j++) {
                // 权限修饰符
                int mo = filed1[j].getModifiers();
                String priv = Modifier.toString(mo);
                // 属性类型
                Class<?> type = filed1[j].getType();
                LogUtils.d(TAG, priv + " " + type.getName() + " " + filed1[j].getName() + ";");
            }

            Method method[] = clazz.getMethods();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < method.length; ++i) {
                Class<?> returnType = method[i].getReturnType();
                Class<?> para[] = method[i].getParameterTypes();
                int temp = method[i].getModifiers();
                builder.append(Modifier.toString(temp) + " ");
                builder.append(returnType.getName() + "  ");
                builder.append(method[i].getName() + " ");
                builder.append("(");
                for (int j = 0; j < para.length; ++j) {
                    builder.append(para[j].getName() + " " + "arg" + j);
                    if (j < para.length - 1) {
                        builder.append(",");
                    }
                }
                Class<?> exce[] = method[i].getExceptionTypes();
                if (exce.length > 0) {
                    builder.append(") throws ");
                    for (int k = 0; k < exce.length; ++k) {
                        builder.append(exce[k].getName() + " ");
                        if (k < exce.length - 1) {
                            builder.append(",");
                        }
                    }
                } else {
                    builder.append(")");
                }
                builder.append('\n');
            }
            LogUtils.d(TAG, builder.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
