package com.superrecycleview.library.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.superrecycleview.library.R;
import com.superrecycleview.library.adapter.RefreshAndLoadMoreAdapter;
import com.superrecycleview.superlibrary.recycleview.ProgressStyle;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by super南仔 on 07/28/16.
 * blog: http://supercwn.github.io/
 * GitHub: https://github.com/supercwn
 */
public class RefreshAndLoadMoreActivity extends AppCompatActivity implements SuperRecyclerView.LoadingListener {

    private SuperRecyclerView superRecyclerView;
    private RefreshAndLoadMoreAdapter mAdapter;
    private List<String> dataList = new ArrayList<>();
    private List<String> tempList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_load_layout);
        initView();
        dataList = initData(20);
        initAdapter();
    }

    private void initView() {
        superRecyclerView = (SuperRecyclerView) findViewById(R.id.super_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        superRecyclerView.setLayoutManager(layoutManager);
        superRecyclerView.setRefreshEnabled(true);//可以定制是否开启下拉刷新
        superRecyclerView.setLoadingMoreEnabled(true);//可以定制是否开启加载更多
        superRecyclerView.setLoadingListener(this);//下拉刷新，上拉加载的监听
        superRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);//下拉刷新的样式
        superRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);//上拉加载的样式
        superRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);//设置下拉箭头
    }
    private List<String> initData(int size) {
        dataList.clear();
        for (int i = 1; i <= size; i++) {
            dataList.add("数据" + i);
        }
        return dataList;
    }

    //模拟加载更多的数据
    private List<String> getDataList(int size) {
        List<String> data = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            data.add("新加的数据" + i);
        }
        return data;
    }


    private void initAdapter() {
        mAdapter = new RefreshAndLoadMoreAdapter(this,dataList);
        superRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dataList = initData(20);
                superRecyclerView.completeRefresh();
                mAdapter.notifyDataSetChanged();
            }
        }, 3000);
    }

    @Override
    public void onLoadMore() {
        if(dataList.size() >= 50){
            superRecyclerView.setNoMore(true);
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tempList.clear();
                tempList = getDataList(20);
                dataList.addAll(tempList);
                superRecyclerView.completeLoadMore();
            }
        }, 3000);
    }
}
