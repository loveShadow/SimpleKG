package com.sample.kg.main.playlist.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sample.kg.R;
import com.sample.kg.base.adapters.BaseAdapter;
import com.sample.kg.base.adapters.IAdapterCallback;
import com.sample.kg.main.adapters.CurPlayListAdapter;
import com.sample.kg.main.adapters.ICurPlayItemCallback;
import com.sample.kg.main.playlist.itemTouchHelper.PlayItemTouchHelperCallback;
import com.sample.kg.beans.Song;
import com.sample.kg.utils.TestUtil;

/**
 * Created by zhaoli on 2017/3/15.
 * 当前播放列表
 */
public class BottomCurPlayListView extends LinearLayout
        implements IAdapterCallback, View.OnClickListener, ICurPlayItemCallback {

    private RecyclerView curPlayListView;
    private CurPlayListAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ItemTouchHelper touchHelper;
    private LinearLayout menuLayout;
    private Button shareBtn, addToListBtn, playModeBtn, clearListBtn, moveOverBtn;

    public BottomCurPlayListView(Context context) {
        this(context, null);
    }

    public BottomCurPlayListView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BottomCurPlayListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.bottom_playlist_cur_layout, this);

        menuLayout = (LinearLayout) findViewById(R.id.menuLayout);
        shareBtn = (Button) findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(this);
        addToListBtn = (Button) findViewById(R.id.addToListBtn);
        addToListBtn.setOnClickListener(this);
        playModeBtn = (Button) findViewById(R.id.playModeBtn);
        playModeBtn.setOnClickListener(this);
        clearListBtn = (Button) findViewById(R.id.clearListBtn);
        clearListBtn.setOnClickListener(this);
        moveOverBtn = (Button) findViewById(R.id.moveOverBtn);
        moveOverBtn.setOnClickListener(this);

        curPlayListView = (RecyclerView) findViewById(R.id.curPlayListView);
        layoutManager = new LinearLayoutManager(context);
        curPlayListView.setLayoutManager(layoutManager);
        adapter = new CurPlayListAdapter(context);
        adapter.setCallback(this);
        adapter.setItemCallback(this);
        //设置缓存大小为0（解决移动状态时，缓存的Item状态无法改变为移动状态）
        curPlayListView.setItemViewCacheSize(0);

        curPlayListView.setAdapter(adapter);

        //设置可拖动属性
        PlayItemTouchHelperCallback touchHelperCallback = new PlayItemTouchHelperCallback(adapter);
        touchHelper = new ItemTouchHelper(touchHelperCallback);
        touchHelper.attachToRecyclerView(curPlayListView);

        //TODO 测试
        adapter.setData(TestUtil.getTestSongData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (shareBtn == view) {

        } else if (addToListBtn == view) {

        } else if (playModeBtn == view) {

        } else if (clearListBtn == view) {

        } else if (moveOverBtn == view) {
            adapter.setMoveState(false);
            adapter.notifyDataSetChanged();
            menuLayout.setVisibility(VISIBLE);
            moveOverBtn.setVisibility(GONE);
        }
    }

    @Override
    public void onClick(BaseAdapter.BaseViewHolder viewHolder) {

    }

    @Override
    public void onTouch(BaseAdapter.BaseViewHolder viewHolder) {

    }

    @Override
    public void onLongTouch(BaseAdapter.BaseViewHolder viewHolder) {
        //改变所有Item的布局
        adapter.setMoveState(true);
        int firstPos = layoutManager.findFirstVisibleItemPosition();
        int lastPos = layoutManager.findLastVisibleItemPosition();
        for (int i = firstPos; i <= lastPos; i ++) {
            View childView = layoutManager.findViewByPosition(i);
            if (null != childView) {
                CurPlayListAdapter.CurPlayListViewHolder childHolder =
                        (CurPlayListAdapter.CurPlayListViewHolder)
                                curPlayListView.getChildViewHolder(childView);
                childHolder.changeToMoveState();
            }
        }
        menuLayout.setVisibility(GONE);
        moveOverBtn.setVisibility(VISIBLE);
    }

    @Override
    public void onLove(Song song) {

    }

    @Override
    public void onDownload(Song song) {

    }

    @Override
    public void onDelete(Song song) {

    }
}