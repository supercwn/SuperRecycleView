# SuperRecyclerView #

**SuperRecycleView** is a combines XrecycleView and CymChad / BaseRecyclerViewAdapterHelper. If you use SuperRecycleView, you can pullrefresh,load more easier with a reference. More convenient to bind the data.

# Screenshots #
![change](https://github.com/supercwn/SuperRecycleView/blob/master/gif/change.gif)
![change](https://github.com/supercwn/SuperRecycleView/blob/master/gif/multitem.gif)
![change](https://github.com/supercwn/SuperRecycleView/blob/master/gif/swipemenu.gif)
![change](https://github.com/supercwn/SuperRecycleView/blob/master/gif/drag.gif)

#How to Use#

gradle

	compile 'com.supercwn.superrecycleview:superlibrary:1.2.0'

#RefreshAndLoadMore#

    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
	superRecyclerView.setLayoutManager(layoutManager);
	superRecyclerView.setRefreshEnabled(true);//可以定制是否开启下拉刷新
	superRecyclerView.setLoadingMoreEnabled(true);//可以定制是否开启加载更多
    superRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);//可以自定义下拉刷新的样式
    superRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);//可以自定义上拉加载的样式
    superRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);//设置下拉箭头

 we provide a callback to trigger the refresh and LoadMore event.

    superRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
    @Override
    public void onRefresh() {
       //refresh data here
    }

    @Override
    public void onLoadMore() {
       // load more data here
    }
	});

and of course you have to tell our RecyclerView when the refreshing or loading more work is done. you can use

	superRecyclerView.completeRefresh();

to notify that the loading more work is done. and

	superRecyclerView.completeLoadMore();

to notify that the no more data,you can

	superRecyclerView.setNoMore(true);

#HeaderViewAndFooterView#

you can add header and footer to SuperRecycleView 

	View headerView = getLayoutInflater().inflate(R.layout.view_header_layout, (ViewGroup) superRecyclerView.getParent(), false);
	mAdapter.addHeaderView(headerView);
	//
	View footerView = getLayoutInflater().inflate(R.layout.view_footer_layout, (ViewGroup) superRecyclerView.getParent(), false);
	mAdapter.addFooterView(footerView);

you Adapter must extends SuperBaseAdapter<T>, like this

	public class HeaderFooterAdapter extends SuperBaseAdapter<String> {

	    public HeaderFooterAdapter(Context context, List<String> data) {
	        super(context, data);
	    }

	    @Override
	    protected void convert(BaseViewHolder holder, String item, int position) {
	        holder.setText(R.id.tv_info,item);
	    }
	
	    @Override
	    protected int getItemViewLayoutId(int position, String item) {
	        return R.layout.adapter_header_footer_layout;
	    }
	}

#TitleBarColorEvaluate

If you need a title bar gradient effect, you can do 

	superRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrollIdle = (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isScrollIdle && adViewTopSpace < 0) return;
                adViewTopSpace = DensityUtil.px2dip(GradualChangeActivity.this, mAdapter.getHeaderLayout().getTop());
                adViewHeight = DensityUtil.px2dip(GradualChangeActivity.this, mAdapter.getHeaderLayout().getHeight());
                if(-adViewTopSpace<=50){
                    tv_title.setVisibility(View.GONE);
                } else {
                    tv_title.setVisibility(View.VISIBLE);
                }
                handleTitleBarColorEvaluate();
            }
        });

#MultiItem

If you need to display different styles in the list，you can

	public class MultiItemAdapter extends SuperBaseAdapter<MultipleItemBean> {

	    public MultiItemAdapter(Context context, List<MultipleItemBean> data) {
	        super(context, data);
	    }

	    @Override
	    protected void convert(BaseViewHolder holder, MultipleItemBean item, int position) {
	        if(item.getType() == 0){
	            holder.setText(R.id.name_tv,item.getName());
	        } else if(item.getType() == 1){
	            holder.setText(R.id.name_tv,item.getName())
	            .setText(R.id.info_tv,item.getInfo());
	        }
	    }

	    @Override
	    protected int getItemViewLayoutId(int position, MultipleItemBean item) {
			//Depending on the type display different view
	        if(item.getType() == 0){
	            return R.layout.adapter_multi_item1_layout;
	        } else if(item.getType() == 1){
	            return R.layout.adapter_multi_item2_layout;
	        } else{
	            return R.layout.adapter_multi_item3_layout;
	        }
	    }
	}

#Click

if you need itemClick, itemLongClick, itemChildClick, itemChildLongClick, you can

	/**
         * add item click event
         */
        mAdapter.setOnItemClickListener(new SuperBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object item, int position) {
                Toast.makeText(ItemClickActivity.this,"you click item"+position,Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * add item long click event
         */
        mAdapter.setOnItemLongClickListener(new SuperBaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, Object item, int position) {
                Toast.makeText(ItemClickActivity.this,"you long click item"+position,Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * add item child click event
         */
        mAdapter.setOnItemChildClickListener(new SuperBaseAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(SuperBaseAdapter adapter, View view, int position) {
                if(view.getId() == R.id.name_tv){
                    Toast.makeText(ItemClickActivity.this,"you item child click"+dataList.get(position).getName(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**
         * add item child long click event
         */
        mAdapter.setOnItemChildLongClickListener(new SuperBaseAdapter.OnRecyclerViewItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(SuperBaseAdapter adapter, View view, int position) {
                if(view.getId() == R.id.image_iv){
                    Toast.makeText(ItemClickActivity.this,"you item child long click",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

and you Adapter should do

	public class ItemClickAdapter extends SuperBaseAdapter<MultipleItemBean> {

	    public ItemClickAdapter(Context context, List<MultipleItemBean> data) {
	        super(context, data);
	    }
	
	    @Override
	    protected void convert(BaseViewHolder holder, MultipleItemBean item, int position) {
	        holder.setText(R.id.name_tv,item.getName())
	                .setOnClickListener(R.id.name_tv,new OnItemChildClickListener())//add item child click
	                .setOnLongClickListener(R.id.image_iv,new OnItemChildLongClickListener());//add item child long click
	    }
	
	    @Override
	    protected int getItemViewLayoutId(int position, MultipleItemBean item) {
	        return R.layout.adapter_item_click_layout;
	    }
	}
#SwipeMenu

if you need add swipemenu you can

	<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.superrecycleview.superlibrary.recycleview.swipemenu.SuperSwipeMenuRecyclerView
        android:id="@+id/super_swipemenu_recycle_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
	</LinearLayout>

and than

	superSwipeMenuRecyclerView = (SuperSwipeMenuRecyclerView) findViewById(R.id.super_swipemenu_recycle_view);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    superSwipeMenuRecyclerView.setLayoutManager(layoutManager);
    superSwipeMenuRecyclerView.setRefreshEnabled(true);
    superSwipeMenuRecyclerView.setLoadingMoreEnabled(true);
    superSwipeMenuRecyclerView.setLoadingListener(this);
    superSwipeMenuRecyclerView.setSwipeDirection(SuperSwipeMenuRecyclerView.DIRECTION_LEFT);//左滑（默认）
    // superSwipeMenuRecyclerView.setSwipeDirection(SuperSwipeMenuRecyclerView.DIRECTION_LEFT);//右滑

and than 
	
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
	
#SwipeMenu
if you need drag item,you can
	
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

and than
	OnItemDragListener listener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                mRecyclerView.setRefreshEnabled(false);//在开始的时候需要禁止下拉刷新，不然在下滑动的时候会与下拉刷新冲突
                BaseViewHolder holder = ((BaseViewHolder)viewHolder);
                holder.setTextColor(R.id.tv, Color.WHITE);
                ((CardView)viewHolder.itemView).setCardBackgroundColor(ContextCompat.getColor(DragActivity.this, R.color.colorAccent));
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                mRecyclerView.setRefreshEnabled(true);//在结束之后需要开启下拉刷新
                BaseViewHolder holder = ((BaseViewHolder)viewHolder);
                holder.setTextColor(R.id.tv, Color.BLACK);
                ((CardView)viewHolder.itemView).setCardBackgroundColor(Color.WHITE);
            }
        };
        mAdapter = new DragAdapter(this,mData);
        mItemDragAndSwipeCallback = new ItemDragCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mItemDragAndSwipeCallback.setDragMoveFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN);
        mAdapter.enableDragItem(mItemTouchHelper);
        mAdapter.setOnItemDragListener(listener);

Presentation charts

![change](https://github.com/supercwn/SuperRecycleView/blob/master/gif/drag.gif)


#Thanks

[CymChad/BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)

[jianghejie/XRecyclerView](https://github.com/jianghejie/XRecyclerView)

#License

	Copyright 2016 supercwn

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	   http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
