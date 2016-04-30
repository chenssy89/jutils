package com.JUtils.file;

import java.io.File;
import java.io.FileInputStream;

import com.JUtils.base.DateUtils;
import com.JUtils.base.RandomUtils;

/**
 * @desc:文件工具类
 * @Project:JUtils
 * @file:FileUtils.java
 * @Author:chenssy
 * @data:2014年8月7日
 */
public class FileUtils {
	
	/**
	 * @desc:判断指定路径是否存在，如果不存在，根据参数决定是否新建
	 * @autor:chenssy
	 * @data:2014年8月7日
	 *
	 * @param filePath
	 * 			指定的文件路径
	 * @param isNew
	 * 			true：新建、false：不新建
	 * @return 存在返回TRUE，不存在返回FALSE
	 */
	public static boolean isExist(String filePath,boolean isNew){
		File file = new File(filePath);
		if(!file.exists() && isNew){    
			return file.mkdirs();    //新建文件路径
		}
		return false;
	}
	
	/**
	 * 获取文件名，构建结构为 prefix + yyyyMMddHH24mmss + 10位随机数 + suffix + .type
	 * @autor:chenssy
	 * @data:2014年8月11日
	 *
	 * @param type
	 * 				文件类型
	 * @param prefix
	 * 				前缀
	 * @param suffix
	 * 				后缀
	 * @return
	 */
	public static String getFileName(String type,String prefix,String suffix){
		String date = DateUtils.getCurrentTime("yyyyMMddHH24mmss");   //当前时间
		String random = RandomUtils.generateNumberString(10);   //10位随机数
		
		//返回文件名  
		return prefix + date + random + suffix + "." + type;
	}
	
	/**
	 * 获取文件名，文件名构成:当前时间 + 10位随机数 + .type
	 * @autor:chenssy
	 * @data:2014年8月11日
	 *
	 * @param type
	 * 				文件类型
	 * @return
	 */
	public static String getFileName(String type){
		return getFileName(type, "", "");
	}
	
	/**
	 * 获取文件名，文件构成：当前时间 + 10位随机数
	 * @autor:chenssy
	 * @data:2014年8月11日
	 *
	 * @return
	 */
	public static String getFileName(){
		String date = DateUtils.getCurrentTime("yyyyMMddHH24mmss");   //当前时间
		String random = RandomUtils.generateNumberString(10);   //10位随机数
		
		//返回文件名  
		return date + random;
	}
	
	/**
	 * 获取指定文件的大小
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 *
	 * @author:陈明
	 * @data : 2016年4月30日 下午9:10:12
	 */
	@SuppressWarnings("resource")
	public static long getFileSize(File file) throws Exception {
		long size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		} else {
			file.createNewFile();
		}
		return size;
	}
}
