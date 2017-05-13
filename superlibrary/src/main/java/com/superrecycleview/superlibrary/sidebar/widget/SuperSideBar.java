
package com.superrecycleview.superlibrary.sidebar.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.superrecycleview.superlibrary.R;
import com.superrecycleview.superlibrary.sidebar.bean.BaseIndexPinyinBean;
import com.superrecycleview.superlibrary.sidebar.helper.IIndexBarDataHelper;
import com.superrecycleview.superlibrary.sidebar.helper.IndexBarDataHelperImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 介绍：索引右侧边栏 作者：zhangxutong 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601 时间： 16/09/04.
 */

public class SuperSideBar extends View {
    private static final String TAG = "zxt/IndexBar";

    // #在最后面（默认的数据源）
    public static String[] INDEX_STRING = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"
    };
    // 是否需要根据实际的数据来生成索引数据源（例如 只有 A B C 三种tag，那么索引栏就 A B C 三项）
    private boolean isNeedRealIndex;
    // 索引数据源
    private List<String> mIndexDatas;
    // 每个index区域的高度
    private int mGapHeight;
    private Paint mPaint;
    private int offsetY;

    /**
     * sideBar的字母size
     */
    private int sideBarTextSize;
    /**
     * sideBar的背景颜色
     */
    private int sideBarBg = android.R.color.transparent;
    /**
     * 按下的是显示的背景色
     */
    private int sideBarPressBg = android.R.color.transparent;
    /**
     * indexBar的字母颜色
     */
    private int sideBarTextColor = android.R.color.darker_gray;
    /**
     * indexBar选中的字母颜色
     */
    private int sideBarTextColorPress = android.R.color.holo_blue_dark;
    /**
     * 该字母是否选中
     */
    private int choose = -1;// 选中

    // 以下是帮助类
    // 汉语->拼音，拼音->tag
    private IIndexBarDataHelper mDataHelper;

    // 以下边变量是外部set进来的
    private TextView mPressedShowTextView;// 用于特写显示正在被触摸的index值
    private boolean isSourceDatasAlreadySorted;// 源数据 已经有序？
    private List<? extends BaseIndexPinyinBean> mSourceDatas;// Adapter的数据源
    private LinearLayoutManager mLayoutManager;
    private int mHeaderViewCount = 0;

    public SuperSideBar(Context context) {
        this(context, null);
    }

    public SuperSideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperSideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public int getHeaderViewCount() {
        return mHeaderViewCount;
    }

    /**
     * 设置Headerview的Count
     *
     * @param headerViewCount
     * @return
     */
    public SuperSideBar setHeaderViewCount(int headerViewCount) {
        mHeaderViewCount = headerViewCount;
        return this;
    }

    public boolean isSourceDatasAlreadySorted() {
        return isSourceDatasAlreadySorted;
    }

    /**
     * 源数据 是否已经有序
     *
     * @param sourceDatasAlreadySorted
     * @return
     */
    public SuperSideBar setSourceDatasAlreadySorted(boolean sourceDatasAlreadySorted) {
        isSourceDatasAlreadySorted = sourceDatasAlreadySorted;
        return this;
    }

    public IIndexBarDataHelper getDataHelper() {
        return mDataHelper;
    }

    /**
     * 设置数据源帮助类
     *
     * @param dataHelper
     * @return
     */
    public SuperSideBar setDataHelper(IIndexBarDataHelper dataHelper) {
        mDataHelper = dataHelper;
        return this;
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        sideBarTextSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());// 默认的TextSize
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SuperSideBar, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.SuperSideBar_super_sidebar_textsize) {
                sideBarTextSize = typedArray.getDimensionPixelSize(attr, sideBarTextSize);
            } else if (attr == R.styleable.SuperSideBar_super_sidebar_backgrond_press) {
                sideBarPressBg = typedArray.getColor(attr, sideBarPressBg);
            } else if (attr == R.styleable.SuperSideBar_super_sidebar_textcolor) {
                sideBarTextColor = typedArray.getColor(attr, sideBarTextColor);
            } else if (attr == R.styleable.SuperSideBar_super_sidebar_textcolor_press) {
                sideBarTextColorPress = typedArray.getColor(attr, sideBarTextColorPress);
            }
        }
        typedArray.recycle();
        initIndexDatas();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(sideBarTextSize);
        mPaint.setColor(Color.BLACK);

        // 设置index触摸监听器
        setmOnIndexPressedListener(new onIndexPressedListener() {
            @Override
            public void onIndexPressed(int index, String text) {
                if (mPressedShowTextView != null) { // 显示hintTexView
                    mPressedShowTextView.setVisibility(View.VISIBLE);
                    mPressedShowTextView.setText(text);
                }
                // 滑动Rv
                if (mLayoutManager != null) {
                    int position = getPosByTag(text);
                    if (position != -1) {
                        mLayoutManager.scrollToPositionWithOffset(position, 0);
                    }
                }
            }

            @Override
            public void onMotionEventEnd() {
                // 隐藏hintTextView
                if (mPressedShowTextView != null) {
                    mPressedShowTextView.setVisibility(View.GONE);
                }
            }
        });

        mDataHelper = new IndexBarDataHelperImpl();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIndexDatas.isEmpty())
            return;
        int height = getHeight();// 获取对应的高度
        int width = getWidth();// 获取对应的宽度
        mGapHeight = height / mIndexDatas.size();// 获取每个字母的高度
        int dp24 = dip2px(24);
        mGapHeight = mGapHeight > dp24 ? dp24 : mGapHeight;
        offsetY = (height - mGapHeight * mIndexDatas.size()) / 2;
        for (int i = 0; i < mIndexDatas.size(); i++) {
            int colorId = i == choose ? android.R.color.holo_blue_bright
                    : sideBarTextColor;
            mPaint.setColor(ContextCompat.getColor(getContext(), colorId));
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(sideBarTextSize);
            mPaint.setTypeface(Typeface.DEFAULT);
            float xPos = width / 2 - mPaint.measureText(mIndexDatas.get(i)) / 2;
            float yPos = offsetY + mGapHeight * (i + 0.5F);
            if (i == choose) {
                // 选中的状态
                mPaint.setFakeBoldText(true);
                canvas.drawText(mIndexDatas.get(i), dip2px(-56), yPos, mPaint);
            }
            canvas.drawText(mIndexDatas.get(i), xPos, yPos, mPaint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        // 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
        final int c = (int) ((y - offsetY) / mGapHeight);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundResource(sideBarPressBg);// 手指按下时背景变色
                // 注意这里没有break，因为down时，也要计算落点 回调监听器
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c) {
                    if (c >= 0 && c < mIndexDatas.size()) {
                        choose = c;
                        // 回调监听器
                        if (null != mOnIndexPressedListener) {
                            mOnIndexPressedListener.onIndexPressed(c, mIndexDatas.get(c));
                        }
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            default:
                choose = -1;
                setBackgroundResource(sideBarBg);
                if (null != mOnIndexPressedListener) {
                    mOnIndexPressedListener.onMotionEventEnd();
                }
                invalidate();
                break;
        }
        return true;
    }

    private static int dip2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (null == mIndexDatas || mIndexDatas.isEmpty()) {
            return;
        }
    }

    /**
     * 当前被按下的index的监听器
     */
    public interface onIndexPressedListener {
        void onIndexPressed(int index, String text);// 当某个Index被按下

        void onMotionEventEnd();// 当触摸事件结束（UP CANCEL）
    }

    private onIndexPressedListener mOnIndexPressedListener;

    public onIndexPressedListener getmOnIndexPressedListener() {
        return mOnIndexPressedListener;
    }

    public void setmOnIndexPressedListener(onIndexPressedListener mOnIndexPressedListener) {
        this.mOnIndexPressedListener = mOnIndexPressedListener;
    }

    /**
     * 显示当前被按下的index的TextView
     *
     * @return
     */

    public SuperSideBar setmPressedShowTextView(TextView mPressedShowTextView) {
        this.mPressedShowTextView = mPressedShowTextView;
        return this;
    }

    public SuperSideBar setmLayoutManager(LinearLayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
        return this;
    }

    /**
     * 一定要在设置数据源{@link #setSourceDatas(List)}之前调用
     *
     * @param needRealIndex
     * @return
     */
    public SuperSideBar setNeedRealIndex(boolean needRealIndex) {
        isNeedRealIndex = needRealIndex;
        initIndexDatas();
        return this;
    }

    private void initIndexDatas() {
        if (isNeedRealIndex) {
            mIndexDatas = new ArrayList<>();
        } else {
            mIndexDatas = Arrays.asList(INDEX_STRING);
        }
    }

    public SuperSideBar setSourceDatas(List<? extends BaseIndexPinyinBean> mSourceDatas) {
        this.mSourceDatas = mSourceDatas;
        initSourceDatas();// 对数据源进行初始化
        invalidate();
        return this;
    }

    /**
     * 初始化原始数据源，并取出索引数据源
     *
     * @return
     */
    private void initSourceDatas() {
        // add by zhangxutong 2016 09 08 :解决源数据为空 或者size为0的情况,
        if (null == mSourceDatas || mSourceDatas.isEmpty()) {
            return;
        }
        if (!isSourceDatasAlreadySorted) {
            // 排序sourceDatas
            mDataHelper.sortSourceDatas(mSourceDatas);
        } else {
            // 汉语->拼音
            mDataHelper.convert(mSourceDatas);
            // 拼音->tag
            mDataHelper.fillInexTag(mSourceDatas);
        }
        if (isNeedRealIndex) {
            mDataHelper.getSortedIndexDatas(mSourceDatas, mIndexDatas);
        }
    }

    /**
     * 根据传入的pos返回tag
     *
     * @param tag
     * @return
     */
    private int getPosByTag(String tag) {
        // add by zhangxutong 2016 09 08 :解决源数据为空 或者size为0的情况,
        if (null == mSourceDatas || mSourceDatas.isEmpty()) {
            return -1;
        }
        if (TextUtils.isEmpty(tag)) {
            return -1;
        }
        for (int i = 0; i < mSourceDatas.size(); i++) {
            if (tag.equals(mSourceDatas.get(i).getBaseIndexTag())) {
                return i + getHeaderViewCount();
            }
        }
        return -1;
    }

}
