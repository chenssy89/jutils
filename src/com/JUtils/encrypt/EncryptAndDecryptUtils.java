package com.JUtils.encrypt;

/**
 * 加解密工具类<br>
 * 工具类包括：MD5加密、Base64加解密、DES加解密、AEC加解密、SHA加密、RAS加解密<br>
 *
 * @Author:chenssy
 * @date:2016年5月20日 下午4:44:51
 *
 */
public class EncryptAndDecryptUtils {
	
	/**
	 * MD5 加密
	 * 
	 * @author : chenssy
	 * @date : 2016年5月20日 下午4:54:23
	 *
	 * @param value
	 * 				待加密字符
	 * @return
	 */
	public static String encryptMD5(String value){
		return MD5Utils.encrypt(value,MD5Utils.MD5_KEY);
	}
	
	/**
	 * SHA加密
	 * 
	 * @author : chenssy
	 * @date : 2016年5月20日 下午4:59:42
	 *
	 * @param value		
	 * 					待加密字符
	 * @return	密文
	 */
	public static String encryptSHA(String value){
		return MD5Utils.encrypt(value,MD5Utils.SHA_KEY);
	}
	
	/**
	 * BASE64 加密
	 * 
	 * @author : chenssy
	 * @date : 2016年5月20日 下午5:16:12
	 *
	 * @param value
	 * 				待加密字符串
	 * @return
	 */
	public static String encryptBase64(String value){
		return Base64Utils.encrypt(value.getBytes());
	}
	
	/**
	 * BASE64 解密
	 * 
	 * @author : chenssy
	 * @date : 2016年5月20日 下午5:16:34
	 *
	 * @param value
	 * 				待解密字符串
	 * @return
	 */
	public static String decryptBase64(String value){
		String result = null;
		try {
			byte[] bytes = Base64Utils.decrypt(value);
			result = new String(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DES加密<br>
	 * 
	 * @author : chenssy
	 * @date : 2016年5月20日 下午5:39:46
	 *
	 * @param value
	 * 				待加密字符
	 * @param key	
	 * 				若key为空，则使用默认key
	 * @return
	 * 			加密成功返回密文，否则返回null
	 */
	public static String encryptDES(String value,String key){
		key = key == null ? DESUtils.KEY : key;
		String result = null;
		
		try {
			result = DESUtils.encrypt(value, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DES解密
	 * 
	 * @author : chenssy
	 * @date : 2016年5月20日 下午5:55:56
	 *
	 * @param value
	 * 				待解密字符
	 * @param key	
	 * 				若key为空，则使用默认key
	 * @return
	 * @return
	 */
	public static String decryptDES(String value,String key){
		key = key == null ? DESUtils.KEY : key;
		String result = null;
		
		try {
			result =  DESUtils.decrypt(value, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		System.out.println(EncryptAndDecryptUtils.encryptBase64("chenssy"));
		System.out.println(EncryptAndDecryptUtils.encryptSHA("chenssy"));
		System.out.println(encryptBase64("chenssy"));
		System.out.println(decryptBase64("Y2hlbnNzeQ=="));
		System.out.println(encryptDES("0123456789abcdefg", "1111111111"));
		System.out.println(decryptDES("THL4ltC1wBBrGuh6Quf6SmkKkUQ4ShqU", "1111111111"));
	}
}
