package com.JUtils.base;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * 转换工具类<br>
 * 若待转换值为null或者出现异常，则使用默认值
 *
 * @Author:chenssy
 * @data:2016年5月21日 上午10:18:12
 */
public class ConvertUtils {
	
	/**
	 * 字符串转换为int
	 *
	 * @author:chenssy
	 * @data : 2016年5月21日 上午10:16:27
	 *
	 * @param str	
	 * 					待转换的字符串
	 * @param defaultValue
	 * 					默认值
	 * @return
	 */
	public static int strToInt(String str, int defaultValue) {
		try {
			defaultValue = Integer.parseInt(str);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	/**
	 * String转换为long
	 *
	 * @author:chenssy
	 * @data : 2016年5月21日 上午10:18:44
	 *
	 * @param str
	 * 					待转换字符串
	 * @param defaultValue
	 * 					默认值
	 * @return
	 */
	public static long strToLong(String str, long defaultValue) {
		try {
			defaultValue = Long.parseLong(str);
		} catch (Exception localException) {
		}
		return defaultValue;
	}
	
	/**
	 * 字符串转换为float
	 *
	 * @author:chenssy
	 * @data : 2016年5月21日 上午10:19:12
	 *
	 * @param str
	 * 				
	 * @param defaultValue
	 * @return
	 */
	public static float strToFloat(String str, float defaultValue) {
		try {
			defaultValue = Float.parseFloat(str);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	/**
	 * String转换为Double
	 *
	 * @author:chenssy
	 * @data : 2016年5月21日 上午10:21:59
	 *
	 * @param str
	 * 					待转换字符串
	 * @param defaultValue
	 * 					默认值
	 * @return
	 */
	public static double strToDouble(String str, double defaultValue) {
		try {
			defaultValue = Double.parseDouble(str);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	/**
	 * 字符串转换日期
	 *
	 * @author:chenssy
	 * @data : 2016年5月21日 上午10:27:01
	 *
	 * @param str
	 * 						待转换的字符串
	 * @param defaultValue
	 * 						默认日期
	 * @return
	 */
	public static java.util.Date strToDate(String str,java.util.Date defaultValue) {
		return strToDate(str, "yyyy-MM-dd HH:mm:ss", defaultValue);
	}

	/**
	 * 字符串转换为指定格式的日期
	 *
	 * @author:chenssy
	 * @data : 2016年5月21日 上午10:27:24
	 *
	 * @param str
	 * 					待转换的字符串
	 * @param format
	 * 					日期格式
	 * @param defaultValue
	 * 					默认日期
	 * @return
	 */
	public static java.util.Date strToDate(String str, String format,java.util.Date defaultValue) {
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		try {
			defaultValue = fmt.parse(str);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	/**
	 * 日期转换为字符串
	 *
	 * @author:chenssy
	 * @data : 2016年5月21日 上午10:28:05
	 *
	 * @param date
	 * 				待转换的日期
	 * @param defaultValue
	 * 				默认字符串
	 * @return
	 */
	public static String dateToStr(java.util.Date date, String defaultValue) {
		return dateToStr(date, "yyyy-MM-dd HH:mm:ss", defaultValue);
	}

	/**
	 * 日期转换为指定格式的字符串
	 * 
	 * @author:chenssy
	 * @data : 2016年5月21日 上午10:28:51
	 *
	 * @param date
	 * 				待转换的日期
	 * @param format
	 * 				指定格式
	 * @param defaultValue
	 * 				默认值
	 * @return
	 */
	public static String dateToStr(java.util.Date date, String format, String defaultValue) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			defaultValue = sdf.format(date);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	/**
	 * 如果字符串为空则使用默认字符串
	 *
	 * @author:chenssy
	 * @data : 2016年5月21日 上午10:29:35
	 *
	 * @param str
	 * 				字符串
	 * @param defaultValue
	 * 				默认值
	 * @return
	 */
	public static String strToStr(String str, String defaultValue) {
		if ((str != null) && (!(str.isEmpty())))
			defaultValue = str;
		return defaultValue;
	}

	/**
	 * util date 转换为 sqldate
	 *
	 * @author:chenssy
	 * @data : 2016年5月21日 上午10:30:09
	 *
	 * @param date
	 * @return
	 */
	public static java.sql.Date dateToSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * sql date 转换为 util date
	 *
	 * @author:chenssy
	 * @data : 2016年5月21日 上午10:30:26
	 *
	 * @param date
	 * @return
	 */
	public static java.util.Date sqlDateToDate(java.sql.Date date) {
		return new java.util.Date(date.getTime());
	}

	/**
	 * date 转换为 timestamp
	 *
	 * @author:chenssy
	 * @data : 2016年5月21日 上午10:30:51
	 *
	 * @param date
	 * @return
	 */
	public static Timestamp dateToSqlTimestamp(java.util.Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * timestamp 转换为date
	 *
	 * @author:chenssy
	 * @data : 2016年5月21日 上午10:31:13
	 *
	 * @param date
	 * @return
	 */
	public static java.util.Date qlTimestampToDate(Timestamp date) {
		return new java.util.Date(date.getTime());
	}
}
