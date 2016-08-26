package com.example.android.apis.AndyTest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.apis.AndyTest.CustomView.MyView;
import com.example.android.apis.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AndyTestCustomView extends AppCompatActivity {

    @BindView(R.id.my_view)
    MyView myView;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.seekBar1)
    SeekBar seekBar1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.seekBar2)
    SeekBar seekBar2;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.seekBar3)
    SeekBar seekBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andy_test_custom_view);
        ButterKnife.bind(this);
        SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                switch (seekBar.getId()) {
                    case R.id.seekBar1:
                        text1.setText("" + seekBar.getProgress());
                        myView.setSetup1(seekBar.getProgress());
                        break;
                    case R.id.seekBar2:
                        text2.setText("" + seekBar.getProgress());
                        myView.setSetup2(seekBar.getProgress());
                        break;
                    case R.id.seekBar3:
                        text3.setText("" + seekBar.getProgress());
                        myView.setSetup3(seekBar.getProgress());
                        break;
                }
            }
        };
        seekBar1.setOnSeekBarChangeListener(seekBarListener);
        seekBar2.setOnSeekBarChangeListener(seekBarListener);
        seekBar3.setOnSeekBarChangeListener(seekBarListener);
    }


}
