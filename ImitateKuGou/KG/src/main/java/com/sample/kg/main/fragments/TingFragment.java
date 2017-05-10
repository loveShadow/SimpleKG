package com.sample.kg.main.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sample.kg.R;
import com.sample.kg.base.fragments.BaseTopFragment;
import com.sample.kg.main.fragments.tingpage.ChannelFragment;
import com.sample.kg.main.fragments.tingpage.DownloadFragment;
import com.sample.kg.main.fragments.tingpage.KuQunFragment;
import com.sample.kg.main.fragments.tingpage.LocalSongFragment;
import com.sample.kg.main.fragments.tingpage.MyFavFragment;
import com.sample.kg.main.fragments.tingpage.RecentFragment;
import com.sample.kg.main.fragments.tingpage.SongPlayListFragment;

/**
 * Created by zhaoli on 2017/4/11.
 */
public class TingFragment extends Fragment implements View.OnClickListener {

    public interface Type {
        //我喜欢
        int MY_FAV = 1;
        //歌单
        int PLAY_LIST = 2;
        //下载
        int DOWNLOAD = 3;
        //最近
        int RECENT = 4;
        //本地音乐
        int LOCAL_SONG = 5;
        //乐库
        int YUE_KU = 6;
        //电台
        int CHANNEL = 7;
        //酷群
        int KU_QUN = 8;
    }

    private ImageView topBgView;

    private ITingFragmentCallback callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ting_layout, container, false);
        initView(view);
        return view;
    }

    public void setTingFragmentCallback(ITingFragmentCallback callback) {
        this.callback = callback;
    }

    private void initView(View view) {
        topBgView = (ImageView) view.findViewById(R.id.ting_top_bg);
        initCommandBtn(R.id.myLoveBtn, view, Type.MY_FAV);
        initCommandBtn(R.id.songListBtn, view, Type.PLAY_LIST);
        initCommandBtn(R.id.downloadBtn, view, Type.DOWNLOAD);
        initCommandBtn(R.id.recentBtn, view, Type.RECENT);
        initCommandBtn(R.id.localSongBtn, view, Type.LOCAL_SONG);
        initCommandBtn(R.id.yueKuBtn, view, Type.YUE_KU);
        initCommandBtn(R.id.channelBtn, view, Type.CHANNEL);
        initCommandBtn(R.id.kuQunBtn, view, Type.KU_QUN);
    }

    /**
     * 初始化公共按钮
     * @param id
     * @param view
     * @param type
     */
    private void initCommandBtn(int id, View view, int type) {
        View button = view.findViewById(id);
        button.setOnClickListener(this);
        button.setTag(type);
    }

    @Override
    public void onClick(View view) {
        Object type = view.getTag();
        if (null != type && null != callback) {
            callback.onShowFragment((int) type);
        }
    }

    /**
     * 更改顶部背景的透明度
     * @param alpha
     */
    public void changeTopBgViewAlpha(float alpha) {
        if (null != topBgView) {
            topBgView.setAlpha(alpha);
        }
    }

    public static BaseTopFragment createFragment(int type) {
        switch (type) {
            case Type.MY_FAV:
                return new MyFavFragment();
            case Type.PLAY_LIST:
                return new SongPlayListFragment();
            case Type.DOWNLOAD:
                return new DownloadFragment();
            case Type.RECENT:
                return new RecentFragment();
            case Type.LOCAL_SONG:
                return new LocalSongFragment();
            case Type.YUE_KU:
                return new KuQunFragment();
            case Type.CHANNEL:
                return new ChannelFragment();
            case Type.KU_QUN:
                return new KuQunFragment();
        }
        return null;
    }
}
