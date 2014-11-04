package me.ele.utils;

import java.util.Calendar;
import java.util.TimeZone;

public class CalendarUtils {
    
    public static final int MILLIS_SECONDS_UNIT = 1000;

    private CalendarUtils() { }

    public static Calendar getSystemCalendar() {
        Calendar now = Calendar.getInstance();
        now.setTime(DateUtil.getSystemDate());
        return now;
    }
    
    public static Calendar getCalendar(long seconds) {
        Calendar now = Calendar.getInstance();
        now.setTimeZone(TimeZone.getDefault());
        now.setTimeInMillis(seconds * MILLIS_SECONDS_UNIT);
        return now;
    }
    
    public static Calendar getCalendar(String time, String pattern) {
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(DateUtil.getDate(time, pattern));
        return timeCalendar;
    }

    public static int getDayOfYear(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static int getHourOfDay(Calendar calendar) {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    
    public static int getMinute(Calendar calendar) {
        return calendar.get(Calendar.MINUTE);
    }
    
    public static int getYear(Calendar calendar) {
        return calendar.get(Calendar.YEAR);
    }
}
