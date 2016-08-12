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

import commontools.LogUtils;

public class AndyTestActivity4 extends Activity implements View.OnClickListener {

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
    Button btn1, btn2, btn3, btn4, btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andy_test);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);

        btn1.setText(bt1Str);
        btn2.setText(bt2Str);
        btn3.setText(bt3Str);
        btn4.setText(bt4Str);
        btn5.setText(bt5Str);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
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

    @Override
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
