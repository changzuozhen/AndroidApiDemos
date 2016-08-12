package com.example.android.apis.AndyTest.AutoCompleteTextViewCustomAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.android.apis.R;

import java.util.ArrayList;
import java.util.List;

import commontools.LogUtils;

/**
 * Created by AndyChang on 16/8/11.
 */

public class PhoneAdapter extends BaseAdapter implements Filterable {

    private static final String TAG = "PhoneAdapter";

    private ArrayFilter mFilter;
    private List<PhoneContact> mList;
    private Context context;
    private ArrayList<PhoneContact> mUnfilteredData;

    public PhoneAdapter(List<PhoneContact> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {

        LogUtils.v(TAG, "getCount() called with: " + "");
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        LogUtils.d(TAG, "getItem() called with: " + "position = [" + position + "]");
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        LogUtils.d(TAG, "getItemId() called with: " + "position = [" + position + "]");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.phone_item, null);

            holder = new ViewHolder();
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            holder.tv_email = (TextView) view.findViewById(R.id.tv_email);

            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        PhoneContact pc = mList.get(position);

        holder.tv_name.setText("姓名：" + pc.getName());
        holder.tv_phone.setText("电话：" + pc.getPhone());
        holder.tv_email.setText("Email：" + pc.getEmail());

        LogUtils.d(TAG, "getView() called with: " + "position = [" + position + "], data = [" + pc + "]");
        return view;
    }

    @Override
    public android.widget.Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    static class ViewHolder {
        public TextView tv_name;
        public TextView tv_phone;
        public TextView tv_email;
    }

    private class ArrayFilter extends Filter {

        @Override
        protected android.widget.Filter.FilterResults performFiltering(CharSequence prefix) {
            LogUtils.d(TAG, "performFiltering() called with: " + "prefix = [" + prefix + "]");

            android.widget.Filter.FilterResults results = new android.widget.Filter.FilterResults();

            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<PhoneContact>(mList);
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<PhoneContact> list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<PhoneContact> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();

                ArrayList<PhoneContact> newValues = new ArrayList<PhoneContact>(count);

                for (int i = 0; i < count; i++) {
                    PhoneContact pc = unfilteredValues.get(i);
                    if (pc != null) {

                        if (pc.getName() != null && pc.getName().startsWith(prefixString)) {

                            newValues.add(pc);
                        } else if (pc.getEmail() != null && pc.getEmail().startsWith(prefixString)) {

                            newValues.add(pc);
                        }
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                results.values = newValues;
                results.count = newValues.size();
            }
            LogUtils.d(TAG, "performFiltering() returned: " + results.count);

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            //noinspection unchecked
            LogUtils.d(TAG, "publishResults() called with: " + "constraint = [" + constraint + "], results = [" + results.values + "]");
            mList = (List<PhoneContact>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}

