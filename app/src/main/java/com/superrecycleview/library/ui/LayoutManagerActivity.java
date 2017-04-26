
package com.superrecycleview.library.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.superrecycleview.library.R;
import com.superrecycleview.library.adapter.LayoutMannagerAdapter;
import com.superrecycleview.superlibrary.recycleview.ProgressStyle;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by super南仔 on 2017-04-26. 类备注： 需要传入的参数:
 */
public class LayoutManagerActivity extends Activity implements SuperRecyclerView.LoadingListener {
    private SuperRecyclerView superRecyclerView;
    private LayoutMannagerAdapter mAdapter;
    private View headerView;
    private List<String> dataList = new ArrayList<>();
    private List<String> tempList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutmanager_layout);
        initView();
        dataList = initData(20);
        initAdapter();
    }

    private void initView() {
        superRecyclerView = (SuperRecyclerView) findViewById(R.id.superrecycleview_layoutmanager);
        // StaggeredGridLayoutManager layoutManager = new
        // StaggeredGridLayoutManager(2,
        // StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        superRecyclerView.setLayoutManager(layoutManager);
        superRecyclerView.setRefreshEnabled(true);// 可以定制是否开启下拉刷新
        superRecyclerView.setLoadMoreEnabled(false);// 可以定制是否开启加载更多
        superRecyclerView.setLoadingListener(this);// 下拉刷新，上拉加载的监听
        superRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);// 下拉刷新的样式
        superRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);// 上拉加载的样式
        superRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);// 设置下拉箭头\
        headerView = getLayoutInflater().inflate(R.layout.view_header_layout,
                (ViewGroup) superRecyclerView.getParent(), false);
    }

    private List<String> initData(int size) {
        dataList.clear();
        for (int i = 1; i <= size; i++) {
            dataList.add("数据" + i);
        }
        return dataList;
    }

    // 模拟加载更多的数据
    private List<String> getDataList(int size) {
        List<String> data = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            data.add("新加的数据" + i);
        }
        return data;
    }

    private void initAdapter() {
        mAdapter = new LayoutMannagerAdapter(this, dataList);
        superRecyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(headerView);
        View headView = getLayoutInflater().inflate(R.layout.view_footer_layout,
                (ViewGroup) superRecyclerView.getParent(), false);
        mAdapter.addFooterView(headView);
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
        if (dataList.size() >= 50) {
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
