package com.sample.kg.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Created by zhaoli on 2017/3/9.
 */
public class ResourceUtil {
    /**
     * 获取Drawable
     * @param context
     * @param resId
     * @return
     */
    public static Drawable getDrawable(Context context, int resId) {
        if (null == context || resId < 0) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(resId);
        } else {
            return context.getResources().getDrawable(resId);
        }
    }

    public static int getColor(Context context, int resId) {
        if (null == context || resId < 0) {
            return -1;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getColor(resId);
        } else {
            return context.getResources().getColor(resId);
        }
    }
}
