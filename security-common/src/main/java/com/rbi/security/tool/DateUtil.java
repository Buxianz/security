package com.rbi.security.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public final static String FORMAT_PATTERN="yyyy-MM-dd HH:mm:ss";
    public final static String HOUR_PATTERN="yyyy-MM-dd HH:mm";
    public final static String YMD="yyyy-MM-dd";
    public final static String DATE_FORMAT_PATTERN="yyyyMMdd";
    public final static String MONTH_PATTERN="yyyyMM";
    public final static String MONTH_DAY_PATTERN="MM.dd";
    public final static String HOUR_MINUTE_PATTERN="HH:mm";

    public static String  date(String dateType){
        SimpleDateFormat df = new SimpleDateFormat(dateType);
        return df.format(new Date());
    }



    public static String timeStamp(){
        return String.valueOf(new Date().getTime());
    }


    /**
     * 时间加减年数
     * @param startDate 要处理的时间，Null则为当前时间
     * @param years 加减的年数
     * @return Date
     */
    public static Date dateAddYears(Date startDate, int years) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) + years);
        return c.getTime();
    }

    /**
     * 时间加减月数
     * @param startDate 要处理的时间，Null则为当前时间
     * @param months 加减的月数
     * @return Date
     */
    public static Date dateAddMonths(Date startDate, int months) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + months);
        return c.getTime();
    }

    /**
     * 时间加减天数
     * @param startDate 要处理的时间，Null则为当前时间
     * @param days 加减的天数
     * @return Date
     */
    public static Date dateAddDays(Date startDate, int days) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + days);
        return c.getTime();
    }

    /**
     * 时间加减天数
     * @param startDate 要处理的时间，Null则为当前时间
     * @param days 加减的天数
     * @return Date
     */
    public static Date dateLessDays(Date startDate, int days) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.DATE, c.get(Calendar.DATE) - days);
        return c.getTime();
    }

    /**
     * 时间比较（如果myDate>compareDate返回1，<返回-1，相等返回0）
     * @param myDate 时间
     * @param compareDate 要比较的时间
     * @return int
     */
    public static int dateCompare(Date myDate, Date compareDate) {
        Calendar myCal = Calendar.getInstance();
        Calendar compareCal = Calendar.getInstance();
        myCal.setTime(myDate);
        compareCal.setTime(compareDate);
        return myCal.compareTo(compareCal);
    }

    /**
     * String 转date
     */
    public static Date StringToDate(String strDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(strDate, pos);
            return strtodate;
    }


    /**
     * 比较日期，A>B
     */
    public static boolean comPare(Date A, Date B){
        long begin = A.getTime();
        long end = B.getTime();
        if (begin > end){
            return true;
        }else {
            return false;
        }
    }





    /**
     * String 转date
     */
    public static int DategetYear(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

}
