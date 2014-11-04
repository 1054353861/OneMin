package me.ele.utils;

import java.lang.reflect.Method;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


public class ViewHelper {

    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    
    private ViewHelper() {

    }

    public static boolean isGone(View view) {
        if (view != null) {
            return view.getVisibility() == View.GONE;
        }
        return false;
    }

    public static boolean isVisible(View view) {
        if (view != null) {
            return view.getVisibility() == View.VISIBLE;
        }
        return false;
    }

    public static void setGone(View view) {
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    public static void setVisible(View view) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void setInvisible(View view) {
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void setWidth(View view, int width, int unit) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p != null) {
            p.width = getSize(view, width, unit);
            return;
        }
        view.setLayoutParams(generateLayoutParams(view, width, WRAP_CONTENT, unit));
    }

    public static void setDpWidth(View view, int width) {
        setWidth(view, width, TypedValue.COMPLEX_UNIT_DIP);
    }
    
    public static void setPxWidth(View view, int width) {
        setWidth(view, width, TypedValue.COMPLEX_UNIT_PX);
    }
    
    public static void setHeight(View view, int height, int unit) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p != null) {
            p.height = getSize(view, height, unit);
            return;
        }
        view.setLayoutParams(generateLayoutParams(view, WRAP_CONTENT, height, unit));
    }
    
    public static void setDpHeight(View view, int height) {
        setHeight(view, height, TypedValue.COMPLEX_UNIT_DIP);
    }
    
    public static void setPxHeight(View view, int height) {
        setHeight(view, height, TypedValue.COMPLEX_UNIT_PX);
    }

    private static int getSize(View view, int size, int unit) {
        if (size == MATCH_PARENT) {
            return size;
        }
        if (size == WRAP_CONTENT) {
            return size;
        }
        if (!isDpUnit(unit)) {
            return size;
        }
        return DimenUtil.dip2px(view.getContext(), size);
    }

    public static void setLayoutInDpUnit(View view, int width, int height) {
        setLayout(view, width, height, TypedValue.COMPLEX_UNIT_DIP);
    }

    public static void setLayoutInPxUnit(View view, int width, int height) {
        setLayout(view, width, height, TypedValue.COMPLEX_UNIT_PX);
    }

    private static void setLayout(View view, int width, int height, int unit) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p != null) {
            p.width = getSize(view, width, unit);
            p.height = getSize(view, height, unit);
            return;
        }
        view.setLayoutParams(generateLayoutParams(view, width, height, unit));
    }

    private static boolean isDpUnit(int unit) {
        return TypedValue.COMPLEX_UNIT_DIP == unit;
    }

    private static ViewGroup.LayoutParams generateLayoutParams(View view, int width, int height, int unit) {
        if (view == null) {
            return null;
        }
        ViewParent parent = view.getParent();
        ViewGroup.LayoutParams lps = new ViewGroup.LayoutParams(getSize(view, width, unit), getSize(view, height, unit));
        if (parent instanceof ViewGroup) {
            try {
                View parentView = (View) view.getParent();
                Method generateLayoutParamsMethod = parentView.getClass().getDeclaredMethod("generateLayoutParams", ViewGroup.LayoutParams.class);
                generateLayoutParamsMethod.setAccessible(true);
                return (ViewGroup.LayoutParams) generateLayoutParamsMethod.invoke(parentView, lps);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lps;
    }
}
