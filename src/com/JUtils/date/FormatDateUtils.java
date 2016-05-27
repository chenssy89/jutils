package com.JUtils.date;

/**
 * 日期格式化工具类
 *
 * @Author:chenssy
 * @date:2016年5月26日 下午12:39:57
 *
 */
public class FormatDateUtils {
	/**
	 * 
	 * 格式转换<br>
	 * yyyy-MM-dd hh:mm:ss 和 yyyyMMddhhmmss 相互转换<br>
	 * yyyy-mm-dd 和yyyymmss 相互转换
	 * @author chenssy
	 * @date Dec 26, 2013
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
}
