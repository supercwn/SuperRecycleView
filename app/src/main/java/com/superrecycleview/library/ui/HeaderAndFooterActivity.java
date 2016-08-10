package com.superrecycleview.library.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.superrecycleview.library.R;
import com.superrecycleview.library.adapter.HeaderFooterAdapter;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by super南仔 on 07/28/16.
 * blog: http://supercwn.github.io/
 * GitHub: https://github.com/supercwn
 */
public class HeaderAndFooterActivity extends Activity implements View.OnClickListener, SuperRecyclerView.LoadingListener, SuperBaseAdapter.OnItemClickListener {

    private SuperRecyclerView superRecyclerView;
    private View headerView,footerView;
    private HeaderFooterAdapter mAdapter;
    private List<String> dataList = new ArrayList<>();
    private TextView tvAddHeader,tvAddFotter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_footer_layout);
        initView();
        initData();
        initAdapter();
    }

    private void initView(){
        tvAddHeader = (TextView) findViewById(R.id.tv_add_headerview);
        tvAddFotter = (TextView) findViewById(R.id.tv_add_footerview);
        tvAddHeader.setOnClickListener(this);
        tvAddFotter.setOnClickListener(this);

        superRecyclerView = (SuperRecyclerView) findViewById(R.id.superrecycleview_header_footer);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        superRecyclerView.setLayoutManager(layoutManager);
        superRecyclerView.setRefreshEnabled(true);
        superRecyclerView.setLoadMoreEnabled(true);
        superRecyclerView.setLoadingListener(this);

        headerView = getLayoutInflater().inflate(R.layout.view_header_layout, (ViewGroup) superRecyclerView.getParent(), false);
        footerView = getLayoutInflater().inflate(R.layout.view_footer_layout, (ViewGroup) superRecyclerView.getParent(), false);
        //添加头部的点击事件
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HeaderAndFooterActivity.this,"你点击了头部",Toast.LENGTH_SHORT).show();
            }
        });

        //添加底部的点击事件
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HeaderAndFooterActivity.this,"你点击了底部",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData(){
        for (int i = 1 ; i <= 10; i++){
            dataList.add("text"+i);
        }
    }

    private void initAdapter(){
        mAdapter = new HeaderFooterAdapter(this,dataList);
        mAdapter.addHeaderView(headerView);
        mAdapter.addFooterView(footerView);
        mAdapter.setOnItemClickListener(this);
        superRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_add_headerview:
                addHeadView();
                break;
            case R.id.tv_add_footerview:
                addFootView();
                break;
        }
    }

    //测试添加头部
    private void addHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.view_header_layout, (ViewGroup) superRecyclerView.getParent(), false);
        mAdapter.addHeaderView(headView);
    }

    //测试添加尾部
    private void addFootView() {
        View headView = getLayoutInflater().inflate(R.layout.view_footer_layout, (ViewGroup) superRecyclerView.getParent(), false);
        mAdapter.addFooterView(headView);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(HeaderAndFooterActivity.this,"执行刷新完成",Toast.LENGTH_SHORT).show();
                superRecyclerView.completeRefresh();
            }
        },2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(HeaderAndFooterActivity.this,"执行加载完成",Toast.LENGTH_SHORT).show();
                superRecyclerView.completeLoadMore();
            }
        },2000);
    }

    @Override
    public void onItemClick(View view, Object item, int position) {
        Toast.makeText(this,"你点击了"+dataList.get(position),Toast.LENGTH_SHORT).show();
    }
}
