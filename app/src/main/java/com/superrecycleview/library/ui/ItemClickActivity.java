package com.superrecycleview.library.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.superrecycleview.library.R;
import com.superrecycleview.library.adapter.ItemClickAdapter;
import com.superrecycleview.library.bean.MultipleItemBean;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by super南仔 on 07/28/16.
 * blog: http://supercwn.github.io/
 * GitHub: https://github.com/supercwn
 */
public class ItemClickActivity extends Activity {

    private SuperRecyclerView superRecyclerView;
    private ItemClickAdapter mAdapter;
    private List<MultipleItemBean> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_click_layout);
        initView();
        initAdapter();
    }

    private void initView(){
        superRecyclerView = (SuperRecyclerView) findViewById(R.id.super_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        superRecyclerView.setLayoutManager(layoutManager);
        superRecyclerView.setRefreshEnabled(false);
        superRecyclerView.setLoadingMoreEnabled(false);
    }

    private void initAdapter(){
        dataList = getItemData();
        mAdapter = new ItemClickAdapter(this,dataList);

        /**
         * add item click event
         */
        mAdapter.setOnItemClickListener(new SuperBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object item, int position) {
                Toast.makeText(ItemClickActivity.this,"you click item"+position,Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * add item long click event
         */
        mAdapter.setOnItemLongClickListener(new SuperBaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, Object item, int position) {
                Toast.makeText(ItemClickActivity.this,"you long click item"+position,Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * add item child click event
         */
        mAdapter.setOnItemChildClickListener(new SuperBaseAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(SuperBaseAdapter adapter, View view, int position) {
                if(view.getId() == R.id.name_tv){
                    Toast.makeText(ItemClickActivity.this,"you item child click"+dataList.get(position).getName(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**
         * add item child long click event
         */
        mAdapter.setOnItemChildLongClickListener(new SuperBaseAdapter.OnRecyclerViewItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(SuperBaseAdapter adapter, View view, int position) {
                if(view.getId() == R.id.image_iv){
                    Toast.makeText(ItemClickActivity.this,"you item child long click",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        superRecyclerView.setAdapter(mAdapter);
    }

    //初始化数据
    public static List<MultipleItemBean> getItemData() {
        List<MultipleItemBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MultipleItemBean multipleItem = new MultipleItemBean();
            multipleItem.setName("item" + i);
            multipleItem.setType(i);
            multipleItem.setInfo("备注" + i);
            list.add(multipleItem);
        }
        return list;
    }
}
