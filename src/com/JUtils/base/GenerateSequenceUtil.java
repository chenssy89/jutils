package com.JUtils.base;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 根据时间生成唯一序列ID<br>
 * 时间精确到秒，ID最大值为99999且循环使用
 * 
 * @Author:chenssy
 * @date:2016年4月17日
 */
public class GenerateSequenceUtil {
	private static final FieldPosition HELPER_POSITION = new FieldPosition(0);
	
	/** 时间：精确到秒 */
	private final static Format dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
	
	private final static NumberFormat numberFormat = new DecimalFormat("00000");
	
	private static int seq = 0;
	 
    private static final int MAX = 99999;
	
	public static synchronized String generateSequenceNo() {
		 
        Calendar rightNow = Calendar.getInstance();
       
        StringBuffer sb = new StringBuffer();
 
        dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);
 
        numberFormat.format(seq, sb, HELPER_POSITION);
 
        if (seq == MAX) {
            seq = 0;
        } else {
            seq++;
        }
 
        return sb.toString();
    }
}
