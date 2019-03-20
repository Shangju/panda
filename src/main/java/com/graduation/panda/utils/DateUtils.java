package com.graduation.panda.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String HOUR_PATTERN = "yyyy-MM-dd HH";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String YEAR_PATTERN = "yyyy";
    public static final String MIN_ONLY_PATTERN = "mm";
    public static final String HOUR_ONLY_PATTERN = "HH";

    /**
     * 时间格式化成字符串
     * @param date  Date
     * @param pattern  如果为空，则为yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static String dateFormat(Date date,String pattern) throws ParseException {
        if(StringUtils.isBlank(pattern)){
            pattern = DateUtils.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 字符串格式化成时间对象
     * @param dateTimeString    String
     * @param pattern  如果为空，则为yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static Date dateParse(String dateTimeString,String pattern) throws ParseException{
        if(StringUtils.isBlank(pattern)){
            pattern = DateUtils.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateTimeString);
    }

    public static void main(String[] args) {
        try{
            System.out.println(dateParse("2017-02-04 14:58:20", DATE_TIME_PATTERN));
            System.out.println(dateFormat(new Date(), null));
            System.out.println(new Date());
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
