package com.tencent.demorecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.demorecyclerview.HomeAdapter.OnItemClickLitener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;
    private StaggeredHomeAdapter mStaggeredHomeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recyclerview);

        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mAdapter = new HomeAdapter(this, mDatas);
        mStaggeredHomeAdapter = new StaggeredHomeAdapter(this, mDatas, LinearLayout.VERTICAL);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mStaggeredHomeAdapter);

        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        initEvent();

    }

    private void initEvent() {
        mAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(HomeActivity.this, position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(HomeActivity.this, position + " long click",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_add:
                mAdapter.addData(1);
                mStaggeredHomeAdapter.addData(1);
                mRecyclerView.setAdapter(mAdapter);
                break;
            case R.id.id_action_delete:
                mAdapter.removeData(1);
                mStaggeredHomeAdapter.removeData(1);
                mRecyclerView.setAdapter(mAdapter);
                break;
            case R.id.id_action_gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
                mRecyclerView.setAdapter(mAdapter);
                break;
            case R.id.id_action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                mRecyclerView.setAdapter(mAdapter);
                break;
            case R.id.id_action_horizontalGridView:
                mStaggeredHomeAdapter.setOrientation(LinearLayout.HORIZONTAL);
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5,
                        StaggeredGridLayoutManager.HORIZONTAL));
                mRecyclerView.setAdapter(mStaggeredHomeAdapter);
                break;
            case R.id.id_action_verticalGridView:
                mStaggeredHomeAdapter.setOrientation(LinearLayout.VERTICAL);
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(6,
                        StaggeredGridLayoutManager.VERTICAL));
                mRecyclerView.setAdapter(mStaggeredHomeAdapter);
                break;
        }
        return true;
    }

}
