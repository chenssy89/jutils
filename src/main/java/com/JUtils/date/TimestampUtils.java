package com.JUtils.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeStamp工具类，提供TimeStamp与String、Date的转换
 *
 * @author chenssy
 * @date 2016-09-24
 * @since 1.0.0
 */
public class TimestampUtils {

    /**
     * String转换为TimeStamp
     * @param value
     *              待转换的String，格式必须为 yyyy-mm-dd hh:mm:ss[.f...] 这样的格式，中括号表示可选，否则报错
     * @return java.sql.Timestamp
     *
     * @author chenssy
     * @date 2016-09-24
     * @since v1.0.0
     */
    public static Timestamp string2Timestamp(String value){
        if(value == null && !"".equals(value.trim())){
            return null;
        }
    	Timestamp ts = new Timestamp(System.currentTimeMillis());
    	ts = Timestamp.valueOf(value);
    	return ts;
    }

    /**
     * 将Timestamp 转换为String类型，format为null则使用默认格式 yyyy-MM-dd HH:mm:ss
     *
     * @param value
     *              待转换的Timestamp
     * @param format
     *              String的格式
     * @return java.lang.String
     *
     * @author chenssy
     * @date 2016-09-24
     * @since v1.0.0
     */
    public static String timestamp2String(Timestamp value,String format){
    	if(null == value){
    		return "";
    	}
    	SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
    	
    	return sdf.format(value);
    }

    /**
     * Date转换为Timestamp
     *
     * @param date
     *              待转换的Date
     * @return java.sql.Timestamp
     *
     * @author chenssy
     * @date 2016-09-24
     * @since v1.0.0
     */
    public static Timestamp date2Timestamp(Date date){
        if(date == null){
            return null;
        }
        return new Timestamp(date.getTime());
    }

    /**
     * Timestamp转换为Date
     *
     * @param time
     *              待转换的Timestamp
     * @return java.util.Date
     *
     * @author chenssy
     * @date 2016-09-24
     * @since v1.0.0
     */
    public static Date timestamp2Date(Timestamp time){
        return time == null ? null : time;
    }
}
