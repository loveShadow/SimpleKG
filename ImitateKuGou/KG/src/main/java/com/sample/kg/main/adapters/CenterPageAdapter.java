package com.sample.kg.main.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by zhaoli on 2017/4/11.
 */
public class CenterPageAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
