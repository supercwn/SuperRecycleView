package com.superrecycleview.library.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.superrecycleview.library.R;
import com.superrecycleview.library.adapter.RefreshAndLoadMoreAdapter;
import com.superrecycleview.library.utils.ColorUtil;
import com.superrecycleview.library.utils.DensityUtil;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by super南仔 on 07/28/16.
 * blog: http://supercwn.github.io/
 * GitHub: https://github.com/supercwn
 */
public class GradualChangeActivity extends Activity implements SuperRecyclerView.LoadingListener {

    private SuperRecyclerView superRecyclerView;
    private RefreshAndLoadMoreAdapter mAdapter;
    private List<String> dataList = new ArrayList<>();

    private int adViewHeight = 180; // 广告视图的高度
    private int adViewTopSpace; // 广告视图距离顶部的距离
    private int titleViewHeight = 50; // 标题栏的高度
    private boolean isScrollIdle = true; // recycleView是否在滑动
    private RelativeLayout rlBar;
    private View viewTitleBg;
    private View viewActionMoreBg;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradual_change_layout);
        initView();
        initData();
        initAdapter();
    }

    private void initView(){
        superRecyclerView = (SuperRecyclerView) findViewById(R.id.rv_list);
        rlBar = (RelativeLayout) findViewById(R.id.rl_bar);
        viewTitleBg = findViewById(R.id.view_title_bg);
        viewActionMoreBg = findViewById(R.id.view_action_more_bg);
        tv_title = (TextView) findViewById(R.id.tv_title);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        superRecyclerView.setLayoutManager(layoutManager);
        superRecyclerView.setRefreshEnabled(true);
        superRecyclerView.setLoadMoreEnabled(true);
        superRecyclerView.setLoadingListener(this);
        superRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrollIdle = (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isScrollIdle && adViewTopSpace < 0) return;
                adViewTopSpace = DensityUtil.px2dip(GradualChangeActivity.this, mAdapter.getHeaderLayout().getTop());
                adViewHeight = DensityUtil.px2dip(GradualChangeActivity.this, mAdapter.getHeaderLayout().getHeight());
                if(-adViewTopSpace<=50){
                    tv_title.setVisibility(View.GONE);
                } else {
                    tv_title.setVisibility(View.VISIBLE);
                }
                handleTitleBarColorEvaluate();
            }
        });
    }

    private void initData(){
        for (int i = 1 ; i<= 30;i++){
            dataList.add("Super"+i);
        }
    }

    private void initAdapter(){
        mAdapter = new RefreshAndLoadMoreAdapter(this,dataList);
        addHeadView();
        superRecyclerView.setAdapter(mAdapter);
    }

    private void addHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.activity_ad_header_layout, (ViewGroup) superRecyclerView.getParent(), false);
        mAdapter.addHeaderView(headView);
    }


    // 处理标题栏颜色渐变
    private void handleTitleBarColorEvaluate() {
        float fraction;
        rlBar.setAlpha(1f);
        if (adViewTopSpace > 5) {
            fraction = 1f - adViewTopSpace * 1f / 60;
            if (fraction < 0f) fraction = 0f;
            rlBar.setAlpha(fraction);
            return ;
        }
        float space = Math.abs(adViewTopSpace) * 1f;
        fraction = space / (adViewHeight - titleViewHeight);
        if (fraction < 0f) fraction = 0f;
        if (fraction > 1f) fraction = 1f;
        rlBar.setAlpha(1f);
        if (fraction >= 1f) {
            viewTitleBg.setAlpha(0f);
            viewActionMoreBg.setAlpha(0f);
            rlBar.setBackgroundColor(this.getResources().getColor(R.color.orange));
        } else {
            viewTitleBg.setAlpha(1f - fraction);
            viewActionMoreBg.setAlpha(1f - fraction);
            rlBar.setBackgroundColor(ColorUtil.getNewColorByStartEndColor(this, fraction, R.color.transparent, R.color.orange));
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                superRecyclerView.completeRefresh();
            }
        }, 3000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                superRecyclerView.completeLoadMore();
            }
        }, 3000);
    }
}
