/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.apis;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commontools.LogUtils;

public class ApiDemos extends ListActivity {

    private static final String TAG = "ApiDemos";
    private final static Comparator<Map<String, Object>> sDisplayNameComparator =
            new Comparator<Map<String, Object>>() {
                private final Collator collator = Collator.getInstance();

                public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                    return collator.compare(map1.get("title"), map2.get("title"));
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String path = intent.getStringExtra("com.example.android.apis.Path");

        if (path == null) {
            path = "";
        }

        setListAdapter(new SimpleAdapter(this, getData(path),
                android.R.layout.simple_list_item_1, new String[]{"title"},
                new int[]{android.R.id.text1}));
        getListView().setTextFilterEnabled(true);
    }

    protected List<Map<String, Object>> getData(String prefix) {
        List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();

        PackageManager pm = getPackageManager();

//        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
//        mainIntent.addCategory(Intent.CATEGORY_SAMPLE_CODE);
//        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        // https://github.com/android-cn/android-discuss/issues/623
        //分3次加载
        String CATEGORY_SAMPLE_CODE_ONE = "android.intent.category.SAMPLE_CODE_ONE";
        String CATEGORY_SAMPLE_CODE_TWO = "android.intent.category.SAMPLE_CODE_TWO";
        String CATEGORY_SAMPLE_CODE_THREE = "android.intent.category.SAMPLE_CODE_THREE";
        List<ResolveInfo> list = new ArrayList<>();
        Intent mainIntent1 = new Intent(Intent.ACTION_MAIN, null);
        mainIntent1.addCategory(CATEGORY_SAMPLE_CODE_ONE);
        List<ResolveInfo> list1 = pm.queryIntentActivities(mainIntent1, 0);//一定性加载会使报错FAILED BINDER TRANSACTION

        Intent mainIntent2 = new Intent(Intent.ACTION_MAIN, null);
        mainIntent2.addCategory(CATEGORY_SAMPLE_CODE_TWO);
        List<ResolveInfo> list2 = pm.queryIntentActivities(mainIntent2, 0);

        Intent mainIntent3 = new Intent(Intent.ACTION_MAIN, null);
        mainIntent3.addCategory(CATEGORY_SAMPLE_CODE_THREE);
        List<ResolveInfo> list3 = pm.queryIntentActivities(mainIntent3, 0);

        if (list1 != null && list1.size() > 0) {
            list.addAll(list1);
        }

        if (list2 != null && list2.size() > 0) {
            list.addAll(list2);
        }

        if (list3 != null && list3.size() > 0) {
            list.addAll(list3);
        }

        if (null == list)
            return myData;

        String[] prefixPath;
        String prefixWithSlash = prefix;

        if (prefix.equals("")) {
            prefixPath = null;
        } else {
            prefixPath = prefix.split("/");
            prefixWithSlash = prefix + "/";
        }

        int len = list.size();

        Map<String, Boolean> entries = new HashMap<String, Boolean>();

        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null
                    ? labelSeq.toString()
                    : info.activityInfo.name;

            LogUtils.i(TAG, label);
            if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {

                String[] labelPath = label.split("/");

                String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];

                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    addItem(myData, nextLabel, activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
                } else {
                    if (entries.get(nextLabel) == null) {
                        addItem(myData, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }
            }
        }

        Collections.sort(myData, sDisplayNameComparator);
        return myData;
    }

    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, ApiDemos.class);
        result.putExtra("com.example.android.apis.Path", path);
        return result;
    }

    protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> map = (Map<String, Object>) l.getItemAtPosition(position);

        Intent intent = (Intent) map.get("intent");
        startActivity(intent);
    }
}
