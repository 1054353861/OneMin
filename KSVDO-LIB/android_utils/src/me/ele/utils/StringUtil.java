package me.ele.utils;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * @author: chensimin;
 * @Description: TODO;
 * @date: 2012-8-16 下午6:18:05;
 */
public class StringUtil {

    private static final int TEN = 10;
    private static final String MAIL_REGEX = "([_A-Za-z0-9-]+)(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})";
    private static final String CELL_PHONE_REGEX = "^1\\d{10}$";

    private StringUtil() {

    }

    public static String formatDouble(double value) {
        return new DecimalFormat("####.##").format(value);
    }
    
    public static String formatMoney(double value) {
        return "¥" + new DecimalFormat("####.##").format(value);
    }

    public static boolean isEmail(String email) {
        if (isBlank(email)) {
            return false;
        }
        return Pattern.matches(MAIL_REGEX, email);
    }

    public static boolean isCellPhone(String phone) {
        if (isBlank(phone)) {
            return false;
        }
        return Pattern.matches(CELL_PHONE_REGEX, phone);
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlank(String str) {
        return str == null || "".equals(str.trim());
    }

    public static String trim(String str) {
        if ((str != null) && (str.length() != 0))
            str = str.trim();
        return str;
    }

    private static String formatTime(int value) {
        return value < TEN ? "0" + value : value + "";
    }

    public static String getTimeString(int hour, int minute) {
        return formatTime(hour) + ":" + formatTime(minute);
    }

    public static String charArray2String(char[] chars) {
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(c);
        }
        return sb.toString();
    }
    
    public static String removeSpace(String candidate) {
        return candidate.replaceAll("\\s+", "_");
    }
    
}
