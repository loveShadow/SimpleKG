package com.sample.kg.main.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.sample.kg.R;
import com.sample.kg.base.fragments.BaseTopFragment;
import com.sample.kg.base.fragments.ITopFragmentCallback;
import com.sample.kg.main.fragments.ChangeFragment;
import com.sample.kg.main.fragments.ITingFragmentCallback;
import com.sample.kg.main.fragments.KanFragment;
import com.sample.kg.main.fragments.SearchFragment;
import com.sample.kg.main.fragments.TingFragment;
import com.sample.kg.base.activitys.BaseSongActivity;
import com.sample.kg.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhaoli on 2017/5/1.
 */
public class HomeActivity extends BaseSongActivity
       implements ITingFragmentCallback, ITopFragmentCallback {

    private int TING_LEFT;
    private int KAN_LEFT;
    private int CHANG_LEFT;

    private ViewPager centerViewPage;
    private View moveLineView;
    RelativeLayout.LayoutParams moveLiveLp;

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<Button> topMenuBtnList = new ArrayList<>();
    private Button searchBtn;
    private SearchFragment searchFragment;

    //Fragment缓存器
    private HashMap<Integer, BaseTopFragment> baseTopFragmentMap = new HashMap<>();

    @Override
    protected int getSongContentView() {
        return R.layout.activity_home_layout;
    }

    @Override
    protected void onCreateSongContentView(Bundle savedInstanceState) {
        TING_LEFT = getResources().getDimensionPixelSize(R.dimen.fhd_300);
        KAN_LEFT = getResources().getDimensionPixelSize(R.dimen.fhd_500);
        CHANG_LEFT = getResources().getDimensionPixelSize(R.dimen.fhd_700);
        initView();
    }

    private void initView() {
        //初始化底部白线
        moveLineView = findViewById(R.id.moveLineView);
        moveLiveLp = (RelativeLayout.LayoutParams) moveLineView.getLayoutParams();
        if (null == moveLiveLp) {
            moveLiveLp = new RelativeLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.fhd_90),
                    getResources().getDimensionPixelSize(R.dimen.fhd_6));
            moveLiveLp.setMargins(TING_LEFT, 0, 0, 0);
            moveLiveLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            moveLineView.setLayoutParams(moveLiveLp);
        }
        //初始化顶部菜单
        topMenuBtnList.add((Button) findViewById(R.id.tingBtn));
        topMenuBtnList.add((Button) findViewById(R.id.kanBtn));
        topMenuBtnList.add((Button) findViewById(R.id.changBtn));
        //默认选中“听”
        topMenuBtnList.get(0).setSelected(true);
        for (Button button : topMenuBtnList) {
            button.setOnClickListener(this);
        }
        //初始化搜索
        searchBtn = (Button) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(this);
        //初始化内容布局
        centerViewPage = (ViewPager) findViewById(R.id.centerViewPager);
        TingFragment tingFragment = new TingFragment();
        tingFragment.setTingFragmentCallback(this);
        fragmentList.add(tingFragment);
        fragmentList.add(new KanFragment());
        fragmentList.add(new ChangeFragment());
        centerViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //移动底部白线
                int startPos = TING_LEFT;
                if (1 == position) {
                    startPos = KAN_LEFT;
                } else if (2 == position) {
                    startPos = CHANG_LEFT;
                }
                int offset = (int) ((KAN_LEFT - TING_LEFT) * ((float) positionOffsetPixels / ScreenUtil.getScreenWidth(HomeActivity.this)));
                moveLiveLp.setMargins(startPos + offset, 0, 0, 0);
                moveLineView.setLayoutParams(moveLiveLp);
                //从“听”-“看”，顶部背景渐变
                if (0 == position) {
                    Fragment fragment = fragmentList.get(position);
                    if (fragment instanceof TingFragment) {
                        if (positionOffsetPixels >= 0) {
                            ((TingFragment) fragment).changeTopBgViewAlpha((float) positionOffsetPixels / TING_LEFT);
                        } else {
                            ((TingFragment) fragment).changeTopBgViewAlpha(
                                    (float) (ScreenUtil.getScreenWidth(HomeActivity.this) + positionOffsetPixels) / TING_LEFT);
                        }
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                //设置菜单是否可移动
                supportSlidingMenu(0 == position);
                //设置Item选中
                for (int i = 0; i < topMenuBtnList.size(); i++) {
                    topMenuBtnList.get(i).setSelected(i == position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        centerViewPage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
    }

    private void showSearchFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        if (null == searchFragment) {
            searchFragment = new SearchFragment();
            ft.add(searchFragment, "");
        }
        ft.show(searchFragment).commit();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == searchBtn) {
            showSearchFragment();
        }
        for (Button button : topMenuBtnList) {
            if (view == button) {
                button.setSelected(true);
                centerViewPage.setCurrentItem(topMenuBtnList.indexOf(button), true);
            } else {
                button.setSelected(false);
            }
        }
    }

    @Override
    public void onShowFragment(int type) {
        BaseTopFragment fragment = baseTopFragmentMap.get(type);
        if (null == fragment) {
            fragment = TingFragment.createFragment(type);
            if (null == fragment) {
                return;
            }
            fragment.setTopFragmentCallback(this);
            baseTopFragmentMap.put(type, fragment);
        }
        fm.beginTransaction().setCustomAnimations(
                R.anim.base_top_fragment_enter_anim,
                R.anim.base_top_fragment_exit_anim)
                .replace(R.id.replaceContentView, fragment).commit();
    }

    @Override
    public void onMenu() {

    }

    /**
     * 展示Fragment
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        fm.beginTransaction().replace(R.id.replaceContentView, fragment).commit();
    }

    /**
     * 隐藏Fragment
     * @param fragment
     */
    private void hideFragment(Fragment fragment) {
        fm.beginTransaction().remove(fragment).commit();
    }
}
