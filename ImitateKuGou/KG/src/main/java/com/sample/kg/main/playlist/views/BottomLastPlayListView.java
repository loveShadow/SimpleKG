package com.sample.kg.main.playlist.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.sample.kg.R;
import com.sample.kg.main.adapters.LastPlayListAdapter;
import com.sample.kg.utils.TestUtil;

/**
 * Created by zhaoli on 2017/3/15.
 * 当前播放列表
 */
public class BottomLastPlayListView extends LinearLayout {

    private RecyclerView lastPlayListView;
    private LastPlayListAdapter adapter;

    public BottomLastPlayListView(Context context) {
        this(context, null);
    }

    public BottomLastPlayListView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BottomLastPlayListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.bottom_playlist_last_layout, this);
        lastPlayListView = (RecyclerView) findViewById(R.id.lastPlayListView);
        lastPlayListView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new LastPlayListAdapter(context);
        lastPlayListView.setAdapter(adapter);

        //TODO 测试
        adapter.setData(TestUtil.getTestSongData());
        adapter.notifyDataSetChanged();
    }
}
