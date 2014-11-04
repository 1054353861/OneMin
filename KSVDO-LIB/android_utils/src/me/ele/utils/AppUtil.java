package me.ele.utils;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUtil {
    
    private AppUtil() { }

    /**
     * 获得软件版本
     * 
     * @param con
     * @return
     */
    public static String getVersionName(final Context con) {
        String version = "0.0.0";
        PackageManager packageManager = con.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(con.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
    
    /**
     * 获得软件版本
     * 
     * @param con
     * @return
     */
    public static int getVersionCode(final Context con) {
        int version = 1;

        PackageManager packageManager = con.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(con.getPackageName(), 0);
            version = packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }
    
    
    /**
     * 手机系统信息
     * @return
     */
    public static String getSystemInfo() {
        return android.os.Build.MODEL + " " + android.os.Build.VERSION.RELEASE;
    }
    
    @SuppressWarnings("unchecked")
    public synchronized static Class<? extends Activity> getCurrentTopActivityClass(Context con) {
        ActivityManager mActivityManager = (ActivityManager) con.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
        String className = ar.topActivity.getClassName();
        Class<? extends Activity> clazz = null;
        try {
            clazz = (Class<? extends Activity>) Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }
    
    public static synchronized boolean isBackgroundRunning(Context con) {

        ActivityManager activityManager = (ActivityManager) con.getSystemService(Service.ACTIVITY_SERVICE);
        KeyguardManager keyguardManager = (KeyguardManager) con.getSystemService(Service.KEYGUARD_SERVICE);

        if (activityManager == null) return false;
        List<RunningAppProcessInfo> processList = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : processList) {

            if (process.processName.equals(con.getPackageName())) {
                boolean isBackground = process.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                        && process.importance != RunningAppProcessInfo.IMPORTANCE_VISIBLE;
                boolean isLockedState = keyguardManager.inKeyguardRestrictedInputMode();
                if (isBackground || isLockedState) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
    
    public static synchronized boolean isForegroundRunning(Context con) {
        return !isBackgroundRunning(con);
    }
    
}
