package com.JUtils.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类，验证数据是否符合规范
 * 
 * @Author:chenssy
 * @date:2014年8月7日
 */
public class RegexUtils {
	
	/**
	 * 判断字符串是否符合正则表达式
	 * 
	 * @author : chenssy
	 * @date : 2016年6月1日 下午12:43:05
	 *
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean find(String str, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		boolean b = m.find();
		return b;
	}
	
	/**
	 * 判断输入的字符串是否符合Email格式.
	 * @autor:chenssy
	 * @date:2014年8月7日
	 *
	 * @param email
	 * 				传入的字符串
	 * @return 符合Email格式返回true，否则返回false
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.length() < 1 || email.length() > 256) {
			return false;
		}
		Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		return pattern.matcher(email).matches();
	}
	
	/**
	 * 判断输入的字符串是否为纯汉字
	 * @autor:chenssy
	 * @date:2014年8月7日
	 *
	 * @param value
	 * 				传入的字符串
	 * @return
	 */
	public static boolean isChinese(String value) {
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
		return pattern.matcher(value).matches();
	}
	
	/**
	 * 判断是否为浮点数，包括double和float
	 * @autor:chenssy
	 * @date:2014年8月7日
	 *
	 * @param value
	 * 			传入的字符串
	 * @return
	 */
	public static boolean isDouble(String value) {
		Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
		return pattern.matcher(value).matches();
	}
	
	/**
	 * 判断是否为整数
	 * @autor:chenssy
	 * @date:2014年8月7日
	 *
	 * @param value
	 * 			传入的字符串
	 * @return
	 */
	public static boolean isInteger(String value) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
		return pattern.matcher(value).matches();
	}
}
