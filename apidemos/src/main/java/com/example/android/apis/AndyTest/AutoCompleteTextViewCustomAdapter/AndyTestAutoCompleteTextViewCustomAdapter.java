package com.example.android.apis.AndyTest.AutoCompleteTextViewCustomAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.android.apis.R;

import java.util.ArrayList;
import java.util.List;

import commontools.LogUtils;

public class AndyTestAutoCompleteTextViewCustomAdapter extends Activity implements AdapterView.OnItemClickListener {
    private static final String TAG = "AndyTestAutoCompleteTextViewCustomAdapter";
    List<PhoneContact> mList;
    private AutoCompleteTextView mACTV;
    private Button btn1;
    private Button btn2;
    private PhoneAdapter mAdapter;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn1:
                    mAdapter.notifyDataSetInvalidated();
                    break;
                case R.id.btn2:
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andy_test_auto_complete_textview_customadapter);

        buildAppData();
        findView();
    }

    private void buildAppData() {
        String[] names = {"abc", "allen", "bird", "bike", "book", "cray",
                "david", "demon", "eclipse", "felling", "frank", "google",
                "green", "hill", "hook", "jin zhiwen", "jack", "jay", "king", "kevin", "kobe",
                "lily", "lucy", "mike", "nike", "nail", "open", "open cv",
                "panda", "pp", "queue", "ray allen", "risk", "tim cook", "T-MAC", "tony allen",
                "x man", "x phone", "yy", "world", "w3c", "zoom", "zhu ziqing"};

        mList = new ArrayList<PhoneContact>();

        for (int i = 0; i < names.length; i++) {
            PhoneContact pc = new PhoneContact(100 + i, names[i], "1861234567" + i, names[i].concat("@gmail.com"));
            mList.add(pc);
        }

    }

    private void findView() {

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        btn1.setOnClickListener(onClickListener);
        btn2.setOnClickListener(onClickListener);

        mACTV = (AutoCompleteTextView) findViewById(R.id.mACTV);
        mAdapter = new PhoneAdapter(mList, getApplicationContext());
        mACTV.setAdapter(mAdapter);
        mACTV.setThreshold(1);    //设置输入一个字符 提示，默认为2

        mACTV.setOnItemClickListener(this);
        mACTV.requestFocus();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        PhoneContact pc = mList.get(position);
        mACTV.setText(pc.getName() + " " + pc.getPhone());
        LogUtils.d(TAG, "onItemClick() called with: " + " position = [" + position + "], pc = [" + pc + "]");
    }

}
