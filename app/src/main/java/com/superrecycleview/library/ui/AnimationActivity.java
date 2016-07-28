package com.superrecycleview.library.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.superrecycleview.library.R;
import com.superrecycleview.library.adapter.AnimationAdapter;
import com.superrecycleview.superlibrary.adapter.AnimationType;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by super南仔 on 07/28/16.
 * blog: http://supercwn.github.io/
 * GitHub: https://github.com/supercwn
 */
public class AnimationActivity extends AppCompatActivity {

    private SuperRecyclerView superRecyclerView;
    private AnimationAdapter mAdapter;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_layout);
        initView();
        initData();
        initAdapter();
    }

    private void initView() {
        superRecyclerView = (SuperRecyclerView) findViewById(R.id.superrecycleview_animation);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        superRecyclerView.setLayoutManager(layoutManager);
        superRecyclerView.setRefreshEnabled(false);
        superRecyclerView.setLoadingMoreEnabled(false);
    }

    private void initData() {
        for (int i = 1; i <= 50; i++) {
            dataList.add("Animation"+i);
        }
    }

    private void initAdapter() {
        mAdapter = new AnimationAdapter(this,dataList);
        mAdapter.setItemAnimation(AnimationType.SLIDE_FROM_LEFT);//设置显示的动画
        mAdapter.setShowItemAnimationEveryTime(true);//是否每次都会执行动画,默认是false,该方便测试
        superRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_alpha:
                mAdapter.setItemAnimation(AnimationType.ALPHA);
                break;
            case R.id.action_scale:
                mAdapter.setItemAnimation(AnimationType.SCALE);
                break;
            case R.id.action_slide_from_left:
                mAdapter.setItemAnimation(AnimationType.SLIDE_FROM_LEFT);
                break;
            case R.id.action_slide_from_right:
                mAdapter.setItemAnimation(AnimationType.SLIDE_FROM_RIGHT);
                break;
            case R.id.action_slide_from_bottom:
                mAdapter.setItemAnimation(AnimationType.SLIDE_FROM_BOTTOM);
                break;
            case R.id.action_github:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/supercwn"));
                startActivity(browserIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
