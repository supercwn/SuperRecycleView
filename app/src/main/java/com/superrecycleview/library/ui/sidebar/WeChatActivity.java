
package com.superrecycleview.library.ui.sidebar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.superrecycleview.library.R;
import com.superrecycleview.library.adapter.sidebar.WeChatAdapter;
import com.superrecycleview.library.bean.sidebar.WeChatBean;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.callback.SuspensionDecoration;
import com.superrecycleview.superlibrary.sidebar.widget.SuperSideBar;
import com.superrecycleview.superlibrary.utils.SuperDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by super南仔 on 2017-05-04. 类备注： 需要传入的参数:
 */
public class WeChatActivity extends Activity {
    private static final String INDEX_STRING_TOP = "↑";

    private View headerView, footerView;

    private RecyclerView recyclerView;
    /**
     * 右侧边栏导航区域
     */
    private SuperSideBar superSideBar;
    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;

    private WeChatAdapter weChatAdapter2;
    private SuspensionDecoration mDecoration;
    private LinearLayoutManager layoutManager;

    private List<WeChatBean> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_layout);
        initView();
        initAdapter();
        initData();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.super_recycle_view);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        superSideBar = (SuperSideBar) findViewById(R.id.super_side_bar);
        mTvSideBarHint = (TextView) findViewById(R.id.super_tv_hint);
        headerView = getLayoutInflater().inflate(R.layout.view_header_layout,
                (ViewGroup) recyclerView.getParent(), false);
        footerView = getLayoutInflater().inflate(R.layout.view_footer_layout,
                (ViewGroup) recyclerView.getParent(), false);
        // 添加头部的点击事件
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WeChatActivity.this, "你点击了头部", Toast.LENGTH_SHORT).show();
            }
        });

        // 添加底部的点击事件
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WeChatActivity.this, "你点击了底部", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAdapter() {
        weChatAdapter2 = new WeChatAdapter(this, mDatas);
        recyclerView.setAdapter(weChatAdapter2);
        weChatAdapter2.addFooterView(footerView);
        weChatAdapter2.addHeaderView(headerView);
        mDecoration = new SuspensionDecoration(this, mDatas);
        recyclerView.addItemDecoration(mDecoration);// 添加字母悬浮利用添加分割线的方式
        recyclerView.addItemDecoration(
                SuperDivider.newBitmapDivider().setStartSkipCount(1).setEndSkipCount(0));
        superSideBar.setmPressedShowTextView(mTvSideBarHint)// 设置滑动的字母A,B,C
                .setNeedRealIndex(true)// 设置需要真实的索引
                .setmLayoutManager(layoutManager);// 设置RecyclerView的LayoutManager
        weChatAdapter2.setOnItemClickListener(new SuperBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(WeChatActivity.this,
                        mDatas.get(position).getName() + "positon:" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        WeChatBean weChatBean1 = new WeChatBean();
        weChatBean1.setName("新的朋友");
        weChatBean1.setTop(true);
        weChatBean1.setBaseIndexTag(INDEX_STRING_TOP);

        WeChatBean weChatBean2 = new WeChatBean();
        weChatBean2.setName("群聊");
        weChatBean2.setTop(true);
        weChatBean2.setBaseIndexTag(INDEX_STRING_TOP);

        WeChatBean weChatBean3 = new WeChatBean();
        weChatBean3.setName("标签");
        weChatBean3.setTop(true);
        weChatBean3.setBaseIndexTag(INDEX_STRING_TOP);

        WeChatBean weChatBean4 = new WeChatBean();
        weChatBean4.setName("公众号");
        weChatBean4.setTop(true);
        weChatBean4.setBaseIndexTag(INDEX_STRING_TOP);

        mDatas.add(weChatBean1);
        mDatas.add(weChatBean2);
        mDatas.add(weChatBean3);
        mDatas.add(weChatBean4);
        String[] soureData = getResources().getStringArray(R.array.provinces);
        for (int i = 0; i < soureData.length; i++) {
            WeChatBean weChatBean = new WeChatBean();
            weChatBean.setName(soureData[i]);
            mDatas.add(weChatBean);
        }
        weChatAdapter2.setDatas(mDatas);
        weChatAdapter2.notifyDataSetChanged();
        superSideBar.setSourceDatas(mDatas)// 设置数据
                .invalidate();
        mDecoration.setDatas(mDatas);
        ((TextView) footerView.findViewById(R.id.view_footer_tv))
                .setText("共有" + mDatas.size() + "联系人");
    }

}
