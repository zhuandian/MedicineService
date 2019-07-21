package com.zhuandian.medicineserver.datepicker.calendar.utils;

import android.content.Context;

/**
 * 测量工具类
 *
 * Util of measure.
 *
 */
public final class MeasureUtil {
    private MeasureUtil(){}

    public static int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
