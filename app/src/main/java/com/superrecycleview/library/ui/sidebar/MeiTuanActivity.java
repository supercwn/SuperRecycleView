
package com.superrecycleview.library.ui.sidebar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.TextView;

import com.superrecycleview.library.R;
import com.superrecycleview.library.adapter.sidebar.MeiTuanAdapter;
import com.superrecycleview.library.adapter.sidebar.MeituanHeaderAdapter;
import com.superrecycleview.library.bean.sidebar.MeituanHeaderBean;
import com.superrecycleview.library.bean.sidebar.WeChatBean;
import com.superrecycleview.superlibrary.callback.SuspensionDecoration;
import com.superrecycleview.superlibrary.sidebar.bean.BaseIndexPinyinBean;
import com.superrecycleview.superlibrary.sidebar.widget.SuperSideBar;
import com.superrecycleview.superlibrary.utils.SuperDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by super南仔 on 2017-05-04. 类备注： 需要传入的参数:
 */
public class MeiTuanActivity extends Activity {

    private RecyclerView recyclerView;
    /**
     * 右侧边栏导航区域
     */
    private SuperSideBar superSideBar;
    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;

    private MeiTuanAdapter meiTuanAdapter;
    private MeituanHeaderAdapter headerAdapter;
    private SuspensionDecoration mDecoration;
    private LinearLayoutManager layoutManager;

    private List<WeChatBean> mDatas = new ArrayList<>();

    // 设置给InexBar、ItemDecoration的完整数据集
    private List<BaseIndexPinyinBean> mSourceDatas;
    // 头部数据源
    private List<MeituanHeaderBean> mHeaderDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_meituan_layout);
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
    }

    private void initAdapter() {
        mSourceDatas = new ArrayList<>();
        mHeaderDatas = new ArrayList<>();
        List<String> locationCity = new ArrayList<>();
        locationCity.add("定位中");
        mHeaderDatas.add(new MeituanHeaderBean(locationCity, "定位城市", "定"));
        List<String> recentCitys = new ArrayList<>();
        mHeaderDatas.add(new MeituanHeaderBean(recentCitys, "最近访问城市", "近"));
        List<String> hotCitys = new ArrayList<>();
        mHeaderDatas.add(new MeituanHeaderBean(hotCitys, "热门城市", "热"));
        mSourceDatas.addAll(mHeaderDatas);

        meiTuanAdapter = new MeiTuanAdapter(this, mDatas);

        headerAdapter = new MeituanHeaderAdapter(meiTuanAdapter, this);
        headerAdapter.setHeaderView(0, R.layout.view_meituan_header_layout, mHeaderDatas.get(0));
        headerAdapter.setHeaderView(1, R.layout.view_meituan_header_layout, mHeaderDatas.get(1));
        headerAdapter.setHeaderView(2, R.layout.view_meituan_header_layout, mHeaderDatas.get(2));

        recyclerView.setAdapter(headerAdapter);

        mDecoration = new SuspensionDecoration(this, mDatas)
                .setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35,
                        getResources().getDisplayMetrics()))
                .setColorTitleBg(0xffefefef)
                .setTitleFontSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
                        getResources().getDisplayMetrics()))
                .setColorTitleFont(
                        MeiTuanActivity.this.getResources().getColor(android.R.color.black))
                .setHeaderViewCount(headerAdapter.getHeaderViewCount() - mHeaderDatas.size());
        recyclerView.addItemDecoration(mDecoration);// 添加字母悬浮利用添加分割线的方式
        recyclerView.addItemDecoration(
                SuperDivider.newBitmapDivider().setStartSkipCount(1).setEndSkipCount(0));
        superSideBar.setmPressedShowTextView(mTvSideBarHint)// 设置滑动的字母A,B,C
                .setNeedRealIndex(true)// 设置需要真实的索引
                .setmLayoutManager(layoutManager);// 设置RecyclerView的LayoutManager
    }

    private void initData() {
        String[] soureData = getResources().getStringArray(R.array.provinces);
        for (int i = 0; i < soureData.length; i++) {
            WeChatBean weChatBean = new WeChatBean();
            weChatBean.setName(soureData[i]);
            mDatas.add(weChatBean);
        }
        MeituanHeaderBean header1 = mHeaderDatas.get(0);
        header1.getCityList().clear();
        header1.getCityList().add("珠海");

        MeituanHeaderBean header2 = mHeaderDatas.get(1);
        List<String> recentCitys = new ArrayList<>();
        recentCitys.add("日本");
        recentCitys.add("北京");
        header2.setCityList(recentCitys);

        MeituanHeaderBean header3 = mHeaderDatas.get(2);
        List<String> hotCitys = new ArrayList<>();
        hotCitys.add("上海");
        hotCitys.add("北京");
        hotCitys.add("杭州");
        hotCitys.add("广州");
        header3.setCityList(hotCitys);

        mSourceDatas.addAll(mDatas);

        superSideBar.getDataHelper().sortSourceDatas(mDatas);
        meiTuanAdapter.setDatas(mDatas);
        meiTuanAdapter.notifyDataSetChanged();
        superSideBar.setSourceDatas(mSourceDatas);// 设置数据
        mDecoration.setDatas(mSourceDatas);
        headerAdapter.notifyDataSetChanged();
    }

}
