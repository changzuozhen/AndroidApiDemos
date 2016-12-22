package com.example.android.apis.AndyTest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;

import com.example.android.apis.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import commontools.LogUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AndyTestRxJavaActivity extends Activity {

    private static final String TAG = "AndyTestRxJavaActivity";
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
                btn1.setTag(1);

                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        for (int i = 0; i < 10; i++) {
                            subscriber.onNext("" + i);
                            try {
                                Thread.currentThread().sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        subscriber.onCompleted();
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onStart() {
                                super.onStart();
                                LogUtils.d(TAG, "onStart() called with: " + "");
                            }

                            @Override
                            public void onCompleted() {
                                LogUtils.d(TAG, "onCompleted() called with: " + "");
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtils.d(TAG, "onError() called with: " + "e = [" + e + "]");
                            }

                            @Override
                            public void onNext(String s) {
                                LogUtils.d(TAG, "onNext() called with: " + "s = [" + s + "]");
                            }
                        });

                break;
            case 1:
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
                btn2.setTag(1);
                break;
            case 1:
                btn2.setTag(0);
                break;
        }
    }

    private void stop() {
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
}
