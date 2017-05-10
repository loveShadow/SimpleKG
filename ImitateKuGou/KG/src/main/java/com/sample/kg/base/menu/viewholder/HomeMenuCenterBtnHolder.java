package com.sample.kg.base.menu.viewholder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sample.kg.R;

/**
 * Created by zhaoli on 2017/3/9.
 * 首页菜单按钮
 */
public class HomeMenuCenterBtnHolder {
    private RelativeLayout rootView;
    private TextView icon;
    private TextView text;

    public HomeMenuCenterBtnHolder(View root, int id, Drawable iconDrawable, String iconText) {
        this(root, id, iconDrawable, iconText, "");
    }

    public HomeMenuCenterBtnHolder(View root, int id, Drawable iconDrawable, String iconText, String infoText) {
        rootView = (RelativeLayout) root.findViewById(id);
        icon = (TextView) rootView.findViewById(R.id.icon);
        text = (TextView) rootView.findViewById(R.id.text);
        setIconDrawable(iconDrawable);
        setIconText(iconText);
        setText(infoText);
    }

    public void setIconDrawable(Drawable iconDrawable) {
        iconDrawable.setBounds(0, 0, iconDrawable.getMinimumWidth(), iconDrawable.getMinimumHeight());
        icon.setCompoundDrawables(iconDrawable, null, null, null);
    }

    public void setIconText(String iconText) {
        icon.setText(iconText);
    }

    public void setText(String info) {
        text.setText(info);
    }

    public RelativeLayout getRootView() {
        return rootView;
    }
}
