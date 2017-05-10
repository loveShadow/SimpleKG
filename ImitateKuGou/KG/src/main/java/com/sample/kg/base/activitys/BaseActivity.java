package com.sample.kg.base.activitys;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.sample.kg.R;
import com.sample.kg.base.menu.SlidingMenu;

/**
 * Created by zhaoli on 2017/5/6.
 */
public abstract class BaseActivity extends FragmentActivity {

    protected ViewGroup viewContent;
    protected SlidingMenu slidingMenu;
    protected FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | lp.flags);
        }

        setContentView(R.layout.activity_base_layout);

        slidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);

        fm = getSupportFragmentManager();
        initViewContent();

        onCreateContentView(savedInstanceState);
    }

    protected void initViewContent() {
        viewContent = (FrameLayout) findViewById(R.id.viewContent);
        LayoutInflater.from(BaseActivity.this).inflate(getContentView(), viewContent);
    }

    /**
     * 是否支持滑动菜单
     * @param support
     */
    protected void supportSlidingMenu(boolean support) {
        slidingMenu.setSupportSliding(support);
    }

    /**
     * 获取主内容区的layoutID
     *
     * @return
     */
    protected abstract int getContentView();

    /**
     * 初始化主内容区
     *
     * @param savedInstanceState
     */
    protected abstract void onCreateContentView(Bundle savedInstanceState);
}
