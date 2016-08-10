package com.superrecycleview.library.adapter;

import android.content.Context;

import com.superrecycleview.library.R;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseDragAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016-08-08.
 */
public class DragAdapter extends SuperBaseDragAdapter<String> {
    public DragAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item, int position) {
        holder.setText(R.id.tv,item);
    }

    @Override
    protected int getItemViewLayoutId(int position, String item) {
        return R.layout.adapter_draggable_layout;
    }
}
