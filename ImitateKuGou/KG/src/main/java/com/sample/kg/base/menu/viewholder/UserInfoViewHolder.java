package com.sample.kg.base.menu.viewholder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sample.kg.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhaoli on 2017/3/11.
 */
public class UserInfoViewHolder {
    private RelativeLayout rootView;
    private CircleImageView userImageView;
    private TextView userName;
    private ImageView userTitleVip;
    private ImageView userTitleMusicPack;

    public UserInfoViewHolder(View root, int id) {
        rootView = (RelativeLayout) root.findViewById(id);
        userImageView = (CircleImageView) rootView.findViewById(R.id.userImage);
        userName = (TextView) rootView.findViewById(R.id.userName);
        userTitleVip = (ImageView) rootView.findViewById(R.id.userTitleVip);
        userTitleMusicPack = (ImageView) rootView.findViewById(R.id.userTitleMusicPack);
        setUserTitleMusicPackState(false);
        setUserTitleVipState(false);
    }

    public void setUserName(String name) {
        userName.setText(name);
    }

    public void setUserHeadImage(Drawable userHeadImage) {
        userImageView.setImageDrawable(userHeadImage);
    }

    public void setUserTitleVipState(boolean has) {
        userTitleVip.setImageResource(has ? R.drawable.user_title_is_vip : R.drawable.user_title_not_vip);
    }

    public void setUserTitleMusicPackState(boolean has) {
        userTitleMusicPack.setImageResource(has ? R.drawable.user_title_is_music_pack : R.drawable.user_title_not_music_pack);
    }
}
