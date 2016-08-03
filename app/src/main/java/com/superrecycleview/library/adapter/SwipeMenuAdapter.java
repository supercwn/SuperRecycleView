package com.superrecycleview.library.adapter;

import android.content.Context;
import android.view.View;

import com.superrecycleview.library.R;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.recycleview.swipemenu.SuperSwipeMenuLayout;

import java.util.List;

/**
 * Created by super南仔 on 07/28/16.
 * blog: http://supercwn.github.io/
 * GitHub: https://github.com/supercwn
 */
public class SwipeMenuAdapter extends SuperBaseAdapter<String>{

    public SwipeMenuAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item, int position) {
        final SuperSwipeMenuLayout superSwipeMenuLayout = (SuperSwipeMenuLayout) holder.itemView;
        superSwipeMenuLayout.setSwipeEnable(true);//设置是否可以侧滑
        if(position%3==0){
            holder.setText(R.id.name_tv,item)
                    .setOnClickListener(R.id.btFavorite,new OnItemChildClickListener())
                    .setOnClickListener(R.id.btGood,new OnItemChildClickListener())
                    .setOnClickListener(R.id.image_iv,new OnItemChildClickListener());
        } else {
            holder.setText(R.id.name_tv,item).setOnClickListener(R.id.btOpen,new OnItemChildClickListener())
                    .setOnClickListener(R.id.btDelete,new OnItemChildClickListener())
                    .setOnClickListener(R.id.image_iv,new OnItemChildClickListener());
            /**
             * 设置可以非滑动触发的开启菜单
             */
            holder.getView(R.id.image_iv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(superSwipeMenuLayout.isOpen()) {
                        superSwipeMenuLayout.closeMenu();
                    } else {
                        superSwipeMenuLayout.openMenu();
                    }
                }
            });
        }
    }
    @Override
    protected int getItemViewLayoutId(int position, String item) {
        if(position%3==0){
            return R.layout.adapter_swipemenu1_layout;
        } else {
            return R.layout.adapter_swipemenu_layout;
        }
    }
}
