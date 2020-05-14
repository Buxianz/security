package com.rbi.security.tool;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class LocalDateUtils {

    public final static String FORMAT_PATTERN="yyyy-MM-dd HH:mm:ss";
    public final static String HOUR_PATTERN="yyyy-MM-dd HH:mm";
    public final static String DATE_FORMAT_PATTERN="yyyyMMdd";
    public final static String MONTH_PATTERN="yyyyMM";
    public final static String MONTH_DAY_PATTERN="MM.dd";
    public final static String HOUR_MINUTE_PATTERN="HH:mm";
    public final static String YEAR_PATTERN="yyyy";

    /**
     * 将localDate 按照一定的格式转换成String
     * @param localDate
     * @param pattern
     * @return
     */
    public static String localDateFormat(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将localDateTime 按照一定的格式转换成String
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String localDateTimeFormat(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将String 按照pattern 转换成LocalDate
     * @param time
     * @param pattern
     * @return
     */
    public static LocalDate localDateParse(String time, String pattern) {
        return LocalDate.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将String 按照pattern 转换成LocalDateTime
     * @param time
     * @param pattern
     * @return
     */
    public static LocalDateTime localDateTimeParse(String time, String pattern) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    public static String dateFormat(Date date, String pattern) {
        return localDateTimeFormat(dateToLocalDateTime(date), pattern);
    }

    public static Date localDateToDate(LocalDate localDate){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static LocalDate dateToLocalDate(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    public static LocalDateTime dateToLocalDateTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /** 计算两个LocalDateTime 之间的毫秒数
     * @param time1
     * @param time2
     * @return
     */
    public static Long minusToMillsLocalDateTime(LocalDateTime time1, LocalDateTime time2) {
        return Duration.between(time1, time2).toMillis();
    }

    /**
     * 计算两个LocalTime 之间的毫秒数
     * @param time1
     * @param time2
     * @return
     */
    public static Long minusToMillsLocalTime(LocalTime time1, LocalTime time2) {
        return Duration.between(time1, time2).toMillis();
    }

    /**
     * 计算两个LocalDate 之间的毫秒数
     * @param time1
     * @param time2
     * @return
     */
    public static Long minusToMillsLocalDate(LocalDate time1, LocalDate time2) {
        return Duration.between(time1, time2).toMillis();
    }

    /**计算两个LocalDate 之间的Period
     *  @param time1
     *  @param time2
     *  @return
     */
    public static Period periodLocalDate(LocalDate time1, LocalDate time2) {
        return Period.between(time1, time2);
    }

    /** 计算两个Date 之间的Period
     * @param date1
     * @param date2
     * @return
     */
    public static Period periodDate(Date date1, Date date2) {
        return periodLocalDate(dateToLocalDate(date1), dateToLocalDate(date2));
    }

    /** 计算两个Date之间的 Period
     * @param time1
     * @param time2
     * @return
     */
    public static Long minusToMillsDate(Date time1, Date time2) {
        return minusToMillsLocalDateTime(dateToLocalDateTime(time1), dateToLocalDateTime(time2));
    }

    /**
     * 获取某月第一天的00:00:00
     * @param
     * @param
     * @return
     */
    public static String getFirstDayOfMonth(LocalDateTime localDateTime, String pattern) {
        return localDateTimeFormat(localDateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN), pattern);
    }

    /**
     * 获取某月最后一天的23:59:59
     * @param
     * @param
     * @return
     */
    public static String getLastDayOfMonth(LocalDateTime localDateTime, String pattern) {
        return localDateTimeFormat(localDateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX), pattern);
    }

    public static String getMonthLastYear(long minus) {
        return localDateTimeFormat(LocalDateTime.now().minusMonths(minus), MONTH_PATTERN);
    }

    public static Date getNextDate(){
        LocalDateTime nextDay = LocalDateTime.now().plusDays(1).with(LocalTime.MIN);
        Date date = LocalDateUtils.localDateTimeToDate(nextDay);
        return date;
    }

    public static String buildHourInterval(String hour){
        LocalDateTime dateTime = LocalDateUtils.localDateTimeParse(hour, LocalDateUtils.FORMAT_PATTERN);
        LocalDateTime minusHours = dateTime.minusHours(2);
        String afterHour = LocalDateUtils.localDateTimeFormat(dateTime, LocalDateUtils.HOUR_PATTERN);
        String beforHour = LocalDateUtils.localDateTimeFormat(minusHours, LocalDateUtils.HOUR_PATTERN);
        return beforHour+"~"+afterHour;
    }

    public static String buildWeekInterval(String day){
        LocalDate date = LocalDateUtils.localDateParse(day, LocalDateUtils.DATE_FORMAT_PATTERN);
        LocalDate minus = date.minusDays(6);
        String after = LocalDateUtils.localDateFormat(date, LocalDateUtils.DATE_FORMAT_PATTERN);
        String befor = LocalDateUtils.localDateFormat(minus, LocalDateUtils.DATE_FORMAT_PATTERN);
        return befor+"~"+after;
    }

}
