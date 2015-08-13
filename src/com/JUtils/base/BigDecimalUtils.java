package com.JUtils.base;

import java.math.BigDecimal;

/**
 * 提供精确的加减乘除运算
 * @Project:JUtils
 * @file:BigDecimalUtils.java
 * @Authro:chenming
 * @data:2014年9月15日
 */
public class BigDecimalUtils {
	
	/**
	 * 默认保留位：2
	 */
	private static int 	DEFAULT_SCALE = 2;
	
	/**
	 * 默认四舍五入规则为：向上舍入
	 */
	private static int DEFAULT_ROUND = BigDecimal.ROUND_HALF_UP;
	
	/**
	 * 
	 * 加法运算
	 * @autor:chenming
	 * @data:2014年9月15日
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static String add(String v1,String v2){    
        BigDecimal b1 = new BigDecimal(v1);    
        BigDecimal b2 = new BigDecimal(v2);    
        return b1.add(b2).toString();
    }    
	
	/**
	 * 除法运算<br>
	 * 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
	 * @autor:chenming
	 * @data:2014年9月15日
	 *
	 * @param v1
	 * 			除数
	 * @param v2
	 * 			被除数
	 * @param scale
	 * 			精确精度
	 * @return
	 */
	 public static String div(String v1,String v2,int scale,int round){    
	        if(scale<0){    
	            throw new IllegalArgumentException(    
	                "The scale must be a positive integer or zero");    
	        }    
	        
	        if(ValidateHelper.isEmpty(scale)){
	        	scale = DEFAULT_SCALE;
	        }
	        
	        if(ValidateHelper.isEmpty(round)){
	        	round = DEFAULT_ROUND;
	        }
	        
	        BigDecimal b1 = new BigDecimal(v1);    
	        BigDecimal b2 = new BigDecimal(v2);    
	        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).toString();    
	    }   
	
	/**
	 * 比较两个数<br>
	 * v1 > v2 return 1<br>
	 * v1 = v2 return 0<br>
	 * v1 < v2 return -1
	 * @autor:chenming
	 * @data:2014年9月15日
	 *
	 * @param v1
	 * 			比较数
	 * @param v2
	 * 			被比较数
	 * @return
	 */
	public static int compareTo(String v1,String v2){    
        BigDecimal b1 = new BigDecimal(v1);    
        BigDecimal b2 = new BigDecimal(v2);    
        return b1.compareTo(b2);    
    }   
	
	/**
	 * 返回较小数
	 * @autor:chenming
	 * @data:2014年9月15日
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static String returnMin(String v1,String v2){    
        BigDecimal b1 = new BigDecimal(v1);    
        BigDecimal b2 = new BigDecimal(v2);    
        return b1.min(b2).toString();    
    }   
	
	/**
	 * 返回较大数
	 * @autor:chenming
	 * @data:2014年9月15日
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static String returnMax(String v1,String v2){    
        BigDecimal b1 = new BigDecimal(v1);    
        BigDecimal b2 = new BigDecimal(v2);    
        return b1.max(b2).toString();    
    }   
}
