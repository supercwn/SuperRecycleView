/**
 * Copyright 2016 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.superrecycleview.superlibrary.utils;

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.superrecycleview.superlibrary.R;

/**
 * Created by super南仔 on 07/28/16. <br/>
 * blog: http://supercwn.github.io/ <br/>
 * GitHub: https://github.com/supercwn <br/>
 * RecyclerView 分隔线
 */
public class SuperDivider extends RecyclerView.ItemDecoration {
    private Drawable mDividerDrawable;
    private int mMarginLeft;
    private int mMarginRight;
    private int mOrientation = LinearLayout.VERTICAL;// 暂时只考虑竖直列表，不考虑横向列表以及Grid模式
    private int mStartSkipCount = 2;// 默认列表第一个没有；仅仅是在使用SuperRecycleView的情况下
    private int mEndSkipCount = 1;// 默认列表最后一个没有；仅仅是在使用SuperRecycleView的情况下
    private int mSize = 1;

    private SuperDivider(@DrawableRes int drawableResId) {
        mDividerDrawable = ContextCompat.getDrawable(SuperAdapterUtil.getApp(), drawableResId);
        mSize = Math.min(mDividerDrawable.getIntrinsicHeight(),
                mDividerDrawable.getIntrinsicWidth());
    }

    /**
     * 自定义 drawable 资源分隔线
     *
     * @param drawableResId
     * @return
     */
    public static SuperDivider newDrawableDivider(@DrawableRes int drawableResId) {
        return new SuperDivider(drawableResId);
    }

    /**
     * 默认的 shape 资源分隔线
     *
     * @return
     */
    public static SuperDivider newShapeDivider() {
        return new SuperDivider(R.drawable.bga_adapter_divider_shape);
    }

    /**
     * 默认的图片分隔线
     *
     * @return
     */
    public static SuperDivider newBitmapDivider() {
        return new SuperDivider(R.mipmap.bga_adapter_divider_bitmap);
    }

    /**
     * 设置左边距和右边距
     *
     * @param bothMarginDp 单位为 dp
     * @return
     */
    public SuperDivider setBothMarginDp(int bothMarginDp) {
        mMarginLeft = SuperAdapterUtil.dp2px(bothMarginDp);
        mMarginRight = mMarginLeft;
        return this;
    }

    /**
     * 设置左边距
     *
     * @param marginLeftDp 单位为 dp
     * @return
     */
    public SuperDivider setMarginLeftDp(int marginLeftDp) {
        mMarginLeft = SuperAdapterUtil.dp2px(marginLeftDp);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param marginRightDp 单位为 dp
     * @return
     */
    public SuperDivider setMarginRightDp(int marginRightDp) {
        mMarginRight = SuperAdapterUtil.dp2px(marginRightDp);
        return this;
    }

    /**
     * 设置分隔线颜色资源 id
     *
     * @param resId
     * @return
     */
    public SuperDivider setColor(@ColorRes int resId) {
        return setColorResource(SuperAdapterUtil.getColor(resId));
    }

    /**
     * 设置分隔线颜色
     *
     * @param color
     * @return
     */
    private SuperDivider setColorResource(@ColorInt int color) {
        mDividerDrawable.setColorFilter(color, PorterDuff.Mode.SRC);
        return this;
    }

    /**
     * 设置为水平方向
     *
     * @return
     */
    public SuperDivider setHorizontal() {
        mOrientation = LinearLayout.HORIZONTAL;
        return this;
    }

    /**
     * 旋转分隔线，仅用于分隔线为 Bitmap 时。应用场景：UI 给了一个水平分隔线，恰巧项目里需要一条一模一样的竖直分隔线
     *
     * @return
     */
    public SuperDivider rotateDivider() {
        if (mDividerDrawable != null && mDividerDrawable instanceof BitmapDrawable) {
            mDividerDrawable = SuperAdapterUtil
                    .rotateBitmap(((BitmapDrawable) mDividerDrawable).getBitmap());
        }
        return this;
    }

    /**
     * 跳过开始的条数。默认值为 1，不绘制第一个 item 顶部的分隔线
     *
     * @param startSkipCount
     * @return
     */
    public SuperDivider setStartSkipCount(@IntRange(from = 0) int startSkipCount) {
        mStartSkipCount = startSkipCount;
        if (mStartSkipCount < 0) {
            mStartSkipCount = 0;
        }
        return this;
    }

    /**
     * 跳过末尾的条数
     *
     * @param endSkipCount
     * @return
     */
    public SuperDivider setEndSkipCount(@IntRange(from = 0) int endSkipCount) {
        mEndSkipCount = endSkipCount;
        if (mEndSkipCount < 0) {
            mEndSkipCount = 0;
        }
        return this;
    }

    /**
     * 设置分割线尺寸
     *
     * @param sizeDp 单位为 dp
     * @return
     */
    public SuperDivider setSizeDp(int sizeDp) {
        mSize = SuperAdapterUtil.dp2px(sizeDp);
        return this;
    }

    private boolean isNeedSkip(int childAdapterPosition,
            int realItemCount) {
        int lastPosition = realItemCount - 1;
        // 跳过最后 mEndSkipCount 个
        if (childAdapterPosition > lastPosition - mEndSkipCount) {
            return true;
        }

        // 跳过前 mStartSkipCount 个
        // 1
        if (childAdapterPosition < mStartSkipCount) {
            return true;
        }
        // 默认不跳过
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
        if (parent.getLayoutManager() == null || parent.getAdapter() == null) {
            return;
        }

        int childAdapterPosition = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();

        int realChildAdapterPosition = childAdapterPosition;
        int realItemCount = itemCount;

        if (isNeedSkip(childAdapterPosition, realItemCount)) {
            outRect.set(0, 0, 0, 0);
        } else {
            if (mOrientation == LinearLayout.VERTICAL) {
                getVerticalItemOffsets(outRect);
            } else {
                outRect.set(mSize, 0, 0, 0);
            }
        }
    }

    public void getVerticalItemOffsets(Rect outRect) {
        outRect.set(0, mSize, 0, 0);
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || parent.getAdapter() == null) {
            return;
        }
        int itemCount = parent.getAdapter().getItemCount();
        if (mOrientation == LinearLayout.VERTICAL) {
            drawVertical(canvas, parent, itemCount);
        } else {
            drawVertical(canvas, parent, itemCount);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent, int itemCount) {
        int dividerLeft = parent.getPaddingLeft() + mMarginLeft;
        int dividerRight = parent.getWidth() - parent.getPaddingRight() - mMarginRight;
        View itemView;
        RecyclerView.LayoutParams itemLayoutParams;

        for (int childPosition = 0; childPosition < itemCount; childPosition++) {
            itemView = parent.getChildAt(childPosition);
            if (itemView == null || itemView.getLayoutParams() == null) {
                continue;
            }

            int childAdapterPosition = parent.getChildAdapterPosition(itemView);

            if (isNeedSkip(childAdapterPosition, itemCount)) {
                continue;
            }

            itemLayoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            int dividerBottom = itemView.getTop() - itemLayoutParams.topMargin;

            drawVertical(canvas, dividerLeft, dividerRight, dividerBottom);
        }
    }

    public void drawVertical(Canvas canvas, int dividerLeft, int dividerRight, int itemTop) {
        int dividerBottom = itemTop;
        int dividerTop = dividerBottom - mSize;
        mDividerDrawable.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
        mDividerDrawable.draw(canvas);
    }

}
