package com.JUtils.math;

import java.util.Random;

/**
 * 随机数工具类
 * 
 * @Author:chenssy
 * @date:2014年8月11日
 */
public class RandomUtils {
	private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	private static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	private static final String NUMBER_CHAR = "0123456789";
    
    /**
     * 获取定长的随机数，包含大小写、数字
     * @autor:chenssy
     * @date:2014年8月11日
     *
     * @param length
     * 				随机数长度
     * @return
     */
    public static String generateString(int length) { 
        StringBuffer sb = new StringBuffer(); 
        Random random = new Random(); 
        for (int i = 0; i < length; i++) { 
                sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length()))); 
        } 
        return sb.toString(); 
    } 
    
    /**
     * 获取定长的随机数，包含大小写字母
     * @autor:chenssy
     * @date:2014年8月11日
     *
     * @param length
     * 				随机数长度
     * @return
     */
    public static String generateMixString(int length) { 
        StringBuffer sb = new StringBuffer(); 
        Random random = new Random(); 
        for (int i = 0; i < length; i++) { 
                sb.append(LETTER_CHAR.charAt(random.nextInt(LETTER_CHAR.length()))); 
        } 
        return sb.toString(); 
    } 
    
    /**
     * 获取定长的随机数，只包含小写字母
     * @autor:chenssy
     * @date:2014年8月11日
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
     * @date:2014年8月11日
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
     * @date:2014年8月11日
     *
     * @param length
     * 				随机数长度
     * @return
     */
    public static String generateNumberString(int length){
    	StringBuffer sb = new StringBuffer(); 
        Random random = new Random(); 
        for (int i = 0; i < length; i++) { 
                sb.append(NUMBER_CHAR.charAt(random.nextInt(NUMBER_CHAR.length()))); 
        } 
        return sb.toString(); 
    }
    
}
