package com.sample.kg.base.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sample.kg.R;

/**
 * Created by zhaoli on 2017/5/7.
 * 公共头的Fragment
 */
public abstract class BaseTopFragment extends Fragment
        implements View.OnClickListener {

    protected ViewGroup contentView;
    protected TextView baseTitleTextView;
    protected ImageButton baseBackBtn, baseMenuBtn;

    protected ITopFragmentCallback callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_top_layout, container, false);
        initBaseTopFragment(view);
        onCreateFragmentContentView();
        return view;
    }

    private void initBaseTopFragment(View view) {
        contentView = (ViewGroup) view.findViewById(R.id.contentView);
        LayoutInflater.from(getContext()).inflate(getFragmentContentLayout(), contentView);

        baseTitleTextView = (TextView) view.findViewById(R.id.titleTextView);
        baseTitleTextView.setText(getFragmentTitle());
        baseBackBtn = (ImageButton) view.findViewById(R.id.backBtn);
        baseBackBtn.setOnClickListener(this);
        baseMenuBtn = (ImageButton) view.findViewById(R.id.menuBtn);
        baseMenuBtn.setOnClickListener(this);
    }

    public void setTopFragmentCallback(ITopFragmentCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onClick(View view) {
        if (null != callback) {
            if (view == baseBackBtn) {
                getFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.base_top_fragment_enter_anim,
                        R.anim.base_top_fragment_exit_anim).remove(this).commit();
            } else if (view == baseMenuBtn) {
                callback.onMenu();
            }
        }
    }

    /**
     * 获取Fragment标题
     *
     * @return
     */
    protected abstract String getFragmentTitle();

    /**
     * 获取Fragment内容的布局
     *
     * @return
     */
    protected abstract int getFragmentContentLayout();

    /**
     * 创建Fragment的内容布局
     */
    protected abstract void onCreateFragmentContentView();
}
