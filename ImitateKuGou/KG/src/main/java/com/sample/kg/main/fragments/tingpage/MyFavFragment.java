package com.sample.kg.main.fragments.tingpage;

import com.sample.kg.R;
import com.sample.kg.base.fragments.BaseTopFragment;

/**
 * Created by zhaoli on 2017/5/7.
 */
public class MyFavFragment extends BaseTopFragment {
    @Override
    protected String getFragmentTitle() {
        return getResources().getString(R.string.head_ting_fav_title);
    }

    @Override
    protected int getFragmentContentLayout() {
        return R.layout.fragment_ting_page_my_fav_layout;
    }

    @Override
    protected void onCreateFragmentContentView() {

    }
}
