package com.superrecycleview.library.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.superrecycleview.library.R;
import com.superrecycleview.library.adapter.SwipeMenuAdapter;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;
import com.superrecycleview.superlibrary.recycleview.swipemenu.SuperSwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by super南仔 on 07/28/16.
 * blog: http://supercwn.github.io/
 * GitHub: https://github.com/supercwn
 */
public class SwipeMenuActivity extends Activity implements SuperRecyclerView.LoadingListener, SuperBaseAdapter.OnItemClickListener {

    private SuperSwipeMenuRecyclerView superSwipeMenuRecyclerView;
    private SwipeMenuAdapter swipeMenuAdapter;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipemenu_layout);
        initData();
        initView();
        initAdapter();
    }

    private void initView(){
        superSwipeMenuRecyclerView = (SuperSwipeMenuRecyclerView) findViewById(R.id.super_swipemenu_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        superSwipeMenuRecyclerView.setLayoutManager(layoutManager);
        superSwipeMenuRecyclerView.setRefreshEnabled(true);
        superSwipeMenuRecyclerView.setLoadMoreEnabled(true);
        superSwipeMenuRecyclerView.setLoadingListener(this);
        superSwipeMenuRecyclerView.setSwipeDirection(SuperSwipeMenuRecyclerView.DIRECTION_LEFT);//左滑（默认）
        // superSwipeMenuRecyclerView.setSwipeDirection(SuperSwipeMenuRecyclerView.DIRECTION_LEFT);//右滑
    }
    private void initAdapter(){
        swipeMenuAdapter = new SwipeMenuAdapter(this,dataList);
        swipeMenuAdapter.setOnItemClickListener(this);
        swipeMenuAdapter.setOnItemChildClickListener(new SuperBaseAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(SuperBaseAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.btOpen:
                        Toast.makeText(SwipeMenuActivity.this,"show open",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btDelete:
                        Toast.makeText(SwipeMenuActivity.this,"show delete",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btFavorite:
                        Toast.makeText(SwipeMenuActivity.this,"show Favorite",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btGood:
                        Toast.makeText(SwipeMenuActivity.this,"show good",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.image_iv:
                        Toast.makeText(SwipeMenuActivity.this,"show image",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        superSwipeMenuRecyclerView.setAdapter(swipeMenuAdapter);
    }

    private void initData(){
        for (int i = 0 ; i < 20 ; i++){
            dataList.add("Conetnt"+(i+1));
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                superSwipeMenuRecyclerView.completeRefresh();
            }
        },2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                superSwipeMenuRecyclerView.completeLoadMore();
            }
        },2000);
    }

    @Override
    public void onItemClick(View view, Object item, int position) {
        Toast.makeText(this,dataList.get(position),Toast.LENGTH_SHORT).show();
    }
}
