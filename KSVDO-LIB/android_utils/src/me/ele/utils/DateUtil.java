package me.ele.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static final int TEN = 10;
    private static final int TIME_UNIT = 60;
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd";
    
    private DateUtil() { }

    public static String getFormateTime(int secs) {
        int minutes = secs / TIME_UNIT;
        int seconds = secs % TIME_UNIT;
        return addZero(minutes) + ":" + addZero(seconds);
    }

    private static String addZero(int value) {
        return value < TEN ? ("0" + value) : String.valueOf(value);
    }

    /**
     * 获取系统日期
     * 
     * @return
     */
    public static Date getSystemDate() {
        return getSystemDate(null);
    }

    /**
     * 获取系统日期
     * 
     * @return
     */
    public static Date getSystemDate(String pattern) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        Date date = now.getTime();
        if (pattern != null) {
            return getDate(getDateString(date, pattern));
        }
        return date;
    }

    /**
     * 获取系统日期字符串
     * 
     * @param pattern
     *            日期模版
     * @return
     */
    public static String getSystemDateString(String pattern) {
        return getDateString(getSystemDate(), pattern);
    }

    /**
     * 获取系统日期字符串(默认yyyy-MM-dd格式）
     * 
     * @return
     */
    public static String getSystemDateString() {
        return getDateString(getSystemDate(), null);
    }

    /**
     * 格式化日期
     * 
     * @param date
     *            日期源数据
     * @param pattern
     *            日期模版
     * @return 日期字符串
     */
    public static String getDateString(Date date, String pattern) {
        SimpleDateFormat formater = null;
        if (pattern == null) {
            pattern = DEFAULT_PATTERN;
        }
        try {
            formater = new SimpleDateFormat(pattern, Locale.getDefault());
        } catch (Exception e) {
            throw new RuntimeException("不支持的日期模式", e);
        }
        return formater.format(date);
    }

    /**
     * 格式化日期(默认yyyy-MM-dd格式）
     * 
     * @param date
     *            日期源数据
     * @return 日期字符串
     */
    public static String getDateString(Date date) {
        return getDateString(date, null);
    }

    /**
     * 解析日期字符串
     * 
     * @param dateStr
     *            日期字符串
     * @param pattern
     *            日期模版
     * @return
     */
    public static Date getDate(String dateStr, String pattern) {
        Date d = null;
        if (dateStr == null) {
            return null;
        }
        if (pattern == null) {
            pattern = DEFAULT_PATTERN;
        }
        try {
            d = new SimpleDateFormat(pattern, Locale.getDefault()).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 解析日期字符串(默认yyyy-MM-dd格式）
     * 
     * @param dateStr
     *            日期字符串
     * @return
     */
    public static Date getDate(String dateStr) {
        return getDate(dateStr, null);
    }

}
