
package com.superrecycleview.library.adapter.sidebar;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.superrecycleview.library.R;
import com.superrecycleview.library.bean.sidebar.MeituanHeaderBean;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperHeaderAndFooterAdapter;

/**
 * Created by zhangxutong . Date: 16/08/28
 */

public class MeituanHeaderAdapter extends SuperHeaderAndFooterAdapter {
    private Context mContext;

    public MeituanHeaderAdapter(RecyclerView.Adapter mInnerAdapter, Context mContext) {
        super(mContext, mInnerAdapter);
        this.mContext = mContext;
    }

    @Override
    protected void onBindHeaderHolder(BaseViewHolder holder, int headerPos, int layoutId,
            Object o) {
        switch (layoutId) {
            case R.layout.view_meituan_header_layout:
                final MeituanHeaderBean meituanHeaderBean = (MeituanHeaderBean) o;
                // 网格
                RecyclerView recyclerView = holder.getView(R.id.header_location_recycleview);
                recyclerView
                        .setAdapter(new HeaderAdapter(mContext, meituanHeaderBean.getCityList()));
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                break;
            // case R.layout.meituan_item_header_top:
            // MeituanTopHeaderBean meituanTopHeaderBean =
            // (MeituanTopHeaderBean) o;
            // holder.setText(R.id.tvCurrent, meituanTopHeaderBean.getTxt());
            // break;
            default:
                break;
        }
    }
}
