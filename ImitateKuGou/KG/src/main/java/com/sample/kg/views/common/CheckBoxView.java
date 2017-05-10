package com.sample.kg.views.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sample.kg.R;

/**
 * Created by zhaoli on 2017/3/11.
 * 复选框
 */
public class CheckBoxView extends FrameLayout implements View.OnClickListener {

    private FrameLayout checkboxBg;
    private ImageView checkbox;
    private boolean checked = false;

    public CheckBoxView(Context context) {
        this(context, null);
    }

    public CheckBoxView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.checkbox_layout, this);
        checkboxBg = (FrameLayout) findViewById(R.id.checkbox_bg);
        checkbox = (ImageView) findViewById(R.id.checkbox);
        checkboxBg.setOnClickListener(this);
        onCheckboxStateChange();
    }

    @Override
    public void onClick(View v) {
        if (v == checkboxBg) {
            checked = !checked;
            onCheckboxStateChange();
            callOnClick();
        }
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    private void onCheckboxStateChange() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        if (checked) {
            params.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
            checkboxBg.setBackgroundResource(R.drawable.home_menu_bottom_checkbox_bg_selected);
        } else {
            params.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
            checkboxBg.setBackgroundResource(R.drawable.home_menu_bottom_checkbox_bg);
        }
        checkbox.setLayoutParams(params);
    }
}
