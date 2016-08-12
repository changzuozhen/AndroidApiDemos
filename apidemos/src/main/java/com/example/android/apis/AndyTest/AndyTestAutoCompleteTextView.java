package com.example.android.apis.AndyTest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.example.android.apis.AndyTest.CustomView.AutoLineFeedLayout;
import com.example.android.apis.DensityUtil;
import com.example.android.apis.R;

import commontools.LogUtils;

public class AndyTestAutoCompleteTextView extends AppCompatActivity {
    private static final String TAG = "AndyTestAutoCompleteTextView";
    private AutoCompleteTextView autotext;
    private MultiAutoCompleteTextView multiautotext;
    private EditText normalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andy_test_auto_complete_text_view);

        //获取布局文件中的两个控件对象
        normalText = (EditText) findViewById(R.id.normal_text);
        autotext = (AutoCompleteTextView) findViewById(R.id.autotext);
        multiautotext = (MultiAutoCompleteTextView) findViewById(R.id.multiautotext);

        //设置数据源
        String[] autoStrings
//                = new String[]{"New York", "Tokyo", "beijing", "london", "Seoul Special", "Los Angeles", "中国", "上海", "香港", "河南", "周杰伦", "周涛", "周笔畅"};
                = new String[]{"崔健", "朝阳", "曾剑雄", "蔡国庆", "陈汝佳", "陈少华", "陈星", "迟志强", "曹宇", "崔永元", "晨辉", "成震", "陈爽", "陈坤", "苍茫", "陈旭", "常海", "陈宁", "陈尚实", "蔡镇泽", "陈揚杰", "陈见明", "陈杨杰", "陈辉权", "陈龙", "蔡晓恩", "曹玉荣", "陈克华", "陈少云", "陈兴", "陈骄龙", "陈超", "陈柏宇", "陈刚", "陈楚生", "陈崴", "曹轩宾", "陈世维", "陈玉建", "川子", "陈慧恬", "苍宇", "长春虫子", "曹进", "曹磊", "陈凯伦", "陈伟霆", "崔玮", "成都小春", "潮峰", "蔡献华", "秋虹", "陈昱熙", "陈建骐", "丛浩楠", "陈乃荣", "陈玮儒", "常石磊", "赤小豆", "陈烁轩", "程飞鸣", "楚兴元", "陈志远", "陈咏", "柴郡猫", "陈威", "程科", "柴野", "楚博仁", "陈依然", "蔡恒", "蔡星辰", "陈映运", "陈劲", "成林江措", "陈永龙", "程海星", "陈振", "陈娟儿", "丛铭君", "曹群", "成学迅", "陈键耕", "楚奕", "陈黎明", "陈杰瑞", "陈顺", "陈冠锋", "曹震豪", "陈翔", "晨熙", "陈世川", "崔光一", "陈文豪", "陈洪山", "陈绍楠", "陈银清", "春雷", "程伟祥", "崔富帅", "陈子嘟", "常红刚", "曹尔真", "Clown", "陈汉", "陈魁", "陈代全", "崔宇", "陈珧", "曹旭川"};
        //设置ArrayAdapter，并且设定以单行下拉列表风格展示（第二个参数设定）。
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AndyTestAutoCompleteTextView.this,
                android.R.layout.simple_dropdown_item_1line, autoStrings);
        //设置AutoCompleteTextView的Adapter
        autotext.setAdapter(adapter);

        //设置MultiAutoCompleteTextView的Adapter
        multiautotext.setAdapter(adapter);
        //设定选项间隔使用逗号分隔。
        multiautotext.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        multiautotext.requestFocus();


        AutoLineFeedLayout autoll_myshare_explv_child_share = (AutoLineFeedLayout) findViewById(R.id.autoll_myshare_explv_child_share);
        autoll_myshare_explv_child_share.setChildViewWidthSpace(DensityUtil.dip2px(this, 10));
        autoll_myshare_explv_child_share.setChildHeightSpace(DensityUtil.dip2px(this, 10));
        autoll_myshare_explv_child_share.setChildHeight(DensityUtil.dip2px(this, 25));
        autoll_myshare_explv_child_share.setChildWidthRatio(16);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = ((TextView) v).getText().toString();
                LogUtils.d(TAG, "onClick() called with: " + "v = [" + str + "]");
                normalText.setText(str);
                autotext.setText(str);
                multiautotext.setText(str);
                autotext.requestFocus();
            }
        };
        String str = "";
        for (int i = 0; i < autoStrings.length; i++) {
            str += i;
            Button tv = new Button(this);
//            tv.setPadding(0, 0, 0, 0);
            tv.setPadding(16, 0, 16, 0);
            tv.setTextSize(16);
            tv.setGravity(Gravity.CENTER);
            tv.setText(autoStrings[i]);
            tv.setBackgroundColor(Color.parseColor("#ff0000ff"));
            tv.setVisibility(View.VISIBLE);
            tv.setOnClickListener(onClickListener);
            tv.setBackgroundResource(com.tencent.commontools.R.drawable.btn_shape);
//            R.id.btn_shape;

            autoll_myshare_explv_child_share.addView(tv);
        }

    }
}
