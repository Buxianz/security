package com.rbi.security.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    /**
     * 使用正则表达式来判断字符串中是否包含字母
     * @param str 待检验的字符串
     * @return 返回是否包含
     * true: 包含字母 ;false 不包含字母
     */
    public static boolean judgeContainsStr(String pattern,String str) {
        String regex=pattern+"+.*";
        Matcher m= Pattern.compile(regex).matcher(str);
        return m.matches();
    }

    /**
     * 获取上月月份
     * @param args
     * @throws ParseException
     */
    public static String getMonth(Integer count){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, count);
        Date m = calendar.getTime();
        String mon = format.format(m);
        return mon;
    }

    public static String getSpecialFormatMonth(Integer count){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, count);
        Date m = calendar.getTime();
        String mon = format.format(m);
        return mon;
    }


    public static String  getYear(Integer count){
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, count);
        Date m = calendar.getTime();
        String year = format.format(m);
        return year;
    }


    public static void main(String[] args) {
        String pa = "VEHICLE_VOLUME_2_3_1_";
        String str = "VEHICLE_VOLUME_2_3_1_s";
        System.out.println(judgeContainsStr(pa,str));

        System.out.println(getMonth(0));


        System.out.println(getYear(-1));
    }


}
