
package com.superrecycleview.library.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.superrecycleview.library.R;
import com.superrecycleview.library.ui.sidebar.MeiTuanActivity;
import com.superrecycleview.library.ui.sidebar.SlideSwipeActivity;
import com.superrecycleview.library.ui.sidebar.WeChatActivity;

/**
 * Created by super南仔 on 2017-05-04. 类备注： 需要传入的参数:
 */
public class SideBarActivity extends Activity implements View.OnClickListener {

    private TextView tvWechat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidebar_layout);
        initView();
    }

    private void initView() {
        tvWechat = (TextView) findViewById(R.id.super_tv_wechat);
        tvWechat.setOnClickListener(this);
        findViewById(R.id.super_tv_swipe_menu).setOnClickListener(this);
        findViewById(R.id.super_tv_meituan).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.super_tv_wechat:
                startActivity(new Intent(this, WeChatActivity.class));
                break;
            case R.id.super_tv_swipe_menu:
                startActivity(new Intent(this, SlideSwipeActivity.class));
                break;
            case R.id.super_tv_meituan:
                startActivity(new Intent(this, MeiTuanActivity.class));
                break;
        }
    }
}
