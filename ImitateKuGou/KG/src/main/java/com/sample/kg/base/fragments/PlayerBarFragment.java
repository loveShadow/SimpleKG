package com.sample.kg.base.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sample.kg.R;

/**
 * Created by zhaoli on 2017/5/1.
 */
public class PlayerBarFragment extends Fragment implements View.OnClickListener {

    private Button playOrPauseBtn, nextSongBtn, playListBtn;
    private IViewCallback viewCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_player_layout, container, false);
        initView(view);
        return view;
    }

    /**
     * 设置界面的回调
     * @param viewCallback
     */
    public void setViewCallback(IViewCallback viewCallback) {
        this.viewCallback = viewCallback;
    }

    private void initView(View view) {
        playOrPauseBtn = (Button) view.findViewById(R.id.playOrPauseBtn);
        nextSongBtn = (Button) view.findViewById(R.id.nextSongBtn);
        playListBtn = (Button) view.findViewById(R.id.playListBtn);
        playOrPauseBtn.setOnClickListener(this);
        nextSongBtn.setOnClickListener(this);
        playListBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (playListBtn == view) {
            if (null != viewCallback) {
                viewCallback.onPlayListViewChange();
            }
        }
    }

    public interface IViewCallback {
        /**
         * 播放列表界面状态更改
         */
        void onPlayListViewChange();

        /**
         * 展示播放器界面
         */
        void onShowPlayerView();
    }
}
