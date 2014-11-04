package me.ele.utils;


import android.content.Context;
import android.widget.Toast;

/**
 * @author: chensimin;
 * @Description: TODO;  
 * @date: 2012-8-16 下午6:18:09;  
 */
public class ToastUtil {
    
    private static Toast toast;
    
    private ToastUtil() {
        
    }
    
    public static void onStop() {
        cancel();
    }
    
    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }

    public static Toast showToast(Context context, CharSequence text, boolean isLong) {
        if (context == null) return null;
        int time = isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        if (toast == null || toast.getView().getContext() != context) {
            toast = Toast.makeText(context, text, time);
        } else {
            toast.setDuration(time);
            toast.setText(text);
        }
        toast.show();
        return toast;
    }
    
    public static Toast showToast(Context context, int resId, boolean isLong) {
        if (context == null) return null;
        int time = isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        if (toast == null || toast.getView().getContext() != context) {
            toast = Toast.makeText(context, resId, time);
        } else {
            toast.setDuration(time);
            toast.setText(resId);
        }
        toast.show();
        return toast;
    }
    
    public static Toast showToastShort(Context context, Object obj) {
        return showToast(context, obj.toString(), false);
    }
    
    public static Toast showToastShort(Context context, int resourceId) {
        return showToast(context, resourceId, false);
    }
    
    public static Toast showToastLong(Context context, int resourceId) {
        return showToast(context, resourceId, true);
    }
    
    public static Toast showToastLong(Context context, Object obj) {
        return showToast(context, obj.toString(), true);
    }
}
