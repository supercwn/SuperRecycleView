package com.superrecycleview.library.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.superrecycleview.library.R;
import com.superrecycleview.library.adapter.DragAdapter;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.callback.ItemDragCallback;
import com.superrecycleview.superlibrary.callback.OnItemDragListener;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by super南仔 on 07/28/16.
 * blog: http://supercwn.github.io/
 * GitHub: https://github.com/supercwn
 */
public class DragActivity extends Activity implements SuperRecyclerView.LoadingListener, SuperBaseAdapter.OnItemClickListener {

    private SuperRecyclerView mRecyclerView;
    private List<String> mData;
    private DragAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private ItemDragCallback mItemDragAndSwipeCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_layout);
        mRecyclerView = (SuperRecyclerView)findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setRefreshEnabled(true);
        mRecyclerView.setLoadMoreEnabled(true);
        mRecyclerView.setLoadingListener(this);

        mData = generateData(50);
        OnItemDragListener listener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                mRecyclerView.setRefreshEnabled(false);//在开始的时候需要禁止下拉刷新，不然在下滑动的时候会与下拉刷新冲突
                BaseViewHolder holder = ((BaseViewHolder)viewHolder);
                holder.setTextColor(R.id.tv, Color.WHITE);
                ((CardView)viewHolder.itemView).setCardBackgroundColor(ContextCompat.getColor(DragActivity.this, R.color.colorAccent));
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                mRecyclerView.setRefreshEnabled(true);//在结束之后需要开启下拉刷新
                BaseViewHolder holder = ((BaseViewHolder)viewHolder);
                holder.setTextColor(R.id.tv, Color.BLACK);
                ((CardView)viewHolder.itemView).setCardBackgroundColor(Color.WHITE);
            }
        };
        mAdapter = new DragAdapter(this,mData);
        mItemDragAndSwipeCallback = new ItemDragCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mItemDragAndSwipeCallback.setDragMoveFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN);
        mAdapter.enableDragItem(mItemTouchHelper);
        mAdapter.setOnItemDragListener(listener);

        mAdapter.setOnItemClickListener(this);

       View headerView = getLayoutInflater().inflate(R.layout.view_header_layout, (ViewGroup) mRecyclerView.getParent(), false);
        View footerView = getLayoutInflater().inflate(R.layout.view_footer_layout, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.addHeaderView(headerView);
        mAdapter.addFooterView(footerView);

        mRecyclerView.setAdapter(mAdapter);
    }

    private List<String> generateData(int size) {
        ArrayList<String> data = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            data.add("item " + i);
        }
        return data;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.completeRefresh();
            }
        },2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.completeLoadMore();
                mRecyclerView.setNoMore(true);
            }
        },2000);
    }

    @Override
    public void onItemClick(View view, Object item, int position) {
        Toast.makeText(this,mData.get(position),Toast.LENGTH_SHORT).show();
    }

}
