package com.JUtils.base;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @desc:时间处理工具类
 * @Project:JUtils
 * @file:DateUtils.java
 * @Author:chenssy
 * @data:2014年8月4日
 */
public class DateUtils {
	/** yyyy:年 */
	public static final String DATE_YEAR = "yyyy";
	
	/** MM：月 */
	public static final String DATE_MONTH = "MM";
	
	/** DD：日 */
	public static final String DATE_DAY = "dd";
	
	/** HH：时 */
	public static final String DATE_HOUR = "HH";
	
	/** mm：分 */
	public static final String DATE_MINUTE = "mm";
	
	/** ss：秒 */
	public static final String DATE_SECONDES = "ss";
	
	/** yyyy-MM-dd */
	public static final String DATE_FORMAT1 = "yyyy-MM-dd";
		
	/** yyyy-MM-dd hh:mm:ss */
	public static final String DATE_FORMAT2 = "yyyy-MM-dd HH24:mm:ss";

	/** yyyy-MM-dd hh:mm:ss|SSS */
	public static final String TIME_FORMAT_SSS = "yyyy-MM-dd HH24:mm:ss|SSS";
	
	/** yyyyMMdd */
	public static final String DATE_NOFUll_FORMAT = "yyyyMMdd";
	
	/** yyyyMMddhhmmss */
	public static final String TIME_NOFUll_FORMAT = "yyyyMMddHH24mmss";
	
	public static final String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
	
	/**
	 * 根据指定格式获取当前时间
	 * @author chenssy
	 * @data Dec 27, 2013
	 * @param format
	 * @return String
	 */
	public static String getCurrentTime(String format){
		SimpleDateFormat sdf = getFormat(format);
		Date date = new Date();
		return sdf.format(date);
	}
	
	/**
	 * 获取当前时间，格式为：yyyy-MM-dd HH:mm:ss
	 * @author chenssy
	 * @data Dec 27, 2013
	 * @return String
	 */
	public static String getCurrentTime(){
		return getCurrentTime(DateUtils.DATE_FORMAT2);
	}
	
	/**
	 * 获取指定格式的当前时间：为空时格式为yyyy-mm-dd HH:mm:ss
	 * @author chenssy
	 * @data Dec 30, 2013
	 * @param format
	 * @return Date
	 * @throws Exception 
	 */
	public static Date getCurrentDate(String format) throws Exception{
		 SimpleDateFormat sdf = getFormat(format);
		 String dateS = getCurrentTime(format);
		 Date date = null;
		 try {
			 date = sdf.parse(dateS);
		} catch (ParseException e) {
			throw new Exception("时间转换出错..");
		}
		return date;
	}
	
	/**
	 * 获取当前时间，格式为yyyy-MM-dd HH:mm:ss
	 * @author chenssy
	 * @data Dec 30, 2013
	 * @return Date
	 * @throws Exception 
	 */
	public static Date getCurrentDate() throws Exception{
		return getCurrentDate(DateUtils.DATE_FORMAT2);
	}
	
	/**
	 * 
	 * 格式转换<br>
	 * yyyy-MM-dd hh:mm:ss 和 yyyyMMddhhmmss 相互转换<br>
	 * yyyy-mm-dd 和yyyymmss 相互转换
	 * @author chenssy
	 * @data Dec 26, 2013
	 * @param value 
	 * 				日期
	 * @return String
	 */
	public static String stringFormat(String value) {
		String sReturn = "";
		if (value == null || "".equals(value))
			return sReturn;
		if (value.length() == 14) {   //长度为14格式转换成yyyy-mm-dd hh:mm:ss
			sReturn = value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8) + " "
					+ value.substring(8, 10) + ":" + value.substring(10, 12) + ":" + value.substring(12, 14);
			return sReturn;
		}
		if (value.length() == 19) {   //长度为19格式转换成yyyymmddhhmmss
			sReturn = value.substring(0, 4) + value.substring(5, 7) + value.substring(8, 10) + value.substring(11, 13)
					+ value.substring(14, 16) + value.substring(17, 19);
			return sReturn;
		}
		if(value.length() == 10){     //长度为10格式转换成yyyymmhh
			sReturn = value.substring(0, 4) + value.substring(5,7) + value.substring(8,10);
		}
		if(value.length() == 8){      //长度为8格式转化成yyyy-mm-dd
			sReturn = value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8);
		}
		return sReturn;
	}
	
	/**
	 * 给指定日期加入年份，为空时默认当前时间
	 * @author chenssy
	 * @data Dec 30, 2013
	 * @param year 年份  正数相加、负数相减
	 * @param date 为空时，默认为当前时间
	 * @param format 默认格式为：yyyy-MM-dd HH:mm:ss
	 * @return String
	 * @throws Exception 
	 */
	public static String addYearToDate(int year,Date date,String format) throws Exception{
		Calendar calender = getCalendar(date,format);
		SimpleDateFormat sdf = getFormat(format);
		
		calender.add(Calendar.YEAR, year);
		
		return sdf.format(calender.getTime());
	}
	
	/**
	 * 给指定日期加入年份，为空时默认当前时间
	 * @author chenssy
	 * @data Dec 30, 2013
	 * @param year 年份  正数相加、负数相减
	 * @param date 为空时，默认为当前时间
	 * @param format 默认格式为：yyyy-MM-dd HH:mm:ss
	 * @return String
	 * @throws Exception 
	 */
	public static String addYearToDate(int year,String date,String format) throws Exception{
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addYearToDate(year, newDate, format);
	}
	
	/**
	 * 给指定日期增加月份 为空时默认当前时间
	 * @author chenssy
	 * @data Dec 30, 2013
	 * @param month  增加月份  正数相加、负数相减
	 * @param date 指定时间
	 * @param format 指定格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 * @throws Exception 
	 */
	public static String addMothToDate(int month,Date date,String format) throws Exception{
		Calendar calender = getCalendar(date,format);
		SimpleDateFormat sdf = getFormat(format);
		
		calender.add(Calendar.MONTH, month);
		
		return sdf.format(calender.getTime());
	}
	
	/**
	 * 给指定日期增加月份 为空时默认当前时间
	 * @author chenssy
	 * @data Dec 30, 2013
	 * @param month  增加月份  正数相加、负数相减
	 * @param date 指定时间
	 * @param format 指定格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 * @throws Exception 
	 */
	public static String addMothToDate(int month,String date,String format) throws Exception{
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addMothToDate(month, newDate, format);
	}
	
	/**
	 * 给指定日期增加天数，为空时默认当前时间
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param day 增加天数 正数相加、负数相减
	 * @param date 指定日期
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 * @throws Exception 
	 */
	public static String addDayToDate(int day,Date date,String format) throws Exception{
		Calendar calendar = getCalendar(date, format);
		SimpleDateFormat sdf = getFormat(format);
		
		calendar.add(Calendar.DATE, day);
		
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 给指定日期增加天数，为空时默认当前时间
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param day 增加天数 正数相加、负数相减
	 * @param date 指定日期
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 * @throws Exception 
	 */
	public static String addDayToDate(int day,String date,String format) throws Exception{
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addDayToDate(day, newDate, format);
	}
	
	/**
	 * 给指定日期增加小时，为空时默认当前时间
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param hour 增加小时  正数相加、负数相减
	 * @param date 指定日期
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 * @throws Exception 
	 */
	public static String addHourToDate(int hour,Date date,String format) throws Exception{
		Calendar calendar = getCalendar(date, format);
		SimpleDateFormat sdf = getFormat(format);
		
		calendar.add(Calendar.HOUR, hour);
		
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 给指定日期增加小时，为空时默认当前时间
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param hour 增加小时  正数相加、负数相减
	 * @param date 指定日期
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 * @throws Exception 
	 */
	public static String addHourToDate(int hour,String date,String format) throws Exception{
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addHourToDate(hour, newDate, format);
	}
	
	/**
	 * 给指定的日期增加分钟，为空时默认当前时间
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param minute 增加分钟  正数相加、负数相减
	 * @param date 指定日期 
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 * @throws Exception 
	 */
	public static String addMinuteToDate(int minute,Date date,String format) throws Exception{
		Calendar calendar = getCalendar(date, format);
		SimpleDateFormat sdf = getFormat(format);
		
		calendar.add(Calendar.MINUTE, minute);
		
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 给指定的日期增加分钟，为空时默认当前时间
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param minute 增加分钟  正数相加、负数相减
	 * @param date 指定日期 
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 * @throws Exception 
	 */
	public static String addMinuteToDate(int minute,String date,String format) throws Exception{
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addMinuteToDate(minute, newDate, format);
	}
	
	/**
	 * 给指定日期增加秒，为空时默认当前时间
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param second 增加秒 正数相加、负数相减
	 * @param date 指定日期
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 * @throws Exception 
	 */
	public static String addSecondToDate(int second,Date date,String format) throws Exception{
		Calendar calendar = getCalendar(date, format);
		SimpleDateFormat sdf = getFormat(format);
		
		calendar.add(Calendar.SECOND, second);
		
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 给指定日期增加秒，为空时默认当前时间
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param second 增加秒 正数相加、负数相减
	 * @param date 指定日期
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 * @throws Exception 
	 */
	public static String addSecondToDate(int second,String date,String format) throws Exception{
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addSecondToDate(second, newDate, format);
	}
	
	/**
	 * 获取指定格式指定时间的日历
	 * @author chenssy
	 * @data Dec 30, 2013
	 * @param date 时间 
	 * @param format 格式
	 * @return Calendar
	 * @throws Exception 
	 */
	public static Calendar getCalendar(Date date,String format) throws Exception{
		if(date == null){
			date = getCurrentDate(format);
		}
		
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		
		return calender;
	}
	
	/**
	 * 获取日期显示格式，为空默认为yyyy-mm-dd HH:mm:ss
	 * @author chenssy
	 * @data Dec 30, 2013
	 * @param format
	 * @return
	 * @return SimpleDateFormat
	 */
	private static SimpleDateFormat getFormat(String format){
		if(format == null || "".equals(format)){
			format = DateUtils.DATE_FORMAT2;
		}
		return new SimpleDateFormat(format);
	}
	
	/**
	 * 将字符串(格式符合规范)转换成Date
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param value 需要转换的字符串
	 * @param format 日期格式 
	 * @return Date
	 * @throws Exception 
	 */
	public static Date string2Date(String value,String format) throws Exception{
		if(value == null || "".equals(value)){
			return null;
		}
		
		SimpleDateFormat sdf = getFormat(format);
		Date date = null;
		value = formatDate(value, format);
		try {
			date = sdf.parse(value);
		} catch (ParseException e) {
			throw new Exception("时间转换出错..");
		}
		return date;
	}
	
	/**
	 * 将日期格式转换成String
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param value 需要转换的日期
	 * @param format 日期格式
	 * @return String
	 */
	public static String date2String(Date value,String format){
		if(value == null){
			return null;
		}
		
		SimpleDateFormat sdf = getFormat(format);
		return sdf.format(value);
	}
	
	/**
	 * @desc:格式化时间
	 * @autor:chenssy
	 * @data:2014年8月6日
	 *
	 * @param value 时间
	 * @param format 指定格式
	 * @return
	 * @throws Exception 
	 * @throws ParseException 
	 */
	public static String formatDate(String date,String format) throws Exception{
		if(ValidateHelper.isEmpty(date) || ValidateHelper.isEmpty(format)){
			return "";
		}
		Date dt = null;
		SimpleDateFormat inFmt = null;
		SimpleDateFormat outFmt = null;
		ParsePosition pos = new ParsePosition(0);
		date = date.replace("-", "").replace(":", "");
		if ((date == null) || ("".equals(date.trim())))
			return "";
		try {
			if (Long.parseLong(date) == 0L)
				return "";
		} catch (Exception nume) {
			return date;
		}
		try {
			switch (date.trim().length()) {
			case 14:
				inFmt = new SimpleDateFormat("yyyyMMddHHmmss");
				break;
			case 12:
				inFmt = new SimpleDateFormat("yyyyMMddHHmm");
				break;
			case 10:
				inFmt = new SimpleDateFormat("yyyyMMddHH");
				break;
			case 8:
				inFmt = new SimpleDateFormat("yyyyMMdd");
				break;
			case 6:
				inFmt = new SimpleDateFormat("yyyyMM");
				break;
			case 7:
			case 9:
			case 11:
			case 13:
			default:
				return date;
			}
			if ((dt = inFmt.parse(date, pos)) == null)
				return date;
			if ((format == null) || ("".equals(format.trim()))) {
				outFmt = new SimpleDateFormat("yyyy年MM月dd日");
			} else {
				outFmt = new SimpleDateFormat(format);
			}
			return outFmt.format(dt);
		} catch (Exception ex) {
		}
		return date;
	}
	
	/**
	 * @desc:格式化是时间，采用默认格式（yyyy-MM-dd HH24:mm:ss）
	 * @autor:chenssy
	 * @data:2014年8月6日
	 *
	 * @param value
	 * @return
	 * @throws Exception 
	 */
	public static String formatDate(String value) throws Exception{
		return getFormat(DateUtils.DATE_FORMAT2).format(string2Date(value, DateUtils.DATE_FORMAT2));
	}
	
	
	/**
	 * 获取指定日期的年份
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param value 日期
	 * @return int
	 */
	public static int getCurrentYear(Date value){
		String date = date2String(value, DateUtils.DATE_YEAR);
		return Integer.valueOf(date);
	}
	
	/**
	 * 获取指定日期的年份
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param value 日期
	 * @return int
	 * @throws Exception 
	 */
	public static int getCurrentYear(String value) throws Exception{
		Date date = string2Date(value, DateUtils.DATE_YEAR);
		Calendar calendar = getCalendar(date, DateUtils.DATE_YEAR);
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取指定日期的月份
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param value 日期
	 * @return int
	 */
	public static int getCurrentMonth(Date value){
		String date = date2String(value, DateUtils.DATE_MONTH);
		return Integer.valueOf(date);
	}
	
	/**
	 * 获取指定日期的月份
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param value 日期
	 * @return int
	 * @throws Exception 
	 */
	public static int getCurrentMonth(String value) throws Exception{
		Date date = string2Date(value, DateUtils.DATE_MONTH);
		Calendar calendar = getCalendar(date, DateUtils.DATE_MONTH);
		
		return calendar.get(Calendar.MONTH);
	}
	
	/**
	 * 获取指定日期的天份
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param value 日期
	 * @return int
	 */
	public static int getCurrentDay(Date value){
		String date = date2String(value, DateUtils.DATE_DAY);
		return Integer.valueOf(date);
	}
	
	/**
	 * 获取指定日期的天份
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param value 日期
	 * @return int
	 * @throws Exception 
	 */
	public static int getCurrentDay(String value) throws Exception{
		Date date = string2Date(value, DateUtils.DATE_DAY);
		Calendar calendar = getCalendar(date, DateUtils.DATE_DAY);
		
		return calendar.get(Calendar.DATE);
	}
	
	/**
	 * 获取当前日期为星期几
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param value 日期
	 * @return String
	 * @throws Exception 
	 */
	public static String getCurrentWeek(Date value) throws Exception{
		Calendar calendar = getCalendar(value, DateUtils.DATE_FORMAT1);
		int weekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1 < 0 ? 0 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
		
		return weeks[weekIndex];
	}
	
	/**
	 * 获取当前日期为星期几
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param value 日期
	 * @return String
	 * @throws Exception 
	 */
	public static String getCurrentWeek(String value) throws Exception{
		Date date = string2Date(value, DateUtils.DATE_FORMAT1);
		return getCurrentWeek(date);
	}
	
	/**
	 * 获取指定日期的小时
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param value 日期
	 * @return int
	 */
	public static int getCurrentHour(Date value){
		String date = date2String(value, DateUtils.DATE_HOUR);
		return Integer.valueOf(date);
	}
	
	/**
	 * 获取指定日期的小时
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param value 日期
	 * @return
	 * @return int
	 * @throws Exception 
	 */
	public static int getCurrentHour(String value) throws Exception{
		Date date = string2Date(value, DateUtils.DATE_HOUR);
		Calendar calendar = getCalendar(date, DateUtils.DATE_HOUR);
		
		return calendar.get(Calendar.DATE);
	}
	
	/**
	 * 获取指定日期的分钟
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param value 日期
	 * @return int
	 */
	public static int getCurrentMinute(Date value){
		String date = date2String(value, DateUtils.DATE_MINUTE);
		return Integer.valueOf(date);
	}
	
	/**
	 * 获取指定日期的分钟
	 * @author chenssy
	 * @data Dec 31, 2013
	 * @param value 日期
	 * @return int
	 * @throws Exception 
	 */
	public static int getCurrentMinute(String value) throws Exception{
		Date date = string2Date(value, DateUtils.DATE_MINUTE);
		Calendar calendar = getCalendar(date, DateUtils.DATE_MINUTE);
		
		return calendar.get(Calendar.MINUTE);
	}
	
	/**  
	 * 比较两个日期相隔多少天(月、年) <br>
	 * 例：<br>
	 * &nbsp;compareDate("2009-09-12", null, 0);//比较天 <br>
     * &nbsp;compareDate("2009-09-12", null, 1);//比较月 <br> 
     * &nbsp;compareDate("2009-09-12", null, 2);//比较年 <br>
     * 
	 * @author chenssy
	 * @data Dec 31, 2013 
     * @param startDay 需要比较的时间 不能为空(null),需要正确的日期格式 ,如：2009-09-12   
     * @param endDay 被比较的时间  为空(null)则为当前时间    
     * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年    
     * @return int
	 * @throws Exception 
     */    
    public static int compareDate(String startDay,String endDay,int stype) throws Exception{     
        int n = 0;     
        startDay = formatDate(startDay, "yyyy-MM-dd");
        endDay = formatDate(endDay, "yyyy-MM-dd");
        
        String formatStyle = stype==1?"yyyy-MM":"yyyy-MM-dd";     
             
        endDay = endDay==null ? getCurrentTime("yyyy-MM-dd") : endDay;     
             
        DateFormat df = new SimpleDateFormat(formatStyle);     
        Calendar c1 = Calendar.getInstance();     
        Calendar c2 = Calendar.getInstance();     
        try {     
            c1.setTime(df.parse(startDay));     
            c2.setTime(df.parse(endDay));   
        } catch (Exception e) {     
        	throw new Exception("时间转换出错..");
        }     
        while (!c1.after(c2)) {                   // 循环对比，直到相等，n 就是所要的结果     
            n++;     
            if(stype==1){     
                c1.add(Calendar.MONTH, 1);          // 比较月份，月份+1     
            }     
            else{     
                c1.add(Calendar.DATE, 1);           // 比较天数，日期+1     
            }     
        }     
        n = n-1;     
        if(stype==2){     
            n = (int)n/365;     
        }        
        return n;     
    }   
    
    /**
     * 比较两个时间相差多少小时(分钟、秒)
     * @author chenssy
     * @data Jan 2, 2014
     * @param startTime 需要比较的时间 不能为空，且必须符合正确格式：2012-12-12 12:12:
     * @param endTime 需要被比较的时间 若为空则默认当前时间
     * @param type 1：小时   2：分钟   3：秒
     * @return int
     * @throws Exception 
     */
    public static int compareTime(String startTime , String endTime , int type) throws Exception{
    	//endTime是否为空，为空默认当前时间
    	if(endTime == null || "".equals(endTime)){
    		endTime = getCurrentTime();
    	}
    	
    	SimpleDateFormat sdf = getFormat("");
    	int value = 0;
    	try {
			Date begin = sdf.parse(startTime);
			Date end = sdf.parse(endTime);
			long between = (end.getTime() - begin.getTime()) / 1000;  //除以1000转换成豪秒
			if(type == 1){   //小时
				value = (int) (between % (24 * 36000) / 3600);
			}
			else if(type == 2){
				value = (int) (between % 3600 / 60);
			}
			else if(type == 3){
				value = (int) (between % 60 / 60);
			}
		} catch (ParseException e) {
			throw new Exception("时间转换出错..");
		}
    	return value;
    }
    
    /**
     * 比较两个日期的大小。<br>
     * 若date1 > date2 则返回 1<br>
     * 若date1 = date2 则返回 0<br>
     * 若date1 < date2 则返回-1
     * @autor:chenssy
     * @data:2014年9月9日
     *
     * @param date1  
     * @param date2
     * @param format  待转换的格式
     * @return 比较结果
     */
    public static int compare(String date1, String date2,String format) {
        DateFormat df = getFormat(format);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    
    /**
     * 将String 转换为 timestamp<br>
     * 注：value必须形如： yyyy-mm-dd hh:mm:ss[.f...] 这样的格式，中括号表示可选，否则报错！！！ 
     * @autor:chenssy
     * @data:2014年9月22日
     *
     * @param value
     * @param format
     * @return
     * @throws Exception 
     */
    public static Timestamp string2Timestamp(String value) throws Exception{
    	Timestamp ts = new Timestamp(System.currentTimeMillis());  
    	ts = Timestamp.valueOf(value);
    	return ts;
    }
    
    /**
     * 将timeStamp 转换为String类型，format为null则使用默认格式 yyyy-MM-dd HH:mm:ss
     * @autor:chenssy
     * @data:2014年9月22日
     *
     * @param value
     * @param format
     * @return
     */
    public static String timeStamp2String(Timestamp value,String format){
    	if(null == value){
    		return "";
    	}
    	SimpleDateFormat sdf = getFormat(format);
    	
    	return sdf.format(value);
    }
}
