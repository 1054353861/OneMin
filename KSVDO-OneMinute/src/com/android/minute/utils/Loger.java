package com.android.minute.utils;

import android.util.Log;

public class Loger {

    private static final String DEFAULT_TAG = "log";
    private static boolean logFlag = true;

    private Loger() {

    }

    public static void enableLog() {
        logFlag = true;
    }

    public static void d(String tag, String msg) {
        if (msg == null)
            return;
        if (logFlag) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, int msg) {
        d(tag, String.valueOf(msg));
    }

    public static void d(Object object, int msg) {
        d(object, String.valueOf(msg));
    }

    public static void d(Object object, String msg) {
        String tag = DEFAULT_TAG;
        if (object != null) {
            tag = object.getClass().getSimpleName();
        }
        d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (msg == null)
            return;
        if (logFlag) {
            Log.e(tag, msg);
        }
    }

    public static void e(Object object, String msg) {
        String tag = DEFAULT_TAG;
        if (object != null) {
            tag = object.getClass().getSimpleName();
        }
        e(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (msg == null)
            return;
        if (logFlag) {
            Log.i(tag, msg);
        }
    }

    public static void i(Object object, String msg) {
        String tag = DEFAULT_TAG;
        if (object != null) {
            tag = object.getClass().getSimpleName();
        }
        i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (msg == null)
            return;
        if (logFlag) {
            Log.w(tag, msg);
        }
    }

    public static void w(Object object, String msg) {
        String tag = DEFAULT_TAG;
        if (object != null) {
            tag = object.getClass().getSimpleName();
        }
        w(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (msg == null)
            return;
        if (logFlag) {
            Log.v(tag, msg);
        }
    }

    public static void v(Object object, String msg) {
        String tag = DEFAULT_TAG;
        if (object != null) {
            tag = object.getClass().getSimpleName();
        }
        v(tag, msg);
    }

    public static void e(String tag, String msg, Throwable throwable) {
        if (msg == null || throwable == null)
            return;
        if (logFlag) {
            Log.e(tag, msg, throwable);
        }
    }

    public static void e(String tag, Throwable throwable) {
        if (throwable == null)
            return;
        if (logFlag) {
            Log.e(tag, throwable.getMessage(), throwable);
        }
    }

    public static void e(Object object, String msg, Throwable throwable) {
        if (msg == null || throwable == null)
            return;
        String tag = DEFAULT_TAG;
        if (object != null) {
            tag = object.getClass().getSimpleName();
        }
        if (logFlag) {
            Log.e(tag, msg, throwable);
        }
    }

    public static void e(Object object, Throwable throwable) {
        if (throwable == null)
            return;
        e(object, throwable.getMessage(), throwable);
    }

}
