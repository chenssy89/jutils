package com.JUtils.jsp;

import com.JUtils.base.DateUtils;

/**
 * 用于在 web 页面显示
 * @Project:JUtils
 * @file:JSPBeanUtils.java
 * @Author:chenssy
 * @data:2014年8月6日
 */
public class JSPBeanUtils {
	
	/**
	 * 获取时间戳(格式为：yyyyMMddHH24mmss)
	 * @autor:chenming
	 * @data:2014年8月6日
	 *
	 * @return
	 */
	public static String getTimeStamp(){
		return DateUtils.getCurrentTime("yyyyMMddHH24mmss");
	}
	
	/**
	 * 根据指定格式获取当前时间
	 * @autor:chenming
	 * @data:2014年8月7日
	 *
	 * @param format
	 * 			指定格式
	 * @return
	 */
	public static String getCurrentTime(String format){
		return DateUtils.getCurrentTime(format);
	}
	
}
