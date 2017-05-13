
package com.superrecycleview.library.adapter.sidebar;

import android.content.Context;

import com.superrecycleview.library.R;
import com.superrecycleview.library.bean.sidebar.WeChatBean;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.recycleview.swipemenu.SuperSwipeMenuLayout;

import java.util.List;

/**
 * Created by super南仔 on 2017-05-04. 类备注： 需要传入的参数:
 */
public class SlideSwipeAdapter extends SuperBaseAdapter<WeChatBean> {

    public SlideSwipeAdapter(Context context, List<WeChatBean> data) {
        super(context, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, WeChatBean item, int position) {
        holder.setIsRecyclable(false);// 关闭Adapter的复用
        final SuperSwipeMenuLayout superSwipeMenuLayout = (SuperSwipeMenuLayout) holder.itemView;
        superSwipeMenuLayout.setSwipeEnable(true);// 设置是否可以侧滑
        holder.setText(R.id.super_tv_name, item.getName()).setOnClickListener(R.id.btnDel,
                new OnItemChildClickListener());
    }

    @Override
    protected int getItemViewLayoutId(int position, WeChatBean item) {
        return R.layout.adapter_slide_swipe_menu_layout;
    }
}
