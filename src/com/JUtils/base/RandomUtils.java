package com.JUtils.base;

import java.util.Random;

/**
 * 随机数工具类
 * @Project:JUtils
 * @file:RandomUtils.java
 * @Authro:chenssy
 * @data:2014年8月11日
 */
public class RandomUtils {
	private static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	private static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	private static final String numberChar = "0123456789";
    
    /**
     * 获取定长的随机数，包含大小写、数字
     * @autor:chenssy
     * @data:2014年8月11日
     *
     * @param length
     * 				随机数长度
     * @return
     */
    public static String generateString(int length) { 
        StringBuffer sb = new StringBuffer(); 
        Random random = new Random(); 
        for (int i = 0; i < length; i++) { 
                sb.append(allChar.charAt(random.nextInt(allChar.length()))); 
        } 
        return sb.toString(); 
    } 
    
    /**
     * 获取定长的随机数，包含大小写字母
     * @autor:chenssy
     * @data:2014年8月11日
     *
     * @param length
     * 				随机数长度
     * @return
     */
    public static String generateMixString(int length) { 
        StringBuffer sb = new StringBuffer(); 
        Random random = new Random(); 
        for (int i = 0; i < length; i++) { 
                sb.append(letterChar.charAt(random.nextInt(letterChar.length()))); 
        } 
        return sb.toString(); 
    } 
    
    /**
     * 获取定长的随机数，只包含小写字母
     * @autor:chenssy
     * @data:2014年8月11日
     *
     * @param length	
     * 				随机数长度
     * @return
     */
    public static String generateLowerString(int length) { 
        return generateMixString(length).toLowerCase(); 
    } 
    
    /**
     * 获取定长的随机数，只包含大写字母
     * @autor:chenssy
     * @data:2014年8月11日
     *
     * @param length
     * 				随机数长度
     * @return
     */
    public static String generateUpperString(int length) { 
        return generateMixString(length).toUpperCase(); 
    } 
    
    /**
     * 获取定长的随机数，只包含数字
     * @autor:chenssy
     * @data:2014年8月11日
     *
     * @param length
     * 				随机数长度
     * @return
     */
    public static String generateNumberString(int length){
    	StringBuffer sb = new StringBuffer(); 
        Random random = new Random(); 
        for (int i = 0; i < length; i++) { 
                sb.append(numberChar.charAt(random.nextInt(numberChar.length()))); 
        } 
        return sb.toString(); 
    }
    
}
