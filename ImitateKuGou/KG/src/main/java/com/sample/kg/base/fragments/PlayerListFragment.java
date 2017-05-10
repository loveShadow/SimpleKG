package com.sample.kg.base.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sample.kg.R;
import com.sample.kg.main.playlist.views.BottomCurPlayListView;
import com.sample.kg.main.playlist.views.BottomLastPlayListView;
import com.sample.kg.views.common.PageScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoli on 2017/5/1.
 */
public class PlayerListFragment extends Fragment {

    private IViewCallback viewCallback;

    private Context context;
    private LinearLayout playListLayout;
    private PageScrollView playListScrollView;
    private List<View> bottomPointViewList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_player_list_layout, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        //添加布局
        DisplayMetrics dm = context.getApplicationContext().getResources()
                .getDisplayMetrics();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                dm.widthPixels,
                dm.heightPixels);
        playListScrollView = (PageScrollView) view.findViewById(R.id.playListScrollView);
        playListScrollView.setMaxScrollX(dm.widthPixels / 3);
        playListScrollView.setPageCallback(new PageScrollView.IPageCallback() {
            @Override
            public void onPageChange(int currentIndex) {
                changeBottomPointViewState(currentIndex);
            }
        });
        playListLayout = (LinearLayout) view.findViewById(R.id.playListLayout);
        playListLayout.addView(new BottomCurPlayListView(context), params);
        playListLayout.addView(new BottomLastPlayListView(context), params);

        bottomPointViewList.add(view.findViewById(R.id.playlist_first_point));
        bottomPointViewList.add(view.findViewById(R.id.playlist_second_point));
    }

    /**
     * 改变底部点的状态
     * @param index
     */
    private void changeBottomPointViewState(final int index) {
        for (int i = 0; i < bottomPointViewList.size(); i ++) {
            if (index == i) {
                bottomPointViewList.get(i).setBackgroundResource(R.drawable.playlist_bottom_selected_point);
            } else {
                bottomPointViewList.get(i).setBackgroundResource(R.drawable.playlist_bottom_normal_point);
            }
        }
    }



    public void setViewCallback(IViewCallback viewCallback) {
        this.viewCallback = viewCallback;
    }

    public interface IViewCallback {
        void onHidePlayListView();
    }
}
