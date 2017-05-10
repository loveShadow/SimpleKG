package com.sample.kg.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by zhaoli on 2017/5/6.
 */
public class ScreenUtil {
    private static DisplayMetrics dm = null;

    public static int getScreenWidth(Context context) {
        if (null != dm) {
            return dm.widthPixels;
        }
        initDisplayMetrics(context);
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        if (null != dm) {
            return dm.heightPixels;
        }
        initDisplayMetrics(context);
        return dm.heightPixels;
    }

    private static void initDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
    }
}
