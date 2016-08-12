package com.example.android.apis.AndyTest;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.apis.R;

import java.util.ArrayList;

import commontools.LogUtils;

public class AndyTestMediaPlayerActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "AndyTestMediaPlayerActivity";
    int playSong = -1;
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
    private MediaPlayer mMediaPlayer;
    private String path;
    private ArrayList<String> pathArray = new ArrayList<>();

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
//        pathArray.add("/storage/extsd/tencent/wecarmusic/data/cache/101555425.m4a");
        pathArray.add("/mnt/usb/sdd1/test_music/ogg-3 周杰伦-阳光宅男.ogg");
        pathArray.add("/mnt/usb/sdd1/test_music/ac3-9 甘洛凡-至少我们曾经爱过.ac3");
        pathArray.add("setDataSource: /mnt/usb/sdd1/test_music/ac3-4 孤单的呼吸.ac3");
    }

    private void prepare() {

        playSong++;
        path = pathArray.get(playSong);
        if (playSong >= pathArray.size() - 1) playSong = -1;
//        if (path == "") {
//            // Tell the user to provide an audio file URL.
//            Toast
//                    .makeText(
//                            MediaPlayerDemo_Audio.this,
//                            "Please edit MediaPlayer_Audio Activity, "
//                                    + "and set the path variable to your audio file path."
//                                    + " Your audio file must be stored on sdcard.",
//                            Toast.LENGTH_LONG).show();
//
//        }
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        } else {
            mMediaPlayer.reset();
        }
        try {
            LogUtils.d(TAG, "playMusic() called with: " + "path = [" + path + "]");
            mMediaPlayer.setDataSource(path);

            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    LogUtils.d(TAG, "onCompletion() called with: " + "mp = [" + mp + "]" + "path = [" + path + "]");
                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    LogUtils.d(TAG, "onError() called with: " + "mp = [" + mp + "], what = [" + what + "], extra = [" + extra + "]" + "path = [" + path + "]");
                    return true;
                }
            });

            mMediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        mMediaPlayer.start();
    }

    private void start() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

    private void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }

    private void pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    private void reset() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
        }
    }

    private void seek() {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(mMediaPlayer.getDuration() - 1000);
        }
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
