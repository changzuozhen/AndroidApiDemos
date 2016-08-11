package com.example.android.apis.AndyTest.AutoCompleteTextViewCustomAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.example.android.apis.R;

import java.util.ArrayList;
import java.util.List;

public class AndyTestAutoCompleteTextViewCustomAdapter extends Activity implements AdapterView.OnItemClickListener {
    List<PhoneContact> mList;
    private AutoCompleteTextView mACTV;

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
        mACTV = (AutoCompleteTextView) findViewById(R.id.mACTV);
        PhoneAdapter mAdapter = new PhoneAdapter(mList, getApplicationContext());
        mACTV.setAdapter(mAdapter);
        mACTV.setThreshold(1);    //设置输入一个字符 提示，默认为2

        mACTV.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        PhoneContact pc = mList.get(position);
        mACTV.setText(pc.getName() + " " + pc.getPhone());
    }

}
