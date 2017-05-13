
package com.superrecycleview.library.adapter.sidebar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.superrecycleview.library.R;
import com.superrecycleview.library.bean.sidebar.WeChatBean;

import java.util.List;

/**
 * Created by super南仔 on 2017-05-09. 类备注： 需要传入的参数:
 */
public class MeiTuanAdapter extends RecyclerView.Adapter<MeiTuanAdapter.ViewHolder> {
    protected Context mContext;
    protected List<WeChatBean> mDatas;
    protected LayoutInflater mInflater;

    public MeiTuanAdapter(Context mContext, List<WeChatBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    public List<WeChatBean> getDatas() {
        return mDatas;
    }

    public MeiTuanAdapter setDatas(List<WeChatBean> datas) {
        mDatas = datas;
        return this;
    }

    @Override
    public MeiTuanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_wechat_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final MeiTuanAdapter.ViewHolder holder, final int position) {
        final WeChatBean cityBean = mDatas.get(position);
        holder.tvCity.setText(cityBean.getName());
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "pos:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCity;
        View content;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCity = (TextView) itemView.findViewById(R.id.super_tv_name);
            content = itemView.findViewById(R.id.content);
        }
    }
}
