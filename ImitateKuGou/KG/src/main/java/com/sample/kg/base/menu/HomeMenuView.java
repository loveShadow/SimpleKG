package com.sample.kg.base.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sample.kg.R;
import com.sample.kg.base.menu.viewholder.HomeMenuCenterBtnHolder;
import com.sample.kg.base.menu.viewholder.UserInfoViewHolder;
import com.sample.kg.utils.ResourceUtil;
import com.sample.kg.views.common.CheckBoxView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoli on 2017/3/9.
 * 首页菜单
 */
public class HomeMenuView extends RelativeLayout implements View.OnClickListener {

    private List<HomeMenuCenterBtnHolder> homeMenuCenterBtnHolders = new ArrayList<>();
    private List<CheckBoxView> homeMenuCenterBottomBtnHolders = new ArrayList<>();
    private List<LinearLayout> homeMenuBottomBtns = new ArrayList<>();
    private UserInfoViewHolder userInfoViewHolder;
    final Context context;

    public HomeMenuView(Context context) {
        this(context, null);
    }

    public HomeMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.home_menu, this);
        this.context = context;
        initTopMenuView();
        initCenterMenuView();
        initCenterBottomMenuView();
        initBottomMenuView();
    }

    private void initTopMenuView() {
        userInfoViewHolder = new UserInfoViewHolder(this, R.id.home_menu_top);
        userInfoViewHolder.setUserName("傻了吧");
        userInfoViewHolder.setUserHeadImage(ResourceUtil.getDrawable(context, R.drawable.home));
    }

    private void initCenterMenuView() {
        //消息中心
        homeMenuCenterBtnHolders.add(new HomeMenuCenterBtnHolder(this, R.id.message_center,
                ResourceUtil.getDrawable(context, R.drawable.home_menu_center_message_center),
                context.getString(R.string.home_menu_center_message_center)));
        //皮肤中心
        homeMenuCenterBtnHolders.add(new HomeMenuCenterBtnHolder(this, R.id.skin_center,
                ResourceUtil.getDrawable(context, R.drawable.home_menu_center_skin),
                context.getString(R.string.home_menu_center_skin_center), "海洋之声"));
        //会员中心
        homeMenuCenterBtnHolders.add(new HomeMenuCenterBtnHolder(this, R.id.member_center,
                ResourceUtil.getDrawable(context, R.drawable.home_menu_center_member_center),
                context.getString(R.string.home_menu_center_member_center)));
        //定时关闭
        homeMenuCenterBtnHolders.add(new HomeMenuCenterBtnHolder(this, R.id.timed_off,
                ResourceUtil.getDrawable(context, R.drawable.home_menu_center_timed_off),
                context.getString(R.string.home_menu_center_timed_off)));
        //蝰蛇音效
        homeMenuCenterBtnHolders.add(new HomeMenuCenterBtnHolder(this, R.id.sound_effects,
                ResourceUtil.getDrawable(context, R.drawable.home_menu_center_sound_effects),
                context.getString(R.string.home_menu_center_sound_effects)));
        //听歌识曲
        homeMenuCenterBtnHolders.add(new HomeMenuCenterBtnHolder(this, R.id.discern_song,
                ResourceUtil.getDrawable(context, R.drawable.home_menu_center_discern_song),
                context.getString(R.string.home_menu_center_discern_song)));

        for (HomeMenuCenterBtnHolder holder : homeMenuCenterBtnHolders) {
            holder.getRootView().setOnClickListener(this);
        }
    }

    private void initCenterBottomMenuView() {
        //仅WIFI联网
        homeMenuCenterBottomBtnHolders.add((CheckBoxView) findViewById(R.id.only_wifi_connect_network));
        //桌面歌词
        homeMenuCenterBottomBtnHolders.add((CheckBoxView) findViewById(R.id.desktop_lyrics));
        //锁屏歌词
        homeMenuCenterBottomBtnHolders.add((CheckBoxView) findViewById(R.id.lock_screen_lyrics));

        for (CheckBoxView checkBoxView : homeMenuCenterBottomBtnHolders) {
            checkBoxView.setOnClickListener(this);
        }
    }

    private void initBottomMenuView() {
        homeMenuBottomBtns.add((LinearLayout) findViewById(R.id.home_menu_setting));
        homeMenuBottomBtns.add((LinearLayout) findViewById(R.id.home_menu_quit));
        for (LinearLayout ll : homeMenuBottomBtns) {
            ll.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        final int id = view.getId();
        switch (id) {
            case R.id.message_center:
                log("消息中心");
                return;
            case R.id.skin_center:
                log("皮肤中心");
                return;
            case R.id.member_center:
                log("会员中心");
                return;
            case R.id.timed_off:
                log("定时关闭");
                return;
            case R.id.sound_effects:
                log("蝰蛇音效");
                return;
            case R.id.discern_song:
                log("听歌识曲");
                return;
            case R.id.only_wifi_connect_network:
                log("仅WIFI联网");
                return;
            case R.id.desktop_lyrics:
                log("桌面歌词");
                return;
            case R.id.lock_screen_lyrics:
                log("锁屏歌词");
                return;
            case R.id.home_menu_setting:
                log("设置");
                return;
            case R.id.home_menu_quit:
                log("退出");
                return;
        }
    }
    
    private void log(String message) {
        Log.d("TEST", message);
    }
}
