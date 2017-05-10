package com.sample.kg.base.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sample.kg.R;
import com.sample.kg.base.fragments.PlayerBarFragment;
import com.sample.kg.base.fragments.PlayerListFragment;

/**
 * Created by zhaoli on 2017/5/1.
 * 基础Activity
 * 注：底部附有播放条
 */
public abstract class BaseSongActivity extends BaseActivity implements
        PlayerBarFragment.IViewCallback,
        PlayerListFragment.IViewCallback,
        View.OnClickListener {

    protected View playListViewLayout;
    protected PlayerBarFragment playerBarFragment;
    protected PlayerListFragment playerListFragment;
    protected ViewGroup songViewContent;

    @Override
    protected int getContentView() {
        return R.layout.activity_base_song_layout;
    }

    @Override
    protected void onCreateContentView(Bundle savedInstanceState) {
        playListViewLayout = findViewById(R.id.playListViewLayout);
        initPlayerBar();
        initBaseSongView();
        onCreateSongContentView(savedInstanceState);
    }

    protected void initBaseSongView() {
        songViewContent = (FrameLayout) findViewById(R.id.songViewContent);
        LayoutInflater.from(BaseSongActivity.this).inflate(getSongContentView(), songViewContent);
    }

    protected void initPlayerBar() {
        playerBarFragment = new PlayerBarFragment();
        playerBarFragment.setViewCallback(this);
        fm.beginTransaction().replace(R.id.playerBar, playerBarFragment).commit();
    }

    /**
     * 获取主内容区的layoutID
     *
     * @return
     */
    protected abstract int getSongContentView();

    /**
     * 初始化主内容区
     *
     * @param savedInstanceState
     */
    protected abstract void onCreateSongContentView(Bundle savedInstanceState);

    //....回调

    @Override
    public void onClick(View view) {
        if (playListViewLayout == view) {
            onPlayListViewChange();
        }
    }

    @Override
    public void onPlayListViewChange() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.play_list_enter_anim,
                R.anim.play_list_exit_anim);
        boolean showing = (null == playerListFragment) ? false : !playerListFragment.isHidden();
        if (null == playerListFragment) {
            playListViewLayout.setOnClickListener(this);
            playerListFragment = new PlayerListFragment();
            playerListFragment.setViewCallback(this);
            ft.add(R.id.playListView, playerListFragment);
        }
        if (showing) {
            ft.hide(playerListFragment).commit();
        } else {
            ft.show(playerListFragment).commit();
        }
        playListViewLayout.setVisibility(showing ? View.GONE : View.VISIBLE);
        //设置滑动菜单是否支持
        supportSlidingMenu(showing);
    }

    @Override
    public void onShowPlayerView() {

    }

    @Override
    public void onHidePlayListView() {
        if (null == playerListFragment) {
            return;
        }
        fm.beginTransaction().hide(playerListFragment).commit();
    }

    /**
     * 播放列表页面是否在展示
     *
     * @return
     */
    protected boolean playListViewIsShowing() {
        if (null == playerListFragment) {
            return false;
        }
        return !playerListFragment.isHidden();
    }
}
