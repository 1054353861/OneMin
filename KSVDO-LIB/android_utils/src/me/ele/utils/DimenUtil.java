package me.ele.utils;

import android.content.Context;

public class DimenUtil {
    
    private static final float PADDING = 0.5f;

    private DimenUtil() { }
    
    /**
     * dip转px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + PADDING);
    }

    /**
     * px转dip
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + PADDING);
    }
    
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    
    public static int getScreenWidthMinusOf(Context context, int dpValue) {
        return getScreenWidth(context) - dip2px(context, dpValue);
    }
    
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    
    public static int getScreenHeightMinusOf(Context context, int dpValue) {
        return getScreenHeight(context) - dip2px(context, dpValue);
    }
    
}
